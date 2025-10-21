
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

    public AttendanceRawDAO() {
        try {
            con = DBContext.getConnection();
        } catch (Exception e) {
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
        StringBuilder sql = new StringBuilder("SELECT COUNT(ar.emp_id) ")
                .append("FROM hrm.attendance_raw ar ")
                .append("JOIN hrm.employee e ON ar.emp_id = e.emp_id ")
                .append("WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND e.emp_code LIKE ?");
            params.add("%" + search + "%");
        }
        if (fromDate != null && !fromDate.trim().isEmpty()) {
            sql.append(" AND ar.date >= ?");
            params.add(Date.valueOf(fromDate));
        }
        if (toDate != null && !toDate.trim().isEmpty()) {
            sql.append(" AND ar.date <= ?");
            params.add(Date.valueOf(toDate));
        }
        if (filterType != null && !filterType.trim().isEmpty()) {
            sql.append(" AND ar.check_type = ?");
            params.add(filterType);
        }

        long count = 0;
        try (PreparedStatement st = con.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            for (Object param : params) {
                st.setObject(paramIndex++, param);
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

        StringBuilder sql = new StringBuilder(
                "SELECT ar.emp_id, ar.date, ar.check_time, ar.check_type, e.emp_code, e.fullname ")
                .append("FROM hrm.attendance_raw ar ")
                .append("JOIN hrm.employee e ON ar.emp_id = e.emp_id ")
                .append("WHERE 1=1");

        List<Object> params = new ArrayList<>();

        // Add filters
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND e.emp_code LIKE ?");
            params.add("%" + search + "%");
        }
        if (fromDate != null && !fromDate.trim().isEmpty()) {
            sql.append(" AND ar.date >= ?");
            params.add(Date.valueOf(fromDate));
        }
        if (toDate != null && !toDate.trim().isEmpty()) {
            sql.append(" AND ar.date <= ?");
            params.add(Date.valueOf(toDate));
        }
        if (filterType != null && !filterType.trim().isEmpty()) {
            sql.append(" AND ar.check_type = ?");
            params.add(filterType);
        }

        sql.append(" ORDER BY ar.date DESC, ar.check_time DESC");
        sql.append(" LIMIT ? OFFSET ?");

        List<AttendanceRaw> raws = new ArrayList<>();

        try (PreparedStatement st = con.prepareStatement(sql.toString())) {
            // Set filter parameters
            int paramIndex = 1;
            for (Object param : params) {
                st.setObject(paramIndex++, param);
            }

            // Set LIMIT and OFFSET separately with setInt()
            st.setInt(paramIndex++, pageSize);
            st.setInt(paramIndex, offset);

            System.out.println("Executing SQL: " + sql.toString()); // DEBUG
            System.out.println("Parameters: " + params + ", pageSize=" + pageSize + ", offset=" + offset); // DEBUG

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Employee e = new Employee();
                    e.setEmpId(rs.getInt("emp_id"));
                    e.setEmpCode(rs.getString("emp_code"));
                    e.setFullname(rs.getString("fullname"));

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

    public void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AttendanceRawDAO rawDAO = new AttendanceRawDAO();
        List<AttendanceRaw> test = rawDAO.getRawRecords(0, 10, null, null, null, null);
        System.out.println(test);
    }

}
