/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nguyen Dinh Quy HE190184
 */
public class OTRequestDAO extends DBContext {
    
    private Connection connection;
    private String status = "ok";
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    
    public OTRequestDAO() {
        try {
            connection = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Connection failed: " + e.getMessage();
            e.printStackTrace();
        }
    }
    
    public ArrayList<OTRequest> getOTRequestByEmpId(int emp_id) {
        ArrayList<OTRequest> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM hrm.ot_request where emp_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, emp_id);
            ResultSet rs = stm.executeQuery();
            Employee employee = employeeDAO.getEmployeeByEmpId(emp_id);
            while (rs.next()) {
                list.add(new OTRequest(
                        rs.getInt("ot_id"),
                        employee,
                        rs.getDate("date"),
                        rs.getDouble("ot_hours"),
                        employeeDAO.getEmployeeByEmpId(rs.getInt("approved_by")),
                        rs.getTimestamp("approved_at"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    public OTRequest getOTRequestByOTId(int ot_id) {
        try {
            String sql = "SELECT * FROM hrm.ot_request where ot_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, ot_id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                OTRequest OTRequest = new OTRequest(
                        rs.getInt("ot_id"),
                        employeeDAO.getEmployeeByEmpId(rs.getInt("emp_id")),
                        rs.getDate("date"),
                        rs.getDouble("ot_hours"),
                        employeeDAO.getEmployeeByEmpId(rs.getInt("approved_by")),
                        rs.getTimestamp("approved_at"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                return OTRequest;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public int composeOTRequest(int emp_id, Date date, double otHours, int approvedBy) {
        String sql = ""
                + "INSERT INTO hrm.ot_request"
                + "(emp_id, date, ot_hours, approved_by, status, created_at, updated_at)"
                + "VALUES (?, ?, ?, ?,'Pending', ?, ?)";
        
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, emp_id);
            stm.setDate(2, date);
            stm.setDouble(3, otHours);
            stm.setInt(4, approvedBy);
            stm.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            stm.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            
            return stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
    public int deleteOTRequest(int otId) {
        String sql = "DELETE FROM hrm.ot_request WHERE ot_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, otId);
            return stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
    public int updateOTRequest(int id, Date date, double hours) {
        String sql = "UPDATE hrm.ot_request SET date=?, ot_hours=?, updated_at=NOW() WHERE ot_id=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setDate(1, date);
            stm.setDouble(2, hours);
            stm.setInt(3, id);
            return stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int countOTByEmp(int empId) {
        String sql = "SELECT COUNT(*) FROM hrm.ot_request WHERE emp_id = ?";
        try (Connection cn = DBContext.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public List<OTRequest> findOTByEmpPaged(int empId, int offset, int size) {
        String sql = ""
                + "SELECT ot.* "
                + "FROM hrm.ot_request ot "
                + "WHERE ot.emp_id = ? "
                + "ORDER BY ot.created_at DESC "
                + "LIMIT ? OFFSET ? ";
        List<OTRequest> list = new ArrayList<>();
        try (Connection cn = DBContext.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ps.setInt(2, size);
            ps.setInt(3, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new OTRequest(
                            rs.getInt("ot_id"),
                            employeeDAO.getEmployeeByEmpId(rs.getInt("emp_id")),
                            rs.getDate("date"),
                            rs.getDouble("ot_hours"),
                            employeeDAO.getEmployeeByEmpId(rs.getInt("approved_by")),
                            rs.getTimestamp("approved_at"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static void main(String[] args) {
        OTRequestDAO dao = new OTRequestDAO();
        List<OTRequest> list = new ArrayList<>();
        list.addAll(dao.findOTByEmpPaged(1, 0, 10));
        for (OTRequest x : list) {
            System.out.println(x.toString());
        }
    }
}
