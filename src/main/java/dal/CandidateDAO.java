    package dal;

    import java.sql.*;
    import java.util.*;
    import java.util.logging.Level;
    import java.util.logging.Logger;
    import model.Candidate;
    import model.RecruitmentPost;

    public class CandidateDAO extends DBContext {

        private final RecruitmentPostDAO rpDAO = new RecruitmentPostDAO();

        public List<Candidate> getAllCandidate(String result) {
            List<Candidate> candidateList = new ArrayList<>();

            if (result == null) {
                result = "all";
            }

            String sql = "SELECT * FROM candidate";
            switch (result.toLowerCase()) {
                case "pending":
                    sql += " WHERE result IS NULL";
                    break;
                case "approve":
                    sql += " WHERE result = 1";
                    break;
                case "reject":
                    sql += " WHERE result = 0";
                    break;
                case "all":
                default:
                    break;
            }

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
                    Object resultObj = rs.getObject("result");
                    if (resultObj == null) {
                        candidate.setResult(null);
                    } else {
                        candidate.setResult(rs.getBoolean("result"));
                    }

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
            String sql = "SELECT * FROM candidate ORDER BY " + order + " " + direct;
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

        public List<Candidate> getAllCandidateByKeyWord(String key, String result) {
            List<Candidate> candidateList = new ArrayList<>();
            if (key == null || key.trim().isEmpty()) {
                return getAllCandidate("alls");
            }
            String sql = "";
            if (result.equals("pending")) {
                sql = "SELECT * FROM candidate WHERE (name LIKE ? OR email LIKE ? OR phone LIKE ?) AND result IS NULL";
            }
            if (result.equals("approve")) {
                sql = "SELECT * FROM candidate WHERE (name LIKE ? OR email LIKE ? OR phone LIKE ?) AND result = 1";

            }
            if (result.equals("reject")) {
                sql = "SELECT * FROM candidate WHERE (name LIKE ? OR email LIKE ? OR phone LIKE ?) AND result = 0";
            }
            if (result.equals("all")) {
                sql = "SELECT * FROM candidate WHERE (name LIKE ? OR email LIKE ? OR phone LIKE ?)";
            }
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

        public List<Integer> getAllPassCandidateID(String result) {
            List<Integer> idList = new ArrayList<>();
            String sql = "select candidate_id from interview where result=?";
            try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, result);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = 0;
                    id = rs.getInt("candidate_id");
                    idList.add(id);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CandidateDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return idList;
        }

        public List<Candidate> getAllPassedCandidates(List<Integer> candidateIds) {
            List<Candidate> result = new ArrayList<>();
            for (int id : candidateIds) {
                Candidate c = getCandidateById(id);
                if (c != null) {
                    result.add(c);
                }
            }
            return result;
        }

        public List<Candidate> getCandidateByPage(List<Candidate> fullList, int page, int quantityPerPage) {
            List<Candidate> pagedList = new ArrayList<>();

            if (fullList == null || fullList.isEmpty()) {
                return pagedList;
            }

            int start = (page - 1) * quantityPerPage;
            int end = Math.min(start + quantityPerPage, fullList.size());

            if (start >= fullList.size() || start < 0) {
                return pagedList;
            }
            pagedList = fullList.subList(start, end);
            return new ArrayList<>(pagedList);
        }

        public Candidate getCandidateById(int emp_id) {
            String sql = "SELECT * FROM Candidate WHERE candidate_id = ?";
            try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

                stm.setInt(1, emp_id);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        Candidate candidate = new Candidate(
                                rs.getInt("candidate_id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("phone"),
                                rs.getString("CV"),
                                rpDAO.getPostById(rs.getInt("post_id")),
                                rs.getTimestamp("applied_at"),
                                rs.getObject("result") == null ? null : rs.getBoolean("result")
                        );
                        return candidate;
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public boolean updateResultCandidate(int result, int id) {
            String sql = "UPDATE Candidate "
                    + "SET result = ? "
                    + "WHERE candidate_id = ?";

            try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

                stm.setInt(1, result);
                stm.setInt(2, id);

                int rows = stm.executeUpdate();
                return rows > 0;

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            return false;
        }

        public boolean insertCandidate(Candidate c) {
            String checkSql = "SELECT candidate_id FROM candidate WHERE email = ?";
            String insertSql = "INSERT INTO candidate (name, email, phone, cv, post_id, applied_at, result) "
                    + "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, NULL)";
            String updateSql = "UPDATE candidate SET name=?, phone=?, cv=?, post_id=?, applied_at=CURRENT_TIMESTAMP "
                    + "WHERE email=? and result = null";
            try (Connection conn = DBContext.getConnection()) {
                try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                    checkPs.setString(1, c.getEmail());
                    ResultSet rs = checkPs.executeQuery();

                    if (rs.next()) {
                        try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                            ps.setString(1, c.getName());
                            ps.setString(2, c.getPhone());
                            ps.setString(3, c.getCv());
                            ps.setInt(4, c.getPost().getPostId());
                            ps.setString(5, c.getEmail());
                            return ps.executeUpdate() > 0;
                        }
                    } else {
                        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                            ps.setString(1, c.getName());
                            ps.setString(2, c.getEmail());
                            ps.setString(3, c.getPhone());
                            ps.setString(4, c.getCv());
                            ps.setInt(5, c.getPost().getPostId());
                            return ps.executeUpdate() > 0;
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(CandidateDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }

        public static void main(String[] args) {
            CandidateDAO dao = new CandidateDAO();
            List<Integer> idList = dao.getAllPassCandidateID("Pass");
            List<Candidate> canList = dao.getAllPassedCandidates(idList);
            System.out.println(canList);
        }
    }
