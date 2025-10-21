/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.DailyAttendance;
import model.Employee;

/**
 *
 * @author admin
 */
public class DailyAttendanceDAO extends DBContext {

    private Connection con;

    public DailyAttendanceDAO() {
        try {
            con = DBContext.getConnection();
        } catch (Exception e) {
        }
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

        PreparedStatement st = null;
        try {
            boolean originalAutoCommit = con.getAutoCommit();
            con.setAutoCommit(false);

            st = con.prepareStatement(sql);

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
                    st.setNull(4, Types.TIME);  // FIX: Dùng TIME thay vì TIMESTAMP
                }

                if (d.getCheckOutTime() != null) {
                    st.setTime(5, new java.sql.Time(d.getCheckOutTime().getTime()));
                } else {
                    st.setNull(5, Types.TIME);  // FIX: Dùng TIME thay vì TIMESTAMP
                }

                st.setDouble(6, d.getOtHours());
                st.setString(7, d.getStatus());
                st.addBatch();
                count++;

                if (count % 1000 == 0) {
                    int[] results = st.executeBatch();
                }
            }

            // Execute remaining batch
            if (count % 1000 != 0) {
                int[] results = st.executeBatch();
            }

            // FIX: COMMIT transaction
            con.commit();

            // Restore original autoCommit
            con.setAutoCommit(originalAutoCommit);

        } catch (Exception e) {
            e.printStackTrace();

            // FIX: ROLLBACK nếu có lỗi
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // FIX: Throw exception để caller biết có lỗi
            throw new RuntimeException("Failed to upsert daily attendance", e);

        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<DailyAttendance> getAll() {
        List<DailyAttendance> list = new ArrayList<>();
        String sql = "SELECT emp_id, date, work_day, check_in_time, check_out_time, ot_hours, status FROM daily_attendance";

        try (PreparedStatement st = con.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Employee e = new EmployeeDAO().getEmployeeByEmpId(rs.getInt("emp_id"));
                DailyAttendance d = new DailyAttendance(
                        e,
                        rs.getDate("date"),
                        rs.getDouble("work_day"),
                        rs.getTime("check_in_time"),
                        rs.getTime("check_out_time"),
                        rs.getDouble("ot_hours"),
                        rs.getString("status")
                );
                list.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public long countAttendance(String search, String department, int selectedMonth, int selectedYear) {
        long count = 0;
        String sql = "SELECT COUNT(*) FROM daily_attendance ad "
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
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
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
        String sql = "SELECT ad.emp_id, ad.date, ad.work_day, ad.check_in_time, ad.check_out_time, ad.ot_hours, ad.status "
                + "FROM daily_attendance ad "
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
        sql += "ORDER BY ad.date DESC, ad.emp_id ASC LIMIT ? OFFSET ?";
        params.add(pageSize);
        params.add(offset);

        try (PreparedStatement st = con.prepareStatement(sql)) {
            int idx = 1;
            for (Object param : params) {
                st.setObject(idx++, param);
            }
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Employee e = new EmployeeDAO().getEmployeeByEmpId(rs.getInt("emp_id"));
                    DailyAttendance d = new DailyAttendance(
                            e,
                            rs.getDate("date"),
                            rs.getDouble("work_day"),
                            rs.getTime("check_in_time"),
                            rs.getTime("check_out_time"),
                            rs.getDouble("ot_hours"),
                            rs.getString("status")
                    );
                    list.add(d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        String sql = "SELECT * FROM daily_attendance WHERE emp_id IN (" + inClause + ") "
                + "AND MONTH(date) = ? AND YEAR(date) = ? "
                + "ORDER BY emp_id ASC, date ASC";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            for (Integer empId : empIds) {
                ps.setInt(idx++, empId);
            }
            ps.setInt(idx++, selectedMonth);
            ps.setInt(idx, selectedYear);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee e = new EmployeeDAO().getEmployeeByEmpId(rs.getInt("emp_id"));
                    DailyAttendance d = new DailyAttendance(
                            e,
                            rs.getDate("date"),
                            rs.getDouble("work_day"),
                            rs.getTime("check_in_time"),
                            rs.getTime("check_out_time"),
                            rs.getDouble("ot_hours"),
                            rs.getString("status")
                    );
                    list.add(d);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        DailyAttendanceDAO dailyDAO = new DailyAttendanceDAO();
        List<DailyAttendance> list = dailyDAO.getAttendance(0, 5, null, null, 12, 2025);
        System.out.println(list.size());
    }
}
