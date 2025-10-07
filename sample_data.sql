-- ===================================
-- SAMPLE DATA FOR HRM DATABASE
-- ===================================
-- Created by: GitHub Copilot
-- Date: October 6, 2025
-- Total records: 10 per table

USE hrm;

-- Disable foreign key checks temporarily
SET FOREIGN_KEY_CHECKS = 0;

-- ===================================
-- 1. ROLE TABLE (10 records)
-- ===================================
INSERT INTO Role (role_id, role_name) VALUES
(1, 'Admin'),
(2, 'HR Manager'),
(3, 'Department Manager'),
(4, 'Team Lead'),
(5, 'Senior Developer'),
(6, 'Developer'),
(7, 'Junior Developer'),
(8, 'Accountant'),
(9, 'Intern'),
(10, 'Security');

-- ===================================
-- 2. PERMISSION TABLE (10 records)
-- ===================================
INSERT INTO Permission (permission_id, permission_name) VALUES
(1, 'VIEW_ALL_EMPLOYEES'),
(2, 'EDIT_EMPLOYEE'),
(3, 'DELETE_EMPLOYEE'),
(4, 'APPROVE_LEAVE'),
(5, 'APPROVE_OVERTIME'),
(6, 'VIEW_PAYROLL'),
(7, 'EDIT_PAYROLL'),
(8, 'CREATE_RECRUITMENT'),
(9, 'APPROVE_RECRUITMENT'),
(10, 'VIEW_REPORTS');

-- ===================================
-- 3. ROLE_PERMISSION TABLE (10 records)
-- ===================================
INSERT INTO Role_Permission (role_id, permission_id) VALUES
(1, 1), -- Admin can view all employees
(1, 2), -- Admin can edit employees
(1, 3), -- Admin can delete employees
(2, 4), -- HR Manager can approve leave
(2, 5), -- HR Manager can approve overtime
(2, 8), -- HR Manager can create recruitment
(3, 4), -- Department Manager can approve leave
(3, 10), -- Department Manager can view reports
(4, 10), -- Team Lead can view reports
(8, 6); -- Accountant can view payroll

-- ===================================
-- 4. DEPARTMENT TABLE (10 records)
-- ===================================
INSERT INTO Department (dep_id, dep_name, description) VALUES
('IT001', 'Information Technology', 'Software development and IT infrastructure'),
('HR001', 'Human Resources', 'Employee management and recruitment'),
('FIN01', 'Finance', 'Financial planning and accounting'),
('MKT01', 'Marketing', 'Marketing and customer relations'),
('SALE1', 'Sales', 'Sales and business development'),
('OPS01', 'Operations', 'Daily operations and logistics'),
('QA001', 'Quality Assurance', 'Quality control and testing'),
('RND01', 'Research & Development', 'Innovation and product development'),
('ADM01', 'Administration', 'General administration and support'),
('SEC01', 'Security', 'Security and facility management');

