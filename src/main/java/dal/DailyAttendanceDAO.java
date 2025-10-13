/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import model.DailyAttendance;

/**
 *
 * @author admin
 */
public class DailyAttendanceDAO extends DBContext {
//    public void upsert(DailyAttendance d) {
//        try (Connection con = DBContext.getConnection()) {
//            String sql = """
//                INSERT INTO attendance_daily (emp_id, date, check_in_time, check_out_time, work_day, ot_hours, status)
//                VALUES (?, ?, ?, ?, ?, ?, ?)
//                ON DUPLICATE KEY UPDATE
//                    check_in_time = VALUES(check_in_time),
//                    check_out_time = VALUES(check_out_time),
//                    work_day = VALUES(work_day),
//                    ot_hours = VALUES(ot_hours),
//                    status = VALUES(status)
//            """;
//
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setInt(1, d.getEmployee().getEmpId());
//            ps.setDate(2, d.getDate());
//            ps.setTime(3, d.getCheckInTime());
//            ps.setTime(4, d.getCheckOutTime());
//            ps.setDouble(5, d.getWorkDay());
//            ps.setDouble(6, d.getOtHours());
//            ps.setString(7, d.getStatus());
//            ps.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
