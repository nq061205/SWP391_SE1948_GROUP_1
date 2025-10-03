/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Department;
import Models.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Nguyen Dinh Quy HE190184
 */
public class UserDAO extends DBContext {

    
    private final DeptDAO dept = new DeptDAO();
    private Connection connection;

    public UserDAO() {
        try {
            connection = new DBContext().getConnection();
        } catch (Exception e) {
        }
    }

    public Employee getEmployeeByEmpCode(String emp_code) {
        Employee employee = new Employee();
        try {
            String sql = "select * from employee where emp_code = ?";
            Department deparment = dept.getDepartmentByEmpCode(emp_code);
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
                        deparment,
                        rs.getInt("role_id")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employee;
    }
    
    public Employee getEmployeeByEmpId(int emp_id) {
         Employee employee = new Employee();
        try {
            String sql = "select * from employee where emp_id = ?";
            Department deparment = dept.getDepartmentByEmpId(emp_id);
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, emp_id);
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
                        deparment,
                        rs.getInt("role_id")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employee;
    }
    
    
    
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        System.out.println(dao.getEmployeeByEmpId(1).toString());
    }
}