-- ===================================
-- 5. EMPLOYEE TABLE (10 records)
-- ===================================
INSERT INTO Employee (emp_id, emp_code, fullname, email, password, gender, dob, phone, position_title, image, dependant_count, dep_id, role_id, status) VALUES
(1, 'EMP001', 'Nguyen Van Admin', 'admin@company.com', '$2a$10$encrypted_password_hash', 1, '1985-03-15', '0901234567', 'System Administrator', 'admin.jpg', 2, 'IT001', 1, true),
(2, 'EMP002', 'Tran Thi Lan', 'hr.manager@company.com', '$2a$10$encrypted_password_hash', 0, '1987-07-22', '0902345678', 'HR Manager', 'hr_manager.jpg', 1, 'HR001', 2, true),
(3, 'EMP003', 'Le Van Duc', 'it.manager@company.com', '$2a$10$encrypted_password_hash', 1, '1982-11-08', '0903456789', 'IT Manager', 'it_manager.jpg', 3, 'IT001', 3, true),
(4, 'EMP004', 'Pham Thi Mai', 'team.lead@company.com', '$2a$10$encrypted_password_hash', 0, '1990-05-12', '0904567890', 'Team Lead', 'team_lead.jpg', 0, 'IT001', 4, true),
(5, 'EMP005', 'Hoang Van Nam', 'senior.dev@company.com', '$2a$10$encrypted_password_hash', 1, '1988-09-25', '0905678901', 'Senior Developer', 'senior_dev.jpg', 1, 'IT001', 5, true),
(6, 'EMP006', 'Vo Thi Linh', 'developer@company.com', '$2a$10$encrypted_password_hash', 0, '1992-01-18', '0906789012', 'Developer', 'developer.jpg', 0, 'IT001', 6, true),
(7, 'EMP007', 'Dang Van Minh', 'junior.dev@company.com', '$2a$10$encrypted_password_hash', 1, '1995-04-30', '0907890123', 'Junior Developer', 'junior_dev.jpg', 0, 'IT001', 7, true),
(8, 'EMP008', 'Bui Thi Hoa', 'accountant@company.com', '$2a$10$encrypted_password_hash', 0, '1986-12-03', '0908901234', 'Accountant', 'accountant.jpg', 2, 'FIN01', 8, true),
(9, 'EMP009', 'Cao Van Hieu', 'intern@company.com', '$2a$10$encrypted_password_hash', 1, '1998-08-14', '0909012345', 'Intern Developer', 'intern.jpg', 0, 'IT001', 9, true),
(10, 'EMP010', 'Do Thi An', 'security@company.com', '$2a$10$encrypted_password_hash', 0, '1984-06-27', '0910123456', 'Security Officer', 'security.jpg', 1, 'SEC01', 10, true);

-- ===================================
-- 6. RECRUITMENT POST TABLE (10 records)
-- ===================================
INSERT INTO RecruitmentPost (post_id, title, content, status, is_delete, created_by, approved_by, dep_id, approved_at, created_at, updated_at) VALUES
(1, 'Senior Java Developer', 'We are looking for an experienced Java developer with 5+ years experience in Spring Boot, microservices...', 'Approved', false, 2, 3, 'IT001', '2024-10-01 10:00:00', '2024-09-30 09:00:00', '2024-10-01 10:00:00'),
(2, 'HR Specialist', 'Join our HR team to help manage employee relations, recruitment processes, and policy development...', 'Approved', false, 2, 1, 'HR001', '2024-10-02 11:00:00', '2024-10-01 14:00:00', '2024-10-02 11:00:00'),
(3, 'Marketing Manager', 'Lead our marketing initiatives, develop campaigns, and manage brand strategy for digital products...', 'Pending', false, 2, null, 'MKT01', null, '2024-10-03 15:30:00', '2024-10-03 15:30:00'),
(4, 'Frontend Developer', 'React.js developer needed for building modern web applications. Experience with TypeScript preferred...', 'Approved', false, 3, 3, 'IT001', '2024-10-04 09:15:00', '2024-10-03 16:45:00', '2024-10-04 09:15:00'),
(5, 'Data Analyst', 'Analyze business data to provide insights for decision making. SQL, Python, and Tableau skills required...', 'Rejected', false, 2, 1, 'IT001', '2024-10-05 13:20:00', '2024-10-04 11:30:00', '2024-10-05 13:20:00'),
(6, 'Sales Representative', 'Drive sales growth by identifying new business opportunities and maintaining client relationships...', 'Approved', false, 2, 1, 'SALE1', '2024-10-05 16:00:00', '2024-10-04 14:20:00', '2024-10-05 16:00:00'),
(7, 'Quality Assurance Engineer', 'Ensure product quality through comprehensive testing. Automation testing experience is a plus...', 'Pending', false, 3, null, 'QA001', null, '2024-10-05 10:45:00', '2024-10-05 10:45:00'),
(8, 'DevOps Engineer', 'Manage CI/CD pipelines, cloud infrastructure, and deployment processes. AWS/Azure experience required...', 'Approved', false, 3, 1, 'IT001', '2024-10-06 08:30:00', '2024-10-05 17:15:00', '2024-10-06 08:30:00'),
(9, 'Financial Analyst', 'Support financial planning and analysis activities. Strong Excel and financial modeling skills needed...', 'Approved', false, 2, 1, 'FIN01', '2024-10-06 12:00:00', '2024-10-05 13:45:00', '2024-10-06 12:00:00'),
(10, 'UI/UX Designer', 'Design intuitive user interfaces and experiences for web and mobile applications...', 'Pending', false, 2, null, 'IT001', null, '2024-10-06 14:30:00', '2024-10-06 14:30:00');

