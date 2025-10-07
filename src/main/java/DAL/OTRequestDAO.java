/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Nguyen Dinh Quy HE190184
 */
public class OTRequestDAO {

    private Connection connection;
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public OTRequestDAO() {
        try {
            connection = new DBContext().getConnection();
        } catch (Exception e) {
        }
    }

    public ArrayList<OTRequest> getOTRequestByEmpId(int emp_id) {
        ArrayList<OTRequest> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM hrm.ot_request where emp_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, emp_id);
            ResultSet rs = stm.executeQuery();
            Employee employee = employeeDAO.getEmployeeByEmpId(emp_id);
            while (rs.next()) {
                list.add(new OTRequest(
                        rs.getInt("ot_id"),
                        employee,
                        rs.getDate("date"),
                        rs.getDouble("ot_hours"),
                        employeeDAO.getEmployeeByEmpId(rs.getInt("approved_by")),
                        rs.getTimestamp("approved_at"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public OTRequest getOTRequestByOTId(int ot_id, int emp_id) {
        try {
            String sql = "SELECT * FROM hrm.ot_request where ot_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, ot_id);
            ResultSet rs = stm.executeQuery();
            Employee employee = employeeDAO.getEmployeeByEmpId(emp_id);
            if (rs.next()) {
                OTRequest OTRequest = new OTRequest(
                        rs.getInt("ot_id"),
                        employee,
                        rs.getDate("date"),
                        rs.getDouble("ot_hours"),
                        employeeDAO.getEmployeeByEmpId(rs.getInt("approved_by")),
                        rs.getTimestamp("approved_at"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                return OTRequest;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        OTRequestDAO dao = new OTRequestDAO();
        System.out.println(dao.getOTRequestByEmpId(1).toString());
    }
}
