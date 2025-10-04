/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author admin
 */
public class Holiday {

    private int holidayId;
    private Date date;
    private String name;
    private String source;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Holiday() {
    }

    public Holiday(int holidayId, Date date, String name, String source, Timestamp createdAt, Timestamp updatedAt) {
        this.holidayId = holidayId;
        this.date = date;
        this.name = name;
        this.source = source;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(int holidayId) {
        this.holidayId = holidayId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
        return "Holiday{" + "holidayId=" + holidayId + ", date=" + date + ", name=" + name + ", source=" + source + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }

}
