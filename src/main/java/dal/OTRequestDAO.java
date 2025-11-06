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

    private final String OT_SELECT_SQL = "SELECT "
            + "ot.ot_id, ot.emp_id, ot.date, ot.ot_hours, ot.approved_by, ot.approved_at, ot.status, ot.created_at, ot.updated_at, "
            + "e.emp_code as emp_code, e.fullname as emp_fullname, e.email as emp_email, e.position_title as emp_pos, "
            + "a.emp_code as app_code, a.fullname as app_fullname, a.email as app_email, a.position_title as app_pos "
            + "FROM hrm.ot_request ot "
            + "JOIN hrm.employee e ON ot.emp_id = e.emp_id "
            + "LEFT JOIN hrm.employee a ON ot.approved_by = a.emp_id ";

    private OTRequest mapResultSetToOTRequest(ResultSet rs) throws SQLException {
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

        return new OTRequest(
                rs.getInt("ot_id"),
                employee,
                rs.getDate("date"),
                rs.getDouble("ot_hours"),
                approver,
                rs.getTimestamp("approved_at"),
                rs.getString("status"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")
        );
    }

    public ArrayList<OTRequest> getOTRequestByEmpId(int emp_id) {
        ArrayList<OTRequest> list = new ArrayList<>();
        String sql = OT_SELECT_SQL + " WHERE ot.emp_id = ?";
        try (Connection cn = DBContext.getConnection(); PreparedStatement stm = cn.prepareStatement(sql)) {

            stm.setInt(1, emp_id);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToOTRequest(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public OTRequest getOTRequestByOTId(int ot_id) {
        String sql = OT_SELECT_SQL + " WHERE ot.ot_id = ?";
        try (Connection cn = DBContext.getConnection(); PreparedStatement stm = cn.prepareStatement(sql)) {
            stm.setInt(1, ot_id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToOTRequest(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int composeOTRequest(int emp_id, Date date, double otHours, int approvedBy) {
        String sql = "INSERT INTO hrm.ot_request (emp_id, date, ot_hours, approved_by, status, created_at, updated_at) "
                + "VALUES (?, ?, ?, ?, 'Pending', ?, ?)";
        try (Connection cn = DBContext.getConnection(); PreparedStatement stm = cn.prepareStatement(sql)) {
            stm.setInt(1, emp_id);
            stm.setDate(2, date);
            stm.setDouble(3, otHours);
            stm.setInt(4, approvedBy);
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            stm.setTimestamp(5, now);
            stm.setTimestamp(6, now);
            return stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int deleteOTRequest(int otId) {
        String sql = "DELETE FROM hrm.ot_request WHERE ot_id = ?";
        try (Connection cn = DBContext.getConnection(); PreparedStatement stm = cn.prepareStatement(sql)) {
            stm.setInt(1, otId);
            return stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int updateOTRequest(int id, Date date, double hours) {
        String sql = "UPDATE hrm.ot_request SET date=?, ot_hours=?, updated_at=NOW() WHERE ot_id=?";
        try (Connection cn = DBContext.getConnection(); PreparedStatement stm = cn.prepareStatement(sql)) {
            stm.setDate(1, date);
            stm.setDouble(2, hours);
            stm.setInt(3, id);
            return stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countOTByEmpFiltered(int empId, String search, String status, Date startDate, Date endDate) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM hrm.ot_request WHERE emp_id = ?");
        List<Object> params = new ArrayList<>();
        params.add(empId);

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status.trim());
        }

        if (search != null && !search.trim().isEmpty()) {
            String keyword = "%" + search.trim() + "%";
            sql.append(" AND (CAST(ot_hours AS CHAR) LIKE ? OR status LIKE ? OR CAST(date AS CHAR) LIKE ?)");
            params.add(keyword);
            params.add(keyword);
            params.add(keyword);
        }

        if (startDate != null) {
            sql.append(" AND date >= ?");
            params.add(startDate);
        }
        if (endDate != null) {
            sql.append(" AND date <= ?");
            params.add(endDate);
        }

        try (Connection cn = DBContext.getConnection(); PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            int index = 1;
            for (Object p : params) {
                ps.setObject(index++, p);
            }

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

    public int countOTByApproverFiltered(int empId, String search, Date startDate, Date endDate) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM hrm.ot_request WHERE emp_id = ? and ot.status = 'Pending'");
        List<Object> params = new ArrayList<>();
        params.add(empId);

        if (search != null && !search.trim().isEmpty()) {
            String keyword = "%" + search.trim() + "%";
            sql.append(" AND (e.fullname LIKE ? OR e.email LIKE ?)");
            params.add(keyword);
            params.add(keyword);
        }

        if (startDate != null) {
            sql.append(" AND date >= ?");
            params.add(startDate);
        }
        if (endDate != null) {
            sql.append(" AND date <= ?");
            params.add(endDate);
        }

        try (Connection cn = DBContext.getConnection(); PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            int index = 1;
            for (Object p : params) {
                ps.setObject(index++, p);
            }

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

    public List<OTRequest> findOTByEmpPaged(int empId, int offset, int size, String search, String status, Date startDate, Date endDate) {
        StringBuilder sql = new StringBuilder(
                "SELECT ot.ot_id, ot.emp_id, ot.date, ot.ot_hours, ot.approved_by, ot.approved_at, ot.status, ot.created_at, ot.updated_at, "
                + "e.emp_code as emp_code, e.fullname as emp_fullname, e.email as emp_email, e.position_title as emp_pos, "
                + "a.emp_code as app_code, a.fullname as app_fullname, a.email as app_email, a.position_title as app_pos "
                + "FROM hrm.ot_request ot "
                + "JOIN hrm.employee e ON ot.emp_id = e.emp_id "
                + "LEFT JOIN hrm.employee a ON ot.approved_by = a.emp_id "
                + "WHERE ot.emp_id = ? "
        );

        List<Object> params = new ArrayList<>();
        params.add(empId);

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND ot.status = ? ");
            params.add(status.trim());
        }

        if (search != null && !search.trim().isEmpty()) {
            String kw = "%" + search.trim() + "%";
            sql.append(" AND (CAST(ot.ot_hours AS CHAR) LIKE ? "
                    + "  OR ot.status LIKE ? "
                    + "  OR CAST(ot.date AS CHAR) LIKE ?) ");
            params.add(kw);
            params.add(kw);
            params.add(kw);
        }

        if (startDate != null) {
            sql.append(" AND ot.created_at >= ? ");
            params.add(startDate);
        }
        if (endDate != null) {
            sql.append(" AND ot.created_at <= ? ");
            params.add(endDate);
        }

        sql.append(" ORDER BY ot.created_at DESC LIMIT ? OFFSET ? ");

        List<OTRequest> list = new ArrayList<>();

        try (Connection cn = DBContext.getConnection(); PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            int idx = 1;

            for (Object p : params) {
                ps.setObject(idx++, p);
            }

            ps.setInt(idx++, size);
            ps.setInt(idx, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToOTRequest(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<OTRequest> findOTByApproverEmpPaged(int empId, int offset, int size, String search, Date startDate, Date endDate) {
        StringBuilder sql = new StringBuilder(
                "SELECT ot.ot_id, ot.emp_id, ot.date, ot.ot_hours, ot.approved_by, ot.approved_at, ot.status, ot.created_at, ot.updated_at, "
                + "e.emp_code as emp_code, e.fullname as emp_fullname, e.email as emp_email, e.position_title as emp_pos, "
                + "a.emp_code as app_code, a.fullname as app_fullname, a.email as app_email, a.position_title as app_pos "
                + "FROM hrm.ot_request ot "
                + "JOIN hrm.employee e ON ot.emp_id = e.emp_id "
                + "LEFT JOIN hrm.employee a ON ot.approved_by = a.emp_id "
                + "WHERE ot.emp_id = ? and ot.status = 'Pending' "
        );

        List<Object> params = new ArrayList<>();
        params.add(empId);

        if (search != null && !search.trim().isEmpty()) {
            String keyword = "%" + search.trim() + "%";
            sql.append(" AND (e.fullname LIKE ? OR e.email LIKE ?)");
            params.add(keyword);
            params.add(keyword);
        }

        if (startDate != null) {
            sql.append(" AND ot.created_at >= ? ");
            params.add(startDate);
        }
        if (endDate != null) {
            sql.append(" AND ot.created_at <= ? ");
            params.add(endDate);
        }

        sql.append(" ORDER BY ot.created_at DESC LIMIT ? OFFSET ? ");

        List<OTRequest> list = new ArrayList<>();

        try (Connection cn = DBContext.getConnection(); PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            int idx = 1;

            for (Object p : params) {
                ps.setObject(idx++, p);
            }

            ps.setInt(idx++, size);
            ps.setInt(idx, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToOTRequest(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<OTRequest> getApprovedOTBetween(Date minDate, Date maxDate) {
        List<OTRequest> list = new ArrayList<>();
        String sql = OT_SELECT_SQL + " WHERE ot.status = 'Approved' AND ot.date BETWEEN ? AND ?";

        try (Connection cn = DBContext.getConnection(); PreparedStatement st = cn.prepareStatement(sql)) {
            st.setDate(1, minDate);
            st.setDate(2, maxDate);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToOTRequest(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateOTStatus(int id, String action) {
        String sql = "UPDATE ot_request SET "
                + "status = ?, "
                + "approved_at = NOW(), "
                + "updated_at = NOW() "
                + "WHERE ot_id = ?";

        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, action);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        OTRequestDAO dao = new OTRequestDAO();
        dao.updateOTStatus(1, "Approved");
    }

}
