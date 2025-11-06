/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Dependant;
import model.Employee;

/**
 * @author Do Quang Huy_HE191197
 */
public class DependantDAO {

    EmployeeDAO empDAO = new EmployeeDAO();
    private final String BASE_SELECT_SQL = "select * from dependant";

    private Dependant mapResultSetToDependant(ResultSet rs) throws SQLException {
        Dependant dependant = new Dependant();
        dependant.setDependantId(rs.getInt("dependant_id"));
        Employee emp = empDAO.getEmployeeByEmpId(rs.getInt("emp_id"));
        dependant.setEmployee(emp);
        dependant.setName(rs.getString("name"));
        dependant.setRelationship(rs.getString("relationship"));
        dependant.setDob(rs.getDate("dob"));
        dependant.setGender(rs.getBoolean("gender"));
        dependant.setPhone(rs.getString("phone"));
        return dependant;
    }

    public List<Dependant> getAllDependantByEmpId(int emp_id) {
        List<Dependant> dependantList = new ArrayList<>();
        String sql = BASE_SELECT_SQL + " where emp_id=?";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, emp_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dependantList.add(mapResultSetToDependant(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DependantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dependantList;
    }

    public Dependant getDependantByEmpId(int emp_id) {
        Dependant dependant = new Dependant();
        String sql = BASE_SELECT_SQL + " where emp_id=? ";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, emp_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dependant = mapResultSetToDependant(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DependantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dependant;
    }

    public List<String> getRelationshipListEnumValues() {
        List<String> relationList = new ArrayList<>();
        String sql = "SHOW COLUMNS FROM dependant LIKE 'relationship'";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("Type");
                type = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
                String[] relationValues = type.replace("'", "").split(",");
                for (String value : relationValues) {
                    relationList.add(value);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DependantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return relationList;
    }

    public void updateDependant(Dependant dependant) {
        String sql = "Update dependant set name=?,relationship=?,dob=?,gender=?,phone=? where emp_id=?";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dependant.getName());
            ps.setString(2, dependant.getRelationship());
            ps.setDate(3, dependant.getDob());
            ps.setBoolean(4, dependant.getGender());
            ps.setString(5, dependant.getPhone());
            ps.setInt(6, dependant.getEmployee().getEmpId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DependantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createDependant(Dependant dependant) {
        String sql = "INSERT INTO dependant(name, relationship, dob, gender, phone, emp_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dependant.getName());
            ps.setString(2, dependant.getRelationship());
            ps.setDate(3, dependant.getDob());
            ps.setBoolean(4, dependant.getGender());
            ps.setString(5, dependant.getPhone());
            ps.setInt(6, dependant.getEmployee().getEmpId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int countFilteredDependantsByEmpId(int empId, String searchKey, String relationship, String gender) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM dependant WHERE emp_id = ? ");

        if (searchKey != null && !searchKey.isEmpty()) {
            sql.append("AND name LIKE ? ");
        }
        if (relationship != null && !relationship.isEmpty()) {
            sql.append("AND relationship = ? ");
        }
        if (gender != null && !gender.isEmpty()) {
            sql.append("AND gender = ? ");
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            ps.setInt(index++, empId);

            if (searchKey != null && !searchKey.isEmpty()) {
                ps.setString(index++, "%" + searchKey + "%");
            }
            if (relationship != null && !relationship.isEmpty()) {
                ps.setString(index++, relationship);
            }
            if (gender != null && !gender.isEmpty()) {
                ps.setBoolean(index++, Boolean.parseBoolean(gender));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Dependant> getFilteredDependantsByEmpId(
            int empId, String searchKey, String relationship, String gender, int page, int pageSize) {

        List<Dependant> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM dependant WHERE emp_id = ? ");

        if (searchKey != null && !searchKey.isEmpty()) {
            sql.append("AND name LIKE ? ");
        }
        if (relationship != null && !relationship.isEmpty()) {
            sql.append("AND relationship = ? ");
        }
        if (gender != null && !gender.isEmpty()) {
            sql.append("AND gender = ? ");
        }

        sql.append(" LIMIT ? OFFSET ?");

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            ps.setInt(index++, empId);

            if (searchKey != null && !searchKey.isEmpty()) {
                ps.setString(index++, "%" + searchKey + "%");
            }
            if (relationship != null && !relationship.isEmpty()) {
                ps.setString(index++, relationship);
            }
            if (gender != null && !gender.isEmpty()) {
                ps.setBoolean(index++, Boolean.parseBoolean(gender));
            }
            ps.setInt(index++, pageSize);
            ps.setInt(index++, (page - 1) * pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToDependant(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void main(String[] args) {
        DependantDAO depenDAO = new DependantDAO();
        System.out.println(depenDAO.getFilteredDependantsByEmpId(1, "thanh", "Child", "",1,1));
    }
}
