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

    public List<Interview> getFilteredInterviewsNotInEmployee(
            String result,
            String searchKey,
            String startApplyDate,
            String endApplyDate,
            String startInterviewDate,
            String endInterviewDate,
            int page,
            int pageSize
    ) {
        List<Interview> interList = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT i.* "
                + "FROM interview i "
                + "JOIN candidate c ON i.candidate_id = c.candidate_id "
                + "LEFT JOIN employee e ON e.email = c.email "
                + "WHERE i.result = ? AND e.emp_id IS NULL "
        );

        if (searchKey != null && !searchKey.isEmpty()) {
            sql.append("AND (c.name LIKE ? OR c.email LIKE ?) ");
        }
        if (startApplyDate != null && !startApplyDate.isEmpty()) {
            sql.append("AND c.applied_at >= ? ");
        }
        if (endApplyDate != null && !endApplyDate.isEmpty()) {
            sql.append("AND c.applied_at <= ? ");
        }
        if (startInterviewDate != null && !startInterviewDate.isEmpty()) {
            sql.append("AND i.date >= ? ");
        }
        if (endInterviewDate != null && !endInterviewDate.isEmpty()) {
            sql.append("AND i.date <= ? ");
        }
        sql.append("LIMIT ? OFFSET ? ");

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            ps.setString(index++, result);

            if (searchKey != null && !searchKey.isEmpty()) {
                ps.setString(index++, "%" + searchKey + "%");
                ps.setString(index++, "%" + searchKey + "%");
            }
            if (startApplyDate != null && !startApplyDate.isEmpty()) {
                ps.setDate(index++, Date.valueOf(startApplyDate));
            }
            if (endApplyDate != null && !endApplyDate.isEmpty()) {
                ps.setDate(index++, Date.valueOf(endApplyDate));
            }
            if (startInterviewDate != null && !startInterviewDate.isEmpty()) {
                ps.setDate(index++, Date.valueOf(startInterviewDate));
            }
            if (endInterviewDate != null && !endInterviewDate.isEmpty()) {
                ps.setDate(index++, Date.valueOf(endInterviewDate));
            }

            ps.setInt(index++, pageSize);
            ps.setInt(index++, (page - 1) * pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Interview inter = new Interview();
                inter.setInterviewId(rs.getInt("interview_id"));

                Candidate can = cDAO.getCandidateById(rs.getInt("candidate_id"));
                inter.setCandidate(can);

                Employee interviewBy = eDAO.getEmployeeByEmpId(rs.getInt("interviewed_by"));
                inter.setInterviewedBy(interviewBy);

                inter.setDate(rs.getDate("date"));
                inter.setTime(rs.getTime("time"));
                inter.setResult(rs.getString("result"));

                interList.add(inter);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return interList;
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

    public int countFilteredInterviewsNotInEmployee(
            String result,
            String searchKey,
            String startApplyDate,
            String endApplyDate,
            String startInterviewDate,
            String endInterviewDate) {

        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM interview i "
                + "JOIN candidate c ON i.candidate_id = c.candidate_id "
                + "LEFT JOIN employee e ON e.email = c.email "
                + "WHERE i.result = ? AND e.emp_id IS NULL "
        );

        if (searchKey != null && !searchKey.isEmpty()) {
            sql.append("AND (c.name LIKE ? OR c.email LIKE ?) ");
        }
        if (startApplyDate != null && !startApplyDate.isEmpty()) {
            sql.append("AND c.applied_at >= ? ");
        }
        if (endApplyDate != null && !endApplyDate.isEmpty()) {
            sql.append("AND c.applied_at <= ? ");
        }
        if (startInterviewDate != null && !startInterviewDate.isEmpty()) {
            sql.append("AND i.date >= ? ");
        }
        if (endInterviewDate != null && !endInterviewDate.isEmpty()) {
            sql.append("AND i.date <= ? ");
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            ps.setString(index++, result);

            if (searchKey != null && !searchKey.isEmpty()) {
                ps.setString(index++, "%" + searchKey + "%");
                ps.setString(index++, "%" + searchKey + "%");
            }
            if (startApplyDate != null && !startApplyDate.isEmpty()) {
                ps.setDate(index++, Date.valueOf(startApplyDate));
            }
            if (endApplyDate != null && !endApplyDate.isEmpty()) {
                ps.setDate(index++, Date.valueOf(endApplyDate));
            }
            if (startInterviewDate != null && !startInterviewDate.isEmpty()) {
                ps.setDate(index++, Date.valueOf(startInterviewDate));
            }
            if (endInterviewDate != null && !endInterviewDate.isEmpty()) {
                ps.setDate(index++, Date.valueOf(endInterviewDate));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void main(String[] args) {
        InterviewDAO d = new InterviewDAO();
        System.out.println(d.getFilteredInterviewsNotInEmployee("Pass", "", "", "", "", "", 1, 5));
    }
}
