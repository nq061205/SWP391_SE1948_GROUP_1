/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
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
        return new DailyAttendance(
                e,
                rs.getDate("date"),
                rs.getDouble("work_day"),
                rs.getTime("check_in_time"),
                rs.getTime("check_out_time"),
                rs.getDouble("ot_hours"),
                rs.getString("status"),
                rs.getString("note")
        );
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

        try (Connection conn = DBContext.getConnection();
             PreparedStatement st = conn.prepareStatement(sql); 
             ResultSet rs = st.executeQuery()) {

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

        try (Connection conn = DBContext.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
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

    public static void main(String[] args) {
        DailyAttendanceDAO dailyDAO = new DailyAttendanceDAO();
        EmployeeDAO empDAO = new EmployeeDAO();
        List<Employee> employees = empDAO.getEmployees(0, 5, null, null);
        List<Integer> empIds = employees.stream().map(e -> e.getEmpId()).collect(Collectors.toList());
        List<DailyAttendance> list = dailyDAO.getAttendanceByEmpIds(empIds, 10, 2025);
        System.out.println(list);
    }
}