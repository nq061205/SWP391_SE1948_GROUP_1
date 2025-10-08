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
    
    private Connection connection;
    private List<Role> roleList;
    public RoleDAO() {
        try {
            connection = new DBContext().getConnection();
        } catch (Exception e) {
        }
    }
    public List<Role> getAllRoles()  {
       roleList = new ArrayList<>();
       String sql = "select * from Role";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               Role role = new Role();
               role.setRoleId(rs.getInt("role_id"));
               role.setRoleName(rs.getString("role_name"));
               roleList.add(role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       return roleList;
    }

     public Role getRoleByEmpId(int emp_id) {
        Role role = new Role();
        try {
            String sql = "select * from role join employee on role.role_id = employee.role_id where emp_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, emp_id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                role = new Role(
                        rs.getInt("role_id"),
                        rs.getString("role_name")
                );
                return role;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
      public Role getRoleByRoleId(int roleId) {
        Role role = new Role();
        try {
            String sql = "select * from role where role_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, roleId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                role = new Role(
                        rs.getInt("role_id"),
                        rs.getString("role_name")
                );
                return role;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

     public static void main(String[] args) {
        RoleDAO dao = new RoleDAO();
        System.out.println(dao.getRoleByRoleId(1).toString());
    }
}
