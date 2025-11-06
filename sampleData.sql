-- =====================================================
-- SAMPLE DATA FOR HRM SYSTEM - FOCUS ON RECRUITMENT
-- Created for testing HR and HRM recruitment features
-- =====================================================

-- Clear existing data (in correct order to avoid FK constraints)
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE Dependant;
TRUNCATE TABLE Holiday;
TRUNCATE TABLE Contract;
TRUNCATE TABLE Leave_Request;
TRUNCATE TABLE OT_Request;
TRUNCATE TABLE Daily_Attendance;
TRUNCATE TABLE Attendance_Raw;
TRUNCATE TABLE Salary;
TRUNCATE TABLE Payroll;
TRUNCATE TABLE Interview;
TRUNCATE TABLE Candidate;
TRUNCATE TABLE RecruitmentPost;
TRUNCATE TABLE Employee;
TRUNCATE TABLE Department;
TRUNCATE TABLE Role_Permission;
TRUNCATE TABLE Permission;
TRUNCATE TABLE Role;
SET FOREIGN_KEY_CHECKS = 1;

-- =====================================================
-- 1. ROLES
-- =====================================================
INSERT INTO Role (role_id, role_name) VALUES
(1, 'Admin'),
(2, 'HR Manager'),
(3, 'HR'),
(4, 'Employee'),
(5, 'Department Manager');

-- =====================================================
-- 2. PERMISSIONS
-- =====================================================
INSERT INTO Permission (permission_id, permission_name) VALUES
(1, 'Manage Users'),
(2, 'Manage Departments'),
(3, 'Manage Recruitment'),
(4, 'Approve Recruitment Posts'),
(5, 'Manage Interviews'),
(6, 'Manage Attendance'),
(7, 'Manage Payroll'),
(8, 'Approve Leave Requests'),
(9, 'Approve OT Requests'),
(10, 'View Reports');

-- =====================================================
-- 3. ROLE_PERMISSION MAPPING
-- =====================================================
INSERT INTO Role_Permission (role_id, permission_id) VALUES
-- Admin has all permissions
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10),
-- HR Manager
(2, 2), (2, 3), (2, 4), (2, 5), (2, 6), (2, 7), (2, 8), (2, 9), (2, 10),
-- HR
(3, 3), (3, 5), (3, 6), (3, 10),
-- Employee
(4, 10),
-- Department Manager
(5, 2), (5, 8), (5, 9), (5, 10);

-- =====================================================
-- 4. DEPARTMENTS
-- =====================================================
INSERT INTO Department (dep_id, dep_name, description) VALUES
('IT', 'Information Technology', 'Software development and IT infrastructure'),
('HR', 'Human Resources', 'Employee relations and recruitment'),
('SALES', 'Sales & Marketing', 'Sales operations and marketing'),
('FIN', 'Finance', 'Financial planning and accounting'),
('OPS', 'Operations', 'Business operations and logistics');

-- =====================================================
-- 5. EMPLOYEES
-- Password: 123456 (hashed with MD5 for testing)
-- =====================================================
INSERT INTO Employee (emp_id, emp_code, fullname, email, password, gender, dob, phone, position_title, image, paid_leave_days, dep_id, role_id, status) VALUES
-- Admin
(1, 'EMP001', 'Nguyen Van Admin', 'admin@hrm.com', 'e10adc3949ba59abbe56e057f20f883e', 1, '1985-01-15', '0901234567', 'System Administrator', 'admin.jpg', 12, 'IT', 1, TRUE),

-- HR Manager
(2, 'EMP002', 'Tran Thi Mai', 'mai.tran@hrm.com', 'e10adc3949ba59abbe56e057f20f883e', 0, '1988-03-20', '0901234568', 'HR Manager', 'mai.jpg', 15, 'HR', 2, TRUE),

-- HR Staff (for creating recruitment posts)
(3, 'EMP003', 'Le Van Hung', 'hung.le@hrm.com', 'e10adc3949ba59abbe56e057f20f883e', 1, '1992-06-10', '0901234569', 'HR Specialist', 'hung.jpg', 10, 'HR', 3, TRUE),
(4, 'EMP004', 'Pham Thi Lan', 'lan.pham@hrm.com', 'e10adc3949ba59abbe56e057f20f883e', 0, '1993-08-25', '0901234570', 'HR Recruiter', 'lan.jpg', 10, 'HR', 3, TRUE),

