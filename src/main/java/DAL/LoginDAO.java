package DAL;

import Models.Department;
import Models.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public Employee getEmployee(String username, String pass) throws SQLException {
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
                    emp.setDepId(dept);

                    emp.setRoleId(rs.getInt("role_id"));
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
            emp.setDepId(dept);

            emp.setRoleId(rs.getInt("role_id"));
            return emp;
        }
    }
    return null;
}


    public static void main(String[] args) {
        LoginDAO ldao = new LoginDAO();
        try {
            Employee emp = ldao.getEmployee("EMP001", "123");
            if (emp != null) {
                System.out.println("Login success: " + emp.getFullname());
            } else {
                System.out.println("Sai username hoặc password!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