-- ===================================
-- 7. CANDIDATE TABLE (10 records)
-- ===================================
INSERT INTO Candidate (candidate_id, name, email, phone, CV, post_id, applied_at, result) VALUES
(1, 'Nguyen Van Hung', 'hungnv@email.com', '0911111111', 'cv_hungnv.pdf', 1, '2024-10-02 09:30:00', true),
(2, 'Tran Thi Yen', 'yentt@email.com', '0912222222', 'cv_yentt.pdf', 2, '2024-10-03 14:15:00', false),
(3, 'Le Van Khanh', 'khanhlv@email.com', '0913333333', 'cv_khanhlv.pdf', 1, '2024-10-03 16:45:00', true),
(4, 'Pham Thi Nga', 'ngapt@email.com', '0914444444', 'cv_ngapt.pdf', 4, '2024-10-04 10:20:00', false),
(5, 'Hoang Van Duc', 'duchv@email.com', '0915555555', 'cv_duchv.pdf', 6, '2024-10-05 11:30:00', true),
(6, 'Vo Thi Huong', 'huongvt@email.com', '0916666666', 'cv_huongvt.pdf', 8, '2024-10-05 15:45:00', false),
(7, 'Dang Van Tuan', 'tuandv@email.com', '0917777777', 'cv_tuandv.pdf', 9, '2024-10-06 09:15:00', true),
(8, 'Bui Thi Lan', 'lanbt@email.com', '0918888888', 'cv_lanbt.pdf', 1, '2024-10-06 13:30:00', false),
(9, 'Cao Van Minh', 'minhcv@email.com', '0919999999', 'cv_minhcv.pdf', 4, '2024-10-06 16:20:00', true),
(10, 'Do Thi Phuong', 'phuongdt@email.com', '0920000000', 'cv_phuongdt.pdf', 2, '2024-10-06 17:45:00', false);

-- ===================================
-- 8. INTERVIEW TABLE (10 records)
-- ===================================
INSERT INTO Interview (interview_id, candidate_id, created_by, interviewed_by, date, time, result, created_at, updated_at) VALUES
(1, 1, 2, 3, '2024-10-07', '09:00:00', 'Pass', '2024-10-02 17:00:00', '2024-10-07 10:00:00'),
(2, 2, 2, 2, '2024-10-08', '10:30:00', 'Fail', '2024-10-03 16:30:00', '2024-10-08 11:30:00'),
(3, 3, 3, 5, '2024-10-08', '14:00:00', 'Pass', '2024-10-04 09:15:00', '2024-10-08 15:00:00'),
(4, 4, 3, 4, '2024-10-09', '11:00:00', 'Pending', '2024-10-04 14:30:00', '2024-10-04 14:30:00'),
(5, 5, 2, 1, '2024-10-09', '15:30:00', 'Pass', '2024-10-05 13:45:00', '2024-10-09 16:30:00'),
(6, 6, 3, 3, '2024-10-10', '09:30:00', 'Pending', '2024-10-05 18:00:00', '2024-10-05 18:00:00'),
(7, 7, 2, 8, '2024-10-10', '13:00:00', 'Pass', '2024-10-06 11:30:00', '2024-10-10 14:00:00'),
(8, 8, 3, 5, '2024-10-11', '10:00:00', 'Pending', '2024-10-06 15:45:00', '2024-10-06 15:45:00'),
(9, 9, 3, 4, '2024-10-11', '16:00:00', 'Pending', '2024-10-06 18:30:00', '2024-10-06 18:30:00'),
(10, 10, 2, 2, '2024-10-12', '14:30:00', 'Pending', '2024-10-06 19:00:00', '2024-10-06 19:00:00');