-- Department Managers
(5, 'EMP005', 'Hoang Van Cuong', 'cuong.hoang@hrm.com', 'e10adc3949ba59abbe56e057f20f883e', 1, '1987-05-12', '0901234571', 'IT Manager', 'cuong.jpg', 14, 'IT', 5, TRUE),
(6, 'EMP006', 'Nguyen Thi Huong', 'huong.nguyen@hrm.com', 'e10adc3949ba59abbe56e057f20f883e', 0, '1989-09-18', '0901234572', 'Sales Manager', 'huong.jpg', 14, 'SALES', 5, TRUE),
(7, 'EMP007', 'Vu Van Nam', 'nam.vu@hrm.com', 'e10adc3949ba59abbe56e057f20f883e', 1, '1986-11-05', '0901234573', 'Finance Manager', 'nam.jpg', 14, 'FIN', 5, TRUE),

-- Regular Employees
(8, 'EMP008', 'Do Thi Thao', 'thao.do@hrm.com', 'e10adc3949ba59abbe56e057f20f883e', 0, '1995-02-14', '0901234574', 'Software Engineer', 'thao.jpg', 8, 'IT', 4, TRUE),
(9, 'EMP009', 'Bui Van Tuan', 'tuan.bui@hrm.com', 'e10adc3949ba59abbe56e057f20f883e', 1, '1994-07-22', '0901234575', 'Sales Executive', 'tuan.jpg', 8, 'SALES', 4, TRUE),
(10, 'EMP010', 'Ngo Thi Hoa', 'hoa.ngo@hrm.com', 'e10adc3949ba59abbe56e057f20f883e', 0, '1996-04-30', '0901234576', 'Accountant', 'hoa.jpg', 8, 'FIN', 4, TRUE);

-- =====================================================
-- 6. RECRUITMENT POSTS (Various statuses for testing)
-- =====================================================

-- NEW POSTS (waiting for approval)
INSERT INTO RecruitmentPost (post_id, title, content, status, created_by, dep_id, created_at) VALUES
(1, 'Senior Java Developer', 
'<h3>Job Description</h3>
<p>We are looking for an experienced Senior Java Developer to join our IT team.</p>
<h4>Requirements:</h4>
<ul>
<li>5+ years of experience in Java development</li>
<li>Strong knowledge of Spring Framework, Hibernate</li>
<li>Experience with microservices architecture</li>
<li>Good communication skills in English</li>
</ul>
<h4>Benefits:</h4>
<ul>
<li>Competitive salary: 25-35M VND</li>
<li>13th month salary + performance bonus</li>
<li>Health insurance, annual leave</li>
<li>Modern working environment</li>
</ul>', 
'New', 3, 'IT', '2025-11-01 08:30:00'),

(2, 'HR Intern', 
'<h3>Job Description</h3>
<p>Great opportunity for students to learn about HR operations.</p>
<h4>Requirements:</h4>
<ul>
<li>Currently studying HR, Business Administration or related field</li>
<li>Good communication and interpersonal skills</li>
<li>Proficient in MS Office</li>
<li>Eager to learn</li>
</ul>
<h4>What you will learn:</h4>
<ul>
<li>Recruitment process</li>
<li>Employee onboarding</li>
<li>HR documentation</li>
</ul>', 
'New', 4, 'HR', '2025-11-02 09:15:00');

-- APPROVED POSTS (ready for publishing)
INSERT INTO RecruitmentPost (post_id, title, content, status, created_by, approved_by, dep_id, created_at, approved_at) VALUES
(3, 'Full Stack Developer', 
'<h3>Job Description</h3>
<p>Join our dynamic IT team as a Full Stack Developer.</p>
<h4>Requirements:</h4>
<ul>
<li>3+ years experience with React, Node.js</li>
<li>Knowledge of SQL and NoSQL databases</li>
<li>Experience with RESTful APIs</li>
<li>Team player with problem-solving skills</li>
</ul>
<h4>Responsibilities:</h4>
<ul>
<li>Develop and maintain web applications</li>
<li>Write clean, maintainable code</li>
<li>Collaborate with cross-functional teams</li>
</ul>', 
'Approved', 3, 2, 'IT', '2025-10-25 10:00:00', '2025-10-26 14:30:00'),

