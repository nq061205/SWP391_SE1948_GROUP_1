CREATE TABLE Role (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(100) NOT NULL
);

CREATE TABLE Permission (
    permission_id INT AUTO_INCREMENT PRIMARY KEY,
    permission_name VARCHAR(100) NOT NULL
);

CREATE TABLE Role_Permission (
    role_id INT NOT NULL,
    permission_id INT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES Role(role_id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES Permission(permission_id) ON DELETE CASCADE
);

CREATE TABLE Department (
    dep_id VARCHAR(5) PRIMARY KEY,
    dep_name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE Employee (
    emp_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_code VARCHAR(10) UNIQUE,
    fullname VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    gender BOOLEAN NOT NULL,
    dob DATE,
    phone VARCHAR(20),
    position_title VARCHAR(100),
    image VARCHAR(255),
    dependant_count INT DEFAULT 0,
    paid_leave_days int DEFAULT 0,   -- Số ngày nghỉ có lương
    dep_id VARCHAR(5),
    role_id INT,
    status BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (dep_id) REFERENCES Department(dep_id) ON DELETE SET NULL,
    FOREIGN KEY (role_id) REFERENCES Role(role_id) ON DELETE SET NULL
);

CREATE TABLE RecruitmentPost (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    status ENUM('Approved', 'Rejected', 'Pending') DEFAULT 'Pending',
    is_delete BOOLEAN DEFAULT FALSE,
    created_by INT NOT NULL,
    approved_by INT,
    dep_id VARCHAR(5),
    approved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES Employee(emp_id) ON DELETE CASCADE,
    FOREIGN KEY (approved_by) REFERENCES Employee(emp_id) ON DELETE SET NULL,
    FOREIGN KEY (dep_id) REFERENCES Department(dep_id) ON DELETE SET NULL
);

CREATE TABLE Candidate (
    candidate_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    CV VARCHAR(255),
    post_id INT NOT NULL,
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    result BOOLEAN NULL,
    FOREIGN KEY (post_id) REFERENCES RecruitmentPost(post_id) ON DELETE CASCADE
);

CREATE TABLE Interview (
    interview_id INT AUTO_INCREMENT PRIMARY KEY,
    candidate_id INT UNIQUE,
    created_by INT,
    interviewed_by INT,
    date DATE NOT NULL,
    time TIME NOT NULL,
    result ENUM('Pending','Pass','Fail') DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (candidate_id) REFERENCES Candidate(candidate_id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES Employee(emp_id) ON DELETE SET NULL,
    FOREIGN KEY (interviewed_by) REFERENCES Employee(emp_id) ON DELETE SET NULL
);

CREATE TABLE Payroll (
    payroll_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL,
    total_work_day DECIMAL(5,2) DEFAULT 0.00,
    total_work_hours DECIMAL(7,2) DEFAULT 0.00, 
    SI DECIMAL(10,2) DEFAULT 0.00,
    HI DECIMAL(10,2) DEFAULT 0.00,
    UI DECIMAL(10,2) DEFAULT 0.00,
    tax DECIMAL(10,2) DEFAULT 0.00,
    total_salary DECIMAL(15,2) DEFAULT 0.00,
    month TINYINT NOT NULL CHECK (month BETWEEN 1 AND 12),
    year YEAR NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (emp_id) REFERENCES Employee(emp_id) ON DELETE CASCADE
);

CREATE TABLE Salary (
    salary_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL,
    base_salary DECIMAL(15,2) NOT NULL,
    allowance DECIMAL(15,2) DEFAULT 0.00,
    start_date DATE NOT NULL,
    end_date DATE DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN NOT NULL,
    FOREIGN KEY (emp_id) REFERENCES Employee(emp_id) ON DELETE CASCADE
);

CREATE TABLE Attendance_Raw (
    id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL,
    date DATE NOT NULL,
    check_time TIME NOT NULL,
    check_type ENUM('IN','OUT') NOT NULL,
    FOREIGN KEY (emp_id) REFERENCES Employee(emp_id) ON DELETE CASCADE
);

CREATE TABLE Daily_Attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL,
    date DATE NOT NULL,
    work_day DECIMAL(3,2) DEFAULT 1.00,
    check_in_time TIME,
    check_out_time TIME,
    ot_hours DECIMAL(5,2) DEFAULT 0.00,
    status ENUM('Present','Absent','Holiday','Leave') DEFAULT 'Absent',
    is_locked BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (emp_id) REFERENCES Employee(emp_id) ON DELETE CASCADE,
    UNIQUE(emp_id, date)
);

CREATE TABLE OT_Request (
    ot_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL, 
    date DATE NOT NULL,
    ot_hours DECIMAL(5,2) NOT NULL,
    approved_by INT DEFAULT NULL,
    approved_at TIMESTAMP NULL,
    status ENUM('Pending','Approved','Rejected','Invalid') DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (emp_id) REFERENCES Employee(emp_id) ON DELETE CASCADE,
    FOREIGN KEY (approved_by) REFERENCES Employee(emp_id) ON DELETE SET NULL
);

CREATE TABLE Leave_Request (
    leave_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL,
    leave_type ENUM('Annual Leave','Sick','Unpaid','Maternity','Other') NOT NULL,
    reason VARCHAR(255),
    day_requested DECIMAL(5,2) NOT NULL,    
    start_date DATE NOT NULL,            
    end_date DATE NOT NULL,            
    approved_by INT DEFAULT NULL,
    approved_at TIMESTAMP NULL,
    status ENUM('Pending','Approved','Rejected','Invalid') DEFAULT 'Pending',
    note TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (emp_id) REFERENCES Employee(emp_id) ON DELETE CASCADE,
    FOREIGN KEY (approved_by) REFERENCES Employee(emp_id) ON DELETE SET NULL
);

CREATE TABLE Contract (
    contract_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL,
    type ENUM('Internship','Probation','Fixed-term','Permanent','Seasonal','Other') NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE DEFAULT NULL,
    contract_img VARCHAR(255),
    FOREIGN KEY (emp_id) REFERENCES Employee(emp_id) ON DELETE CASCADE
);

CREATE TABLE Holiday (
    holiday_id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL UNIQUE, 
    name VARCHAR(150) NOT NULL, 
    source VARCHAR(100),       
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
