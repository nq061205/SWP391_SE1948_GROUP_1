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
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            connection = new DBContext().getConnection();
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
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
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

    public Department getDepartmentByDeptID(String deptID) {
        Department dept = new Department();
        String sql = "SELECT * FROM Department WHERE dep_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
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
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
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

    public void createEmployee(String email, String empCode) {
        String sql = "INSERT INTO Employee(email,emp_code) values(?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, empCode);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteEmployee(String empCode) {
        String sql = "DELETE FROM Employee where emp_code=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, empCode);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateEmployee(Employee employee) {
        String sql = "UPDATE Employee SET fullname=?,email=?,password=?,gender=?,dob=?,phone=?,position_title=?,image=?,dep_id=?,role_id=?,status=?  WHERE emp_code = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, employee.getFullname());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getPassword());
            ps.setBoolean(4, employee.isGender());
            ps.setDate(5, employee.getDob());
            ps.setString(6, employee.getPhone());
            ps.setString(7, employee.getPositionTitle());
            ps.setString(8, employee.getImage());
            ps.setString(9, employee.getDept().getDepId());
            ps.setInt(10, employee.getRole().getRoleId());
            ps.setBoolean(11, employee.isStatus());
            ps.setString(12, employee.getEmpCode());
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

    public List<Employee> searchEmployee(String searchkey) {
        empList = new ArrayList<>();
        String sql = "select * from Employee where emp_code like ? or fullname like ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + searchkey + "%");
            ps.setString(2, "%" + searchkey + "%");
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

    public List<Employee> filterEmployees(Boolean status, String[] deptIds, String[] roleIds) {
        empList = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM Employee WHERE 1=1");

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

    public List<Employee> getSortedEmployee(String sortBy, String order) {
        empList = new ArrayList<>();
        String sql = "SELECT * FROM Employee order by ";
        if (sortBy != null) {
            sql += sortBy;
        }
        if (order != null && order.equalsIgnoreCase("desc")) {
            sql += " DESC";
        } else {
            sql += " ASC";
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
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

    public List<Employee> getEmployeeByPage(int page, int quantityOfPage) {
        empList = new ArrayList<>();
        String sql = "Select * from Employee limit ? offset ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, quantityOfPage);
            ps.setInt(2, (page - 1) * quantityOfPage);
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

    public int countRecordOfEmployee() {
        String sql = "Select count(*) from Employee";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
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

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        String sortBy ="emp_code";
        String order="DESC";
        
        System.out.println(dao.updateEmployeeInformation(1, "Nguyễn Đình Quý", true, Date.valueOf("2005-12-06"), "0337364331", ""));
    }
}
