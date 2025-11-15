# DANH SÁCH VALIDATION - QUY TRÌNH TẠO BÀI TUYỂN DỤNG

## Tổng quan
File này liệt kê tất cả các validation được áp dụng trong quy trình tạo và quản lý bài tuyển dụng cho vai trò HR và HRM.

---

## 1. VALIDATION CHO HR (Human Resources)

### 1.1. Tạo Bài Đăng Mới (Create Post)
**Servlet:** `HRRecruitmentServlet.java` - Method: `createPost()`

#### A. Authentication & Authorization
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Kiểm tra user đã đăng nhập | "You must be logged in to create a post." |

#### B. Input Field Validations
| # | Field | Validation | Thông báo lỗi |
|---|-------|-----------|---------------|
| 1 | **Title** | Không được null hoặc rỗng | "Title is required." |
| 2 | **Title** | Không vượt quá 255 ký tự | "Title must not exceed 255 characters." |
| 3 | **Content** | Không được null hoặc rỗng | "Job description is required." |
| 4 | **Department ID** | Không được null, rỗng hoặc empty string | "Please select a department." |

#### C. Client-side Validations (JavaScript - recruitmentManagement.jsp)
| # | Field | Validation | Hành động |
|---|-------|-----------|-----------|
| 1 | **Content (Summernote)** | Kiểm tra nội dung không rỗng và không phải `<p><br></p>` | Alert: "Please enter job description" |

---

### 1.2. Chỉnh Sửa Bài Đăng (Edit Post)
**Servlet:** `HRRecruitmentServlet.java` - Method: `editPost()`

#### A. Request Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Post ID không được null hoặc rỗng | "Invalid post ID." |
| 2 | Post ID phải là số hợp lệ (NumberFormatException) | "Invalid post ID format." |
| 3 | Bài đăng phải tồn tại trong database | "Post not found or cannot be edited." |
| 4 | Status của bài đăng phải là "Rejected" hoặc "New" | "Post not found or cannot be edited." |

---

### 1.3. Cập Nhật Bài Đăng (Update Post)
**Servlet:** `HRRecruitmentServlet.java` - Method: `updatePost()`

#### A. Input Validations
| # | Field | Validation | Thông báo lỗi |
|---|-------|-----------|---------------|
| 1 | **Post ID** | Không được null hoặc rỗng | "Post ID is required." |
| 2 | **Post ID** | Phải là số hợp lệ | "Invalid post ID format." |
| 3 | **Title** | Không được null hoặc rỗng | "Title is required." |
| 4 | **Title** | Không vượt quá 255 ký tự | "Title must not exceed 255 characters." |
| 5 | **Content** | Không được null hoặc rỗng | "Job description is required." |
| 6 | **Department ID** | Không được null, rỗng hoặc empty string | "Please select a department." |

#### B. Business Logic Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Status phải là 'New' hoặc 'Rejected' (kiểm tra trong DAO) | "Failed to update post. Please ensure the post status is 'New' or 'Rejected'." |

#### C. Client-side Validations (JavaScript - recruitmentManagement.jsp)
| # | Field | Validation | Hành động |
|---|-------|-----------|-----------|
| 1 | **Edit Content (Summernote)** | Kiểm tra nội dung không rỗng và không phải `<p><br></p>` | Alert: "Please enter job description" |

---

### 1.4. Gửi Bài Đăng (Send Post)
**Servlet:** `HRRecruitmentServlet.java` - Method: `sendPost()`

#### A. Input Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Post ID không được null hoặc rỗng | "Post ID is required." |
| 2 | Post ID phải là số hợp lệ | "Invalid post ID format." |

#### B. Business Logic Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Status phải là 'New' (kiểm tra trong DAO) | "Failed to send post. Please ensure the post status is 'New'." |

---

### 1.5. Xóa Bài Đăng (Delete Post)
**Servlet:** `HRRecruitmentServlet.java` - Method: `deletePost()`

#### A. Input Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Post ID không được null hoặc rỗng | "Post ID is required." |
| 2 | Post ID phải là số hợp lệ | "Invalid post ID format." |
| 3 | Bài đăng phải tồn tại | "Post not found." |

#### B. Business Logic Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Status phải là 'New' hoặc 'Rejected' | "Only posts with status 'New' or 'Rejected' can be deleted." |

---

## 2. VALIDATION CHO HRM (HR Manager)

### 2.1. Duyệt Bài Đăng (Approve Post)
**Servlet:** `HRManagerRecruitmentServlet.java` - Method: `approvePost()`

#### A. Authentication & Authorization
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Kiểm tra user đã đăng nhập | "You must be logged in to approve a post." |

#### B. Input Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Post ID không được null hoặc rỗng | "Post ID is required." |
| 2 | Post ID phải là số hợp lệ | "Invalid post ID format." |
| 3 | Bài đăng phải tồn tại | "Post not found." |

#### C. Business Logic Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Status phải là 'Waiting' | "Only waiting posts can be approved." |

---

### 2.2. Từ Chối Bài Đăng (Reject Post)
**Servlet:** `HRManagerRecruitmentServlet.java` - Method: `rejectPost()`

#### A. Input Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Post ID không được null hoặc rỗng | "Post ID is required." |
| 2 | Post ID phải là số hợp lệ | "Invalid post ID format." |
| 3 | Bài đăng phải tồn tại | "Post not found." |

#### B. Business Logic Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Status phải là 'Waiting' | "Only waiting posts can be rejected." |

---

### 2.3. Đăng Tải Bài (Upload Post)
**Servlet:** `ManagePostServlet.java` - Method: `uploadPost()`

#### A. Input Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Post ID không được null hoặc rỗng | "Post ID is required." |
| 2 | Post ID phải là số hợp lệ | "Invalid post ID format." |
| 3 | Bài đăng phải tồn tại | "Post not found." |

