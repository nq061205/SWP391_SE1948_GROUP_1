/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;

/**
 *
 * @author admin
 */
public class AttendanceRawDAO extends DBContext {

    private Connection con;
    private String status = "ok";

    public AttendanceRawDAO() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Connection failed: " + e.getMessage();
            e.printStackTrace();
        }
    }

    public void insertRawBatch(List<AttendanceRaw> list) {
        String sql = "INSERT INTO attendance_raw (emp_id, date, check_time, check_type) VALUES (?, ?, ?, ?)";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            for (AttendanceRaw a : list) {
                st.setInt(1, a.getEmp().getEmpId());
                st.setDate(2, a.getDate());
                st.setTime(3, a.getCheckTime());
                st.setString(4, a.getCheckType());
                st.addBatch();
            }
            st.executeBatch();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