-- ===================================
-- 9. HOLIDAY TABLE (10 records)
-- ===================================
INSERT INTO Holiday (holiday_id, date, name, source, created_at, updated_at) VALUES
(1, '2024-01-01', 'New Year Day', 'National Holiday', '2024-01-01 00:00:00', '2024-01-01 00:00:00'),
(2, '2024-02-10', 'Lunar New Year Day 1', 'National Holiday', '2024-01-15 00:00:00', '2024-01-15 00:00:00'),
(3, '2024-02-11', 'Lunar New Year Day 2', 'National Holiday', '2024-01-15 00:00:00', '2024-01-15 00:00:00'),
(4, '2024-02-12', 'Lunar New Year Day 3', 'National Holiday', '2024-01-15 00:00:00', '2024-01-15 00:00:00'),
(5, '2024-04-18', 'Hung Kings Festival', 'National Holiday', '2024-03-01 00:00:00', '2024-03-01 00:00:00'),
(6, '2024-04-30', 'Liberation Day', 'National Holiday', '2024-03-01 00:00:00', '2024-03-01 00:00:00'),
(7, '2024-05-01', 'International Labor Day', 'National Holiday', '2024-03-01 00:00:00', '2024-03-01 00:00:00'),
(8, '2024-09-02', 'National Independence Day', 'National Holiday', '2024-07-01 00:00:00', '2024-07-01 00:00:00'),
(9, '2024-12-25', 'Christmas Day', 'Company Holiday', '2024-11-01 00:00:00', '2024-11-01 00:00:00'),
(10, '2024-12-31', 'New Year Eve', 'Company Holiday', '2024-11-01 00:00:00', '2024-11-01 00:00:00');

-- ===================================
-- 10. SALARY TABLE (10 records)
-- ===================================
INSERT INTO Salary (salary_id, emp_id, base_salary, allowance, start_date, end_date, created_at, is_active) VALUES
(1, 1, 25000000.00, 5000000.00, '2024-01-01', null, '2024-01-01 00:00:00', true),
(2, 2, 22000000.00, 4000000.00, '2024-01-01', null, '2024-01-01 00:00:00', true),
(3, 3, 28000000.00, 5500000.00, '2024-01-01', null, '2024-01-01 00:00:00', true),
(4, 4, 18000000.00, 3000000.00, '2024-01-01', null, '2024-01-01 00:00:00', true),
(5, 5, 20000000.00, 3500000.00, '2024-01-01', null, '2024-01-01 00:00:00', true),
(6, 6, 15000000.00, 2500000.00, '2024-01-01', null, '2024-01-01 00:00:00', true),
(7, 7, 12000000.00, 2000000.00, '2024-01-01', null, '2024-01-01 00:00:00', true),
(8, 8, 16000000.00, 2800000.00, '2024-01-01', null, '2024-01-01 00:00:00', true),
(9, 9, 8000000.00, 1000000.00, '2024-06-01', null, '2024-06-01 00:00:00', true),
(10, 10, 14000000.00, 2200000.00, '2024-01-01', null, '2024-01-01 00:00:00', true);

