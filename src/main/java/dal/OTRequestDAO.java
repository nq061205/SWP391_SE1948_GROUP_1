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
        String sql = "SELECT * FROM hrm.ot_request WHERE emp_id = ?";
        try (Connection cn = DBContext.getConnection(); PreparedStatement stm = cn.prepareStatement(sql)) {

            stm.setInt(1, emp_id);
            try (ResultSet rs = stm.executeQuery()) {
                EmployeeDAO ed = new EmployeeDAO();
                Employee employee = ed.getEmployeeByEmpId(emp_id);
                while (rs.next()) {
                    list.add(new OTRequest(
                            rs.getInt("ot_id"),
                            employee,
                            rs.getDate("date"),
                            rs.getDouble("ot_hours"),
                            ed.getEmployeeByEmpId(rs.getInt("approved_by")),
                            rs.getTimestamp("approved_at"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    ));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public OTRequest getOTRequestByOTId(int ot_id) {
        String sql = "SELECT * FROM hrm.ot_request WHERE ot_id = ?";
        try (Connection cn = DBContext.getConnection(); PreparedStatement stm = cn.prepareStatement(sql)) {
            stm.setInt(1, ot_id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    EmployeeDAO ed = new EmployeeDAO();
                    return new OTRequest(
                            rs.getInt("ot_id"),
                            ed.getEmployeeByEmpId(rs.getInt("emp_id")),
                            rs.getDate("date"),
                            rs.getDouble("ot_hours"),
                            ed.getEmployeeByEmpId(rs.getInt("approved_by")),
                            rs.getTimestamp("approved_at"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    );
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
                if (p instanceof Integer) {
                    ps.setInt(index++, (Integer) p);
                } else if (p instanceof String) {
                    ps.setString(index++, (String) p);
                } else if (p instanceof java.sql.Date) {
                    ps.setDate(index++, (java.sql.Date) p);
                }
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
                "SELECT ot.* "
                + "FROM hrm.ot_request ot "
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
                if (p instanceof Integer) {
                    ps.setInt(idx++, (Integer) p);
                } else if (p instanceof String) {
                    ps.setString(idx++, (String) p);
                } else if (p instanceof java.sql.Date) {
                    ps.setDate(idx++, (java.sql.Date) p);
                } else if (p instanceof java.sql.Timestamp) {
                    ps.setTimestamp(idx++, (java.sql.Timestamp) p);
                } else {
                    throw new SQLException("Unsupported param type: " + p.getClass());
                }
            }

            ps.setInt(idx++, size);
            ps.setInt(idx, offset);

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

    public List<OTRequest> getApprovedOTs() {
        List<OTRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM hrm.ot_request WHERE status = 'Approved'";
        try (Connection cn = DBContext.getConnection(); PreparedStatement st = cn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            EmployeeDAO ed = new EmployeeDAO();
            while (rs.next()) {
                Employee e = ed.getEmployeeByEmpId(rs.getInt("emp_id"));
                Employee a = ed.getEmployeeByEmpId(rs.getInt("approved_by"));
                list.add(new OTRequest(
                        rs.getInt("ot_id"), e,
                        rs.getDate("date"),
                        rs.getDouble("ot_hours"), a,
                        rs.getTimestamp("approved_at"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        OTRequestDAO dao = new OTRequestDAO();
        List<OTRequest> list = new ArrayList<>();
        System.out.println(dao.countOTByEmpFiltered(1, null, "Pending", null, null));
        list = dao.findOTByEmpPaged(1, 0, 10, null, "Pending", null, null);
        for (OTRequest x : list) {
            System.out.println(x.toString());
        }
    }
}