(4, 'Sales Executive', 
'<h3>Job Description</h3>
<p>Expand our customer base and achieve sales targets.</p>
<h4>Requirements:</h4>
<ul>
<li>2+ years experience in B2B sales</li>
<li>Excellent negotiation and presentation skills</li>
<li>Self-motivated and target-oriented</li>
<li>Good English communication</li>
</ul>
<h4>Offer:</h4>
<ul>
<li>Base salary + attractive commission</li>
<li>Training and career development</li>
<li>Dynamic work environment</li>
</ul>', 
'Approved', 4, 2, 'SALES', '2025-10-28 11:20:00', '2025-10-29 09:00:00'),

(5, 'Senior Accountant', 
'<h3>Job Description</h3>
<p>Manage financial operations and ensure compliance.</p>
<h4>Requirements:</h4>
<ul>
<li>Bachelor degree in Accounting or Finance</li>
<li>5+ years experience in accounting</li>
<li>CPA certification preferred</li>
<li>Strong analytical and detail-oriented</li>
</ul>
<h4>Responsibilities:</h4>
<ul>
<li>Prepare financial statements</li>
<li>Tax planning and compliance</li>
<li>Financial analysis and reporting</li>
</ul>', 
'Approved', 3, 2, 'FIN', '2025-10-20 13:45:00', '2025-10-21 10:15:00');

-- WAITING POSTS (submitted for approval)
INSERT INTO RecruitmentPost (post_id, title, content, status, created_by, dep_id, created_at) VALUES
(6, 'Marketing Manager', 
'<h3>Job Description</h3>
<p>Lead our marketing initiatives and brand development.</p>
<h4>Requirements:</h4>
<ul>
<li>5+ years in marketing management</li>
<li>Experience in digital marketing</li>
<li>Strong leadership skills</li>
<li>Creative and strategic thinking</li>
</ul>', 
'Waiting', 4, 'SALES', '2025-11-03 15:00:00'),

(7, 'DevOps Engineer', 
'<h3>Job Description</h3>
<p>Build and maintain our CI/CD infrastructure.</p>
<h4>Requirements:</h4>
<ul>
<li>3+ years DevOps experience</li>
<li>Knowledge of Docker, Kubernetes</li>
<li>Experience with AWS/Azure</li>
<li>Scripting skills (Python, Bash)</li>
</ul>', 
'Waiting', 3, 'IT', '2025-11-04 10:30:00');

-- REJECTED POSTS
INSERT INTO RecruitmentPost (post_id, title, content, status, created_by, approved_by, dep_id, created_at, approved_at) VALUES
(8, 'Junior Developer', 
'<h3>Job Description</h3>
<p>Entry level position for fresh graduates.</p>
<h4>Requirements:</h4>
<ul>
<li>Fresh graduate or final year student</li>
<li>Basic programming knowledge</li>
</ul>', 
'Rejected', 4, 2, 'IT', '2025-10-15 09:00:00', '2025-10-16 11:00:00');

-- UPLOADED POSTS (published to job sites)
INSERT INTO RecruitmentPost (post_id, title, content, status, created_by, approved_by, dep_id, created_at, approved_at) VALUES
(9, 'UI/UX Designer', 
'<h3>Job Description</h3>
<p>Create beautiful and intuitive user interfaces.</p>
<h4>Requirements:</h4>
<ul>
<li>3+ years UI/UX design experience</li>
<li>Proficient in Figma, Adobe XD</li>
<li>Strong portfolio</li>
<li>Understanding of user-centered design</li>
</ul>
<h4>What we offer:</h4>
<ul>
<li>Salary: 15-25M VND</li>
<li>Creative work environment</li>
<li>Latest design tools and equipment</li>
</ul>', 
'Uploaded', 3, 2, 'IT', '2025-10-10 08:00:00', '2025-10-11 09:00:00'),

(10, 'Business Analyst', 
'<h3>Job Description</h3>
<p>Bridge the gap between business needs and technical solutions.</p>
<h4>Requirements:</h4>
<ul>
<li>3+ years as Business Analyst</li>
<li>Strong analytical and documentation skills</li>
<li>Experience with Agile methodology</li>
<li>Excellent communication skills</li>
</ul>', 
'Uploaded', 4, 2, 'OPS', '2025-10-05 14:00:00', '2025-10-06 10:30:00');

-- =====================================================
-- 7. CANDIDATES (Applications for recruitment posts)
-- =====================================================