-- ===================================
-- 11. CONTRACT TABLE (10 records)
-- ===================================
INSERT INTO Contract (contract_id, emp_id, type, start_date, end_date, contract_img) VALUES
(1, 1, 'Permanent', '2024-01-01', null, 'contract_emp001.pdf'),
(2, 2, 'Permanent', '2024-01-01', null, 'contract_emp002.pdf'),
(3, 3, 'Permanent', '2024-01-01', null, 'contract_emp003.pdf'),
(4, 4, 'Fixed-term', '2024-01-01', '2025-12-31', 'contract_emp004.pdf'),
(5, 5, 'Permanent', '2024-01-01', null, 'contract_emp005.pdf'),
(6, 6, 'Fixed-term', '2024-01-01', '2025-06-30', 'contract_emp006.pdf'),
(7, 7, 'Probation', '2024-01-01', '2024-03-31', 'contract_emp007.pdf'),
(8, 8, 'Permanent', '2024-01-01', null, 'contract_emp008.pdf'),
(9, 9, 'Internship', '2024-06-01', '2024-08-31', 'contract_emp009.pdf'),
(10, 10, 'Fixed-term', '2024-01-01', '2024-12-31', 'contract_emp010.pdf');

-- ===================================
-- 12. ATTENDANCE_RAW TABLE (10 records)
-- ===================================
INSERT INTO Attendance_Raw (id, emp_id, date, check_time, check_type) VALUES
(1, 1, '2024-10-06', '08:00:00', 'IN'),
(2, 1, '2024-10-06', '17:30:00', 'OUT'),
(3, 2, '2024-10-06', '08:15:00', 'IN'),
(4, 2, '2024-10-06', '17:45:00', 'OUT'),
(5, 3, '2024-10-06', '07:45:00', 'IN'),
(6, 3, '2024-10-06', '18:00:00', 'OUT'),
(7, 4, '2024-10-06', '08:30:00', 'IN'),
(8, 4, '2024-10-06', '17:15:00', 'OUT'),
(9, 5, '2024-10-06', '08:10:00', 'IN'),
(10, 5, '2024-10-06', '17:40:00', 'OUT');

-- ===================================
-- 13. DAILY_ATTENDANCE TABLE (10 records)
-- ===================================
INSERT INTO Daily_Attendance (id, emp_id, date, work_day, check_in_time, check_out_time, ot_hours, status, is_locked, created_at, updated_at) VALUES
(1, 1, '2024-10-06', 1.00, '08:00:00', '17:30:00', 1.50, 'Present', true, '2024-10-06 18:00:00', '2024-10-06 18:00:00'),
(2, 2, '2024-10-06', 1.00, '08:15:00', '17:45:00', 1.75, 'Present', true, '2024-10-06 18:00:00', '2024-10-06 18:00:00'),
(3, 3, '2024-10-06', 1.00, '07:45:00', '18:00:00', 2.25, 'Present', true, '2024-10-06 18:30:00', '2024-10-06 18:30:00'),
(4, 4, '2024-10-06', 0.50, '08:30:00', '17:15:00', 0.00, 'Present', false, '2024-10-06 17:30:00', '2024-10-06 17:30:00'),
(5, 5, '2024-10-06', 1.00, '08:10:00', '17:40:00', 1.67, 'Present', true, '2024-10-06 18:00:00', '2024-10-06 18:00:00'),
(6, 6, '2024-10-05', 0.00, null, null, 0.00, 'Absent', true, '2024-10-05 18:00:00', '2024-10-05 18:00:00'),
(7, 7, '2024-10-04', 1.00, '08:25:00', '17:30:00', 0.50, 'Present', true, '2024-10-04 18:00:00', '2024-10-04 18:00:00'),
(8, 8, '2024-10-03', 1.00, '08:05:00', '17:35:00', 0.00, 'Present', true, '2024-10-03 18:00:00', '2024-10-03 18:00:00'),
(9, 9, '2024-10-02', 0.00, null, null, 0.00, 'Holiday', true, '2024-10-02 00:00:00', '2024-10-02 00:00:00'),
(10, 10, '2024-10-01', 1.00, '08:20:00', '17:25:00', 0.00, 'Present', true, '2024-10-01 18:00:00', '2024-10-01 18:00:00');

