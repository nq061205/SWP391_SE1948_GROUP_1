/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;
import java.sql.Timestamp;
/**
 *
 * @author admin
 */
public class RecruitmentPost {

    private int postId;
    private String title;
    private String content;
    private String status;   // Approve, Reject, Pending
    private boolean isDelete;
    private Employee createdBy;
    private Employee approvedBy;
    private Department department;
    private Timestamp approvedAt;

    public RecruitmentPost() {
    }

    public RecruitmentPost(int postId, String title, String content, String status, boolean isDelete,
            Employee createdBy, Employee approvedBy, Department dep, Timestamp approvedAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.isDelete = isDelete;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
        this.department = dep;
        this.approvedAt = approvedAt;
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

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
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

    public Department getDept() {
        return department;
    }

    public void setDepId(Department dep) {
        this.department = dep;
    }

    public Timestamp getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(Timestamp approveAt) {
        this.approvedAt = approveAt;
    }

    @Override
    public String toString() {
        return "RecruitmentPost{" + "postId=" + postId + ", title=" + title + ", content=" + content + ", status=" + status + ", isDelete=" + isDelete + ", createdBy=" + createdBy + ", approvedBy=" + approvedBy + ", department=" + department + ", approvedAt=" + approvedAt + '}';
    }
    
}