#### B. Business Logic Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Status phải là 'Approved' | "Only approved posts can be uploaded." |

---

### 2.4. Xóa Bài Đã Duyệt (Delete Approved Post)
**Servlet:** `ManagePostServlet.java` - Method: `deletePost()`

#### A. Input Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Post ID không được null hoặc rỗng | "Post ID is required." |
| 2 | Post ID phải là số hợp lệ | "Invalid post ID format." |
| 3 | Bài đăng phải tồn tại | "Post not found." |

#### B. Business Logic Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Status phải là 'Approved' | "Only approved posts can be deleted." |

---

### 2.5. Gỡ Bài Đã Đăng (Take Down Post)
**Servlet:** `ManagePostServlet.java` - Method: `takeDownPost()`

#### A. Input Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Post ID không được null hoặc rỗng | "Post ID is required." |
| 2 | Post ID phải là số hợp lệ | "Invalid post ID format." |
| 3 | Bài đăng phải tồn tại | "Post not found." |

#### B. Business Logic Validations
| # | Validation | Thông báo lỗi |
|---|-----------|---------------|
| 1 | Status phải là 'Uploaded' | "Only uploaded posts can be taken down." |

---

## 3. LƯU ĐỒ TRẠNG THÁI (STATUS FLOW)

```
[New] ──(HR: Send)──> [Waiting] ──(HRM: Approve)──> [Approved] ──(HRM: Upload)──> [Uploaded]
  │                       │                              │                             │
  │                       │                              │                             │
  └──(HR: Edit)───────────┴──(HRM: Reject)──> [Rejected] │                             │
  │                                             │         │                             │
  └──(HR: Delete)                               └─────────┴──(HRM: Delete)              │
                                                                                        │
                                                                └──(HRM: Take Down)─────┘
```

### Quy Tắc Chuyển Trạng Thái:
| Trạng thái hiện tại | Hành động cho phép | Vai trò | Trạng thái mới |
|---------------------|-------------------|---------|----------------|
| **New** | Edit | HR | New |
| **New** | Delete | HR | (Deleted) |
| **New** | Send | HR | Waiting |
| **Waiting** | Approve | HRM | Approved |
| **Waiting** | Reject | HRM | Rejected |
| **Rejected** | Edit | HR | New |
| **Rejected** | Delete | HR | (Deleted) |
| **Approved** | Upload | HRM | Uploaded |
| **Approved** | Delete | HRM | (Deleted) |
| **Uploaded** | Take Down | HRM | Approved |

---

## 4. PAGINATION & FILTERING VALIDATIONS

### 4.1. HR - Pagination
**Servlet:** `HRRecruitmentServlet.java`

| # | Parameter | Validation | Giá trị mặc định |
|---|-----------|-----------|------------------|
| 1 | **page** | Phải là số nguyên dương >= 1 | 1 |
| 2 | **pageSize** | Phải trong khoảng 5-100 | 10 |
| 3 | **notifPageSize** | Phải trong khoảng 5-50 | 5 |

### 4.2. HRM - Pagination
**Servlet:** `HRManagerRecruitmentServlet.java` & `ManagePostServlet.java`

| # | Parameter | Validation | Giá trị mặc định |
|---|-----------|-----------|------------------|
| 1 | **page** | Phải là số nguyên dương >= 1 | 1 |
| 2 | **pageSize** | Phải trong khoảng 5-100 | 10 |

---

## 5. EXCEPTION HANDLING

### 5.1. Các Exception Được Xử Lý:
| Exception | Nguyên nhân | Xử lý |
|-----------|------------|-------|
| **NumberFormatException** | Post ID không phải số hợp lệ | Thông báo lỗi + redirect |
| **NullPointerException** | Dữ liệu null không mong muốn | Log error + thông báo chung |
| **ServletException** | Lỗi servlet | Log error + thông báo chung |
| **IOException** | Lỗi I/O | Log error + thông báo chung |
| **Exception** | Lỗi chung | Log error + thông báo chung |

---

## 6. DATA SANITIZATION

### 6.1. Các Trường Được Sanitize:
| Field | Phương pháp |
|-------|-------------|
| **Title** | `.trim()` - Loại bỏ khoảng trắng đầu/cuối |
| **Content** | `.trim()` - Loại bỏ khoảng trắng đầu/cuối |
| **Department ID** | `.trim()` - Loại bỏ khoảng trắng đầu/cuối |
| **Note** | `.trim()` - Loại bỏ khoảng trắng đầu/cuối (nếu có) |
| **Post ID** | `.trim()` + `parseInt()` - Chuyển đổi thành số |

---

## 7. NOTES & BEST PRACTICES

### 7.1. Validation Order:
1. **Authentication** (kiểm tra đăng nhập)
2. **Input presence** (kiểm tra field tồn tại và không null)
3. **Input format** (kiểm tra định dạng - số, độ dài, etc.)
4. **Business logic** (kiểm tra quy tắc nghiệp vụ - status, quyền, etc.)
5. **Database operations** (thực hiện với dữ liệu đã được validate)

### 7.2. Error Message Guidelines:
- Thông báo lỗi rõ ràng, dễ hiểu
- Không tiết lộ thông tin nhạy cảm về hệ thống
- Ghi log chi tiết cho developer (console/file)
- Hiển thị thông báo thân thiện cho user

### 7.3. Security Considerations:
- Luôn kiểm tra authentication trước khi thực hiện action quan trọng
- Validate cả server-side và client-side
- Sử dụng prepared statements trong DAO để tránh SQL injection
- Trim input để tránh whitespace attacks

---

**Ngày tạo:** 14/11/2025  
**Phiên bản:** 1.0  
**Người tạo:** System Documentation
