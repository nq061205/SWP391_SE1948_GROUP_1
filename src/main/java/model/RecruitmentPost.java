/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Timestamp;
/**
 *
 * @author admin
 */
public class RecruitmentPost {

    private int postId;
    private String title;
    private String content;
    private String status; 
    private Employee createdBy;
    private Employee approvedBy;
    private Department department;
    private Timestamp approvedAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public RecruitmentPost() {
    }

    public RecruitmentPost(int postId, String title, String content, String status, Employee createdBy, Employee approvedBy, Department department, Timestamp approvedAt, Timestamp createdAt, Timestamp updatedAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
        this.department = department;
        this.approvedAt = approvedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Employee getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Employee createdBy) {
        this.createdBy = createdBy;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Employee approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Timestamp getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(Timestamp approveAt) {
        this.approvedAt = approveAt;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "RecruitmentPost{" + "postId=" + postId + ", title=" + title + ", content=" + content + ", status=" + status + ", createdBy=" + createdBy + ", approvedBy=" + approvedBy + ", department=" + department + ", approvedAt=" + approvedAt + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }

    
    
}
