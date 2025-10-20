-- ===== ROLE & PERMISSION =====
INSERT INTO Role (role_name) VALUES
('Admin'),
('HR Manager'),
('HR Staff'),
('IT Staff'),
('Marketing Staff');

INSERT INTO Permission (permission_name) VALUES
('Manage Employees'),
('Approve Leave Requests'),
('Manage Recruitment'),
('View Payroll'),
('Manage Payroll'),
('View Reports');

INSERT INTO Role_Permission VALUES
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),
(2,2),(2,3),(2,4),(2,6),
(3,3),(3,4),
(4,4),
(5,3);

-- ===== DEPARTMENT =====
INSERT INTO Department VALUES
('HR', 'Human Resources', 'Handles employee relations and recruitment.'),
('IT', 'Information Technology', 'Responsible for company software and infrastructure.'),
('MK', 'Marketing', 'Handles promotions, campaigns, and branding.'),
('FN', 'Finance', 'Manages payroll, budget, and accounting.'),
('AD', 'Administration', 'Supports general office operations.');

-- ===== EMPLOYEE =====
INSERT INTO Employee
(emp_code, fullname, email, password, gender, dob, phone, position_title, image, dependant_count, paid_leave_days, dep_id, role_id, status)
VALUES
('E001', 'Nguyen Van A', 'admin@company.com', '123', 1, '1988-05-10', '0909000001', 'Admin', NULL, 0, 12.00, 'HR', 1, TRUE),
('E002', 'Tran Thi B', 'hrmanager@company.com', '123456', 0, '1990-02-15', '0909000002', 'HR Manager', NULL, 1, 12.00, 'HR', 2, TRUE),
('E003', 'Le Van C', 'hrstaff@company.com', '123456', 1, '1994-09-12', '0909000003', 'HR Staff', NULL, 0, 12.00, 'HR', 3, TRUE),
('E004', 'Pham Van D', 'itstaff1@company.com', '123456', 1, '1996-03-25', '0909000004', 'Backend Developer', NULL, 2, 12.00, 'IT', 4, TRUE),
('E005', 'Nguyen Thi E', 'itstaff2@company.com', '123456', 0, '1997-07-08', '0909000005', 'Frontend Developer', NULL, 0, 12.00, 'IT', 4, TRUE),
('E006', 'Do Van F', 'marketing1@company.com', '123456', 1, '1993-11-01', '0909000006', 'Marketing Specialist', NULL, 1, 12.00, 'MK', 5, TRUE),
('E007', 'Hoang Thi G', 'marketing2@company.com', '123456', 0, '1999-06-12', '0909000007', 'Content Writer', NULL, 0, 12.00, 'MK', 5, TRUE),
('E008', 'Vo Van H', 'finance1@company.com', '123456', 1, '1985-04-18', '0909000008', 'Accountant', NULL, 2, 12.00, 'FN', 2, TRUE),
('E009', 'Phan Thi I', 'admin1@company.com', '123456', 0, '1992-12-02', '0909000009', 'Office Admin', NULL, 0, 12.00, 'AD', 3, TRUE),
('E010', 'Tran Van J', 'itstaff3@company.com', '123456', 1, '1998-01-14', '0909000010', 'System Engineer', NULL, 0, 12.00, 'IT', 4, TRUE);

-- ===== RECRUITMENT POST =====
INSERT INTO RecruitmentPost (title, content, status, is_delete, created_by, approved_by, dep_id)
VALUES
('Tuyển Thực tập sinh HR', 'Hỗ trợ phỏng vấn và nhập hồ sơ nhân sự.', 'Approved', FALSE, 2, 1, 'HR'),
('Tuyển Backend Java', 'Kinh nghiệm Spring Boot là lợi thế.', 'Approved', FALSE, 4, 1, 'IT'),
('Tuyển Content Creator', 'Viết bài cho chiến dịch marketing.', 'Pending', FALSE, 6, NULL, 'MK'),
('Tuyển Nhân viên Kế toán', 'Thành thạo MISA.', 'Approved', FALSE, 8, 2, 'FN'),
('Tuyển Thực tập IT Helpdesk', 'Hỗ trợ kỹ thuật máy tính.', 'Pending', FALSE, 5, NULL, 'IT');

