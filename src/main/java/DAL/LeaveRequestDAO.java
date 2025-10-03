/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.*;
import Models.LeaveRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Nguyen Dinh Quy HE190184
 */
public class LeaveRequestDAO {

    private Connection connection;
    private UserDAO userDAO = new UserDAO();

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
            Employee employee = userDAO.getEmployeeByEmpId(emp_id);
            while (rs.next()) {
                list.add(new LeaveRequest(
                        rs.getInt("leave_id"),
                        employee,
                        rs.getString("leave_type"),
                        rs.getString("reason"),
                        rs.getDouble("day_requested"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        userDAO.getEmployeeByEmpId(rs.getInt("approved_by")),
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

    public static void main(String[] args) {
        LeaveRequestDAO dao = new LeaveRequestDAO();
        System.out.println(dao.getLeaveRequestByEmpId(1).toString());
    }
}
