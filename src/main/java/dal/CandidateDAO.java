/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Candidate;
import model.RecruitmentPost;

/**
* @author Do Quang Huy_HE191197
*/
public class CandidateDAO extends DBContext{
    private Connection connection;
    private List<Candidate> candidateList;
    
     public CandidateDAO() {
        try {
            connection = new DBContext().getConnection();
        } catch (Exception e) {
        }
    }
    private List<Candidate> getAllPassesCandidate(boolean result) {
        candidateList = new ArrayList<>();
        String sql = "select * from Candidate where result=?";
        RecruitmentPostDAO rpDAO = new RecruitmentPostDAO();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, result);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Candidate can = new Candidate();
                can.setCandidateId(rs.getInt("candidate_id"));
                can.setName(rs.getString("name"));
                can.setEmail(rs.getString("email"));
                can.setPhone("phone");
                RecruitmentPost post = rpDAO.getPostById(rs.getInt("post_id"));
                can.setPost(post);    
                candidateList.add(can);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CandidateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return candidateList;
    } 
}
