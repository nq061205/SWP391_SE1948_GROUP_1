/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Role;
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
public class RoleDAO extends DBContext {

    public List<Role> getAllRoles() {
        List<Role> roleList = new ArrayList<>();
        String sql = "select * from Role";
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Role role = new Role();
                role.setRoleId(rs.getInt("role_id"));
                role.setRoleName(rs.getString("role_name"));
                roleList.add(role);
            }
        } catch (Exception ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roleList;
    }

    public Role getRoleByEmpId(int emp_id) {
        String sql = "select r.role_id, r.role_name from role r join employee e on r.role_id = e.role_id where e.emp_id = ?";
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement stm = conn.prepareStatement(sql)) {
            
            stm.setInt(1, emp_id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return new Role(
                            rs.getInt("role_id"),
                            rs.getString("role_name")
                    );
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Role getRoleByRoleId(int roleId) {
        String sql = "select * from role where role_id = ?";
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement stm = conn.prepareStatement(sql)) {
            
            stm.setInt(1, roleId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return new Role(
                            rs.getInt("role_id"),
                            rs.getString("role_name")
                    );
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        RoleDAO dao = new RoleDAO();
        for (Role allRole : dao.getAllRoles()) {
            System.out.println(allRole);
        }
    }
}