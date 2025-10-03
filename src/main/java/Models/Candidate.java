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
public class Candidate {

    private int candidateId;
    private String name;
    private String email;
    private String phone;
    private String cv;
    private RecruitmentPost post;
    private Timestamp appliedAt;

    public Candidate() {
    }

    public Candidate(int candidateId, String name, String email, String phone, String cv, RecruitmentPost post, Timestamp appliedAt) {
        this.candidateId = candidateId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cv = cv;
        this.post = post;
        this.appliedAt = appliedAt;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public RecruitmentPost getPostId() {
        return post;
    }

    public void setPostId(RecruitmentPost post) {
        this.post = post;
    }

    public Timestamp getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(Timestamp appliedAt) {
        this.appliedAt = appliedAt;
    }
}