-- ===== CANDIDATE =====
INSERT INTO Candidate (name, email, phone, CV, post_id, result)
VALUES
('Nguyen Thanh T', 't@gmail.com', '0909123123', 'cv_t.pdf', 1, NULL),
('Le Thi U', 'u@gmail.com', '0909345456', 'cv_u.pdf', 2, TRUE),
('Tran Van V', 'v@gmail.com', '0909876543', 'cv_v.pdf', 3, NULL),
('Pham Thi W', 'w@gmail.com', '0909887766', 'cv_w.pdf', 4, FALSE),
('Do Van X', 'x@gmail.com', '0909998877', 'cv_x.pdf', 5, NULL),
('Nguyen Thi Y', 'y@gmail.com', '0909333222', 'cv_y.pdf', 2, TRUE),
('Ho Van Z', 'z@gmail.com', '0909000999', 'cv_z.pdf', 4, NULL);

-- ===== INTERVIEW =====
INSERT INTO Interview (candidate_id, created_by, interviewed_by, date, time, result)
VALUES
(1, 3, 2, '2025-10-22', '09:00:00', 'Pending'),
(2, 4, 1, '2025-10-23', '10:00:00', 'Pass'),
(3, 6, 2, '2025-10-24', '14:00:00', 'Pending'),
(4, 8, 1, '2025-10-25', '15:00:00', 'Fail'),
(5, 5, 4, '2025-10-26', '09:30:00', 'Pending'),
(6, 4, 2, '2025-10-27', '11:00:00', 'Pass'),
(7, 8, 1, '2025-10-28', '13:00:00', 'Pending');

-- ===== SALARY =====
INSERT INTO Salary (emp_id, base_salary, allowance, start_date, is_active)
VALUES
(1, 40000000, 8000000, '2025-01-01', TRUE),
(2, 25000000, 5000000, '2025-01-01', TRUE),
(3, 18000000, 3000000, '2025-01-01', TRUE),
(4, 20000000, 2000000, '2025-01-01', TRUE),
(5, 19000000, 1500000, '2025-01-01', TRUE),
(6, 17000000, 3000000, '2025-01-01', TRUE),
(7, 15000000, 2000000, '2025-01-01', TRUE),
(8, 22000000, 2500000, '2025-01-01', TRUE),
(9, 16000000, 1500000, '2025-01-01', TRUE),
(10, 21000000, 2000000, '2025-01-01', TRUE);

-- ===== PAYROLL (tháng 9/2025) =====
INSERT INTO Payroll (emp_id, total_work_day, total_work_hours, SI, HI, UI, tax, total_salary, month, year)
VALUES
(1, 22, 176, 500000, 300000, 200000, 2000000, 46800000, 9, 2025),
(2, 21, 168, 400000, 250000, 150000, 1500000, 29000000, 9, 2025),
(3, 20, 160, 300000, 200000, 100000, 1000000, 23000000, 9, 2025),
(4, 23, 184, 300000, 250000, 120000, 900000, 25500000, 9, 2025),
(5, 22, 176, 300000, 250000, 100000, 800000, 24500000, 9, 2025),
(6, 21, 168, 250000, 200000, 100000, 700000, 20500000, 9, 2025),
(7, 20, 160, 250000, 200000, 80000, 600000, 19000000, 9, 2025),
(8, 22, 176, 350000, 250000, 120000, 1000000, 24000000, 9, 2025),
(9, 22, 176, 250000, 200000, 90000, 600000, 19000000, 9, 2025),
(10, 22, 176, 300000, 250000, 100000, 800000, 26000000, 9, 2025);

-- ===== ATTENDANCE_RAW (mẫu IN/OUT) =====
INSERT INTO Attendance_Raw (emp_id, date, check_time, check_type) VALUES
(1, '2025-10-15', '08:00:00', 'IN'), (1, '2025-10-15', '17:00:00', 'OUT'),
(2, '2025-10-15', '08:10:00', 'IN'), (2, '2025-10-15', '17:15:00', 'OUT'),
(4, '2025-10-15', '08:00:00', 'IN'), (4, '2025-10-15', '19:00:00', 'OUT'),
(5, '2025-10-15', '08:30:00', 'IN'), (5, '2025-10-15', '17:30:00', 'OUT');

