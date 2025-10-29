package dal;

import model.Department;
import model.Employee;
import model.RecruitmentPost;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecruitmentPostDAO extends DBContext {

    public List<RecruitmentPost> getApprovedPosts() {
        List<RecruitmentPost> approvedPosts = new ArrayList<>();
        String sql = "SELECT rp.post_id, rp.title, rp.content, rp.status, "
                   + "rp.created_by, rp.approved_by, rp.approved_at, rp.created_at, rp.updated_at, "
                   + "d.dep_id, d.dep_name, d.description AS dep_description, "
                   + "e1.emp_id AS created_emp_id, e1.emp_code AS created_emp_code, "
                   + "e1.fullname AS created_fullname, e1.email AS created_email, e1.position_title AS created_position, "
                   + "e2.emp_id AS approved_emp_id, e2.emp_code AS approved_emp_code, "
                   + "e2.fullname AS approved_fullname, e2.email AS approved_email, e2.position_title AS approved_position "
                   + "FROM RecruitmentPost rp "
                   + "LEFT JOIN Department d ON rp.dep_id = d.dep_id "
                   + "LEFT JOIN Employee e1 ON rp.created_by = e1.emp_id "
                   + "LEFT JOIN Employee e2 ON rp.approved_by = e2.emp_id "
                   + "WHERE rp.status = 'Approved' "
                   + "ORDER BY rp.approved_at DESC";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                approvedPosts.add(mapResultSetToPost(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return approvedPosts;
    }

    public List<RecruitmentPost> getAllPosts() {
        List<RecruitmentPost> allPosts = new ArrayList<>();
        String sql = "SELECT rp.post_id, rp.title, rp.content, rp.status, "
                   + "rp.created_by, rp.approved_by, rp.approved_at, rp.created_at, rp.updated_at, "
                   + "d.dep_id, d.dep_name, d.description AS dep_description, "
                   + "e1.emp_id AS created_emp_id, e1.emp_code AS created_emp_code, "
                   + "e1.fullname AS created_fullname, e1.email AS created_email, e1.position_title AS created_position, "
                   + "e2.emp_id AS approved_emp_id, e2.emp_code AS approved_emp_code, "
                   + "e2.fullname AS approved_fullname, e2.email AS approved_email, e2.position_title AS approved_position "
                   + "FROM RecruitmentPost rp "
                   + "LEFT JOIN Department d ON rp.dep_id = d.dep_id "
                   + "LEFT JOIN Employee e1 ON rp.created_by = e1.emp_id "
                   + "LEFT JOIN Employee e2 ON rp.approved_by = e2.emp_id "
                   + "WHERE rp.status <> 'Deleted' "
                   + "ORDER BY rp.created_at DESC";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                allPosts.add(mapResultSetToPost(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allPosts;
    }

    public RecruitmentPost getPostById(int postId) {
        String sql = "SELECT rp.post_id, rp.title, rp.content, rp.status, "
                   + "rp.created_by, rp.approved_by, rp.approved_at, rp.created_at, rp.updated_at, "
                   + "d.dep_id, d.dep_name, d.description AS dep_description, "
                   + "e1.emp_id AS created_emp_id, e1.emp_code AS created_emp_code, "
                   + "e1.fullname AS created_fullname, e1.email AS created_email, e1.position_title AS created_position, "
                   + "e2.emp_id AS approved_emp_id, e2.emp_code AS approved_emp_code, "
                   + "e2.fullname AS approved_fullname, e2.email AS approved_email, e2.position_title AS approved_position "
                   + "FROM RecruitmentPost rp "
                   + "LEFT JOIN Department d ON rp.dep_id = d.dep_id "
                   + "LEFT JOIN Employee e1 ON rp.created_by = e1.emp_id "
                   + "LEFT JOIN Employee e2 ON rp.approved_by = e2.emp_id "
                   + "WHERE rp.post_id = ? AND rp.status <> 'Deleted'";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPost(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<RecruitmentPost> getPendingAndRejectedPosts() {
        List<RecruitmentPost> posts = new ArrayList<>();
        String sql = "SELECT rp.post_id, rp.title, rp.content, rp.status, "
                   + "rp.created_by, rp.approved_by, rp.approved_at, rp.created_at, rp.updated_at, "
                   + "d.dep_id, d.dep_name, d.description AS dep_description, "
                   + "e1.emp_id AS created_emp_id, e1.emp_code AS created_emp_code, "
                   + "e1.fullname AS created_fullname, e1.email AS created_email, e1.position_title AS created_position "
                   + "FROM RecruitmentPost rp "
                   + "LEFT JOIN Department d ON rp.dep_id = d.dep_id "
                   + "LEFT JOIN Employee e1 ON rp.created_by = e1.emp_id "
                   + "WHERE (rp.status = 'Pending' OR rp.status = 'Rejected') "
                   + "ORDER BY rp.created_at DESC";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                posts.add(mapResultSetToPost(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public boolean createPost(String title, String content, String depId, int createdBy, int approvedBy) {
        String sql = "INSERT INTO RecruitmentPost "
                   + "(title, content, dep_id, status, created_by, approved_by, created_at, updated_at) "
                   + "VALUES (?, ?, ?, 'Pending', ?, ?, NOW(), NOW())";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, content);
            ps.setString(3, depId);
            ps.setInt(4, createdBy);
            ps.setInt(5, approvedBy);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePost(int postId, String title, String content, String depId) {
        String sql = "UPDATE RecruitmentPost SET title = ?, content = ?, dep_id = ?, "
                   + "status = 'Pending', updated_at = NOW() "
                   + "WHERE post_id = ? AND status <> 'Deleted'";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, content);
            ps.setString(3, depId);
            ps.setInt(4, postId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean approvePost(int postId, int approvedBy, String note) {
        String sql = "UPDATE RecruitmentPost "
                   + "SET status = 'Approved', approved_by = ?, approved_at = NOW(), updated_at = NOW() "
                   + "WHERE post_id = ? AND status = 'Pending'";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, approvedBy);
            ps.setInt(2, postId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectPost(int postId, int rejectedBy) {
        String sql = "UPDATE RecruitmentPost "
                   + "SET status = 'Rejected', approved_by = ?, approved_at = NOW(), updated_at = NOW() "
                   + "WHERE post_id = ? AND status = 'Pending'";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rejectedBy);
            ps.setInt(2, postId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePost(int postId) {
        String sql = "UPDATE RecruitmentPost SET status = 'Deleted', updated_at = NOW() WHERE post_id = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, postId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT dep_id, dep_name, description FROM Department ORDER BY dep_name";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Department d = new Department();
                d.setDepId(rs.getString("dep_id"));
                d.setDepName(rs.getString("dep_name"));
                d.setDescription(rs.getString("description"));
                departments.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }

    private RecruitmentPost mapResultSetToPost(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setDepId(rs.getString("dep_id"));
        department.setDepName(rs.getString("dep_name"));
        department.setDescription(rs.getString("dep_description"));

        Employee createdBy = new Employee();
        createdBy.setEmpId(rs.getInt("created_emp_id"));
        createdBy.setEmpCode(rs.getString("created_emp_code"));
        createdBy.setFullname(rs.getString("created_fullname"));
        createdBy.setEmail(rs.getString("created_email"));
        createdBy.setPositionTitle(rs.getString("created_position"));

        Employee approvedBy = null;
        int approvedEmpId = rs.getInt("approved_emp_id");
        if (approvedEmpId != 0) {
            approvedBy = new Employee();
            approvedBy.setEmpId(approvedEmpId);
            approvedBy.setEmpCode(rs.getString("approved_emp_code"));
            approvedBy.setFullname(rs.getString("approved_fullname"));
            approvedBy.setEmail(rs.getString("approved_email"));
            approvedBy.setPositionTitle(rs.getString("approved_position"));
        }

        RecruitmentPost post = new RecruitmentPost();
        post.setPostId(rs.getInt("post_id"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setStatus(rs.getString("status"));
        post.setCreatedBy(createdBy);
        post.setApprovedBy(approvedBy);
        post.setDepartment(department);
        post.setApprovedAt(rs.getTimestamp("approved_at"));
        post.setCreatedAt(rs.getTimestamp("created_at"));
        post.setUpdatedAt(rs.getTimestamp("updated_at"));
        return post;
    }
    
    public static void main(String[] args) {
        RecruitmentPostDAO rDAO = new RecruitmentPostDAO();
        for (RecruitmentPost arg : rDAO.getAllPosts()) {
            System.out.println(arg);
        }
    }
}
