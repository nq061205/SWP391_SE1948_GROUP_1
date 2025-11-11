/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.DailyAttendance;
import model.Employee;

/**
 *
 * @author admin
 */
public class DailyAttendanceDAO extends DBContext {

    private final String BASE_SELECT_SQL = "SELECT ad.*, "
            + "e.emp_code, e.fullname, e.email, e.position_title "
            + "FROM daily_attendance ad "
            + "JOIN employee e ON ad.emp_id = e.emp_id ";

    private DailyAttendance mapResultSetToDailyAttendance(ResultSet rs) throws SQLException {
        EmployeeDAO empDAO = new EmployeeDAO();
        Employee e = empDAO.getEmployeeByEmpId(rs.getInt("emp_id"));

        DailyAttendance attendance = new DailyAttendance(
                e,
                rs.getDate("date"),
                rs.getDouble("work_day"),
                rs.getTime("check_in_time"),
                rs.getTime("check_out_time"),
                rs.getDouble("ot_hours"),
                rs.getString("status"),
                rs.getString("note")
        );
        attendance.setLocked(rs.getBoolean("is_locked"));

        return attendance;
    }

    public void upsertDailyAttendance(List<DailyAttendance> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO daily_attendance(emp_id, date, work_day, check_in_time, check_out_time, ot_hours, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "work_day = VALUES(work_day), "
                + "check_in_time = VALUES(check_in_time), "
                + "check_out_time = VALUES(check_out_time), "
                + "ot_hours = VALUES(ot_hours), "
                + "status = VALUES(status)";

        try (Connection conn = DBContext.getConnection()) {
            boolean originalAutoCommit = conn.getAutoCommit();
            try {
                conn.setAutoCommit(false);

                try (PreparedStatement st = conn.prepareStatement(sql)) {
                    int count = 0;
                    for (DailyAttendance d : list) {
                        if (d.getEmployee() == null || d.getEmployee().getEmpId() == 0) {
                            continue;
                        }

                        st.setInt(1, d.getEmployee().getEmpId());
                        st.setDate(2, new java.sql.Date(d.getDate().getTime()));
                        st.setDouble(3, d.getWorkDay());

                        if (d.getCheckInTime() != null) {
                            st.setTime(4, new java.sql.Time(d.getCheckInTime().getTime()));
                        } else {
                            st.setNull(4, Types.TIME);
                        }

                        if (d.getCheckOutTime() != null) {
                            st.setTime(5, new java.sql.Time(d.getCheckOutTime().getTime()));
                        } else {
                            st.setNull(5, Types.TIME);
                        }

                        st.setDouble(6, d.getOtHours());
                        st.setString(7, d.getStatus());
                        st.addBatch();
                        count++;

                        if (count % 1000 == 0) {
                            st.executeBatch();
                        }
                    }

                    if (count % 1000 != 0) {
                        st.executeBatch();
                    }
                }
                conn.commit();

            } catch (Exception e) {
                e.printStackTrace();
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                throw new RuntimeException("Failed to upsert daily attendance", e);
            } finally {
                try {
                    conn.setAutoCommit(originalAutoCommit);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<DailyAttendance> getAll() {
        List<DailyAttendance> list = new ArrayList<>();
        String sql = BASE_SELECT_SQL;

        try (Connection conn = DBContext.getConnection(); PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToDailyAttendance(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public long countAttendance(String search, String department, int selectedMonth, int selectedYear) {
        long count = 0;
        String sql = "SELECT COUNT(ad.emp_id) FROM daily_attendance ad "
                + "JOIN employee e ON ad.emp_id = e.emp_id "
                + "WHERE MONTH(ad.date) = ? AND YEAR(ad.date) = ? ";
        List<Object> params = new ArrayList<>();
        params.add(selectedMonth);
        params.add(selectedYear);
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

    public List<DailyAttendance> getAttendance(int offset, int pageSize, String search, String department, int selectedMonth, int selectedYear) {
        List<DailyAttendance> list = new ArrayList<>();
        String sql = BASE_SELECT_SQL
                + "WHERE MONTH(ad.date) = ? AND YEAR(ad.date) = ? ";
        List<Object> params = new ArrayList<>();
        params.add(selectedMonth);
        params.add(selectedYear);
        if (search != null && !search.isEmpty()) {
            sql += "AND (e.emp_code LIKE ? OR e.fullname LIKE ?) ";
            params.add("%" + search + "%");
            params.add("%" + search + "%");
        }
        if (department != null && !department.isEmpty()) {
            sql += "AND e.dep_id = ? ";
            params.add(department);
        }
        sql += "ORDER BY ad.date DESC, ad.emp_id ASC LIMIT ? OFFSET ?";
        params.add(pageSize);
        params.add(offset);

        try (Connection conn = DBContext.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
            int idx = 1;
            for (Object param : params) {
                st.setObject(idx++, param);
            }
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToDailyAttendance(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DailyAttendance> getAttendanceByEmpIds(List<Integer> empIds, int selectedMonth, int selectedYear) {
        List<DailyAttendance> list = new ArrayList<>();
        if (empIds == null || empIds.isEmpty()) {
            return list;
        }
        StringBuilder inClause = new StringBuilder();
        for (int i = 0; i < empIds.size(); i++) {
            inClause.append("?");
            if (i < empIds.size() - 1) {
                inClause.append(",");
            }
        }
        String sql = BASE_SELECT_SQL
                + "WHERE ad.emp_id IN (" + inClause + ") "
                + "AND MONTH(ad.date) = ? AND YEAR(ad.date) = ? "
                + "ORDER BY ad.emp_id ASC, ad.date ASC";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            for (Integer empId : empIds) {
                ps.setInt(idx++, empId);
            }
            ps.setInt(idx++, selectedMonth);
            ps.setInt(idx, selectedYear);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToDailyAttendance(rs));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public DailyAttendance getDailyAttendentByTime(int empId, int day, int month, int year) {
        DailyAttendance attendance = null;

        String sql = BASE_SELECT_SQL
                + " WHERE ad.emp_id = ? "
                + " AND DAY(ad.date) = ? "
                + " AND MONTH(ad.date) = ? "
                + " AND YEAR(ad.date) = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, empId);
            st.setInt(2, day);
            st.setInt(3, month);
            st.setInt(4, year);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    attendance = mapResultSetToDailyAttendance(rs);
                }
            }

        } catch (Exception e) {
        }
        return attendance;
    }

    public boolean updateDailyAttendance(int empId, String date, String status,
            double workDay, double otHours, String note) {
        String sql = "UPDATE daily_attendance SET status = ?, work_day = ?, ot_hours = ?, note = ? "
                + "WHERE emp_id = ? AND date = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setDouble(2, workDay);
            ps.setDouble(3, otHours);
            ps.setString(4, note);
            ps.setInt(5, empId);
            ps.setString(6, date);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Double> getTotalWorkData(int empId, int month, int year) {
        String sql = "SELECT COALESCE(SUM(work_day), 0) as total_work_day, "
                + "COALESCE(SUM(ot_hours), 0) as total_ot_hours "
                + "FROM daily_attendance "
                + "WHERE emp_id = ? AND MONTH(date) = ? AND YEAR(date) = ? ";

        Map<String, Double> result = new HashMap<>();
        result.put("totalWorkDay", 0.0);
        result.put("totalOTHours", 0.0);

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.setInt(2, month);
            ps.setInt(3, year);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double totalWorkDay = rs.getDouble("total_work_day");
                    double totalOTHours = rs.getDouble("total_ot_hours");

                    result.put("totalWorkDay", totalWorkDay);
                    result.put("totalOTHours", totalOTHours);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<Integer, Map<String, Double>> getTotalWorkDataByEmpIds(List<Integer> empIds, int month, int year) {
        Map<Integer, Map<String, Double>> result = new HashMap<>();

        if (empIds == null || empIds.isEmpty()) {
            return result;
        }

        StringBuilder sql = new StringBuilder("SELECT emp_id, COALESCE(SUM(work_day), 0) as total_work_day, "
                + "COALESCE(SUM(ot_hours), 0) as total_ot_hours "
                + "FROM daily_attendance "
                + "WHERE MONTH(date) = ? AND YEAR(date) = ? "
                + "AND emp_id IN (");

        for (int i = 0; i < empIds.size(); i++) {
            sql.append("?");
            if (i < empIds.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(") GROUP BY emp_id");

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ps.setInt(1, month);
            ps.setInt(2, year);

            for (int i = 0; i < empIds.size(); i++) {
                ps.setInt(3 + i, empIds.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int empId = rs.getInt("emp_id");
                    Map<String, Double> data = new HashMap<>();
                    data.put("totalWorkDay", rs.getDouble("total_work_day"));
                    data.put("totalOTHours", rs.getDouble("total_ot_hours"));
                    result.put(empId, data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean lockAttendanceForMonth(int month, int year) {
        String sql = "UPDATE daily_attendance "
                + "SET is_locked = 1 "
                + "WHERE MONTH(date) = ? AND YEAR(date) = ? "
                + "AND is_locked = 0";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, year);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAttendanceLocked(int month, int year) {
        String sql = "SELECT COUNT(*) FROM daily_attendance "
                + "WHERE MONTH(date) = ? AND YEAR(date) = ? "
                + "AND is_locked = 0";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int unlockedCount = rs.getInt(1);
                    return unlockedCount == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean unlockAttendance(int empId, String date) {
        String sql = "UPDATE daily_attendance SET is_locked = FALSE "
                + "WHERE emp_id = ? AND date = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.setString(2, date);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAndLockAttendance(int empId, String date, String status,
            double workDay, double otHours, String note) {
        String sql = "UPDATE daily_attendance "
                + "SET status = ?, work_day = ?, ot_hours = ?, note = ?, is_locked = 1 "
                + "WHERE emp_id = ? AND date = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setDouble(2, workDay);
            ps.setDouble(3, otHours);
            ps.setString(4, note);
            ps.setInt(5, empId);
            ps.setString(6, date);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean relockAttendance(int empId, String date) {
        String sql = "UPDATE daily_attendance SET is_locked = 1 WHERE emp_id = ? AND date = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ps.setString(2, date);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        DailyAttendanceDAO dailyDAO = new DailyAttendanceDAO();
        int empId = 1;
        int month = 10;
        int year = 2025;
        Map<String, Double> result = dailyDAO.getTotalWorkData(empId, month, year);

        System.out.println("Tổng ngày làm việc: " + result.get("totalWorkDay"));
        System.out.println("Tổng giờ tăng ca: " + result.get("totalOTHours"));
    }

}
