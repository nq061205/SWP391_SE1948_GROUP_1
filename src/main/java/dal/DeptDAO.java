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
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

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
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

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
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

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
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

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

    public boolean existsById(String depId) {
        String sql = "SELECT COUNT(*) FROM department WHERE dep_id = ?";
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

    public List<Department> getAllDepartment() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT dep_id, dep_name, description FROM department ORDER BY dep_name";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

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

    public List<Department> getDepartmentsByFilter(String depId, String searchKey, int page, int pageSize,
            String sortBy, String order) {
        List<Department> departments = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT dep_id, dep_name, description FROM department WHERE 1=1");

        if (depId != null && !depId.trim().isEmpty()) {
            sql.append(" AND dep_id = ?");
        }
        if (searchKey != null && !searchKey.trim().isEmpty()) {
            sql.append(" AND (dep_id LIKE ? OR dep_name LIKE ?)");
        }
        String sortColumn = "dep_name";
        if ("dep_id".equalsIgnoreCase(sortBy)) {
            sortColumn = "dep_id";
        }

        String sortOrder = "ASC";
        if ("DESC".equalsIgnoreCase(order)) {
            sortOrder = "DESC";
        }

        sql.append(" ORDER BY ").append(sortColumn).append(" ").append(sortOrder)
                .append(" LIMIT ? OFFSET ?");

        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (depId != null && !depId.trim().isEmpty()) {
                stm.setString(index++, depId.trim());
            }
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                String key = "%" + searchKey.trim() + "%";
                stm.setString(index++, key);
                stm.setString(index++, key);
            }

            stm.setInt(index++, pageSize);
            stm.setInt(index, (page - 1) * pageSize);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Department department = new Department(
                            rs.getString("dep_id"),
                            rs.getString("dep_name"),
                            rs.getString("description")
                    );
                    departments.add(department);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return departments;
    }

    public String generateDeptId() {
        String sql = "SELECT MAX(dep_id) FROM Department";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {

            if (rs.next()) {
                String maxDep = rs.getString(1);
                if (maxDep == null) {
                    return "D001";
                }
                String numberPart = maxDep.substring(1);
                int number = Integer.parseInt(numberPart);
                number++;
                return String.format("D%03d", number);
            }
        } catch (Exception ex) {
            Logger.getLogger(DeptDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "D001";
    }

    public int countDepartmentsByFilter(String depId, String searchKey) {
        int count = 0;

        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM department WHERE 1=1");

        if (depId != null && !depId.trim().isEmpty()) {
            sql.append(" AND dep_id = ?");
        }
        if (searchKey != null && !searchKey.trim().isEmpty()) {
            sql.append(" AND (dep_id LIKE ? OR dep_name LIKE ?)");
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (depId != null && !depId.trim().isEmpty()) {
                stm.setString(index++, depId.trim());
            }
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                String key = "%" + searchKey.trim() + "%";
                stm.setString(index++, key);
                stm.setString(index++, key);
            }

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return count;
    }

    public int countAllDepartments() {
        int count = 0;

        String sql = "SELECT COUNT(*) FROM department";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return count;
    }

    public boolean existsByDepName(String depName) {
        String sql = "SELECT 1 FROM Department WHERE dep_name = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, depName);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; 
    }

    public List<String> getDepartmentsHavingManager() {
        List<String> depIds = new ArrayList<>();
        String sql = "SELECT DISTINCT e.dep_id\n"
                + "        FROM employee e\n"
                + "        JOIN role r ON e.role_id = r.role_id\n"
                + "        WHERE r.role_name Like '%Dept Manager%'";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                depIds.add(rs.getString("dep_id").trim().toUpperCase());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return depIds;
    }

    public void createDepartment(Department department) {
        String sql = "INSERT INTO department VALUES(?,?,?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, department.getDepName());
            ps.setString(2, department.getDescription());
            ps.setString(3, department.getDepId());
            ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(DeptDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int countEmployeeInDepartment(String depId) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Employee WHERE dep_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, depId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void main(String[] args) {
        DeptDAO dao = new DeptDAO();
        System.out.println(dao.getDepartmentsHavingManager());
    }
}
