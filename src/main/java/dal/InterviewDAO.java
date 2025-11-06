package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Interview;
import model.Candidate;
import model.Employee;

public class InterviewDAO {

    private final CandidateDAO cDAO = new CandidateDAO();
    private final EmployeeDAO eDAO = new EmployeeDAO();

    public void insertToInterview(Interview interview) {
        String sql = "INSERT INTO Interview (candidate_id, created_by, interviewed_by, date, time, result) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, interview.getCandidate().getCandidateId());
            stm.setInt(2, interview.getCreatedBy().getEmpId());
            stm.setInt(3, interview.getInterviewedBy().getEmpId());
            stm.setDate(4, interview.getDate());
            stm.setTime(5, interview.getTime());
            stm.setString(6, interview.getResult());

            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Interview> getAllInterviews() {
        List<Interview> list = new ArrayList<>();
        String sql = "SELECT i.*, "
                + "c.candidate_id, c.name AS candidate_name, c.email AS candidate_email, c.phone AS candidate_phone, "
                + "e1.emp_id AS created_emp_id, e1.fullname AS created_name, "
                + "e2.emp_id AS interviewed_emp_id, e2.fullname AS interviewed_name "
                + "FROM Interview i "
                + "LEFT JOIN Candidate c ON i.candidate_id = c.candidate_id "
                + "LEFT JOIN Employee e1 ON i.created_by = e1.emp_id "
                + "LEFT JOIN Employee e2 ON i.interviewed_by = e2.emp_id";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Interview interview = new Interview();

                interview.setInterviewId(rs.getInt("interview_id"));
                interview.setDate(rs.getDate("date"));
                interview.setTime(rs.getTime("time"));
                interview.setResult(rs.getString("result"));
                interview.setCreatedAt(rs.getTimestamp("created_at"));
                interview.setUpdatedAt(rs.getTimestamp("updated_at"));
                Candidate candidate = cDAO.getCandidateById(rs.getInt("candidate_id"));
                interview.setCandidate(candidate);
                Employee createdBy = eDAO.getEmployeeByEmpId(rs.getInt("created_by"));
                interview.setCreatedBy(createdBy);
                Employee interviewedBy = eDAO.getEmployeeByEmpId(rs.getInt("interviewed_by"));
                interview.setInterviewedBy(interviewedBy);

                list.add(interview);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<Interview> searchInterviewsByCandidate(String keyword) {
        List<Interview> list = new ArrayList<>();
        String sql = "SELECT i.*, "
                + "c.candidate_id, c.name AS candidate_name, c.email AS candidate_email, c.phone AS candidate_phone, "
                + "e1.emp_id AS created_emp_id, e1.fullname AS created_name, "
                + "e2.emp_id AS interviewed_emp_id, e2.fullname AS interviewed_name "
                + "FROM Interview i "
                + "LEFT JOIN Candidate c ON i.candidate_id = c.candidate_id "
                + "LEFT JOIN Employee e1 ON i.created_by = e1.emp_id "
                + "LEFT JOIN Employee e2 ON i.interviewed_by = e2.emp_id "
                + "WHERE c.name LIKE ? or c.email LIKE ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            String pattern = "%" + keyword + "%";
            stm.setString(1, pattern);
            stm.setString(2, pattern);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Interview interview = new Interview();

                    interview.setInterviewId(rs.getInt("interview_id"));
                    interview.setDate(rs.getDate("date"));
                    interview.setTime(rs.getTime("time"));
                    interview.setResult(rs.getString("result"));
                    interview.setCreatedAt(rs.getTimestamp("created_at"));
                    interview.setUpdatedAt(rs.getTimestamp("updated_at"));

                    Candidate candidate = cDAO.getCandidateById(rs.getInt("candidate_id"));
                    interview.setCandidate(candidate);

                    Employee createdBy = eDAO.getEmployeeByEmpId(rs.getInt("created_by"));
                    interview.setCreatedBy(createdBy);

                    Employee interviewedBy = eDAO.getEmployeeByEmpId(rs.getInt("interviewed_by"));
                    interview.setInterviewedBy(interviewedBy);

                    list.add(interview);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public Interview getInterviewById(int interviewId) {
        String sql = "SELECT i.*, "
                + "c.candidate_id, c.name AS candidate_name, c.email AS candidate_email, c.phone AS candidate_phone, "
                + "e1.emp_id AS created_emp_id, e1.fullname AS created_name, "
                + "e2.emp_id AS interviewed_emp_id, e2.fullname AS interviewed_name "
                + "FROM Interview i "
                + "LEFT JOIN Candidate c ON i.candidate_id = c.candidate_id "
                + "LEFT JOIN Employee e1 ON i.created_by = e1.emp_id "
                + "LEFT JOIN Employee e2 ON i.interviewed_by = e2.emp_id "
                + "WHERE i.interview_id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, interviewId);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Interview interview = new Interview();
                    interview.setInterviewId(rs.getInt("interview_id"));
                    interview.setDate(rs.getDate("date"));
                    interview.setTime(rs.getTime("time"));
                    interview.setResult(rs.getString("result"));
                    interview.setCreatedAt(rs.getTimestamp("created_at"));
                    interview.setUpdatedAt(rs.getTimestamp("updated_at"));

                    // Lấy các đối tượng liên quan
                    Candidate candidate = cDAO.getCandidateById(rs.getInt("candidate_id"));
                    interview.setCandidate(candidate);

                    Employee createdBy = eDAO.getEmployeeByEmpId(rs.getInt("created_by"));
                    interview.setCreatedBy(createdBy);

                    Employee interviewedBy = eDAO.getEmployeeByEmpId(rs.getInt("interviewed_by"));
                    interview.setInterviewedBy(interviewedBy);

                    return interview;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null; 
    }

    public static void main(String[] args) {
        InterviewDAO d = new InterviewDAO();
        System.out.println(d.getInterviewById(36));
    }
}
