/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Department;
import Models.Employee;
import Models.Role;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Combined version of UserDAO and EmployeeDAO
 */
public class EmployeeDAO extends DBContext {

    private final DeptDAO deptDAO = new DeptDAO();
    private final RoleDAO roleDAO = new RoleDAO();
    private Connection connection;
    private String status = "ok";
    private List<Employee> empList;

    public EmployeeDAO() {
        try {
            connection = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Connection failed: " + e.getMessage();
            e.printStackTrace();
        }
    }

    // =========================================================
    // FROM UserDAO
    // =========================================================
    public Employee getEmployeeByEmpCode(String emp_code) {
        Employee employee = null;
        try {
            String sql = "SELECT * FROM Employee WHERE emp_code = ?";
            Department department = deptDAO.getDepartmentByEmpCode(emp_code);
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, emp_code);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("emp_code"),
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBoolean("gender"),
                        rs.getDate("dob"),
                        rs.getString("phone"),
                        rs.getString("position_title"),
                        rs.getString("image"),
                        rs.getInt("dependant_count"),
                        department,
                        roleDAO.getRoleByEmpId(rs.getInt("emp_id"))
                );
                employee.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employee;
    }

    public Employee getEmployeeByEmpId(int emp_id) {
        String sql = "SELECT * FROM Employee WHERE emp_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, emp_id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Department department = deptDAO.getDepartmentByEmpId(emp_id);
                    Employee employee = new Employee(
                            rs.getInt("emp_id"),
                            rs.getString("emp_code"),
                            rs.getString("fullname"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getBoolean("gender"),
                            rs.getDate("dob"),
                            rs.getString("phone"),
                            rs.getString("position_title"),
                            rs.getString("image"),
                            rs.getInt("dependant_count"),
                            department,
                            roleDAO.getRoleByEmpId(rs.getInt("emp_id"))
                    );
                    employee.setStatus(rs.getBoolean("status"));
                    return employee;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Employee getEmployeeByUsernamePassword(String username, String pass) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE emp_code = ? AND password = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, username);
            stm.setString(2, pass);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
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
                    emp.setDependantCount(rs.getInt("dependant_count"));
                    Department dept = deptDAO.getDepartmentByEmpId(rs.getInt("emp_id"));
                    emp.setDept(dept);
                    Role role = roleDAO.getRoleByEmpId(rs.getInt("emp_id"));
                    emp.setRole(role);
                    emp.setStatus(rs.getBoolean("status"));
                    return emp;
                }
            }
        }
        return null;
    }

    public Employee getEmployeeByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE email = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
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
                emp.setDependantCount(rs.getInt("dependant_count"));
                Department dept = deptDAO.getDepartmentByEmpId(rs.getInt("emp_id"));
                emp.setDept(dept);
                Role role = roleDAO.getRoleByEmpId(rs.getInt("emp_id"));
                emp.setRole(role);
                emp.setStatus(rs.getBoolean("status"));
                return emp;
            }
        }
        return null;
    }

    public boolean updatePassword(String empCode, String newPassword) throws SQLException {
        String sql = "UPDATE Employee SET password = ? WHERE emp_code = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, newPassword);
            stm.setString(2, empCode);
            int rows = stm.executeUpdate();
            return rows > 0;
        }
    }

    // =========================================================
    // FROM EmployeeDAO
    // =========================================================
    public List<Employee> getAllEmployees(int roleId) {
        empList = new ArrayList<>();
        String sql = "SELECT * FROM Employee WHERE role_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, roleId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setEmpCode(rs.getString("emp_code"));
                emp.setFullname(rs.getString("fullname"));
                emp.setEmail(rs.getString("email"));
                emp.setPassword(rs.getString("password"));
                emp.setGender(rs.getBoolean("gender"));
                emp.setDob(rs.getDate("dob"));
                emp.setPhone(rs.getString("phone"));
                emp.setPositionTitle(rs.getString("position_title"));
                emp.setImage(rs.getString("image"));
                emp.setDependantCount(rs.getInt("dependant_count"));

                Department dept = getDepartmentByDeptID(rs.getString("dep_id"));
                emp.setDept(dept);

                Role role = roleDAO.getRoleByRoleId(rs.getInt("role_id"));
                emp.setRole(role);
                empList.add(emp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empList;
    }

    public Department getDepartmentByDeptID(String deptID) {
        Department dept = new Department();
        String sql = "SELECT * FROM Department WHERE dep_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, deptID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dept.setDepId(rs.getString("dep_id"));
                dept.setDepName(rs.getString("dep_name"));
                dept.setDescription(rs.getString("description"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dept;
    }

    public void updateEmployeeInformation(int emp_id, String fullname, boolean gender, Date dob, String phone, String image) {
        String sql = "UPDATE Employee SET fullname = ?, gender = ?, dob = ?, phone = ?, image = ? WHERE email = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, fullname);
            stm.setBoolean(2, gender);
            stm.setDate(3, dob);
            stm.setString(4, phone);
            stm.setString(5, image);
            stm.setInt(6, emp_id);

            int rows = stm.executeUpdate();
            System.out.println("Updated rows: " + rows);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        System.out.println(dao.getEmployeeByEmpId(1).toString());
    }
}
