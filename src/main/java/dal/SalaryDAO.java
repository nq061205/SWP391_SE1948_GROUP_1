/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.*;

/**
 * @author Nguyen Dinh Quy HE190184
 */
public class SalaryDAO {

    private static final String SALARY_SELECT_SQL
            = "SELECT s.salary_id, s.emp_id, s.base_salary, s.allowance, \n"
            + "	   s.start_date, s.end_date, s.created_at, s.is_active,\n"
            + "       e.emp_code, e.fullname, e.email, e.password, e.gender, e.dob, e.phone, \n"
            + "       e.position_title, e.image, e.paid_leave_days, e.status, \n"
            + "       d.dep_id, d.dep_name, d.description AS dep_description, \n"
            + "       r.role_id, r.role_name \n"
            + "       FROM salary s \n"
            + "       JOIN employee e ON s.emp_id = e.emp_id \n"
            + "       LEFT JOIN department d ON e.dep_id = d.dep_id \n"
            + "	   LEFT JOIN role r ON e.role_id = r.role_id ";

    private Salary mapResultSetToPayroll(ResultSet rs) throws SQLException {
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

        Salary p = new Salary();
        p.setSalaryId(rs.getInt("salary_id"));
        p.setEmployee(emp);
        p.setBaseSalary(rs.getDouble("base_salary"));
        p.setAllowance(rs.getDouble("allowance"));
        p.setStartDate(rs.getDate("start_date"));
        p.setEndDate(rs.getDate("end_date"));
        p.setCreatedAt(rs.getTimestamp("created_at"));
        p.setActive(rs.getBoolean("is_active"));

        return p;
    }

    public Salary getSalaryDeatailByPayrollId(int salary_id) {
        String sql = SALARY_SELECT_SQL + " WHERE s.salary_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, salary_id);
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

    public Salary getSalaryDeatailByTime(int empId, int month, int year) {
        String sql = SALARY_SELECT_SQL
                + " WHERE s.emp_id = ? "
                + "   AND (? BETWEEN MONTH(s.start_date) AND IFNULL(MONTH(s.end_date), ?)) "
                + "   AND (? BETWEEN YEAR(s.start_date) AND IFNULL(YEAR(s.end_date), ?))";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, empId);
            stm.setInt(2, month);
            stm.setInt(3, month);
            stm.setInt(4, year);
            stm.setInt(5, year);

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
        SalaryDAO dao = new SalaryDAO();
        System.out.println(dao.getSalaryDeatailByTime(18, 10, 2025));
        
    }
}
