package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Permission;

public class PermissionDAO {

    public List<Permission> getAllPermission() {
        List<Permission> list = new ArrayList<>();
        String sql = "SELECT * FROM Permission";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Permission p = new Permission();
                p.setPermissionId(rs.getInt("permission_id"));
                p.setPermissionName(rs.getString("permission_name"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        PermissionDAO dao = new PermissionDAO();
        for (Permission p : dao.getAllPermission()) {
            System.out.println(p);
        }
    }
}
