/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import helper.PasswordEncryption;
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
import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Combined version of UserDAO and EmployeeDAO
 */
public class EmployeeDAO extends DBContext {

    private final DeptDAO deptDAO = new DeptDAO();
    private final RoleDAO roleDAO = new RoleDAO();

    private final String BASE_SELECT_SQL = "SELECT e.*, "
            + "d.dep_name, d.description AS dep_description, "
            + "r.role_name "
            + "FROM Employee e "
            + "LEFT JOIN Department d ON e.dep_id = d.dep_id "
            + "LEFT JOIN Role r ON e.role_id = r.role_id ";

    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Department dept = new Department();
        dept.setDepId(rs.getString("dep_id"));
        dept.setDepName(rs.getString("dep_name"));
        dept.setDescription(rs.getString("dep_description"));

        Role role = new Role();
        role.setRoleId(rs.getInt("role_id"));
        role.setRoleName(rs.getString("role_name"));

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
        emp.setPaidLeaveDays(rs.getInt("paid_leave_days"));
        emp.setStatus(rs.getBoolean("status"));
        emp.setDept(dept);
        emp.setRole(role);
        return emp;
    }

    public Employee getEmployeeByEmpCode(String emp_code) {
        Employee employee = null;
        String sql = BASE_SELECT_SQL + " WHERE e.emp_code = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, emp_code);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    employee = mapResultSetToEmployee(rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return employee;
    }

    public Employee getEmployeeByEmpId(int emp_id) {
        String sql = BASE_SELECT_SQL + " WHERE e.emp_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, emp_id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEmployee(rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Employee getEmployeeByUsernamePassword(String username, String plainPassword) {
        String sql = BASE_SELECT_SQL + " WHERE e.emp_code = ? AND e.status = true";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, username);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");

                    if (PasswordEncryption.checkPassword(plainPassword, hashedPassword)) {
                        return mapResultSetToEmployee(rs);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Employee getEmployeeByEmail(String email) {
        String sql = BASE_SELECT_SQL + " WHERE e.email = ? AND e.status = true";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEmployee(rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean updatePassword(String empCode, String newPassword) {
        String sql = "UPDATE Employee SET password = ? WHERE emp_code = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, newPassword);
            stm.setString(2, empCode);
            int rows = stm.executeUpdate();
            return rows > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> empList = new ArrayList<>();
        String sql = BASE_SELECT_SQL;
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {

            while (rs.next()) {
                empList.add(mapResultSetToEmployee(rs));
            }
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empList;
    }

    public List<String> getAllPosition() {
        List<String> positionList = new ArrayList<>();
        String sql = "SELECT DISTINCT position_title FROM Employee WHERE position_title IS NOT NULL;";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {

            while (rs.next()) {
                positionList.add(rs.getString("position_title"));
            }
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return positionList;
    }

    public Department getDepartmentByDeptID(String deptID) {
        Department dept = new Department();
        String sql = "SELECT * FROM Department WHERE dep_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, deptID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dept.setDepId(rs.getString("dep_id"));
                    dept.setDepName(rs.getString("dep_name"));
                    dept.setDescription(rs.getString("description"));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dept;
    }

    public Employee getEmployeeByEmployeeName(String empName) {
        Employee emp = null;
        String sql = BASE_SELECT_SQL + " WHERE e.fullname = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, empName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emp = mapResultSetToEmployee(rs);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emp;
    }

    public int countSearchAndFilterEmployeeByDepartment(String depId, String searchKey, Boolean gender, String[] positionTitles) {
        int count = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Employee WHERE dep_id = ?");

        if (searchKey != null && !searchKey.isEmpty()) {
            sql.append(" AND (fullname LIKE ? OR email LIKE ? OR phone LIKE ?)");
        }
        if (gender != null) {
            sql.append(" AND gender = ?");
        }
        if (positionTitles != null && positionTitles.length > 0) {
            sql.append(" AND position_title IN (");
            for (int i = 0; i < positionTitles.length; i++) {
                sql.append("?");
                if (i < positionTitles.length - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            ps.setString(index++, depId);

            if (searchKey != null && !searchKey.isEmpty()) {
                String key = "%" + searchKey + "%";
                ps.setString(index++, key);
                ps.setString(index++, key);
                ps.setString(index++, key);
            }
            if (gender != null) {
                ps.setBoolean(index++, gender);
            }
            if (positionTitles != null && positionTitles.length > 0) {
                for (String pos : positionTitles) {
                    ps.setString(index++, pos);
                }
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public List<Employee> getEmployeesByDepartment(
            String depId, String searchKey, Boolean gender, String[] positionTitles,
            int currentPage, int quantityOfPage) {

        List<Employee> empList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Employee WHERE dep_id = ?");

        if (searchKey != null && !searchKey.isEmpty()) {
            sql.append(" AND (fullname LIKE ? OR email LIKE ? OR phone LIKE ?)");
        }
        if (gender != null) {
            sql.append(" AND gender = ?");
        }
        if (positionTitles != null && positionTitles.length > 0) {
            sql.append(" AND position_title IN (");
            for (int i = 0; i < positionTitles.length; i++) {
                sql.append("?");
                if (i < positionTitles.length - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");
        }

        // Phân trang
        sql.append(" ORDER BY emp_id ASC LIMIT ? OFFSET ?");

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            ps.setString(index++, depId);

            if (searchKey != null && !searchKey.isEmpty()) {
                String key = "%" + searchKey + "%";
                ps.setString(index++, key);
                ps.setString(index++, key);
                ps.setString(index++, key);
            }
            if (gender != null) {
                ps.setBoolean(index++, gender);
            }
            if (positionTitles != null && positionTitles.length > 0) {
                for (String pos : positionTitles) {
                    ps.setString(index++, pos);
                }
            }

            ps.setInt(index++, quantityOfPage); // LIMIT
            ps.setInt(index, (currentPage - 1) * quantityOfPage); // OFFSET

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setEmpId(rs.getInt("emp_id"));
                emp.setEmpCode(rs.getString("emp_code"));
                emp.setFullname(rs.getString("fullname"));
                emp.setEmail(rs.getString("email"));
                emp.setPhone(rs.getString("phone"));
                emp.setGender(rs.getBoolean("gender"));
                emp.setDob(rs.getDate("dob"));
                emp.setPositionTitle(rs.getString("position_title"));
                emp.setImage(rs.getString("image"));
                emp.setPaidLeaveDays(rs.getInt("paid_leave_days"));
                emp.setStatus(rs.getBoolean("status"));

                // Dept
                Department dept = new Department();
                dept.setDepId(rs.getString("dep_id"));
                emp.setDept(dept);

                // Role
                Role role = new Role();
                role.setRoleId(rs.getInt("role_id"));
                emp.setRole(role);

                empList.add(emp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return empList;
    }

    public List<String> getPositionByDepartment(String depId) {
        List<String> positionList = new ArrayList<>();
        String sql = "SELECT DISTINCT position_title FROM Employee WHERE dep_id = ? AND position_title IS NOT NULL";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, depId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                positionList.add(rs.getString("position_title"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return positionList;
    }

    public void deleteEmployee(String empCode) {
        String sql = "DELETE FROM Employee where emp_code=?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, empCode);
            ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateEmployee(Employee employee) {
        String sql = "UPDATE Employee SET fullname=?,email=?,password=?,gender=?,dob=?,phone=?,position_title=?,image=?,paid_leave_days=?,dep_id=?,role_id=?,status=?  WHERE emp_code = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, employee.getFullname());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getPassword());
            ps.setBoolean(4, employee.isGender());
            ps.setDate(5, employee.getDob());
            ps.setString(6, employee.getPhone());
            ps.setString(7, employee.getPositionTitle());
            ps.setString(8, employee.getImage());
            ps.setInt(9, employee.getPaidLeaveDays());
            ps.setString(10, employee.getDept().getDepId());
            ps.setInt(11, employee.getRole().getRoleId());
            ps.setBoolean(12, employee.isStatus());
            ps.setString(13, employee.getEmpCode());
            ps.executeUpdate();
        } catch (Exception ex) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public List<Employee> ManageEmployeeWithPaging(String searchkey, int page, int quantityOfPage,
            Boolean status, String[] deptIds, String[] roleIds,
            String sortBy, String order) {

        List<Employee> empList = new ArrayList<>();
        StringBuilder sql = new StringBuilder(BASE_SELECT_SQL + " WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (searchkey != null && !searchkey.trim().isEmpty()) {
            sql.append(" AND (e.emp_code LIKE ? OR e.fullname LIKE ?)");
            params.add("%" + searchkey + "%");
            params.add("%" + searchkey + "%");
        }
        if (status != null) {
            sql.append(" AND e.status = ?");
            params.add(status);
        }
        if (deptIds != null && deptIds.length > 0) {
            sql.append(" AND e.dep_id IN (");
            for (int i = 0; i < deptIds.length; i++) {
                sql.append("?");
                if (i < deptIds.length - 1) {
                    sql.append(",");
                }
                params.add(deptIds[i]);
            }
            sql.append(")");
        }
        if (roleIds != null && roleIds.length > 0) {
            sql.append(" AND e.role_id IN (");
            for (int i = 0; i < roleIds.length; i++) {
                sql.append("?");
                if (i < roleIds.length - 1) {
                    sql.append(",");
                }
                params.add(Integer.parseInt(roleIds[i]));
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
        params.add(quantityOfPage);
        params.add((page - 1) * quantityOfPage);

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString());) {

            int index = 1;
            for (Object param : params) {
                ps.setObject(index++, param);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    empList.add(mapResultSetToEmployee(rs));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return empList;
    }

    public int countSearchAndFilterAccount(String searchKey, Boolean status, String[] deptIds, String[] roleIds) {
        try (Connection conn = DBContext.getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Employee WHERE 1=1");

            List<Object> params = new ArrayList<>();

            if (searchKey != null && !searchKey.trim().isEmpty()) {
                sql.append(" AND (emp_code LIKE ? OR fullname LIKE ?)");
                params.add("%" + searchKey + "%");
                params.add("%" + searchKey + "%");
            }

            if (status != null) {
                sql.append(" AND status = ?");
                params.add(status);
            }

            if (deptIds != null && deptIds.length > 0) {
                sql.append(" AND dep_id IN (");
                for (int i = 0; i < deptIds.length; i++) {
                    sql.append("?");
                    if (i < deptIds.length - 1) {
                        sql.append(",");
                    }
                    params.add(deptIds[i]);
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
                    params.add(Integer.parseInt(roleIds[i]));
                }
                sql.append(")");
            }

            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                int index = 1;
                for (Object param : params) {
                    ps.setObject(index++, param);
                }

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int countSearchAndFilterEmployee(String searchKey, Boolean gender, String[] posTitle, String ageRange) {
        try (Connection conn = DBContext.getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Employee WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (searchKey != null && !searchKey.trim().isEmpty()) {
                sql.append(" AND (emp_code LIKE ? OR fullname LIKE ?)");
                params.add("%" + searchKey + "%");
                params.add("%" + searchKey + "%");
            }

            if (gender != null) {
                sql.append(" AND gender = ?");
                params.add(gender);
            }

            if (posTitle != null && posTitle.length > 0) {
                sql.append(" AND position_title IN (");
                for (int i = 0; i < posTitle.length; i++) {
                    sql.append("?");
                    if (i < posTitle.length - 1) {
                        sql.append(",");
                    }
                    params.add(posTitle[i]);
                }
                sql.append(")");
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

            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                int index = 1;
                for (Object param : params) {
                    ps.setObject(index++, param);
                }

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public List<Employee> manageEmployeeForHR(String searchkey, int currentPage, int quantityOfPage, Boolean gender, String[] positionTitle, String ageRange, String sortBy, String order) {
        List<Employee> empList = new ArrayList<>();
        try (Connection conn = DBContext.getConnection()) {
            StringBuilder sql = new StringBuilder(BASE_SELECT_SQL + " WHERE 1=1 ");
            List<Object> params = new ArrayList<>();

            if (searchkey != null && !searchkey.trim().isEmpty()) {
                sql.append(" AND (e.fullname LIKE ? OR e.emp_code LIKE ?) ");
                params.add("%" + searchkey + "%");
                params.add("%" + searchkey + "%");
            }
            if (gender != null) {
                sql.append(" AND e.gender = ? ");
                params.add(gender);
            }
            if (positionTitle != null && positionTitle.length > 0) {
                sql.append(" AND e.position_title IN (");
                for (int i = 0; i < positionTitle.length; i++) {
                    sql.append("?");
                    if (i < positionTitle.length - 1) {
                        sql.append(", ");
                    }
                    params.add(positionTitle[i]);
                }
                sql.append(") ");
            }

            if (ageRange != null) {
                switch (ageRange) {
                    case "under25":
                        sql.append(" AND TIMESTAMPDIFF(YEAR, e.dob, CURDATE()) < 25 ");
                        break;
                    case "25to30":
                        sql.append(" AND TIMESTAMPDIFF(YEAR, e.dob, CURDATE()) BETWEEN 25 AND 30 ");
                        break;
                    case "31to40":
                        sql.append(" AND TIMESTAMPDIFF(YEAR, e.dob, CURDATE()) BETWEEN 31 AND 40 ");
                        break;
                    case "above40":
                        sql.append(" AND TIMESTAMPDIFF(YEAR, e.dob, CURDATE()) > 40 ");
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
            params.add(quantityOfPage);
            params.add((currentPage - 1) * quantityOfPage);

            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                int index = 1;
                for (Object param : params) {
                    ps.setObject(index++, param);
                }

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        empList.add(mapResultSetToEmployee(rs));
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return empList;
    }

    public boolean hasManager(String depId) {
        String sql = "SELECT COUNT(*) FROM employee WHERE dep_id = ? AND position_title LIKE '%Manager%'";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, depId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countAllRecordOfEmployee() {
        String sql = "Select count(*) from Employee";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public String generateUserName() {
        String sql = "SELECT MAX(emp_code) FROM Employee";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {

            if (rs.next()) {
                String maxCode = rs.getString(1);
                if (maxCode == null) {
                    return "E001";
                }
                String numberPart = maxCode.substring(1);
                int number = Integer.parseInt(numberPart);
                number++;
                return String.format("E%03d", number);
            }
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "E001";
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

    public void createEmployee(Employee emp) {
        String sql = "INSERT INTO Employee(emp_code, fullname, password, email, phone, gender, dep_id, role_id, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emp.getEmpCode());
            ps.setString(2, emp.getFullname());
            String hashedPass = BCrypt.hashpw(emp.getPassword(), BCrypt.gensalt());
            ps.setString(3, hashedPass);

            ps.setString(4, emp.getEmail());
            ps.setString(5, emp.getPhone());
            ps.setBoolean(6, emp.isGender());
            ps.setString(7, emp.getDept().getDepId());
            ps.setInt(8, emp.getRole().getRoleId());
            ps.setBoolean(9, emp.isStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM Employee WHERE email = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public long countEmployees(String search, String department) {
        long count = 0;
        String sql = "SELECT COUNT(*) FROM Employee e WHERE 1=1 ";
        List<Object> params = new ArrayList<>();
        if (search != null && !search.isEmpty()) {
            sql += "AND (e.emp_code LIKE ? OR e.fullname LIKE ?) ";
            params.add("%" + search + "%");
            params.add("%" + search + "%");
        }
        if (department != null && !department.isEmpty()) {
            sql += "AND e.dep_id = ? ";
            params.add(department);
        }
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            for (Object param : params) {
                ps.setObject(idx++, param);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getLong(1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public List<Employee> getEmployees(int offset, int pageSize, String search, String department) {
        List<Employee> list = new ArrayList<>();
        String sql = BASE_SELECT_SQL + " WHERE 1=1 ";
        List<Object> params = new ArrayList<>();
        if (search != null && !search.isEmpty()) {
            sql += "AND (e.emp_code LIKE ? OR e.fullname LIKE ?) ";
            params.add("%" + search + "%");
            params.add("%" + search + "%");
        }
        if (department != null && !department.isEmpty()) {
            sql += "AND e.dep_id = ? ";
            params.add(department);
        }
        sql += "ORDER BY e.emp_id ASC LIMIT ? OFFSET ?";
        params.add(pageSize);
        params.add(offset);

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            for (Object param : params) {
                ps.setObject(idx++, param);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToEmployee(rs));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public int countFilterEmployee(Boolean gender, String[] positionTitle, String ageRange) {
        int count = 0;
        try (Connection conn = DBContext.getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Employee WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (gender != null) {
                sql.append(" AND gender = ?");
                params.add(gender);
            }

            if (positionTitle != null && positionTitle.length > 0) {
                sql.append(" AND position_title IN (");
                for (int i = 0; i < positionTitle.length; i++) {
                    sql.append("?");
                    if (i < positionTitle.length - 1) {
                        sql.append(", ");
                    }
                    params.add(positionTitle[i]);
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

            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                int index = 1;
                for (Object param : params) {
                    ps.setObject(index++, param);
                }

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        count = rs.getInt(1);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public Employee getEmployeeReceiverByRole(String requesterRole, String depId) {
        String approverRole = null;
        boolean filterByDept = false;

        switch (requesterRole) {
            case "Employee":
                approverRole = "Dept Manager";
                filterByDept = true; 
                break;
            case "Dept Manager":
                approverRole = "HR";
                break;
            case "HR":
                approverRole = "HR Manager"; 
                break;
            case "HR Manager":
                return null;
            default:
                return null;
        }

        StringBuilder sql = new StringBuilder(BASE_SELECT_SQL)
                .append(" WHERE r.role_name = ? ");

        if (filterByDept) {
            sql.append("AND e.dep_id = ? ");
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ps.setString(1, approverRole);
            if (filterByDept) {
                ps.setString(2, depId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEmployee(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employee getManagerByDepartment(String depId) {
        String sql = BASE_SELECT_SQL + " WHERE e.dep_id = ? AND r.role_name like '%Manager%'";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, depId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEmployee(rs);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateDecreasePaidLeaveDaysByEmployeeId(int empId, double dayRequested) {
        String sql = "UPDATE Employee "
                + "SET paid_leave_days = paid_leave_days - ? "
                + "WHERE emp_id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, dayRequested);
            ps.setInt(2, empId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateIncreasePaidLeaveDaysByEmployeeId(int empId, double dayRequested) {
        String sql = "UPDATE Employee "
                + "SET paid_leave_days = paid_leave_days + ? "
                + "WHERE emp_id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, dayRequested);
            ps.setInt(2, empId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        System.out.println(dao.getEmployeeReceiverByRole("Admin", "D001"));
    }

}
