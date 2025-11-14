package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ChartDAO extends DBContext {

    public Map<String, Integer> getEmployeeCountByRole() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT r.role_name, COUNT(e.emp_id) AS total "
                + "FROM employee e INNER JOIN role r ON e.role_id = r.role_id "
                + "GROUP BY r.role_id, r.role_name ORDER BY r.role_id";
        try (Connection conn = DBContext.getConnection(); PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                String role = rs.getString("role_name");
                int count = rs.getInt("total");
                result.put(role, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Integer> getEmployeeCountByDepartment() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT d.dep_name, COUNT(e.emp_id) AS total "
                + "FROM employee e INNER JOIN department d ON e.dep_id = d.dep_id "
                + "GROUP BY d.dep_id, d.dep_name ORDER BY d.dep_id";
        try (Connection conn = DBContext.getConnection(); PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                String dep = rs.getString("dep_name");
                int count = rs.getInt("total");
                result.put(dep, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getTotalEmployee() {
        String sql = "SELECT COUNT(*) FROM employee";
        try (Connection conn = DBContext.getConnection(); PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalDept() {
        String sql = "SELECT COUNT(*) FROM department";
        try (Connection conn = DBContext.getConnection(); PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Map<String, Double> getSalaryPaidByMonth(int year) {
        Map<String, Double> result = new LinkedHashMap<>();
        String sql = "SELECT month, year, SUM(regular_salary + ot_earning) AS total_salary "
                + "FROM payroll WHERE year = ? GROUP BY month, year ORDER BY year, month";
        try (Connection conn = DBContext.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, year);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int m = rs.getInt("month");
                    double totalSalary = rs.getDouble("total_salary");
                    // Key ví dụ: "Jan", "Feb", ...
                    String key = "";
                    switch (m) {
                        case 1:
                            key = "Jan";
                            break;
                        case 2:
                            key = "Feb";
                            break;
                        case 3:
                            key = "Mar";
                            break;
                        case 4:
                            key = "Apr";
                            break;
                        case 5:
                            key = "May";
                            break;
                        case 6:
                            key = "Jun";
                            break;
                        case 7:
                            key = "Jul";
                            break;
                        case 8:
                            key = "Aug";
                            break;
                        case 9:
                            key = "Sep";
                            break;
                        case 10:
                            key = "Oct";
                            break;
                        case 11:
                            key = "Nov";
                            break;
                        case 12:
                            key = "Dec";
                            break;
                    }
                    result.put(key, totalSalary);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
public List<Payroll> getTopAbsenceEmployees(int month, int year, int topN) {
    List<Payroll> list = new ArrayList<>();
    String sql = "SELECT p.payroll_id, p.total_work_day, p.total_ot_hours, p.month, p.year, " +
                 "e.emp_id, e.emp_code, e.fullname, d.dep_id, d.dep_name " +
                 "FROM payroll p " +
                 "JOIN employee e ON p.emp_id = e.emp_id " +
                 "JOIN department d ON e.dep_id = d.dep_id " +
                 "WHERE p.month = ? AND p.year = ? " +
                 "ORDER BY p.total_work_day ASC LIMIT ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement st = conn.prepareStatement(sql)) {
        st.setInt(1, month);
        st.setInt(2, year);
        st.setInt(3, topN);
        try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Payroll payroll = new Payroll();
                Employee emp = new Employee();
                Department dep = new Department();

                payroll.setPayrollId(rs.getInt("payroll_id"));
                payroll.setTotalWorkDay(rs.getDouble("total_work_day"));
                payroll.setTotalOTHours(rs.getDouble("total_ot_hours"));
                payroll.setMonth(rs.getInt("month"));
                payroll.setYear(rs.getInt("year"));

                emp.setEmpId(rs.getInt("emp_id"));
                emp.setEmpCode(rs.getString("emp_code"));
                emp.setFullname(rs.getString("fullname"));

                dep.setDepId(rs.getString("dep_id"));
                dep.setDepName(rs.getString("dep_name"));

                emp.setDept(dep);
                payroll.setEmployee(emp);

                list.add(payroll);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

public List<Payroll> getTopAttendanceEmployees(int month, int year, int topN) {
    List<Payroll> list = new ArrayList<>();
    String sql = "SELECT p.payroll_id, p.total_work_day, p.total_ot_hours, p.month, p.year, " +
                 "e.emp_id, e.emp_code, e.fullname, d.dep_id, d.dep_name " +
                 "FROM payroll p " +
                 "JOIN employee e ON p.emp_id = e.emp_id " +
                 "JOIN department d ON e.dep_id = d.dep_id " +
                 "WHERE p.month = ? AND p.year = ? " +
                 "ORDER BY p.total_work_day DESC, p.total_ot_hours DESC LIMIT ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement st = conn.prepareStatement(sql)) {
        st.setInt(1, month);
        st.setInt(2, year);
        st.setInt(3, topN);
        try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Payroll payroll = new Payroll();
                Employee emp = new Employee();
                Department dep = new Department();

                payroll.setPayrollId(rs.getInt("payroll_id"));
                payroll.setTotalWorkDay(rs.getDouble("total_work_day"));
                payroll.setTotalOTHours(rs.getDouble("total_ot_hours"));
                payroll.setMonth(rs.getInt("month"));
                payroll.setYear(rs.getInt("year"));

                emp.setEmpId(rs.getInt("emp_id"));
                emp.setEmpCode(rs.getString("emp_code"));
                emp.setFullname(rs.getString("fullname"));

                dep.setDepId(rs.getString("dep_id"));
                dep.setDepName(rs.getString("dep_name"));

                emp.setDept(dep);
                payroll.setEmployee(emp);

                list.add(payroll);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


    public static void main(String[] args) {
    ChartDAO dao = new ChartDAO();

    System.out.println("=== Test: getSalaryPaidByMonth (2025) ===");
    Map<String, Double> salaryByMonth = dao.getSalaryPaidByMonth(2025);
    for (Map.Entry<String, Double> entry : salaryByMonth.entrySet()) {
        System.out.println(entry.getKey() + ": " + String.format("%,.2f", entry.getValue()));
    }

    System.out.println("=== Test: getEmployeeCountByRole ===");
    Map<String, Integer> roleMap = dao.getEmployeeCountByRole();
    for (Map.Entry<String, Integer> entry : roleMap.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
    }

    System.out.println("=== Test: getEmployeeCountByDepartment ===");
    Map<String, Integer> deptMap = dao.getEmployeeCountByDepartment();
    for (Map.Entry<String, Integer> entry : deptMap.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
    }

    System.out.println("=== Test: getTotalEmployee ===");
    int totalEmp = dao.getTotalEmployee();
    System.out.println("Total Employee: " + totalEmp);

    System.out.println("=== Test: getTotalDept ===");
    int totalDept = dao.getTotalDept();
    System.out.println("Total Department: " + totalDept);
}


}
