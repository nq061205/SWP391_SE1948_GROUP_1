/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 *
 * @author admin
 */
public class Interview {

    private int interviewId;
    private int candidateId;
    private Integer createdBy;
    private Integer interviewedBy;
    private Date date;
    private Time time;
    private String result; // Pending, Pass, Fail
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Interview() {
    }

    public Interview(int interviewId, int candidateId, Integer createBy, Integer interviewBy, Date date,
            Time time, String result, Timestamp createdAt, Timestamp updateAt) {
        this.interviewId = interviewId;
        this.candidateId = candidateId;
        this.createdBy = createBy;
        this.interviewedBy = interviewBy;
        this.date = date;
        this.time = time;
        this.result = result;
        this.createdAt = createdAt;
        this.updatedAt = updateAt;
    }

    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createBy) {
        this.createdBy = createBy;
    }

    public Integer getInterviewedBy() {
        return interviewedBy;
    }

    public void setInterviewedBy(Integer interviewBy) {
        this.interviewedBy = interviewBy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

}
