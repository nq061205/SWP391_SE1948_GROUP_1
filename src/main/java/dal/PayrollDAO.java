/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.Payroll;
import model.Department;
import model.Role;
import model.Employee;

/**
 * @author Nguyen Dinh Quy HE190184
 */
public class PayrollDAO {

    private static final String PAYROLL_SELECT_SQL
            = "SELECT p.payroll_id, p.emp_id, p.total_work_day, p.total_ot_hours, "
            + "p.regular_salary, p.ot_earning, p.insurance_base, p.SI, p.HI, p.UI, "
            + "p.tax_income, p.tax, p.month, p.year, p.is_paid, p.created_at, p.updated_at, "
            + "e.emp_code, e.fullname, e.email, e.password, e.gender, e.dob, e.phone, "
            + "e.position_title, e.image, e.paid_leave_days, e.status, "
            + "d.dep_id, d.dep_name, d.description AS dep_description, "
            + "r.role_id, r.role_name "
            + "FROM payroll p "
            + "JOIN employee e ON p.emp_id = e.emp_id "
            + "LEFT JOIN department d ON e.dep_id = d.dep_id "
            + "LEFT JOIN role r ON e.role_id = r.role_id ";

    private Payroll mapResultSetToPayroll(ResultSet rs) throws SQLException {
        EmployeeDAO empDAO = new EmployeeDAO();
        Employee e = empDAO.getEmployeeByEmpId(rs.getInt("emp_id"));

        Payroll p = new Payroll();
        p.setPayrollId(rs.getInt("payroll_id"));
        p.setEmployee(e);
        p.setTotalWorkDay(rs.getDouble("total_work_day"));
        p.setTotalOTHours(rs.getDouble("total_ot_hours"));
        p.setRegularSalary(rs.getDouble("regular_salary"));
        p.setOtEarning(rs.getDouble("ot_earning"));
        p.setInsuranceBase(rs.getDouble("insurance_base"));
        p.setSi(rs.getDouble("SI"));
        p.setHi(rs.getDouble("HI"));
        p.setUi(rs.getDouble("UI"));
        p.setTaxIncome(rs.getDouble("tax_income"));
        p.setTax(rs.getDouble("tax"));
        p.setMonth(rs.getInt("month"));
        p.setYear(rs.getInt("year"));
        p.setPaid(rs.getBoolean("is_paid"));
        p.setCreatedAt(rs.getTimestamp("created_at"));
        p.setUpdatedAt(rs.getTimestamp("updated_at"));

        return p;
    }

    public Payroll getPayrollDeatailByPayrollId(int payroll_id) {
        String sql = PAYROLL_SELECT_SQL + " WHERE p.payroll_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, payroll_id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPayroll(rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Payroll getPayrollDeatailByTime(int emp_id, int month, int year) {
        String sql = PAYROLL_SELECT_SQL + " WHERE p.emp_id = ?\n"
                + "       and   p.month = ? AND p.year= ? and p.is_paid = 1\n";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, emp_id);
            stm.setInt(2, month);
            stm.setInt(3, year);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPayroll(rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Payroll> getPayrollByEmpIds(List<Integer> empIds, int selectedMonth, int selectedYear) {
        List<Payroll> result = new ArrayList<>();
        if (empIds == null || empIds.isEmpty()) {
            return result;
        }

        StringBuilder sql = new StringBuilder(PAYROLL_SELECT_SQL + " WHERE p.month = ? AND p.year = ? AND p.emp_id IN (");

        for (int i = 0; i < empIds.size(); i++) {
            sql.append("?");
            if (i < empIds.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(") ORDER BY e.emp_code");

        try (Connection conn = DBContext.getConnection(); PreparedStatement st = conn.prepareStatement(sql.toString())) {
            st.setInt(1, selectedMonth);
            st.setInt(2, selectedYear);
            for (int i = 0; i < empIds.size(); i++) {
                st.setInt(3 + i, empIds.get(i));
            }
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    result.add(mapResultSetToPayroll(rs));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
     public boolean insertPayroll(Payroll payroll) {
        String sql = "INSERT INTO payroll (emp_id, total_work_day, total_ot_hours, regular_salary, "
                + "ot_earning, insurance_base, SI, HI, UI, tax_income, tax, month, year, is_paid) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, payroll.getEmployee().getEmpId());
            ps.setDouble(2, payroll.getTotalWorkDay());
            ps.setDouble(3, payroll.getTotalOTHours());
            ps.setDouble(4, payroll.getRegularSalary());
            ps.setDouble(5, payroll.getOtEarning());
            ps.setDouble(6, payroll.getInsuranceBase());
            ps.setDouble(7, payroll.getSi());
            ps.setDouble(8, payroll.getHi());
            ps.setDouble(9, payroll.getUi());
            ps.setDouble(10, payroll.getTaxIncome());
            ps.setDouble(11, payroll.getTax());
            ps.setInt(12, payroll.getMonth());
            ps.setInt(13, payroll.getYear());
            ps.setBoolean(14, payroll.isPaid());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updatePayroll(Payroll payroll) {
        String sql = "UPDATE payroll SET total_work_day = ?, total_ot_hours = ?, "
                + "regular_salary = ?, ot_earning = ?, insurance_base = ?, SI = ?, HI = ?, UI = ?, "
                + "tax_income = ?, tax = ?, is_paid = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE payroll_id = ?";
        
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDouble(1, payroll.getTotalWorkDay());
            ps.setDouble(2, payroll.getTotalOTHours());
            ps.setDouble(3, payroll.getRegularSalary());
            ps.setDouble(4, payroll.getOtEarning());
            ps.setDouble(5, payroll.getInsuranceBase());
            ps.setDouble(6, payroll.getSi());
            ps.setDouble(7, payroll.getHi());
            ps.setDouble(8, payroll.getUi());
            ps.setDouble(9, payroll.getTaxIncome());
            ps.setDouble(10, payroll.getTax());
            ps.setBoolean(11, payroll.isPaid());
            ps.setInt(12, payroll.getPayrollId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean saveOrUpdatePayroll(Payroll payroll) {
        Payroll existing = getPayrollDeatailByTime(
            payroll.getEmployee().getEmpId(), 
            payroll.getMonth(), 
            payroll.getYear()
        );
        
        if (existing != null) {
            payroll.setPayrollId(existing.getPayrollId());
            return updatePayroll(payroll);
        } else {
            return insertPayroll(payroll);
        }
    }
    
    public boolean markAsPaid(int payrollId) {
        String sql = "UPDATE payroll SET is_paid = 1, updated_at = CURRENT_TIMESTAMP WHERE payroll_id = ?";
        
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, payrollId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        PayrollDAO dao = new PayrollDAO();
        EmployeeDAO empDAO = new EmployeeDAO();
        System.out.println(dao.getPayrollDeatailByTime(1, 10, 2025));
    }
}
