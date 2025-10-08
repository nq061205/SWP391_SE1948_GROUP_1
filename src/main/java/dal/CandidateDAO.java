/*
 * Click nbfs://nbhost/SystemFileSystem/Tcandidatelates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Tcandidatelates/Classes/Class.java to edit this tcandidatelate
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
import model.Department;
import model.Employee;
import model.Role;

/**
 *
 * @author hgduy
 */
public class CandidateDAO extends DBContext {

    private final RoleDAO roleDAO = new RoleDAO();
    private final RecruitmentPostDAO rpDAO = new RecruitmentPostDAO();
    private Connection connection;
    private String status = "ok";
    private List<Candidate> candidateList;

    public CandidateDAO() {
        try {
            this.connection = DBContext.getConnection();
        } catch (Exception e) {
            status = "Connection failed: " + e.getMessage();
            e.printStackTrace();
        }
    }

    public List<Candidate> getAllCandidate() {
        candidateList = new ArrayList<>();
        String sql = "SELECT * FROM Candidate";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setCandidateId(rs.getInt("candidate_id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setCv(rs.getString("CV"));
                candidate.setPost(rpDAO.getPostById(rs.getInt("post_id")));
                candidate.setAppliedAt(rs.getTimestamp("applied_at"));
                candidate.setResult(rs.getBoolean("result"));
                candidateList.add(candidate);

            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return candidateList;
    }

    public static void main(String[] args) {
        CandidateDAO d = new CandidateDAO();
        for (Candidate arg : d.getAllCandidate()) {
            System.out.println(arg.toString());
        }
    }
}