-- ===================================
-- 14. OT_REQUEST TABLE (10 records)
-- ===================================
INSERT INTO OT_Request (ot_id, emp_id, date, ot_hours, approved_by, approved_at, status, created_at, updated_at) VALUES
(1, 1, '2024-10-06', 1.50, 3, '2024-10-06 16:00:00', 'Approved', '2024-10-06 15:30:00', '2024-10-06 16:00:00'),
(2, 2, '2024-10-06', 1.75, 3, '2024-10-06 16:15:00', 'Approved', '2024-10-06 15:45:00', '2024-10-06 16:15:00'),
(3, 3, '2024-10-06', 2.25, 1, '2024-10-06 16:30:00', 'Approved', '2024-10-06 16:00:00', '2024-10-06 16:30:00'),
(4, 5, '2024-10-06', 1.67, 4, '2024-10-06 16:45:00', 'Approved', '2024-10-06 16:15:00', '2024-10-06 16:45:00'),
(5, 6, '2024-10-07', 2.00, null, null, 'Pending', '2024-10-06 17:00:00', '2024-10-06 17:00:00'),
(6, 7, '2024-10-07', 1.00, 4, '2024-10-07 10:00:00', 'Approved', '2024-10-06 17:15:00', '2024-10-07 10:00:00'),
(7, 8, '2024-10-08', 1.50, null, null, 'Pending', '2024-10-07 14:30:00', '2024-10-07 14:30:00'),
(8, 9, '2024-10-08', 1.00, 4, null, 'Rejected', '2024-10-07 15:00:00', '2024-10-07 18:00:00'),
(9, 10, '2024-10-09', 2.50, null, null, 'Pending', '2024-10-08 16:45:00', '2024-10-08 16:45:00'),
(10, 4, '2024-10-10', 1.25, 3, null, 'Pending', '2024-10-09 17:30:00', '2024-10-09 17:30:00');

-- ===================================
-- 15. LEAVE_REQUEST TABLE (10 records)
-- ===================================
INSERT INTO Leave_Request (leave_id, emp_id, leave_type, reason, day_requested, start_date, end_date, approved_by, approved_at, status, note, created_at, updated_at) VALUES
(1, 6, 'Sick', 'Flu symptoms, need rest', 1.00, '2024-10-05', '2024-10-05', 2, '2024-10-04 17:00:00', 'Approved', 'Medical certificate provided', '2024-10-04 16:30:00', '2024-10-04 17:00:00'),
(2, 7, 'Annual Leave', 'Family vacation', 5.00, '2024-10-15', '2024-10-19', 4, '2024-10-02 14:00:00', 'Approved', 'Approved for vacation', '2024-10-01 10:15:00', '2024-10-02 14:00:00'),
(3, 9, 'Other', 'Job interview at another company', 0.50, '2024-10-10', '2024-10-10', null, null, 'Pending', null, '2024-10-08 11:30:00', '2024-10-08 11:30:00'),
(4, 5, 'Annual Leave', 'Personal matters', 2.00, '2024-10-12', '2024-10-13', 3, '2024-10-07 16:30:00', 'Approved', 'Approved for personal time', '2024-10-07 14:45:00', '2024-10-07 16:30:00'),
(5, 8, 'Maternity', 'Prenatal checkup', 0.50, '2024-10-11', '2024-10-11', 2, '2024-10-08 15:45:00', 'Approved', 'Medical appointment', '2024-10-08 13:20:00', '2024-10-08 15:45:00'),
(6, 10, 'Sick', 'Dental emergency', 1.00, '2024-10-09', '2024-10-09', null, null, 'Pending', null, '2024-10-08 18:00:00', '2024-10-08 18:00:00'),
(7, 1, 'Annual Leave', 'Wedding anniversary', 1.00, '2024-10-20', '2024-10-20', null, null, 'Pending', null, '2024-10-06 15:30:00', '2024-10-06 15:30:00'),
(8, 3, 'Other', 'Conference attendance', 2.00, '2024-10-25', '2024-10-26', 1, '2024-10-05 12:00:00', 'Approved', 'Professional development', '2024-10-04 16:45:00', '2024-10-05 12:00:00'),
(9, 4, 'Unpaid', 'Extended personal leave', 3.00, '2024-11-01', '2024-11-03', null, null, 'Rejected', 'Insufficient notice period', '2024-10-06 10:20:00', '2024-10-06 17:45:00'),
(10, 2, 'Annual Leave', 'Family event', 1.00, '2024-10-18', '2024-10-18', null, null, 'Pending', null, '2024-10-06 14:15:00', '2024-10-06 14:15:00');

