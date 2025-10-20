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

        String sql = "INSERT INTO daily_attendance(emp_id, date, work_day, check_in_time, check_out_time, ot_hours, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)"
                + "ON DUPLICATE KEY UPDATE"
                + "work_day = VALUES(work_day),"
                + "check_in_time = VALUES(check_in_time),"
                + "check_out_time = VALUES(check_out_time),"
                + "ot_hours = VALUES(ot_hours),"
                + "status = VALUES(status)";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            for (DailyAttendance d : list) {
                st.setInt(1, d.getEmployee().getEmpId());
                st.setDate(2, new java.sql.Date(d.getDate().getTime()));
                st.setDouble(3, d.getWorkDay());
                if (d.getCheckInTime() != null) {
                    st.setTime(4, new java.sql.Time(d.getCheckInTime().getTime()));
                } else {
                    st.setNull(4, Types.TIMESTAMP);
                }

                if (d.getCheckOutTime() != null) {
                    st.setTime(5, new java.sql.Time(d.getCheckOutTime().getTime()));
                } else {
                    st.setNull(5, Types.TIMESTAMP);
                }
                st.setDouble(6, d.getOtHours());
                st.setString(7, d.getStatus());
                st.addBatch();
            }

            st.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
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

    public List<DailyAttendance> getMonthlyAttendance(int year, int month, String department, String search, String sortBy) {
        List<DailyAttendance> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT da.*, e.emp_id, e.emp_code, e.name, e.department ");
        sql.append("FROM daily_attendance da ");
        sql.append("JOIN employee e ON da.emp_id = e.emp_id ");
        sql.append("WHERE YEAR(da.date) = ? AND MONTH(da.date) = ? ");

        List<Object> params = new ArrayList<>();
        params.add(year);
        params.add(month);
        if (department != null && !department.isEmpty()) {
            sql.append("AND e.department = ? ");
            params.add(department);
        }
        if (search != null && !search.trim().isEmpty()) {
            sql.append("AND (e.emp_code LIKE ? OR e.name LIKE ?) ");
            String searchPattern = "%" + search.trim() + "%";
            params.add(searchPattern);
            params.add(searchPattern);
        }
        if ("name".equals(sortBy)) {
            sql.append("ORDER BY e.name, da.date");
        } else if ("department".equals(sortBy)) {
            sql.append("ORDER BY e.department, e.emp_code, da.date");
        } else {
            sql.append("ORDER BY e.emp_code, da.date");
        }

        try (PreparedStatement st = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = st.executeQuery()) {
                EmployeeDAO empDAO = new EmployeeDAO();
                while (rs.next()) {
                    Employee e = empDAO.getEmployeeByEmpId(rs.getInt("emp_id"));
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

    public DailyAttendance getAttendanceByEmpCodeAndDate(String empCode, java.util.Date date) {
        String sql = "SELECT da.* FROM daily_attendance da "
                + "JOIN employee e ON da.emp_id = e.emp_id "
                + "WHERE e.emp_code = ? AND da.date = ?";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, empCode);
            st.setDate(2, new java.sql.Date(date.getTime()));

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Employee e = new EmployeeDAO().getEmployeeByEmpCode(empCode);

                    return new DailyAttendance(
                            e,
                            rs.getDate("date"),
                            rs.getDouble("work_day"),
                            rs.getTime("check_in_time"),
                            rs.getTime("check_out_time"),
                            rs.getDouble("ot_hours"),
                            rs.getString("status")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public int countEmployeesWithAttendance(int year, int month, String department, String search) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(DISTINCT e.emp_id) as total ");
        sql.append("FROM employee e ");
        sql.append("JOIN daily_attendance da ON e.emp_id = da.emp_id ");
        sql.append("WHERE YEAR(da.date) = ? AND MONTH(da.date) = ? ");

        List<Object> params = new ArrayList<>();
        params.add(year);
        params.add(month);

        if (department != null && !department.isEmpty()) {
            sql.append("AND e.department = ? ");
            params.add(department);
        }

        if (search != null && !search.trim().isEmpty()) {
            sql.append("AND (e.emp_code LIKE ? OR e.name LIKE ?) ");
            String searchPattern = "%" + search.trim() + "%";
            params.add(searchPattern);
            params.add(searchPattern);
        }

        try (PreparedStatement st = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