-- Candidates for UI/UX Designer (post_id = 9)
INSERT INTO Candidate (candidate_id, name, email, phone, CV, post_id, applied_at, result) VALUES
(1, 'Nguyen Van Minh', 'minh.nguyen@email.com', '0912345678', 'cv_minh_nguyen.pdf', 9, '2025-10-12 09:30:00', NULL),
(2, 'Tran Thi Hue', 'hue.tran@email.com', '0912345679', 'cv_hue_tran.pdf', 9, '2025-10-13 14:20:00', NULL),
(3, 'Le Van Khanh', 'khanh.le@email.com', '0912345680', 'cv_khanh_le.pdf', 9, '2025-10-14 10:45:00', TRUE),
(4, 'Pham Thi Linh', 'linh.pham@email.com', '0912345681', 'cv_linh_pham.pdf', 9, '2025-10-15 16:00:00', FALSE),

-- Candidates for Business Analyst (post_id = 10)
(5, 'Hoang Van Duc', 'duc.hoang@email.com', '0912345682', 'cv_duc_hoang.pdf', 10, '2025-10-07 11:00:00', NULL),
(6, 'Nguyen Thi Mai', 'mai.nguyen.ba@email.com', '0912345683', 'cv_mai_nguyen.pdf', 10, '2025-10-08 15:30:00', TRUE),
(7, 'Vu Van Tung', 'tung.vu@email.com', '0912345684', 'cv_tung_vu.pdf', 10, '2025-10-09 09:15:00', NULL),

-- Candidates for Full Stack Developer (post_id = 3)
(8, 'Do Thi My', 'my.do@email.com', '0912345685', 'cv_my_do.pdf', 3, '2025-10-27 08:45:00', NULL),
(9, 'Bui Van Hai', 'hai.bui@email.com', '0912345686', 'cv_hai_bui.pdf', 3, '2025-10-28 13:20:00', NULL),
(10, 'Ngo Thi Nga', 'nga.ngo@email.com', '0912345687', 'cv_nga_ngo.pdf', 3, '2025-10-29 10:00:00', TRUE),

-- Candidates for Sales Executive (post_id = 4)
(11, 'Dang Van Toan', 'toan.dang@email.com', '0912345688', 'cv_toan_dang.pdf', 4, '2025-10-30 14:30:00', NULL),
(12, 'Trinh Thi Thuy', 'thuy.trinh@email.com', '0912345689', 'cv_thuy_trinh.pdf', 4, '2025-10-31 09:45:00', NULL),
(13, 'Phan Van Long', 'long.phan@email.com', '0912345690', 'cv_long_phan.pdf', 4, '2025-11-01 11:15:00', NULL),

-- Candidates for Senior Accountant (post_id = 5)
(14, 'Truong Thi Lan', 'lan.truong@email.com', '0912345691', 'cv_lan_truong.pdf', 5, '2025-10-22 10:30:00', TRUE),
(15, 'Ly Van Hung', 'hung.ly@email.com', '0912345692', 'cv_hung_ly.pdf', 5, '2025-10-23 15:00:00', FALSE);

-- =====================================================
-- 8. INTERVIEWS (Scheduled and completed)
-- =====================================================

-- Completed interviews
INSERT INTO Interview (interview_id, candidate_id, created_by, interviewed_by, date, time, result, created_at) VALUES
(1, 3, 3, 5, '2025-10-18', '09:00:00', 'Pass', '2025-10-16 14:00:00'),
(2, 4, 3, 5, '2025-10-19', '10:30:00', 'Fail', '2025-10-16 14:15:00'),
(3, 6, 4, 7, '2025-10-15', '14:00:00', 'Pass', '2025-10-12 09:00:00'),
(4, 10, 3, 5, '2025-11-02', '13:30:00', 'Pass', '2025-10-30 10:00:00'),
(5, 14, 4, 7, '2025-10-28', '11:00:00', 'Pass', '2025-10-25 15:30:00'),
(6, 15, 4, 7, '2025-10-29', '15:00:00', 'Fail', '2025-10-25 15:45:00');

