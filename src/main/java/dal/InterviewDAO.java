/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Interview;

/**
 *
 * @author hgduy
 */
public class InterviewDAO {
    private final CandidateDAO cDAO = new CandidateDAO();
    private final EmployeeDAO eDAO = new EmployeeDAO();
    
    public void insertToInterview(Interview interview){
        String sql = "Insert into interview (candidate_id, created_by,interviewed_by,date,time,result,create_at, update_at))values (?,?,?,?,?,?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            
            stm.setInt(1, interview.getCandidate().getCandidateId());
            stm.setInt(2, interview.getCreatedBy().getEmpId());
            stm.setInt(3, interview.getInterviewedBy().getEmpId());
            stm.setDate(4, interview.getDate());
            stm.setTime(5, interview.getTime());
            stm.setString(6, interview.getResult());
            stm.setTimestamp(7, interview.getCreatedAt());
            stm.setTimestamp(8, interview.getUpdatedAt());
            stm.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
}
}
