package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.RolePermission;
import model.Role;
import model.Permission;

public class RolePermissionDAO {

    private final RoleDAO roleDAO = new RoleDAO();
    private final PermissionDAO permissionDAO = new PermissionDAO();

    /**
     */
    public List<RolePermission> getAllRolePermission() {
        List<RolePermission> list = new ArrayList<>();
        String sql = "SELECT role_id, permission_id FROM Role_Permission";

        List<Permission> permissions = permissionDAO.getAllPermission();

        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                int roleId = rs.getInt("role_id");
                int permissionId = rs.getInt("permission_id");

                RolePermission rp = new RolePermission();
                Role role = roleDAO.getRoleByRoleId(roleId);

                Permission permission = null;
                for (Permission p : permissions) {
                    if (p.getPermissionId() == permissionId) {
                        permission = p;
                        break;
                    }
                }

                rp.setRole(role);
                rp.setPermission(permission);
                list.add(rp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     */
    public void deleteAll() {
        String sql = "DELETE FROM Role_Permission";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     */
    public void insertRolePermission(int roleId, int permissionId) {
        String sql = "INSERT INTO Role_Permission (role_id, permission_id) VALUES (?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, roleId);
            stm.setInt(2, permissionId);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean hasPermission(int roleId, int permissionId) {
    String sql = "SELECT 1 FROM Role_Permission WHERE role_id = ? AND permission_id = ? LIMIT 1";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, roleId);
        ps.setInt(2, permissionId);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next(); 
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


    public static void main(String[] args) {
        RolePermissionDAO dao = new RolePermissionDAO();
        System.out.println(dao.hasPermission(5, 2));
    }
}
