/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Department;
import Models.Employee;
import Models.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Do Quang Huy_HE191197
 */
public class EmployeeDAO {

    private Connection connection;
    private String status = "ok";
    private List<Employee> empList;
    LoginDAO logindao = new LoginDAO();

    public EmployeeDAO() {
        try {
            DBContext db = new DBContext();
            connection = db.getConnection();
        } catch (Exception e) {
            status = "Connection failed: " + e.getMessage();
            e.printStackTrace();
        }
    }

    public List<Employee> getAllEmployees(int roleId) {
        empList = new ArrayList<>();
        String sql = "select * from Employee where role_id = ?";
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

                Department dept = getDepartmentByDeptID(rs.getString("dep_id"));
                emp.setDept(dept);

                Role role = logindao.getRoleByRoleID(rs.getInt("role_id"));
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
        String sql = "select * from Department where dep_id =?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, deptID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dept.setDepId(rs.getString("dep_id"));
                dept.setDepName(rs.getString("dep_name"));
                dept.setDescription(rs.getString("description"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dept;
    }

    public Employee getEmployeeByEmployeeName(String empName) {
        Employee emp = new Employee();
        String sql = "select * from Employee where fullname =? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, empName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emp.setEmpCode(rs.getString("emp_code"));
                emp.setFullname(rs.getString("fullname"));
                emp.setEmail(rs.getString("email"));
                emp.setPassword(rs.getString("password"));
                emp.setGender(rs.getBoolean("gender"));
                emp.setDob(rs.getDate("dob"));
                emp.setPhone(rs.getString("phone"));
                emp.setPositionTitle(rs.getString("position_title"));

                Department dept = getDepartmentByDeptID(rs.getString("dep_id"));
                emp.setDept(dept);

                Role role = logindao.getRoleByRoleID(rs.getInt("role_id"));
                emp.setRole(role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emp;
    }

    public void createEmployee(String email, String empCode) {
        String sql = "INSERT INTO Employee(email,emp_code) values(?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, empCode);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteEmployee(String empCode) {
        String sql = "DELETE FROM Employee where emp_code=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, empCode);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateEmployee(Employee employee) {
        String sql = "UPDATE Employee SET emp_code=?,fullname=?,email=?,password=?,gender=?,dob=?,phone=?,position_title=?,image=?  WHERE emp_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, employee.getEmpCode());
            ps.setString(2, employee.getFullname());
            ps.setString(3, employee.getEmail());
            ps.setString(4,employee.getPassword());
            ps.setBoolean(5, employee.isGender());
            ps.setDate(6, employee.getDob());
            ps.setString(7, employee.getPhone());
            ps.setString(8, employee.getPositionTitle());
            ps.setString(9, employee.getImage());
            ps.setInt(10, employee.getEmpId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
