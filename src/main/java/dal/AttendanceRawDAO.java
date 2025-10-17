/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.AttendanceRaw;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import model.Employee;

/**
 *
 * @author admin
 */
public class AttendanceRawDAO extends DBContext {

    private Connection con;

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

    public List<AttendanceRaw> getAllRawAttendance() {
        String sql = "SELECT emp_id, date, check_time, check_type FROM attendance_raw";
        List<AttendanceRaw> raws = new ArrayList<>();

        try (PreparedStatement st = con.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Employee e = new EmployeeDAO().getEmployeeByEmpId(rs.getInt("emp_id"));
                AttendanceRaw a = new AttendanceRaw(e,
                        rs.getDate("date"),
                        rs.getTime("check_time"),
                        rs.getString("check_type"));
                raws.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return raws;
    }

    public long countRawRecords(String search, String fromDate, String toDate, String filterType) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM attendance_raw WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (search != null) {
            sql.append(" AND emp_id = ?");
            params.add(Integer.parseInt(search));
        }
        if (fromDate != null) {
            sql.append(" AND date >= ?");
            params.add(Date.valueOf(fromDate));
        }
        if (toDate != null) {
            sql.append(" AND date <= ?");
            params.add(Date.valueOf(toDate));
        }
        if (filterType != null) {
            sql.append(" AND check_type = ?");
            params.add(filterType);
        }

        long count = 0;
        try (PreparedStatement st = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    count = rs.getLong(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<AttendanceRaw> getRawRecords(int offset, int pageSize, String search, String fromDate, String toDate, String filterType) {
        if (offset < 0) {
            offset = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }
        StringBuilder sql = new StringBuilder("SELECT emp_id, date, check_time, check_type FROM attendance_raw WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (search != null) {
            sql.append(" AND emp_id = ?");
            params.add(Integer.parseInt(search));
        }
        if (fromDate != null) {
            sql.append(" AND date >= ?");
            params.add(Date.valueOf(fromDate));
        }
        if (toDate != null) {
            sql.append(" AND date <= ?");
            params.add(Date.valueOf(toDate));
        }
        if (filterType != null) {
            sql.append(" AND check_type = ?");
            params.add(filterType);
        }
        sql.append(" ORDER BY date DESC, check_time DESC");
        sql.append(" LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add(offset);

        List<AttendanceRaw> raws = new ArrayList<>();

        try (PreparedStatement st = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Employee e = new EmployeeDAO().getEmployeeByEmpId(rs.getInt("emp_id"));
                    AttendanceRaw a = new AttendanceRaw(e,
                            rs.getDate("date"),
                            rs.getTime("check_time"),
                            rs.getString("check_type"));
                    raws.add(a);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return raws;
    }

    public static void main(String[] args) {
        AttendanceRawDAO rawDAO = new AttendanceRawDAO();
        List<AttendanceRaw> test = rawDAO.getAllRawAttendance();
        System.out.println(test);
    }

}
