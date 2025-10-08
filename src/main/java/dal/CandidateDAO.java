package dal;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Candidate;

public class CandidateDAO extends DBContext {

    private final RecruitmentPostDAO rpDAO = new RecruitmentPostDAO();

    public List<Candidate> getAllCandidate() {
        List<Candidate> candidateList = new ArrayList<>();
        String sql = "select * from candidate";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setCandidateId(rs.getInt("candidate_id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setCv(rs.getString("cv"));
                candidate.setPost(rpDAO.getPostById(rs.getInt("post_id")));
                candidate.setAppliedAt(rs.getTimestamp("applied_at"));
                candidate.setResult(rs.getBoolean("result"));
                candidateList.add(candidate);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CandidateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return candidateList;
    }

    public List<Candidate> getAllCandidateOrderBy(String order, String direct) {
        List<Candidate> candidateList = new ArrayList<>();
        if (!order.equals("name") && !order.equals("applied_at")) {
            order = "name";
        }
        if (!direct.equalsIgnoreCase("asc") && !direct.equalsIgnoreCase("desc")) {
            direct = "asc";
        }
        String sql = "select * from candidate order by " + order + " " + direct;
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setCandidateId(rs.getInt("candidate_id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setCv(rs.getString("cv"));
                candidate.setPost(rpDAO.getPostById(rs.getInt("post_id")));
                candidate.setAppliedAt(rs.getTimestamp("applied_at"));
                candidate.setResult(rs.getBoolean("result"));
                candidateList.add(candidate);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CandidateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return candidateList;
    }

    public List<Candidate> getAllCandidateByKeyWord(String key) {
        List<Candidate> candidateList = new ArrayList<>();
        if (key == null || key.trim().isEmpty()) {
            return getAllCandidate();
        }
        String sql = "select * from candidate where name like ? or email like ? or phone like ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + key + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Candidate candidate = new Candidate();
                    candidate.setCandidateId(rs.getInt("candidate_id"));
                    candidate.setName(rs.getString("name"));
                    candidate.setEmail(rs.getString("email"));
                    candidate.setPhone(rs.getString("phone"));
                    candidate.setCv(rs.getString("cv"));
                    candidate.setPost(rpDAO.getPostById(rs.getInt("post_id")));
                    candidate.setAppliedAt(rs.getTimestamp("applied_at"));
                    candidate.setResult(rs.getBoolean("result"));
                    candidateList.add(candidate);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CandidateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return candidateList;
    }

    public static void main(String[] args) {
        CandidateDAO dao = new CandidateDAO();
        for (Candidate c : dao.getAllCandidateByKeyWord("81")) {
            System.out.println(c);
        }
    }
}
