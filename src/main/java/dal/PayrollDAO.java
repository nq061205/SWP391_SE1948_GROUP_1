/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        Department dept = new Department();
        dept.setDepId(rs.getString("dep_id"));
        dept.setDepName(rs.getString("dep_name"));
        dept.setDescription(rs.getString("dep_description"));

        Role role = new Role();
        role.setRoleId(rs.getInt("role_id"));
        role.setRoleName(rs.getString("role_name"));

        Employee emp = new Employee();
        emp.setEmpId(rs.getInt("emp_id"));
        emp.setEmpCode(rs.getString("emp_code"));
        emp.setFullname(rs.getString("fullname"));
        emp.setEmail(rs.getString("email"));
        emp.setPassword(rs.getString("password"));
        emp.setGender(rs.getBoolean("gender"));
        emp.setDob(rs.getDate("dob"));
        emp.setPhone(rs.getString("phone"));
        emp.setPositionTitle(rs.getString("position_title"));
        emp.setImage(rs.getString("image"));
        emp.setPaidLeaveDays(rs.getInt("paid_leave_days"));
        emp.setStatus(rs.getBoolean("status"));
        emp.setDept(dept);
        emp.setRole(role);

        Payroll p = new Payroll();
        p.setPayrollId(rs.getInt("payroll_id"));
        p.setEmployee(emp);
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
                + "       and   p.month = ? AND p.year= ?\n";
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

    public static void main(String[] args) {
        PayrollDAO dao = new PayrollDAO();
        System.out.println(dao.getPayrollDeatailByTime(18,10,2025));

    }
}
