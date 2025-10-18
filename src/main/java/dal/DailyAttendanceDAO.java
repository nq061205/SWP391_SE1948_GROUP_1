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

}