-- Pending interviews (scheduled but not yet conducted)
INSERT INTO Interview (interview_id, candidate_id, created_by, interviewed_by, date, time, result, created_at) VALUES
(7, 1, 3, 5, '2025-11-08', '09:00:00', 'Pending', '2025-11-05 10:00:00'),
(8, 2, 3, 5, '2025-11-08', '14:00:00', 'Pending', '2025-11-05 10:15:00'),
(9, 5, 4, 7, '2025-11-09', '10:30:00', 'Pending', '2025-11-05 11:00:00'),
(10, 8, 3, 5, '2025-11-10', '13:00:00', 'Pending', '2025-11-05 13:30:00'),
(11, 9, 3, 5, '2025-11-10', '15:30:00', 'Pending', '2025-11-05 13:45:00'),
(12, 11, 4, 6, '2025-11-11', '09:30:00', 'Pending', '2025-11-05 14:00:00'),
(13, 12, 4, 6, '2025-11-11', '11:00:00', 'Pending', '2025-11-05 14:15:00'),
(14, 13, 4, 6, '2025-11-12', '14:00:00', 'Pending', '2025-11-05 14:30:00');

-- =====================================================
-- 9. SALARY RECORDS
-- =====================================================
INSERT INTO Salary (salary_id, emp_id, base_salary, allowance, start_date, end_date, is_active) VALUES
(1, 1, 30000000, 5000000, '2023-01-01', NULL, TRUE),
(2, 2, 28000000, 4000000, '2023-01-01', NULL, TRUE),
(3, 3, 18000000, 2000000, '2023-06-01', NULL, TRUE),
(4, 4, 17000000, 2000000, '2023-06-01', NULL, TRUE),
(5, 5, 25000000, 3000000, '2023-01-01', NULL, TRUE),
(6, 6, 24000000, 3000000, '2023-01-01', NULL, TRUE),
(7, 7, 26000000, 3500000, '2023-01-01', NULL, TRUE),
(8, 8, 20000000, 2500000, '2024-01-01', NULL, TRUE),
(9, 9, 16000000, 2000000, '2024-01-01', NULL, TRUE),
(10, 10, 15000000, 1500000, '2024-03-01', NULL, TRUE);

-- =====================================================
-- 10. CONTRACTS
-- =====================================================
INSERT INTO Contract (contract_id, emp_id, type, start_date, end_date, contract_img) VALUES
(1, 1, 'Permanent', '2023-01-01', NULL, 'contract_emp001.pdf'),
(2, 2, 'Permanent', '2023-01-01', NULL, 'contract_emp002.pdf'),
(3, 3, 'Fixed-term', '2023-06-01', '2025-06-01', 'contract_emp003.pdf'),
(4, 4, 'Fixed-term', '2023-06-01', '2025-06-01', 'contract_emp004.pdf'),
(5, 5, 'Permanent', '2023-01-01', NULL, 'contract_emp005.pdf'),
(6, 6, 'Permanent', '2023-01-01', NULL, 'contract_emp006.pdf'),
(7, 7, 'Permanent', '2023-01-01', NULL, 'contract_emp007.pdf'),
(8, 8, 'Fixed-term', '2024-01-01', '2026-01-01', 'contract_emp008.pdf'),
(9, 9, 'Probation', '2024-01-01', '2024-03-01', 'contract_emp009.pdf'),
(10, 10, 'Fixed-term', '2024-03-01', '2026-03-01', 'contract_emp010.pdf');

-- =====================================================
-- 11. ATTENDANCE RAW DATA (Sample for October 2025)
-- =====================================================
INSERT INTO Attendance_Raw (emp_id, date, check_time, check_type) VALUES
-- Employee 3 (HR Staff) - Regular attendance
(3, '2025-10-01', '08:00:00', 'IN'),
(3, '2025-10-01', '17:30:00', 'OUT'),
(3, '2025-10-02', '08:05:00', 'IN'),
(3, '2025-10-02', '17:35:00', 'OUT'),
(3, '2025-10-03', '07:55:00', 'IN'),
(3, '2025-10-03', '18:00:00', 'OUT'),

-- Employee 8 (Software Engineer) - Some OT
(8, '2025-10-01', '08:10:00', 'IN'),
(8, '2025-10-01', '19:30:00', 'OUT'),
(8, '2025-10-02', '08:00:00', 'IN'),
(8, '2025-10-02', '20:00:00', 'OUT'),
(8, '2025-10-03', '08:15:00', 'IN'),
(8, '2025-10-03', '17:30:00', 'OUT');

