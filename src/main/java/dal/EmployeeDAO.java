/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Department;
import model.Employee;
import model.Role;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Candidate;

/**
 * @author Combined version of UserDAO and EmployeeDAO
 */
public class EmployeeDAO extends DBContext {

    private final DeptDAO deptDAO = new DeptDAO();
    private final RoleDAO roleDAO = new RoleDAO();
    private Connection connection;
    private String status = "ok";
    private List<Employee> empList;

    public EmployeeDAO() {
        try {
            this.connection = DBContext.getConnection();
        } catch (Exception e) {
            status = "Connection failed: " + e.getMessage();
            e.printStackTrace();
        }
    }

    // =========================================================
    // FROM UserDAO
    // =========================================================
    public Employee getEmployeeByEmpCode(String emp_code) {
        Employee employee = null;
        try {
            String sql = "SELECT * FROM Employee WHERE emp_code = ?";
            Department department = deptDAO.getDepartmentByEmpCode(emp_code);
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
                        department,
                        roleDAO.getRoleByEmpId(rs.getInt("emp_id"))
                );
                employee.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employee;
    }

    public Employee getEmployeeByEmpId(int emp_id) {
        String sql = "SELECT * FROM Employee WHERE emp_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, emp_id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Department department = deptDAO.getDepartmentByEmpId(emp_id);
                    Employee employee = new Employee(
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
                            department,
                            roleDAO.getRoleByEmpId(rs.getInt("emp_id"))
                    );
                    employee.setStatus(rs.getBoolean("status"));
                    return employee;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Employee getEmployeeByUsernamePassword(String username, String pass) {
        String sql = "SELECT * FROM Employee WHERE binary emp_code = ? AND binary password = ? and status = true";
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
                    Department dept = deptDAO.getDepartmentByEmpId(rs.getInt("emp_id"));
                    emp.setDept(dept);
                    Role role = roleDAO.getRoleByEmpId(rs.getInt("emp_id"));
                    emp.setRole(role);
                    emp.setStatus(rs.getBoolean("status"));
                    return emp;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Employee getEmployeeByEmail(String email) {
        String sql = "SELECT * FROM Employee WHERE email = ? && status = true";
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
                Department dept = deptDAO.getDepartmentByEmpId(rs.getInt("emp_id"));
                emp.setDept(dept);
                Role role = roleDAO.getRoleByEmpId(rs.getInt("emp_id"));
                emp.setRole(role);
                emp.setStatus(rs.getBoolean("status"));
                return emp;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean updatePassword(String empCode, String newPassword) {
        String sql = "UPDATE Employee SET password = ? WHERE emp_code = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, newPassword);
            stm.setString(2, empCode);
            int rows = stm.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // =========================================================
    // FROM EmployeeDAO
    // =========================================================
    public List<Employee> getAllEmployees() {
        empList = new ArrayList<>();
        String sql = "SELECT * FROM Employee";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {

            while (rs.next()) {
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

                Department dept = getDepartmentByDeptID(rs.getString("dep_id"));
                emp.setDept(dept);

                Role role = roleDAO.getRoleByRoleId(rs.getInt("role_id"));
                emp.setRole(role);
                emp.setStatus(rs.getBoolean("status"));
                empList.add(emp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empList;
    }

    public List<String> getAllPosition() {
        List<String> positionList = new ArrayList<>();
        String sql = "SELECT DISTINCT position_title FROM Employee WHERE position_title IS NOT NULL;";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                positionList.add(rs.getString("position_title"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return positionList;
    }

    public Department getDepartmentByDeptID(String deptID) {
        Department dept = new Department();
        String sql = "SELECT * FROM Department WHERE dep_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, deptID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dept.setDepId(rs.getString("dep_id"));
                dept.setDepName(rs.getString("dep_name"));
                dept.setDescription(rs.getString("description"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dept;
    }

    public Employee getEmployeeByEmployeeName(String empName) {
        Employee emp = new Employee();
        String sql = "select * from Employee where fullname =? ";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, empName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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

                Role role = roleDAO.getRoleByRoleId(rs.getInt("role_id"));
                emp.setRole(role);
                emp.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emp;
    }

    public void deleteEmployee(String empCode) {
        String sql = "DELETE FROM Employee where emp_code=?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, empCode);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateEmployee(Employee employee) {
        String sql = "UPDATE Employee SET fullname=?,email=?,password=?,gender=?,dob=?,phone=?,position_title=?,image=?,dependant_count=?,dep_id=?,role_id=?,status=?  WHERE emp_code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, employee.getFullname());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getPassword());
            ps.setBoolean(4, employee.isGender());
            ps.setDate(5, employee.getDob());
            ps.setString(6, employee.getPhone());
            ps.setString(7, employee.getPositionTitle());
            ps.setString(8, employee.getImage());
            ps.setInt(9, employee.getDependantCount());
            ps.setString(10, employee.getDept().getDepId());
            ps.setInt(11, employee.getRole().getRoleId());
            ps.setBoolean(12, employee.isStatus());
            ps.setString(13, employee.getEmpCode());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int updateEmployeeInformation(int emp_id, String fullname, boolean gender, Date dob, String phone, String image) {
        String sql = "UPDATE Employee SET fullname = ?, gender = ?, dob = ?, phone = ?, image = ? WHERE emp_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, fullname);
            stm.setBoolean(2, gender);
            stm.setDate(3, dob);
            stm.setString(4, phone);
            stm.setString(5, image);
            stm.setInt(6, emp_id);

            int rows = stm.executeUpdate();
            return rows;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public List<Employee> ManageEmployeeWithPaging(String searchkey, int page, int quantityOfPage,
            Boolean status, String[] deptIds, String[] roleIds,
            String sortBy, String order) {

        empList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Employee WHERE 1=1");
        if (searchkey != null && !searchkey.trim().isEmpty()) {
            sql.append(" AND (emp_code LIKE ? OR fullname LIKE ?)");
        }
        if (status != null) {
            sql.append(" AND status = ?");
        }
        if (deptIds != null && deptIds.length > 0) {
            sql.append(" AND dep_id IN (");
            for (int i = 0; i < deptIds.length; i++) {
                sql.append("?");
                if (i < deptIds.length - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");
        }
        if (roleIds != null && roleIds.length > 0) {
            sql.append(" AND role_id IN (");
            for (int i = 0; i < roleIds.length; i++) {
                sql.append("?");
                if (i < roleIds.length - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            sql.append(" ORDER BY ").append(sortBy);
            if (order != null && order.equalsIgnoreCase("desc")) {
                sql.append(" DESC ");
            } else {
                sql.append(" ASC ");
            }
        }

        sql.append(" LIMIT ? OFFSET ?");
        try (PreparedStatement ps = connection.prepareStatement(sql.toString());) {
            int index = 1;
            if (searchkey != null && !searchkey.trim().isEmpty()) {
                ps.setString(index++, "%" + searchkey + "%");
                ps.setString(index++, "%" + searchkey + "%");
            }
            if (status != null) {
                ps.setBoolean(index++, status);
            }
            if (deptIds != null) {
                for (String dep : deptIds) {
                    ps.setString(index++, dep);
                }
            }
            if (roleIds != null) {
                for (String r : roleIds) {
                    ps.setInt(index++, Integer.parseInt(r));
                }
            }
            ps.setInt(index++, quantityOfPage);
            ps.setInt(index++, (page - 1) * quantityOfPage);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                emp.setStatus(rs.getBoolean("status"));

                Department dept = getDepartmentByDeptID(rs.getString("dep_id"));
                emp.setDept(dept);
                Role role = roleDAO.getRoleByRoleId(rs.getInt("role_id"));
                emp.setRole(role);

                empList.add(emp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return empList;
    }

    public int countSearchRecordOfEmployee(String searchkey) {
        String sql = "Select count(*) from Employee WHERE emp_code LIKE ? OR fullname LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, "%" + searchkey + "%");
            ps.setString(2, "%" + searchkey + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public int countFilterRecordOfEmployee(Boolean status, String[] deptIds, String[] roleIds) {
        try {
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Employee WHERE 1=1");

            if (status != null) {
                sql.append(" AND status = ?");
            }

            if (deptIds != null && deptIds.length > 0) {
                sql.append(" AND dep_id IN (");
                for (int i = 0; i < deptIds.length; i++) {
                    sql.append("?");
                    if (i < deptIds.length - 1) {
                        sql.append(",");
                    }
                }
                sql.append(")");
            }

            if (roleIds != null && roleIds.length > 0) {
                sql.append(" AND role_id IN (");
                for (int i = 0; i < roleIds.length; i++) {
                    sql.append("?");
                    if (i < roleIds.length - 1) {
                        sql.append(",");
                    }
                }
                sql.append(")");
            }

            PreparedStatement ps = connection.prepareStatement(sql.toString());
            int index = 1;
            if (status != null) {
                ps.setBoolean(index++, status);
            }

            if (deptIds != null) {
                for (String depId : deptIds) {
                    ps.setString(index++, depId);
                }
            }
            if (roleIds != null) {
                for (String roleId : roleIds) {
                    ps.setInt(index++, Integer.parseInt(roleId));
                }
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public List<Employee> manageEmployeeForHR(String searchkey, int currentPage, int quantityOfPage, Boolean gender, String[] positionTitle, String ageRange, String sortBy, String order) {
        empList = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM Employee WHERE 1=1 ");
            if (searchkey != null && !searchkey.trim().isEmpty()) {
                sql.append(" AND (fullname LIKE ? OR emp_code LIKE ?) ");
            }
            if (gender != null) {
                sql.append(" AND gender = ? ");
            }
            if (positionTitle != null && positionTitle.length > 0) {
                sql.append(" AND position_title IN (");
                for (int i = 0; i < positionTitle.length; i++) {
                    sql.append("?");
                    if (i < positionTitle.length - 1) {
                        sql.append(", ");
                    }
                }
                sql.append(") ");
            }

            if (ageRange != null) {
                switch (ageRange) {
                    case "under25":
                        sql.append(" AND TIMESTAMPDIFF(YEAR, dob, CURDATE()) < 25 ");
                        break;
                    case "25to30":
                        sql.append(" AND TIMESTAMPDIFF(YEAR, dob, CURDATE()) BETWEEN 25 AND 30 ");
                        break;
                    case "31to40":
                        sql.append(" AND TIMESTAMPDIFF(YEAR, dob, CURDATE()) BETWEEN 31 AND 40 ");
                        break;
                    case "above40":
                        sql.append(" AND TIMESTAMPDIFF(YEAR, dob, CURDATE()) > 40 ");
                        break;
                }
            }
            if (sortBy != null) {
                sql.append(" order by ").append(sortBy);
                if (order != null && order.equalsIgnoreCase("desc")) {
                    sql.append(" DESC");
                } else {
                    sql.append(" ASC");
                }
            }
            sql.append(" LIMIT ? OFFSET ? ");
            PreparedStatement ps = connection.prepareStatement(sql.toString());
            int index = 1;

            if (searchkey != null && !searchkey.trim().isEmpty()) {
                ps.setString(index++, "%" + searchkey + "%");
                ps.setString(index++, "%" + searchkey + "%");
            }
            if (gender != null) {
                ps.setBoolean(index++, gender);
            }
            if (positionTitle != null) {
                for (String pt : positionTitle) {
                    ps.setString(index++, pt);
                }
            }

            ps.setInt(index++, quantityOfPage);
            ps.setInt(index++, (currentPage - 1) * quantityOfPage);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                emp.setStatus(rs.getBoolean("status"));
                Department dept = getDepartmentByDeptID(rs.getString("dep_id"));
                emp.setDept(dept);
                Role role = roleDAO.getRoleByRoleId(rs.getInt("role_id"));
                emp.setRole(role);
                empList.add(emp);
            }
        } catch (SQLException e) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return empList;
    }

    public int countAllRecordOfEmployee() {
        String sql = "Select count(*) from Employee";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public String generateUserName() {
        String sql = "SELECT MAX(emp_code) FROM Employee";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                String maxCode = rs.getString(1);
                if (maxCode == null) {
                    return "EMP001";
                }
                String numberPart = maxCode.substring(3);
                int number = Integer.parseInt(numberPart);
                number++;
                return String.format("EMP%03d", number);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "EMP001";
    }

    public String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public void createEmployee(String username, String password, String fullname, String email, boolean gender, String phone) {
        String sql = "INSERT INTO Employee(emp_code,password,fullname,email,gender,phone) values(?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, fullname);
            ps.setString(4, email);
            ps.setBoolean(5, gender);
            ps.setString(6, phone);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT * FROM Employee WHERE email = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int countFilterEmployee(Boolean gender, String[] positionTitle, String ageRange) {
        int count = 0;
        try {
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Employee WHERE 1=1");

            if (gender != null) {
                sql.append(" AND gender = ?");
            }

            if (positionTitle != null && positionTitle.length > 0) {
                sql.append(" AND position_title IN (");
                for (int i = 0; i < positionTitle.length; i++) {
                    sql.append("?");
                    if (i < positionTitle.length - 1) {
                        sql.append(", ");
                    }
                }
                sql.append(")");
            }

            if (ageRange != null && !ageRange.trim().isEmpty()) {
                switch (ageRange) {
                    case "under25":
                        sql.append(" AND TIMESTAMPDIFF(YEAR, dob, CURDATE()) < 25");
                        break;
                    case "25to30":
                        sql.append(" AND TIMESTAMPDIFF(YEAR, dob, CURDATE()) BETWEEN 25 AND 30");
                        break;
                    case "31to40":
                        sql.append(" AND TIMESTAMPDIFF(YEAR, dob, CURDATE()) BETWEEN 31 AND 40");
                        break;
                    case "above40":
                        sql.append(" AND TIMESTAMPDIFF(YEAR, dob, CURDATE()) > 40");
                        break;
                }
            }

            PreparedStatement ps = connection.prepareStatement(sql.toString());
            int index = 1;

            if (gender != null) {
                ps.setBoolean(index++, gender);
            }

            if (positionTitle != null && positionTitle.length > 0) {
                for (String pos : positionTitle) {
                    ps.setString(index++, pos);
                }
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

    }

}
