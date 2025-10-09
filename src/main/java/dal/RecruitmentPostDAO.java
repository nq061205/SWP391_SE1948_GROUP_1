/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Department;
import model.Employee;
import model.RecruitmentPost;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dat Tran
 */
public class RecruitmentPostDAO {
    
    /**
     * Get all approved recruitment posts with department and employee information
     * @return List of approved RecruitmentPost objects
     */
    public List<RecruitmentPost> getApprovedPosts() {
        List<RecruitmentPost> approvedPosts = new ArrayList<>();
        String sql = "SELECT " +
                     "rp.post_id, " +
                     "rp.title, " +
                     "rp.content, " +
                     "rp.status, " +
                     "rp.is_delete, " +
                     "rp.created_by, " +
                     "rp.approved_by, " +
                     "rp.approved_at, " +
                     "rp.created_at, " +
                     "rp.updated_at, " +
                     "d.dep_id, " +
                     "d.dep_name, " +
                     "d.description as dep_description, " +
                     "e1.emp_id as created_emp_id, " +
                     "e1.emp_code as created_emp_code, " +
                     "e1.fullname as created_fullname, " +
                     "e1.email as created_email, " +
                     "e1.position_title as created_position, " +
                     "e2.emp_id as approved_emp_id, " +
                     "e2.emp_code as approved_emp_code, " +
                     "e2.fullname as approved_fullname, " +
                     "e2.email as approved_email, " +
                     "e2.position_title as approved_position " +
                     "FROM RecruitmentPost rp " +
                     "LEFT JOIN Department d ON rp.dep_id = d.dep_id " +
                     "LEFT JOIN Employee e1 ON rp.created_by = e1.emp_id " +
                     "LEFT JOIN Employee e2 ON rp.approved_by = e2.emp_id " +
                     "WHERE rp.status = 'Approved' AND rp.is_delete = FALSE " +
                     "ORDER BY rp.approved_at DESC";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                // Create Department object
                Department department = new Department();
                department.setDepId(rs.getString("dep_id"));
                department.setDepName(rs.getString("dep_name"));
                department.setDescription(rs.getString("dep_description"));
                
                // Create Created By Employee object
                Employee createdBy = new Employee();
                createdBy.setEmpId(rs.getInt("created_emp_id"));
                createdBy.setEmpCode(rs.getString("created_emp_code"));
                createdBy.setFullname(rs.getString("created_fullname"));
                createdBy.setEmail(rs.getString("created_email"));
                createdBy.setPositionTitle(rs.getString("created_position"));
                
                // Create Approved By Employee object (might be null)
                Employee approvedBy = null;
                if (rs.getInt("approved_emp_id") != 0) {
                    approvedBy = new Employee();
                    approvedBy.setEmpId(rs.getInt("approved_emp_id"));
                    approvedBy.setEmpCode(rs.getString("approved_emp_code"));
                    approvedBy.setFullname(rs.getString("approved_fullname"));
                    approvedBy.setEmail(rs.getString("approved_email"));
                    approvedBy.setPositionTitle(rs.getString("approved_position"));
                }
                
                // Create RecruitmentPost object
                RecruitmentPost post = new RecruitmentPost();
                post.setPostId(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setStatus(rs.getString("status"));
                post.setDelete(rs.getBoolean("is_delete"));
                post.setCreatedBy(createdBy);
                post.setApprovedBy(approvedBy);
                post.setDepartment(department);
                post.setApprovedAt(rs.getTimestamp("approved_at"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                post.setUpdatedAt(rs.getTimestamp("updated_at"));
                
                approvedPosts.add(post);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting approved recruitment posts: " + e.getMessage());
            e.printStackTrace();
        }
        
        return approvedPosts;
    }
    
    /**
     * Get all recruitment posts (including pending, approved, rejected)
     * @return List of all RecruitmentPost objects
     */
    public List<RecruitmentPost> getAllPosts() {
        List<RecruitmentPost> allPosts = new ArrayList<>();
        String sql = "SELECT " +
                     "rp.post_id, " +
                     "rp.title, " +
                     "rp.content, " +
                     "rp.status, " +
                     "rp.is_delete, " +
                     "rp.created_by, " +
                     "rp.approved_by, " +
                     "rp.approved_at, " +
                     "rp.created_at, " +
                     "rp.updated_at, " +
                     "d.dep_id, " +
                     "d.dep_name, " +
                     "d.description as dep_description, " +
                     "e1.emp_id as created_emp_id, " +
                     "e1.emp_code as created_emp_code, " +
                     "e1.fullname as created_fullname, " +
                     "e1.email as created_email, " +
                     "e1.position_title as created_position, " +
                     "e2.emp_id as approved_emp_id, " +
                     "e2.emp_code as approved_emp_code, " +
                     "e2.fullname as approved_fullname, " +
                     "e2.email as approved_email, " +
                     "e2.position_title as approved_position " +
                     "FROM RecruitmentPost rp " +
                     "LEFT JOIN Department d ON rp.dep_id = d.dep_id " +
                     "LEFT JOIN Employee e1 ON rp.created_by = e1.emp_id " +
                     "LEFT JOIN Employee e2 ON rp.approved_by = e2.emp_id " +
                     "WHERE rp.is_delete = FALSE " +
                     "ORDER BY rp.created_at DESC";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                // Create Department object
                Department department = new Department();
                department.setDepId(rs.getString("dep_id"));
                department.setDepName(rs.getString("dep_name"));
                department.setDescription(rs.getString("dep_description"));
                
                // Create Created By Employee object
                Employee createdBy = new Employee();
                createdBy.setEmpId(rs.getInt("created_emp_id"));
                createdBy.setEmpCode(rs.getString("created_emp_code"));
                createdBy.setFullname(rs.getString("created_fullname"));
                createdBy.setEmail(rs.getString("created_email"));
                createdBy.setPositionTitle(rs.getString("created_position"));
                
                // Create Approved By Employee object (might be null)
                Employee approvedBy = null;
                if (rs.getInt("approved_emp_id") != 0) {
                    approvedBy = new Employee();
                    approvedBy.setEmpId(rs.getInt("approved_emp_id"));
                    approvedBy.setEmpCode(rs.getString("approved_emp_code"));
                    approvedBy.setFullname(rs.getString("approved_fullname"));
                    approvedBy.setEmail(rs.getString("approved_email"));
                    approvedBy.setPositionTitle(rs.getString("approved_position"));
                }
                
                // Create RecruitmentPost object
                RecruitmentPost post = new RecruitmentPost();
                post.setPostId(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setStatus(rs.getString("status"));
                post.setDelete(rs.getBoolean("is_delete"));
                post.setCreatedBy(createdBy);
                post.setApprovedBy(approvedBy);
                post.setDepartment(department);
                post.setApprovedAt(rs.getTimestamp("approved_at"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                post.setUpdatedAt(rs.getTimestamp("updated_at"));
                
                allPosts.add(post);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all recruitment posts: " + e.getMessage());
            e.printStackTrace();
        }
        
        return allPosts;
    }
    
    /**
     * Get recruitment post by ID
     * @param postId
     * @return RecruitmentPost object or null if not found
     */
    public RecruitmentPost getPostById(int postId) {
        String sql = "SELECT " +
                     "rp.post_id, " +
                     "rp.title, " +
                     "rp.content, " +
                     "rp.status, " +
                     "rp.is_delete, " +
                     "rp.created_by, " +
                     "rp.approved_by, " +
                     "rp.approved_at, " +
                     "rp.created_at, " +
                     "rp.updated_at, " +
                     "d.dep_id, " +
                     "d.dep_name, " +
                     "d.description as dep_description, " +
                     "e1.emp_id as created_emp_id, " +
                     "e1.emp_code as created_emp_code, " +
                     "e1.fullname as created_fullname, " +
                     "e1.email as created_email, " +
                     "e1.position_title as created_position, " +
                     "e2.emp_id as approved_emp_id, " +
                     "e2.emp_code as approved_emp_code, " +
                     "e2.fullname as approved_fullname, " +
                     "e2.email as approved_email, " +
                     "e2.position_title as approved_position " +
                     "FROM RecruitmentPost rp " +
                     "LEFT JOIN Department d ON rp.dep_id = d.dep_id " +
                     "LEFT JOIN Employee e1 ON rp.created_by = e1.emp_id " +
                     "LEFT JOIN Employee e2 ON rp.approved_by = e2.emp_id " +
                     "WHERE rp.post_id = ? AND rp.is_delete = FALSE";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Create Department object
                    Department department = new Department();
                    department.setDepId(rs.getString("dep_id"));
                    department.setDepName(rs.getString("dep_name"));
                    department.setDescription(rs.getString("dep_description"));
                    
                    // Create Created By Employee object
                    Employee createdBy = new Employee();
                    createdBy.setEmpId(rs.getInt("created_emp_id"));
                    createdBy.setEmpCode(rs.getString("created_emp_code"));
                    createdBy.setFullname(rs.getString("created_fullname"));
                    createdBy.setEmail(rs.getString("created_email"));
                    createdBy.setPositionTitle(rs.getString("created_position"));
                    
                    // Create Approved By Employee object (might be null)
                    Employee approvedBy = null;
                    if (rs.getInt("approved_emp_id") != 0) {
                        approvedBy = new Employee();
                        approvedBy.setEmpId(rs.getInt("approved_emp_id"));
                        approvedBy.setEmpCode(rs.getString("approved_emp_code"));
                        approvedBy.setFullname(rs.getString("approved_fullname"));
                        approvedBy.setEmail(rs.getString("approved_email"));
                        approvedBy.setPositionTitle(rs.getString("approved_position"));
                    }
                    
                    // Create RecruitmentPost object
                    RecruitmentPost post = new RecruitmentPost();
                    post.setPostId(rs.getInt("post_id"));
                    post.setTitle(rs.getString("title"));
                    post.setContent(rs.getString("content"));
                    post.setStatus(rs.getString("status"));
                    post.setDelete(rs.getBoolean("is_delete"));
                    post.setCreatedBy(createdBy);
                    post.setApprovedBy(approvedBy);
                    post.setDepartment(department);
                    post.setApprovedAt(rs.getTimestamp("approved_at"));
                    post.setCreatedAt(rs.getTimestamp("created_at"));
                    post.setUpdatedAt(rs.getTimestamp("updated_at"));
                    
                    return post;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting recruitment post by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Get pending and rejected recruitment posts for notification table
     * @return List of pending and rejected RecruitmentPost objects
     */
    public List<RecruitmentPost> getPendingAndRejectedPosts() {
        List<RecruitmentPost> posts = new ArrayList<>();
        String sql = "SELECT " +
                     "rp.post_id, " +
                     "rp.title, " +
                     "rp.content, " +
                     "rp.status, " +
                     "rp.is_delete, " +
                     "rp.created_by, " +
                     "rp.approved_by, " +
                     "rp.approved_at, " +
                     "rp.created_at, " +
                     "rp.updated_at, " +
                     "d.dep_id, " +
                     "d.dep_name, " +
                     "d.description as dep_description, " +
                     "e1.emp_id as created_emp_id, " +
                     "e1.emp_code as created_emp_code, " +
                     "e1.fullname as created_fullname, " +
                     "e1.email as created_email, " +
                     "e1.position_title as created_position " +
                     "FROM RecruitmentPost rp " +
                     "LEFT JOIN Department d ON rp.dep_id = d.dep_id " +
                     "LEFT JOIN Employee e1 ON rp.created_by = e1.emp_id " +
                     "WHERE (rp.status = 'Pending' OR rp.status = 'Rejected') AND rp.is_delete = FALSE " +
                     "ORDER BY rp.created_at DESC";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                // Create Department object
                Department department = new Department();
                department.setDepId(rs.getString("dep_id"));
                department.setDepName(rs.getString("dep_name"));
                department.setDescription(rs.getString("dep_description"));
                
                // Create Created By Employee object
                Employee createdBy = new Employee();
                createdBy.setEmpId(rs.getInt("created_emp_id"));
                createdBy.setEmpCode(rs.getString("created_emp_code"));
                createdBy.setFullname(rs.getString("created_fullname"));
                createdBy.setEmail(rs.getString("created_email"));
                createdBy.setPositionTitle(rs.getString("created_position"));
                
                // Create RecruitmentPost object
                RecruitmentPost post = new RecruitmentPost();
                post.setPostId(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setStatus(rs.getString("status"));
                post.setDelete(rs.getBoolean("is_delete"));
                post.setCreatedBy(createdBy);
                post.setDepartment(department);
                post.setApprovedAt(rs.getTimestamp("approved_at"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                post.setUpdatedAt(rs.getTimestamp("updated_at"));
                
                posts.add(post);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting pending and rejected recruitment posts: " + e.getMessage());
            e.printStackTrace();
        }
        
        return posts;
    }
    
    /**
     * Create a new recruitment post
     * @param title
     * @param content
     * @param depId
     * @param createdBy
     * @param approvedBy
     * @return true if successful, false otherwise
     */
    public boolean createPost(String title, String content, String depId, int createdBy, int approvedBy) {
        String sql = "INSERT INTO RecruitmentPost (title, content, dep_id, status, is_delete, created_by, approved_by, created_at, updated_at) " +
                     "VALUES (?, ?, ?, 'Pending', FALSE, ?, ?, NOW(), NOW())";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, title);
            ps.setString(2, content);
            ps.setString(3, depId);
            ps.setInt(4, createdBy);
            ps.setInt(5, approvedBy);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating recruitment post: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update recruitment post (for rejected posts being updated)
     * @param postId
     * @param title
     * @param content
     * @param depId
     * @return true if successful, false otherwise
     */
    public boolean updatePost(int postId, String title, String content, String depId) {
        String sql = "UPDATE RecruitmentPost SET title = ?, content = ?, dep_id = ?, status = 'Pending', updated_at = NOW() " +
                     "WHERE post_id = ? AND is_delete = FALSE";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, title);
            ps.setString(2, content);
            ps.setString(3, depId);
            ps.setInt(4, postId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating recruitment post: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get all departments for dropdown
     * @return List of Department objects
     */
    public List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT dep_id, dep_name, description FROM Department ORDER BY dep_name";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Department department = new Department();
                department.setDepId(rs.getString("dep_id"));
                department.setDepName(rs.getString("dep_name"));
                department.setDescription(rs.getString("description"));
                departments.add(department);
                System.out.println("Loaded department: " + department.getDepId() + " - " + department.getDepName());
            }
            
            System.out.println("Total departments loaded: " + departments.size());
            
        } catch (SQLException e) {
            System.err.println("Error getting departments: " + e.getMessage());
            e.printStackTrace();
        }
        
        return departments;
    }
}