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
public class LeaveRequestDAO extends DBContext {

    private Connection connection;
    private String status = "ok";
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public LeaveRequestDAO() {
        try {
            connection = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Connection failed: " + e.getMessage();
            e.printStackTrace();
        }
    }

    public ArrayList<LeaveRequest> getLeaveRequestByEmpId(int emp_id) {
        ArrayList<LeaveRequest> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM hrm.leave_request where emp_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, emp_id);
            ResultSet rs = stm.executeQuery();
            Employee employee = employeeDAO.getEmployeeByEmpId(emp_id);
            while (rs.next()) {
                list.add(new LeaveRequest(
                        rs.getInt("leave_id"),
                        employee,
                        rs.getString("leave_type"),
                        rs.getString("reason"),
                        rs.getDouble("day_requested"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        employeeDAO.getEmployeeByEmpId(rs.getInt("approved_by")),
                        rs.getTimestamp("approved_at"),
                        rs.getString("status"),
                        rs.getString("note"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public LeaveRequest getLeaveRequestByLeaveId(int leave_id, int emp_id) {
        try {
            String sql = "SELECT * FROM hrm.leave_request where leave_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, leave_id);
            ResultSet rs = stm.executeQuery();
            Employee employee = employeeDAO.getEmployeeByEmpId(emp_id);
            if (rs.next()) {
                LeaveRequest leaveRequest = new LeaveRequest(
                        rs.getInt("leave_id"),
                        employee,
                        rs.getString("leave_type"),
                        rs.getString("reason"),
                        rs.getDouble("day_requested"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        employeeDAO.getEmployeeByEmpId(rs.getInt("approved_by")),
                        rs.getTimestamp("approved_at"),
                        rs.getString("status"),
                        rs.getString("note"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                return leaveRequest;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int composeLeaveRequest(int emp_id, String leave_type, String reason,
            Date startDate, Date endDate, int approvedBy) {
        String sql = ""
                + "INSERT INTO hrm.leave_request"
                + "(emp_id, leave_type, reason, day_requested, start_date, end_date, approved_by, status, created_at, updated_at)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, 'Pending', ?, ?)";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            long days = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24) + 1;

            stm.setInt(1, emp_id);
            stm.setString(2, leave_type);
            stm.setString(3, reason);
            stm.setInt(4, (int) days);
            stm.setDate(5, new java.sql.Date(startDate.getTime()));
            stm.setDate(6, new java.sql.Date(endDate.getTime()));
            stm.setInt(7, approvedBy);
            stm.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            stm.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));

            return stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int deleteLeaveRequest(int leaveId) {
        String sql = "DELETE FROM hrm.leave_request WHERE leave_id= ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, leaveId);
            return stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int updateLeaveRequest(int id, String leaveType, String content, Date startDate, Date endDate) {
        String sql = "UPDATE hrm.leave_request SET leave_type=?, reason=?, start_date=?, end_date=?, updated_at=NOW() WHERE leave_id=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, leaveType);
            stm.setString(2, content);
            stm.setDate(3, startDate);
            stm.setDate(4, endDate);
            stm.setInt(5, id);
            return stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countLeaveByEmpFiltered(
            int empId, String search, String status, String type,
            Date startDate, Date endDate) {

        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) "
                + "FROM hrm.leave_request lr "
                + "WHERE lr.emp_id = ?"
        );

        List<Object> params = new ArrayList<>();
        params.add(empId);

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND lr.status = ?");
            params.add(status.trim());
        }

        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND lr.leave_type = ?");
            params.add(type.trim());
        }
        
        if (search != null && !search.trim().isEmpty()) {
            String kw = "%" + search.trim() + "%";
            sql.append(" AND (lr.status LIKE ? "
                    + "  OR lr.leave_type LIKE ? "
                    + "  OR lr.reason LIKE ? "
                    + "  OR CAST(lr.start_date AS CHAR) LIKE ? "
                    + "  OR CAST(lr.end_date AS CHAR) LIKE ?)");
            params.add(kw);
            params.add(kw);
            params.add(kw);
            params.add(kw);
            params.add(kw);
        }

        if (startDate != null) {
            sql.append(" AND lr.created_at >= ?");
            params.add(java.sql.Timestamp.valueOf(startDate.toLocalDate().atStartOfDay()));
        }
        if (endDate != null) {
            sql.append(" AND lr.created_at <= ?");
            params.add(java.sql.Timestamp.valueOf(endDate.toLocalDate().plusDays(1).atStartOfDay()));
        }

        try (Connection cn = DBContext.getConnection(); PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            int i = 1;
            for (Object p : params) {
                if (p instanceof Integer) {
                    ps.setInt(i++, (Integer) p);
                } else if (p instanceof String) {
                    ps.setString(i++, (String) p);
                } else if (p instanceof java.sql.Date) {
                    ps.setDate(i++, (java.sql.Date) p);     // nếu dùng DATE ở range
                } else if (p instanceof java.sql.Timestamp) {
                    ps.setTimestamp(i++, (java.sql.Timestamp) p);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<LeaveRequest> findLeaveByEmpFilteredPaged(
            int empId, String search, String status, String type,
            Date startDate, Date endDate,
            int offset, int size) {

        StringBuilder sql = new StringBuilder(
                "SELECT lr.* "
                + "FROM hrm.leave_request lr "
                + "WHERE lr.emp_id = ?"
        );

        List<Object> params = new ArrayList<>();
        params.add(empId);

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND lr.status = ?");
            params.add(status.trim());
        }

        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND lr.leave_type = ?");
            params.add(type.trim());
        }

        if (search != null && !search.trim().isEmpty()) {
            String kw = "%" + search.trim() + "%";
            sql.append(" AND (lr.status LIKE ? "
                    + "  OR lr.leave_type LIKE ? "
                    + "  OR lr.reason LIKE ? "
                    + "  OR CAST(lr.start_date AS CHAR) LIKE ? "
                    + "  OR CAST(lr.end_date   AS CHAR) LIKE ?)");
            params.add(kw);
            params.add(kw);
            params.add(kw);
            params.add(kw);
            params.add(kw);
        }

        if (startDate != null) {
            sql.append(" AND lr.created_at >= ?");
            params.add(java.sql.Timestamp.valueOf(startDate.toLocalDate().atStartOfDay()));
        }
        if (endDate != null) {
            sql.append(" AND lr.created_at <= ?");
            params.add(java.sql.Timestamp.valueOf(endDate.toLocalDate().plusDays(1).atStartOfDay()));
        }

        sql.append(" ORDER BY lr.created_at DESC LIMIT ? OFFSET ?");
        params.add(size);
        params.add(offset);

        List<LeaveRequest> list = new ArrayList<>();

        try (Connection cn = DBContext.getConnection(); PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            int i = 1;
            for (Object p : params) {
                if (p instanceof Integer) {
                    ps.setInt(i++, (Integer) p);
                } else if (p instanceof String) {
                    ps.setString(i++, (String) p);
                } else if (p instanceof java.sql.Date) {
                    ps.setDate(i++, (java.sql.Date) p);
                } else if (p instanceof java.sql.Timestamp) {
                    ps.setTimestamp(i++, (java.sql.Timestamp) p);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new LeaveRequest(
                            rs.getInt("leave_id"),
                            employeeDAO.getEmployeeByEmpId(rs.getInt("emp_id")),
                            rs.getString("leave_type"),
                            rs.getString("reason"),
                            rs.getDouble("day_requested"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date"),
                            employeeDAO.getEmployeeByEmpId(rs.getInt("approved_by")),
                            rs.getTimestamp("approved_at"),
                            rs.getString("status"),
                            rs.getString("note"),
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
        LeaveRequestDAO dao = new LeaveRequestDAO();
    }
}
