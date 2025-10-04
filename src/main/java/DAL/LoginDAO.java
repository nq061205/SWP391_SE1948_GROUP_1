package DAL;

import Models.Department;
import Models.Employee;
import Models.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginDAO {

    private Connection connection;
    private String status = "ok";

    public LoginDAO() {
        try {
            DBContext db = new DBContext();
            connection = db.getConnection();
        } catch (Exception e) {
            status = "Connection failed: " + e.getMessage();
            e.printStackTrace();
        }
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

                    Department dept = new Department();
                    dept.setDepId(rs.getString("dep_id"));
                    emp.setDept(dept);

                    Role role = getRoleByRoleID(rs.getInt("role_id"));
                    emp.setRole(role);
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

                Department dept = new Department();
                dept.setDepId(rs.getString("dep_id"));
                emp.setDept(dept);

                Role role = getRoleByRoleID(rs.getInt("role_id"));
                emp.setRole(role);
                return emp;
            }
        }
        return null;
    }

    public Role getRoleByRoleID(int roleId) {
        Role role = new Role();
        String sql = "select * from Role where role_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, roleId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                role.setRoleId(rs.getInt("role_id"));
                role.setRoleName(rs.getString("role_name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return role;
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

    public static void main(String[] args) {

    }
}
