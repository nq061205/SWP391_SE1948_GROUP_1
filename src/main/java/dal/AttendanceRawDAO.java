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
import model.Department;
import model.Employee;

/**
 *
 * @author admin
 */
public class AttendanceRawDAO extends DBContext {

    public List<AttendanceRaw> findDuplicates(List<AttendanceRaw> list) {
        List<AttendanceRaw> duplicates = new ArrayList<>();
        String sql = "SELECT * FROM attendance_raw WHERE emp_id = ? AND date = ? AND check_time = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
            for (AttendanceRaw a : list) {
                st.setInt(1, a.getEmp().getEmpId());
                st.setDate(2, a.getDate());
                st.setTime(3, a.getCheckTime());

                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        duplicates.add(a);
                    }
                }
                st.clearParameters();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duplicates;
    }

    public void deleteExisting(List<AttendanceRaw> list) throws Exception {
        String sql = "DELETE FROM attendance_raw WHERE emp_id = ? AND date = ? AND check_time = ?";
        Connection conn = DBContext.getConnection();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                for (AttendanceRaw a : list) {
                    st.setInt(1, a.getEmp().getEmpId());
                    st.setDate(2, a.getDate());
                    st.setTime(3, a.getCheckTime());
                    st.addBatch();
                }
                st.executeBatch();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public void insertRawBatch(List<AttendanceRaw> list) throws Exception {
        if (list == null || list.isEmpty()) {
            return;
        }
        String sql = "INSERT INTO attendance_raw (emp_id, date, check_time, check_type) VALUES (?, ?, ?, ?)";
        Connection conn = DBContext.getConnection();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                for (AttendanceRaw a : list) {
                    if (a.getDate() == null || a.getCheckTime() == null) {
                        throw new Exception("Date or Time is null for emp_id: " + a.getEmp().getEmpId());
                    }
                    st.setInt(1, a.getEmp().getEmpId());
                    st.setDate(2, a.getDate());
                    st.setTime(3, a.getCheckTime());
                    st.setString(4, a.getCheckType());
                    st.addBatch();
                }
                st.executeBatch();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public void insertRawBatchWithOverwrite(List<AttendanceRaw> list) throws Exception {
        deleteExisting(list);
        insertRawBatch(list);
    }

    public List<AttendanceRaw> getAllRawAttendance() {
        String sql = "SELECT ar.emp_id, ar.date, ar.check_time, ar.check_type, e.emp_code, e.fullname "
                + "FROM attendance_raw ar "
                + "JOIN employee e ON ar.emp_id = e.emp_id";
        List<AttendanceRaw> raws = new ArrayList<>();

        try (Connection conn = DBContext.getConnection(); PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return raws;
    }

    public List<AttendanceRaw> getRawRecordsByDate(int offset, int pageSize, String search,
            String date, String filterType, String department) {
        List<AttendanceRaw> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ar.*, e.emp_code, e.fullname, e.dep_id, d.dep_name ");
        sql.append("FROM attendance_raw ar ");
        sql.append("INNER JOIN employee e ON ar.emp_id = e.emp_id ");
        sql.append("LEFT JOIN department d ON e.dep_id = d.dep_id ");
        sql.append("WHERE ar.date = ? ");

        if (search != null && !search.isEmpty()) {
            sql.append("AND (e.emp_code LIKE ? OR e.fullname LIKE ?) ");
        }
        if (filterType != null && !filterType.isEmpty()) {
            sql.append("AND ar.check_type = ? ");
        }
        if (department != null && !department.isEmpty()) {
            sql.append("AND e.dep_id = ? ");
        }

        sql.append("ORDER BY e.emp_code ASC, ar.check_time ASC ");
        sql.append("LIMIT ? OFFSET ?");

        try (Connection conn = DBContext.getConnection(); PreparedStatement st = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            st.setString(paramIndex++, date);

            if (search != null && !search.isEmpty()) {
                st.setString(paramIndex++, "%" + search + "%");
                st.setString(paramIndex++, "%" + search + "%");
            }
            if (filterType != null && !filterType.isEmpty()) {
                st.setString(paramIndex++, filterType);
            }
            if (department != null && !department.isEmpty()) {
                st.setString(paramIndex++, department);
            }

            st.setInt(paramIndex++, pageSize);
            st.setInt(paramIndex++, offset);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setEmpId(rs.getInt("emp_id"));
                    emp.setEmpCode(rs.getString("emp_code"));
                    emp.setFullname(rs.getString("fullname"));

                    Department dept = new Department();
                    dept.setDepId(rs.getString("dep_id"));
                    dept.setDepName(rs.getString("dep_name"));
                    emp.setDept(dept);

                    AttendanceRaw raw = new AttendanceRaw();
                    raw.setId(rs.getInt("id"));
                    raw.setEmp(emp);
                    raw.setDate(rs.getDate("date"));
                    raw.setCheckTime(rs.getTime("check_time"));
                    raw.setCheckType(rs.getString("check_type"));

                    list.add(raw);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public long countRawRecordsByDate(String search, String date, String filterType, String department) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM attendance_raw ar ");
        sql.append("INNER JOIN employee e ON ar.emp_id = e.emp_id ");
        sql.append("WHERE ar.date = ? ");

        if (search != null && !search.isEmpty()) {
            sql.append("AND (e.emp_code LIKE ? OR e.fullname LIKE ?) ");
        }
        if (filterType != null && !filterType.isEmpty()) {
            sql.append("AND ar.check_type = ? ");
        }
        if (department != null && !department.isEmpty()) {
            sql.append("AND e.dep_id = ? ");
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement st = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            st.setString(paramIndex++, date);

            if (search != null && !search.isEmpty()) {
                st.setString(paramIndex++, "%" + search + "%");
                st.setString(paramIndex++, "%" + search + "%");
            }
            if (filterType != null && !filterType.isEmpty()) {
                st.setString(paramIndex++, filterType);
            }
            if (department != null && !department.isEmpty()) {
                st.setString(paramIndex++, department);
            }

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        AttendanceRawDAO dao = new AttendanceRawDAO();
        long c = dao.countRawRecordsByDate(null, "2025-10-25", null, null);
        System.out.println(c);
    }
}