-- ===== DAILY_ATTENDANCE (tổng hợp) =====
INSERT INTO Daily_Attendance (emp_id, date, check_in_time, check_out_time, status)
VALUES
(1, '2025-10-15', '08:00:00', '17:00:00', 'Present'),
(2, '2025-10-15', '08:10:00', '17:15:00', 'Present'),
(3, '2025-10-15', NULL, NULL, 'Absent'),
(4, '2025-10-15', '08:00:00', '19:00:00', 'Present'),
(5, '2025-10-15', '08:30:00', '17:30:00', 'Present'),
(6, '2025-10-15', '08:15:00', '17:10:00', 'Present'),
(7, '2025-10-15', NULL, NULL, 'Leave'),
(8, '2025-10-15', '08:00:00', '17:00:00', 'Present'),
(9, '2025-10-15', '08:00:00', '17:00:00', 'Present'),
(10, '2025-10-15', '08:00:00', '17:30:00', 'Present');

-- ===== OT REQUEST =====
INSERT INTO OT_Request (emp_id, date, ot_hours, approved_by, status)
VALUES
(4, '2025-10-12', 2.5, 2, 'Approved'),
(5, '2025-10-13', 3.0, 2, 'Approved'),
(10, '2025-10-14', 1.5, 2, 'Pending'),
(6, '2025-10-15', 2.0, 2, 'Approved'),
(7, '2025-10-16', 2.0, 2, 'Pending'),
(3, '2025-10-17', 1.5, 2, 'Rejected');

-- ===== LEAVE REQUEST (day_requested = INT) =====
INSERT INTO Leave_Request (emp_id, leave_type, reason, day_requested, start_date, end_date, approved_by, status)
VALUES
(3, 'Sick', 'Ốm nhẹ cần nghỉ ngơi', 1, '2025-10-05', '2025-10-05', 2, 'Approved'),
(7, 'Annual Leave', 'Du lịch cùng gia đình', 3, '2025-10-10', '2025-10-12', 2, 'Approved'),
(9, 'Unpaid', 'Việc riêng cá nhân', 1, '2025-10-08', '2025-10-08', 1, 'Approved'),
(5, 'Annual Leave', 'Về quê', 2, '2025-10-03', '2025-10-04', 2, 'Rejected'),
(6, 'Sick', 'Cảm cúm', 1, '2025-10-09', '2025-10-09', 2, 'Approved'),
(10, 'Annual Leave', 'Du lịch', 4, '2025-10-18', '2025-10-21', 1, 'Pending'),
(8, 'Other', 'Họp gia đình', 1, '2025-10-11', '2025-10-11', 2, 'Approved');

-- ===== CONTRACT =====
INSERT INTO Contract (emp_id, type, start_date, end_date, contract_img)
VALUES
(1, 'Permanent', '2020-01-01', NULL, 'contract_1.pdf'),
(2, 'Permanent', '2021-03-01', NULL, 'contract_2.pdf'),
(3, 'Fixed-term', '2023-06-01', '2025-12-31', 'contract_3.pdf'),
(4, 'Permanent', '2022-01-01', NULL, 'contract_4.pdf'),
(5, 'Fixed-term', '2024-02-01', '2025-12-31', 'contract_5.pdf'),
(6, 'Fixed-term', '2024-03-01', '2025-12-31', 'contract_6.pdf'),
(7, 'Probation', '2025-09-01', '2025-12-31', 'contract_7.pdf'),
(8, 'Permanent', '2021-05-01', NULL, 'contract_8.pdf'),
(9, 'Fixed-term', '2024-07-01', '2026-06-30', 'contract_9.pdf'),
(10, 'Probation', '2025-08-01', '2025-12-31', 'contract_10.pdf');

-- ===== HOLIDAY =====
INSERT INTO Holiday (date, name, source)
VALUES
('2025-01-01', 'New Year', 'Government'),
('2025-02-28', 'Lunar New Year', 'Government'),
('2025-03-01', 'Lunar New Year Holiday', 'Government'),
('2025-04-30', 'Liberation Day', 'Government'),
('2025-05-01', 'Labor Day', 'Government'),
('2025-09-02', 'National Day', 'Government'),
('2025-09-15', 'Mid-Autumn Festival', 'Company Event'),
('2025-12-25', 'Christmas Day', 'Company Holiday');