-- ===================================
-- 16. PAYROLL TABLE (10 records)
-- ===================================
INSERT INTO Payroll (payroll_id, emp_id, total_work_day, total_work_hours, SI, HI, UI, tax, total_salary, month, year, created_at, updated_at) VALUES
(1, 1, 22.00, 176.00, 750000.00, 450000.00, 300000.00, 2800000.00, 26700000.00, 9, 2024, '2024-10-01 00:00:00', '2024-10-01 00:00:00'),
(2, 2, 21.50, 172.00, 660000.00, 396000.00, 264000.00, 2450000.00, 22830000.00, 9, 2024, '2024-10-01 00:00:00', '2024-10-01 00:00:00'),
(3, 3, 22.00, 180.00, 845000.00, 507000.00, 338000.00, 3200000.00, 29610000.00, 9, 2024, '2024-10-01 00:00:00', '2024-10-01 00:00:00'),
(4, 4, 20.00, 160.00, 540000.00, 324000.00, 216000.00, 1850000.00, 18570000.00, 9, 2024, '2024-10-01 00:00:00', '2024-10-01 00:00:00'),
(5, 5, 22.00, 178.00, 600000.00, 360000.00, 240000.00, 2200000.00, 21300000.00, 9, 2024, '2024-10-01 00:00:00', '2024-10-01 00:00:00'),
(6, 6, 19.00, 152.00, 450000.00, 270000.00, 180000.00, 1500000.00, 15600000.00, 9, 2024, '2024-10-01 00:00:00', '2024-10-01 00:00:00'),
(7, 7, 22.00, 176.00, 360000.00, 216000.00, 144000.00, 1100000.00, 12180000.00, 9, 2024, '2024-10-01 00:00:00', '2024-10-01 00:00:00'),
(8, 8, 21.00, 168.00, 480000.00, 288000.00, 192000.00, 1650000.00, 16190000.00, 9, 2024, '2024-10-01 00:00:00', '2024-10-01 00:00:00'),
(9, 9, 15.00, 120.00, 240000.00, 144000.00, 96000.00, 600000.00, 8120000.00, 9, 2024, '2024-10-01 00:00:00', '2024-10-01 00:00:00'),
(10, 10, 22.00, 176.00, 420000.00, 252000.00, 168000.00, 1350000.00, 14010000.00, 9, 2024, '2024-10-01 00:00:00', '2024-10-01 00:00:00');

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- ===================================
-- SAMPLE DATA SUMMARY
-- ===================================
-- Total records inserted:
-- - Role: 10 records
-- - Permission: 10 records  
-- - Role_Permission: 10 records
-- - Department: 10 records
-- - Employee: 10 records
-- - RecruitmentPost: 10 records
-- - Candidate: 10 records
-- - Interview: 10 records
-- - Holiday: 10 records
-- - Salary: 10 records
-- - Contract: 10 records
-- - Attendance_Raw: 10 records
-- - Daily_Attendance: 10 records
-- - OT_Request: 10 records
-- - Leave_Request: 10 records
-- - Payroll: 10 records
-- 
-- Total: 160 records across 16 tables
-- ===================================
