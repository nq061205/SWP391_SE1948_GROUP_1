/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Department;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Nguyen Dinh Quy HE190184
 */
public class DeptDAO extends DBContext {

    public Department getDepartmentByDepartmentId(String depId) {
        Department department = null;
        String sql = "select * from department where dep_id =?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            
            stm.setString(1, depId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    department = new Department(
                            rs.getString("dep_id"),
                            rs.getString("dep_name"),
                            rs.getString("description")
                    );
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return department;
    }

    public Department getDepartmentByEmpCode(String emp_code) {
        Department department = null;
        String sql = "select d.* from department d join employee e on d.dep_id = e.dep_id where e.emp_code = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            
            stm.setString(1, emp_code);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    department = new Department(
                            rs.getString("dep_id"),
                            rs.getString("dep_name"),
                            rs.getString("description")
                    );
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return department;
    }

    public Department getDepartmentByEmpId(int emp_id) {
        Department department = null;
        String sql = "select d.* from department d join employee e on d.dep_id = e.dep_id where e.emp_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            
            stm.setInt(1, emp_id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    department = new Department(
                            rs.getString("dep_id"),
                            rs.getString("dep_name"),
                            rs.getString("description")
                    );
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return department;
    }

    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT dep_id, dep_name, description FROM department WHERE is_delete = FALSE ORDER BY dep_name";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            
            while (rs.next()) {
                Department department = new Department(
                        rs.getString("dep_id"),
                        rs.getString("dep_name"),
                        rs.getString("description")
                );
                departments.add(department);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return departments;
    }

    public List<Department> getAllDepartment() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT dep_id, dep_name, description FROM department";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            
            while (rs.next()) {
                Department department = new Department(
                        rs.getString("dep_id"),
                        rs.getString("dep_name"),
                        rs.getString("description")
                );
                departments.add(department);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return departments;
    }

    public void createDepartment(Department department) {
        String sql = "INSERT INTO department VALUES(?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, department.getDepId());
            ps.setString(2, department.getDepName());
            ps.setString(3, department.getDescription());
            ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(DeptDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateDepartment(Department department) {
        String sql = "UPDATE department SET dep_name=?,description=? where dep_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, department.getDepName());
            ps.setString(2, department.getDescription());
            ps.setString(3, department.getDepId());
            ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(DeptDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        DeptDAO dao = new DeptDAO();
        Department dep = new Department();
        dep.setDescription("Quản lí tài chín");
        dao.updateDepartment(dep);
        System.out.println(dao.getAllDepartment());
    }
}