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
        String sql="select * from Department where dep_id =?";
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
     

}
