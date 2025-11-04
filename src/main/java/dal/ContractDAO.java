/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Contract;
import model.Employee;

/**
 * @author Do Quang Huy_HE191197
 */
public class ContractDAO {

    EmployeeDAO empDAO = new EmployeeDAO();
    private final String BASE_SELECT_SQL = "SELECT c.*,e.emp_code "
            + "FROM Contract c "
            + "LEFT JOIN Employee e ON c.emp_id = e.emp_id ";

    private Contract mapResultSetToContract(ResultSet rs) throws SQLException {
        Contract con = new Contract();
        con.setContractId(rs.getInt("contract_id"));
        Employee emp = empDAO.getEmployeeByEmpCode(rs.getString("emp_code"));
        con.setEmployee(emp);
        con.setType(rs.getString("type"));
        con.setStartDate(rs.getDate("start_date"));
        con.setEndDate(rs.getDate("end_date"));
        con.setContractImg(rs.getString("contract_img"));
        return con;
    }

    public Contract getContractByEmployeeCode(String empCode) {
        String sql = BASE_SELECT_SQL + "where e.emp_code=?";
        Contract con = null;
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, empCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    con = mapResultSetToContract(rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return con;
    }

    public List<String> getAllType() {
        List<String> typeList = new ArrayList<>();
        String sql = "SELECT DISTINCT type FROM Contract WHERE type IS NOT NULL;";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {

            while (rs.next()) {
                typeList.add(rs.getString("type"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ContractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return typeList;
    }

    public void updateContract(Contract contract) {
        String sql = "Update Contract set type=?,start_date=?,end_date=?,contract_img=? where contract_id=?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, contract.getType());
            ps.setDate(2, contract.getStartDate());
            ps.setDate(3, contract.getEndDate());
            ps.setString(4, contract.getContractImg());
            ps.setInt(5, contract.getContractId());
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ContractDAO conDAO = new ContractDAO();
        Contract con = conDAO.getContractByEmployeeCode("E002");
        con.setType("Probation");
        conDAO.updateContract(con);
        System.out.println(con);
    }

}
