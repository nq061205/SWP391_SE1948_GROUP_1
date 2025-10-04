/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;
import java.sql.Date;
/**
 *
 * @author admin
 */
public class Employee {

    private int empId;
    private String empCode;
    private String fullname;
    private String email;
    private String password;
    private boolean gender;
    private Date dob;
    private String phone;
    private String positionTitle;
    private String image;
    private int dependantCount;
    private Department dept;
    private Role role;

    public Employee() {
    }

    public Employee(int empId, String empCode, String fullname, String email, String password, boolean gender, Date dob, String phone, String positionTitle, String image, int dependantCount, Department dept, Role role) {
        this.empId = empId;
        this.empCode = empCode;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.positionTitle = positionTitle;
        this.image = image;
        this.dependantCount = dependantCount;
        this.dept = dept;
        this.role = role;
    }


    public void setDept(Department dept) {
        this.dept = dept;
    }
 
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDependantCount() {
        return dependantCount;
    }

    public void setDependantCount(int dependantCount) {
        this.dependantCount = dependantCount;
    }

    public Department getDept() {
        return dept;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "Employee{" + "empId=" + empId + ", empCode=" + empCode + ", fullname=" + fullname + ", email=" + email + ", password=" + password + ", gender=" + gender + ", dob=" + dob + ", phone=" + phone + ", positionTitle=" + positionTitle + ", image=" + image + ", dependantCount=" + dependantCount + ", dept=" + dept + ", role=" + role + '}';
    }
    
}