-- =====================================================
-- 12. DAILY ATTENDANCE (Processed)
-- =====================================================
INSERT INTO Daily_Attendance (emp_id, date, work_day, check_in_time, check_out_time, ot_hours, status, is_locked) VALUES
(1, '2025-10-01', 1.00, '08:00:00', '17:00:00', 0.00, 'Present', TRUE),
(2, '2025-10-01', 1.00, '08:05:00', '17:10:00', 0.00, 'Present', TRUE),
(3, '2025-10-01', 1.00, '08:00:00', '17:30:00', 0.00, 'Present', TRUE),
(4, '2025-10-01', 1.00, '08:10:00', '17:20:00', 0.00, 'Present', TRUE),
(5, '2025-10-01', 1.00, '08:00:00', '17:00:00', 0.00, 'Present', TRUE),
(8, '2025-10-01', 1.00, '08:10:00', '19:30:00', 2.00, 'Present', TRUE),

(1, '2025-10-02', 1.00, '08:00:00', '17:00:00', 0.00, 'Present', TRUE),
(2, '2025-10-02', 1.00, '08:00:00', '17:00:00', 0.00, 'Present', TRUE),
(3, '2025-10-02', 1.00, '08:05:00', '17:35:00', 0.00, 'Present', TRUE),
(4, '2025-10-02', 0.00, NULL, NULL, 0.00, 'Absent', TRUE),
(8, '2025-10-02', 1.00, '08:00:00', '20:00:00', 2.50, 'Present', TRUE);

-- =====================================================
-- 13. LEAVE REQUESTS
-- =====================================================
INSERT INTO Leave_Request (leave_id, emp_id, leave_type, reason, day_requested, start_date, end_date, approved_by, status, created_at) VALUES
(1, 8, 'Annual Leave', 'Family vacation', 3.00, '2025-11-15', '2025-11-17', 5, 'Approved', '2025-11-01 09:00:00'),
(2, 9, 'Sick Leave', 'Medical treatment', 2.00, '2025-11-08', '2025-11-09', 6, 'Approved', '2025-11-05 08:30:00'),
(3, 10, 'Personal Reason', 'Personal matters', 1.00, '2025-11-20', '2025-11-20', 7, 'Pending', '2025-11-06 10:00:00'),
(4, 4, 'Annual Leave', 'Short break', 1.00, '2025-11-13', '2025-11-13', NULL, 'Pending', '2025-11-06 11:30:00');

-- =====================================================
-- 14. OT REQUESTS
-- =====================================================
INSERT INTO OT_Request (ot_id, emp_id, date, ot_hours, approved_by, status, created_at) VALUES
(1, 8, '2025-10-01', 2.00, 5, 'Approved', '2025-10-02 08:00:00'),
(2, 8, '2025-10-02', 2.50, 5, 'Approved', '2025-10-03 08:00:00'),
(3, 3, '2025-11-10', 1.50, 2, 'Pending', '2025-11-06 09:00:00'),
(4, 9, '2025-11-12', 2.00, 6, 'Pending', '2025-11-06 10:00:00');

-- =====================================================
-- 15. HOLIDAYS (Vietnam 2025)
-- =====================================================
INSERT INTO Holiday (date, name, source) VALUES
('2025-01-01', 'New Year', 'National Holiday'),
('2025-01-28', 'Tet Holiday - Day 1', 'Lunar New Year'),
('2025-01-29', 'Tet Holiday - Day 2', 'Lunar New Year'),
('2025-01-30', 'Tet Holiday - Day 3', 'Lunar New Year'),
('2025-01-31', 'Tet Holiday - Day 4', 'Lunar New Year'),
('2025-02-01', 'Tet Holiday - Day 5', 'Lunar New Year'),
('2025-04-10', 'Hung Kings Festival', 'National Holiday'),
('2025-04-30', 'Reunification Day', 'National Holiday'),
('2025-05-01', 'International Labor Day', 'National Holiday'),
('2025-09-02', 'National Day', 'National Holiday');

-- =====================================================
-- 16. DEPENDANTS (Sample data)
-- =====================================================
INSERT INTO Dependant (dependant_id, emp_id, name, relationship, dob, gender, phone) VALUES
(1, 2, 'Tran Van Son', 'Spouse', '1987-05-15', 1, '0908765432'),
(2, 2, 'Tran Minh Anh', 'Child', '2015-09-20', 0, NULL),
(3, 5, 'Hoang Thi Thuy', 'Spouse', '1989-08-12', 0, '0909876543'),
(4, 5, 'Hoang Minh Quan', 'Child', '2016-03-25', 1, NULL),
(5, 6, 'Nguyen Van Tuan', 'Spouse', '1988-11-03', 1, '0907654321'),
(6, 7, 'Vu Thi Lan', 'Spouse', '1990-02-18', 0, '0906543210'),
(7, 8, 'Do Van Thanh', 'Parent', '1965-07-08', 1, '0905432109');

