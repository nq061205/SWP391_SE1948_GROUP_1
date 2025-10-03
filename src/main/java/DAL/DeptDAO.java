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

/**
 * @author Nguyen Dinh Quy HE190184
 */
public class DeptDAO extends DBContext {
    
    private Connection connection;

    public DeptDAO() {
        try {
            connection = new DBContext().getConnection();
        } catch (Exception e) {
        }
    }

     public Department getDepartmentByEmpCode(String emp_code) {
        Department department = new Department();
        try {
            String sql = "select * from department join employee on department.dep_id = employee.dep_id where emp_code = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, emp_code);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                department = new Department(
                        rs.getString("dep_id"),
                        rs.getString("dep_name"),
                        rs.getString("description")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return department;
    }
     
     public Department getDepartmentByEmpId(int emp_id) {
        Department department = new Department();
        try {
            String sql = "select * from department join employee on department.dep_id = employee.dep_id where emp_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, emp_id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                department = new Department(
                        rs.getString("dep_id"),
                        rs.getString("dep_name"),
                        rs.getString("description")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return department;
    }
}
