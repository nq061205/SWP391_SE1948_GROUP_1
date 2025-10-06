/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.*;
import Models.LeaveRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author Nguyen Dinh Quy HE190184
 */
public class LeaveRequestDAO {

    private Connection connection;
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public LeaveRequestDAO() {
        try {
            connection = new DBContext().getConnection();
        } catch (Exception e) {
        }
    }

    public ArrayList<LeaveRequest> getLeaveRequestByEmpId(int emp_id) {
        ArrayList<LeaveRequest> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM hrm.leave_request where emp_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, emp_id);
            ResultSet rs = stm.executeQuery();
            Employee employee = employeeDAO.getEmployeeByEmpId(emp_id);
            while (rs.next()) {
                list.add(new LeaveRequest(
                        rs.getInt("leave_id"),
                        employee,
                        rs.getString("leave_type"),
                        rs.getString("reason"),
                        rs.getDouble("day_requested"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        employeeDAO.getEmployeeByEmpId(rs.getInt("approved_by")),
                        rs.getTimestamp("approved_at"),
                        rs.getString("status"),
                        rs.getString("note"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public LeaveRequest getLeaveRequestByLeaveId(int leave_id, int emp_id) {
        try {
            String sql = "SELECT * FROM hrm.leave_request where leave_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, leave_id);
            ResultSet rs = stm.executeQuery();
            Employee employee = employeeDAO.getEmployeeByEmpId(emp_id);
            if (rs.next()) {
                LeaveRequest leaveRequest = new LeaveRequest(
                        rs.getInt("leave_id"),
                        employee,
                        rs.getString("leave_type"),
                        rs.getString("reason"),
                        rs.getDouble("day_requested"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        employeeDAO.getEmployeeByEmpId(rs.getInt("approved_by")),
                        rs.getTimestamp("approved_at"),
                        rs.getString("status"),
                        rs.getString("note"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                return leaveRequest;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int composeLeaveRequest(int emp_id, String leave_type, String reason, Date startDate, Date endDate,int approvedBy) {
        try {
            String sql = "insert into hrm.leave_request\n"
                    + "(emp_id, leave_type, reason, day_requested, start_date, end_date,approved_by, status, created_at, updated_at)\n"
                    + "values\n"
                    + "(?, ?, ?, ?, ?, ?, ?,'Pending',?, ?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, emp_id);
            stm.setString(2, leave_type);
            stm.setString(3, reason);
            stm.setDouble(4, startDate.getTime() - endDate.getTime() + 1);
            stm.setDate(5, startDate);
            stm.setDate(6, endDate);
            stm.setInt(7,approvedBy);
            stm.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            stm.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            int rows = stm.executeUpdate();
                return rows;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        LeaveRequestDAO dao = new LeaveRequestDAO();
        System.out.println(""+dao.composeLeaveRequest(1, "Sick", "hhoho", Date.valueOf("2025-12-12"), Date.valueOf("2025-12-12"),1));
    }
}