-- =====================================================
-- 17. PAYROLL (Sample for September 2025)
-- =====================================================
INSERT INTO Payroll (payroll_id, emp_id, total_work_day, total_ot_hours, regular_salary, ot_earning, insurance_base, SI, HI, UI, tax_income, tax, month, year, is_paid) VALUES
(1, 1, 22.00, 0.00, 30000000, 0, 29800000, 2086000, 1192000, 298000, 26424000, 2206800, 9, 2025, TRUE),
(2, 2, 22.00, 0.00, 28000000, 0, 27800000, 1946000, 1112000, 278000, 24664000, 1933200, 9, 2025, TRUE),
(3, 3, 22.00, 3.00, 18000000, 360000, 17900000, 1253000, 716000, 179000, 16212000, 971880, 9, 2025, TRUE),
(4, 8, 21.00, 5.50, 19090909, 660000, 18900000, 1323000, 756000, 189000, 17482909, 1123227, 9, 2025, TRUE),
(5, 9, 22.00, 0.00, 16000000, 0, 15900000, 1113000, 636000, 159000, 14092000, 818760, 9, 2025, FALSE),
(6, 10, 22.00, 0.00, 15000000, 0, 14900000, 1043000, 596000, 149000, 13212000, 721080, 9, 2025, FALSE);

-- =====================================================
-- END OF SAMPLE DATA
-- =====================================================

-- Verify data inserted
SELECT 'Roles' as Table_Name, COUNT(*) as Record_Count FROM Role
UNION ALL
SELECT 'Departments', COUNT(*) FROM Department
UNION ALL
SELECT 'Employees', COUNT(*) FROM Employee
UNION ALL
SELECT 'RecruitmentPosts', COUNT(*) FROM RecruitmentPost
UNION ALL
SELECT 'Candidates', COUNT(*) FROM Candidate
UNION ALL
SELECT 'Interviews', COUNT(*) FROM Interview
UNION ALL
SELECT 'Contracts', COUNT(*) FROM Contract
UNION ALL
SELECT 'Salary', COUNT(*) FROM Salary
UNION ALL
SELECT 'Leave_Request', COUNT(*) FROM Leave_Request
UNION ALL
SELECT 'OT_Request', COUNT(*) FROM OT_Request
UNION ALL
SELECT 'Holidays', COUNT(*) FROM Holiday
UNION ALL
SELECT 'Dependants', COUNT(*) FROM Dependant;

-- Quick check recruitment status
SELECT 
    status,
    COUNT(*) as post_count,
    GROUP_CONCAT(title SEPARATOR '; ') as posts
FROM RecruitmentPost
GROUP BY status
ORDER BY FIELD(status, 'New', 'Waiting', 'Approved', 'Rejected', 'Uploaded', 'Deleted');

-- Quick check candidates by post
SELECT 
    rp.title as Post_Title,
    rp.status as Post_Status,
    COUNT(c.candidate_id) as Total_Candidates,
    SUM(CASE WHEN c.result = 1 THEN 1 ELSE 0 END) as Accepted,
    SUM(CASE WHEN c.result = 0 THEN 1 ELSE 0 END) as Rejected,
    SUM(CASE WHEN c.result IS NULL THEN 1 ELSE 0 END) as Pending
FROM RecruitmentPost rp
LEFT JOIN Candidate c ON rp.post_id = c.post_id
GROUP BY rp.post_id, rp.title, rp.status
ORDER BY rp.created_at DESC;

-- Check interviews schedule
SELECT 
    i.interview_id,
    c.name as Candidate_Name,
    rp.title as Position,
    i.date as Interview_Date,
    i.time as Interview_Time,
    e.fullname as Interviewer,
    i.result as Status
FROM Interview i
JOIN Candidate c ON i.candidate_id = c.candidate_id
JOIN RecruitmentPost rp ON c.post_id = rp.post_id
JOIN Employee e ON i.interviewed_by = e.emp_id
ORDER BY i.date DESC, i.time;
