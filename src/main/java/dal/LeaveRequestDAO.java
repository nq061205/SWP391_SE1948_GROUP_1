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

    private final String LEAVE_SELECT_SQL = "SELECT "
            + "lr.leave_id, lr.emp_id, lr.leave_type, lr.reason, lr.day_requested, lr.start_date, lr.end_date, lr.approved_by, lr.approved_at, lr.status, lr.note, lr.created_at, lr.updated_at, "
            + "e.emp_code as emp_code, e.fullname as emp_fullname, e.email as emp_email, e.position_title as emp_pos, "
            + "a.emp_code as app_code, a.fullname as app_fullname, a.email as app_email, a.position_title as app_pos "
            + "FROM hrm.leave_request lr "
            + "JOIN hrm.employee e ON lr.emp_id = e.emp_id "
            + "LEFT JOIN hrm.employee a ON lr.approved_by = a.emp_id";

    private LeaveRequest mapResultSetToLeaveRequest(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setEmpId(rs.getInt("emp_id"));
        employee.setEmpCode(rs.getString("emp_code"));
        employee.setFullname(rs.getString("emp_fullname"));
        employee.setEmail(rs.getString("emp_email"));
        employee.setPositionTitle(rs.getString("emp_pos"));

        Employee approver = null;
        if (rs.getObject("approved_by") != null) {
            approver = new Employee();
            approver.setEmpId(rs.getInt("approved_by"));
            approver.setEmpCode(rs.getString("app_code"));
            approver.setFullname(rs.getString("app_fullname"));
            approver.setEmail(rs.getString("app_email"));
            approver.setPositionTitle(rs.getString("app_pos"));
        }

        return new LeaveRequest(
                rs.getInt("leave_id"),
                employee,
                rs.getString("leave_type"),
                rs.getString("reason"),
                rs.getDouble("day_requested"),
                rs.getDate("start_date"),
                rs.getDate("end_date"),
                approver,
                rs.getTimestamp("approved_at"),
                rs.getString("status"),
                rs.getString("note"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")
        );
    }

    public ArrayList<LeaveRequest> getLeaveRequestByEmpId(int emp_id) {
        ArrayList<LeaveRequest> list = new ArrayList<>();
        String sql = LEAVE_SELECT_SQL + " WHERE lr.emp_id = ?";
        try (Connection cn = DBContext.getConnection();
                PreparedStatement stm = cn.prepareStatement(sql)) {
            stm.setInt(1, emp_id);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToLeaveRequest(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public LeaveRequest getLeaveRequestByLeaveId(int leave_id, int emp_id) {
        String sql = LEAVE_SELECT_SQL + " WHERE lr.leave_id = ? AND lr.emp_id = ?";
        try (Connection cn = DBContext.getConnection();
                PreparedStatement stm = cn.prepareStatement(sql)) {
            stm.setInt(1, leave_id);
            stm.setInt(2, emp_id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToLeaveRequest(rs);
                }
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

        try (Connection conn = DBContext.getConnection();
                PreparedStatement stm = conn.prepareStatement(sql)) {
            long diff = endDate.getTime() - startDate.getTime();
            double days = (double) diff / (1000 * 60 * 60 * 24) + 1;

            stm.setInt(1, emp_id);
            stm.setString(2, leave_type);
            stm.setString(3, reason);
            stm.setDouble(4, days);
            stm.setDate(5, startDate);
            stm.setDate(6, endDate);
            stm.setInt(7, approvedBy);
            stm.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            stm.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));

            return stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int deleteLeaveRequest(int leaveId) {
        String sql = "DELETE FROM hrm.leave_request WHERE leave_id= ?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, leaveId);
            return stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int updateLeaveRequest(int id, String leaveType, String content, Date startDate, Date endDate) {
        String sql = "UPDATE hrm.leave_request SET leave_type=?, reason=?,day_requested = ? ,start_date = ?, end_date = ?, updated_at = NOW() WHERE leave_id=?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement stm = conn.prepareStatement(sql)) {

            long diff = endDate.getTime() - startDate.getTime();
            double days = (double) diff / (1000 * 60 * 60 * 24) + 1;

            stm.setString(1, leaveType);
            stm.setString(2, content);
            stm.setDouble(3, days);
            stm.setDate(4, startDate);
            stm.setDate(5, endDate);
            stm.setInt(6, id);
            return stm.executeUpdate();
        } catch (Exception e) {
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
            params.add(Timestamp.valueOf(startDate.toLocalDate().atStartOfDay()));
        }
        if (endDate != null) {
            sql.append(" AND lr.created_at <= ?");
            params.add(Timestamp.valueOf(endDate.toLocalDate().plusDays(1).atStartOfDay()));
        }

        try (Connection cn = DBContext.getConnection(); PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            int i = 1;
            for (Object p : params) {
                ps.setObject(i++, p);
            }

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<LeaveRequest> findLeaveByEmpFilteredPaged(
            int empId, String search, String status, String type,
            Date startDate, Date endDate,
            int offset, int size) {

        StringBuilder sql = new StringBuilder(LEAVE_SELECT_SQL + " WHERE lr.emp_id = ?");

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
                ps.setObject(i++, p);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToLeaveRequest(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<LeaveRequest> getApprovedLeaves() {
        List<LeaveRequest> list = new ArrayList<>();
        String sql = LEAVE_SELECT_SQL + " WHERE lr.status = 'Approved'";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement st = conn.prepareStatement(sql);
                ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToLeaveRequest(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LeaveRequest> getApprovedLeavesBetween(Date minDate, Date maxDate) {
        List<LeaveRequest> list = new ArrayList<>();
        String sql = LEAVE_SELECT_SQL + " WHERE lr.status = 'Approved' AND (lr.start_date <= ? AND lr.end_date >= ?)";

        try (Connection conn = DBContext.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setDate(1, maxDate);
            st.setDate(2, minDate);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToLeaveRequest(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        LeaveRequestDAO dao = new LeaveRequestDAO();
        System.out.println(dao.composeLeaveRequest(1, "Sick", "a", Date.valueOf("2025-12-12"), Date.valueOf("2025-12-12"), 2));
    }

}