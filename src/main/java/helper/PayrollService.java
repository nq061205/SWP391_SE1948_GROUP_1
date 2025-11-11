package helper;

import dal.*;
import model.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class PayrollService {
    
    private PayrollDAO payrollDAO = new PayrollDAO();
    private SalaryDAO salaryDAO = new SalaryDAO();
    private DependantDAO dependantDAO = new DependantDAO();
    private DailyAttendanceDAO dailyAttendanceDAO = new DailyAttendanceDAO();
    
    public int getStandardWorkDays(int month, int year) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();
        
        int workDays = 0;
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (date.getDayOfWeek().getValue() >= 1 && date.getDayOfWeek().getValue() <= 5) {
                workDays++;
            }
        }
        return workDays;
    }
    
   public Payroll calculateAndSavePayroll(int empId, int month, int year) {    
    try {
        Map<String, Double> workData = dailyAttendanceDAO.getTotalWorkData(empId, month, year);
        
        double totalWorkDay = workData.get("totalWorkDay");
        double totalOTHours = workData.get("totalOTHours");
        
        Salary salary = salaryDAO.getActiveSalaryByEmpId(empId);
        if (salary == null) {
            throw new RuntimeException("Cannot find salary for Employee who has ID = " + empId);
        }
        
        double baseSalary = salary.getBaseSalary();
        double allowance = salary.getAllowance();
        int standardWorkDays = getStandardWorkDays(month, year);
        
        double regularSalary = (baseSalary / standardWorkDays) * totalWorkDay;
        double otEarning = (baseSalary / (standardWorkDays * 8)) * totalOTHours * 1.5;
        double totalGross = regularSalary + otEarning + allowance;
        
        double insuranceBase = regularSalary + allowance;
        double si = insuranceBase * ConfigReader.getDouble("SI_EMPLOYEE");
        double hi = insuranceBase * ConfigReader.getDouble("HI_EMPLOYEE");
        double ui = insuranceBase * ConfigReader.getDouble("UI_EMPLOYEE");
        double totalInsurance = si + hi + ui;
        
        int nod = dependantDAO.countDependantsByEmpId(empId);
        
        double personalDeduction = ConfigReader.getDouble("PERSONAL_DEDUCTION");
        double dependentDeduction = ConfigReader.getDouble("DEPENDENT_DEDUCTION");
        double taxableIncome = regularSalary + (otEarning/1.5) + allowance 
                             - personalDeduction 
                             - (nod * dependentDeduction)
                             - totalInsurance;
        if (taxableIncome < 0) {
            taxableIncome = 0;
        }
        double tax = calculateProgressiveTax(taxableIncome);
        
        double netSalary = totalGross - totalInsurance - tax;
        
        Employee emp = new Employee();
        emp.setEmpId(empId);
        
        Payroll payroll = new Payroll();
        payroll.setEmployee(emp);
        payroll.setTotalWorkDay(totalWorkDay);
        payroll.setTotalOTHours(totalOTHours);
        payroll.setRegularSalary(regularSalary);
        payroll.setOtEarning(otEarning);
        payroll.setInsuranceBase(insuranceBase);
        payroll.setSi(si);
        payroll.setHi(hi);
        payroll.setUi(ui);
        payroll.setTaxIncome(taxableIncome);
        payroll.setTax(tax);
        payroll.setMonth(month);
        payroll.setYear(year);
        payroll.setPaid(false);
        
        boolean saved = payrollDAO.saveOrUpdatePayroll(payroll);
        
        if (saved) {
            System.out.println("✅ SUCCESS: Payroll saved for empId: " + empId);
            return payroll;
        } else {
            System.err.println("❌ FAILED: Could not save payroll for empId: " + empId);
            return null;
        }
        
    } catch (Exception e) {
        System.err.println("❌ EXCEPTION for empId " + empId + ": " + e.getMessage());
        e.printStackTrace();
        throw e;
    }
}

    
    public void calculatePayrollForMonth(int month, int year, List<Integer> empIds) {        
        for (Integer empId : empIds) {
            try {
                calculateAndSavePayroll(empId, month, year);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private double calculateProgressiveTax(double taxableIncome) {
        if (taxableIncome <= 0) {
            return 0;
        }  
        double tax = 0;
        double rate1 = ConfigReader.getDouble("TAX_RATE_1");     // 5%
        double rate2 = ConfigReader.getDouble("TAX_RATE_2");     // 10%
        double rate3 = ConfigReader.getDouble("TAX_RATE_3");     // 15%
        double rate4 = ConfigReader.getDouble("TAX_RATE_4");     // 20%
        double rate5 = ConfigReader.getDouble("TAX_RATE_5");     // 25%
        double rate6 = ConfigReader.getDouble("TAX_RATE_6");     // 30%
        double rate7 = ConfigReader.getDouble("TAX_RATE_7");     // 35%
        
        // Bậc 1: đến 5 triệu - 5%
        if (taxableIncome <= 5000000) {
            tax = taxableIncome * rate1;
        }
        // Bậc 2: 5-10 triệu - 10%
        else if (taxableIncome <= 10000000) {
            tax = 5000000 * rate1
                + (taxableIncome - 5000000) * rate2;
        }
        // Bậc 3: 10-18 triệu - 15%
        else if (taxableIncome <= 18000000) {
            tax = 5000000 * rate1
                + 5000000 * rate2
                + (taxableIncome - 10000000) * rate3;
        }
        // Bậc 4: 18-32 triệu - 20%
        else if (taxableIncome <= 32000000) {
            tax = 5000000 * rate1
                + 5000000 * rate2
                + 8000000 * rate3
                + (taxableIncome - 18000000) * rate4;
        }
        // Bậc 5: 32-52 triệu - 25%
        else if (taxableIncome <= 52000000) {
            tax = 5000000 * rate1
                + 5000000 * rate2
                + 8000000 * rate3
                + 14000000 * rate4
                + (taxableIncome - 32000000) * rate5;
        }
        // Bậc 6: 52-80 triệu - 30%
        else if (taxableIncome <= 80000000) {
            tax = 5000000 * rate1
                + 5000000 * rate2
                + 8000000 * rate3
                + 14000000 * rate4
                + 20000000 * rate5
                + (taxableIncome - 52000000) * rate6;
        }
        // Bậc 7: trên 80 triệu - 35%
        else {
            tax = 5000000 * rate1
                + 5000000 * rate2
                + 8000000 * rate3
                + 14000000 * rate4
                + 20000000 * rate5
                + 28000000 * rate6
                + (taxableIncome - 80000000) * rate7;
        }
        return tax;
    }
    
    public Map<String, Object> getPayrollDetails(int empId, int month, int year) {
        Payroll payroll = payrollDAO.getPayrollDeatailByTime(empId, month, year);
        Map<String, Object> details = new HashMap<>();
        
        if (payroll != null) {
            details.put("empId", payroll.getEmployee().getEmpId());
            details.put("totalWorkDay", payroll.getTotalWorkDay());
            details.put("totalOTHours", payroll.getTotalOTHours());
            details.put("regularSalary", payroll.getRegularSalary());
            details.put("otEarning", payroll.getOtEarning());
            details.put("allowance", payroll.getInsuranceBase() - payroll.getRegularSalary());
            details.put("totalGross", payroll.getRegularSalary() + payroll.getOtEarning() + 
                                     (payroll.getInsuranceBase() - payroll.getRegularSalary()));
            details.put("si", payroll.getSi());
            details.put("hi", payroll.getHi());
            details.put("ui", payroll.getUi());
            details.put("totalInsurance", payroll.getSi() + payroll.getHi() + payroll.getUi());
            details.put("taxIncome", payroll.getTaxIncome());
            details.put("tax", payroll.getTax());
            details.put("netSalary", payroll.getRegularSalary() + payroll.getOtEarning() + 
                                     (payroll.getInsuranceBase() - payroll.getRegularSalary()) - 
                                     (payroll.getSi() + payroll.getHi() + payroll.getUi()) - 
                                     payroll.getTax());
            details.put("isPaid", payroll.isPaid());
        }
        
        return details;
    }
    
    public boolean markPayrollAsPaid(int payrollId) {
        return payrollDAO.markAsPaid(payrollId);
    }
}
