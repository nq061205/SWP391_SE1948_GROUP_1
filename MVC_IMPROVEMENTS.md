# Cải Tiến Tuân Thủ Mô Hình MVC

## Tóm tắt
Đã chuyển các logic từ JSP về Servlet để tuân thủ đúng mô hình MVC (Model-View-Controller).

## Các Thay Đổi Chính

### 1. **HRRecruitmentServlet.java** - Controller Layer

#### a. Business Logic trong `showApprovedPostsList()`
**Trước:**
```java
request.setAttribute("totalPosts", approvedPosts.size());
```

**Sau:**
```java
// Business logic: Process data before sending to view
// Calculate total posts (handle null safely)
int totalPosts = (approvedPosts != null) ? approvedPosts.size() : 0;

// Check if lists are empty and set flags for view
boolean hasApprovedPosts = (approvedPosts != null && !approvedPosts.isEmpty());
boolean hasPendingOrRejected = (pendingAndRejectedPosts != null && !pendingAndRejectedPosts.isEmpty());
boolean hasDepartments = (departments != null && !departments.isEmpty());

request.setAttribute("totalPosts", totalPosts);
request.setAttribute("hasApprovedPosts", hasApprovedPosts);
request.setAttribute("hasPendingOrRejected", hasPendingOrRejected);
request.setAttribute("hasDepartments", hasDepartments);
```

**Lợi ích:**
- Xử lý null safely trong Controller thay vì JSP
- Tính toán và set flags để JSP chỉ cần hiển thị
- Giảm logic điều kiện phức tạp trong View

#### b. Server-side Validation trong `createPost()`
**Trước:**
```java
// Validate input
if (title == null || title.trim().isEmpty() ||
    content == null || content.trim().isEmpty() ||
    depId == null || depId.trim().isEmpty()) {
    request.getSession().setAttribute("errorMessage", "All fields are required.");
    response.sendRedirect(request.getContextPath() + "/hrrecruitment");
    return;
}
```

**Sau:**
```java
// Server-side validation - Business logic
StringBuilder validationErrors = new StringBuilder();

if (title == null || title.trim().isEmpty()) {
    validationErrors.append("Title is required. ");
} else if (title.trim().length() > 255) {
    validationErrors.append("Title must not exceed 255 characters. ");
}

if (content == null || content.trim().isEmpty()) {
    validationErrors.append("Job description is required. ");
}

if (depId == null || depId.trim().isEmpty()) {
    validationErrors.append("Department is required. ");
}

// If validation fails, redirect with error message
if (validationErrors.length() > 0) {
    request.getSession().setAttribute("errorMessage", validationErrors.toString().trim());
    response.sendRedirect(request.getContextPath() + "/hrrecruitment");
    return;
}

// Create post with trimmed values
boolean success = recruitmentPostDAO.createPost(title.trim(), content.trim(), depId.trim(), createdBy, approvedBy);
```

**Lợi ích:**
- Validation chi tiết hơn với nhiều rule
- Error messages cụ thể cho từng trường
- Trim dữ liệu trước khi lưu vào database
- Validation logic tập trung ở Controller, không phụ thuộc vào client-side JavaScript

#### c. Enhanced `viewPostDetail()` 
**Trước:**
```java
if (post != null) {
    request.setAttribute("post", post);
    request.setAttribute("currentPage", "Recruitment Management");
    request.setAttribute("pageTitle", "Post Detail");
    request.getRequestDispatcher("/Views/HR/recruitmentPostDetail.jsp").forward(request, response);
}
```

**Sau:**
```java
if (post != null) {
    // Business logic: Set flags for view
    boolean hasPost = true;
    boolean hasContent = (post.getContent() != null && !post.getContent().trim().isEmpty());
    boolean hasDepartment = (post.getDepartment() != null);
    boolean hasCreatedBy = (post.getCreatedBy() != null);
    boolean hasApprovedBy = (post.getApprovedBy() != null);
    boolean hasCreatedAt = (post.getCreatedAt() != null);
    boolean hasApprovedAt = (post.getApprovedAt() != null);
    boolean hasUpdatedAt = (post.getUpdatedAt() != null);
    
    request.setAttribute("post", post);
    request.setAttribute("hasPost", hasPost);
    request.setAttribute("hasContent", hasContent);
    request.setAttribute("hasDepartment", hasDepartment);
    request.setAttribute("hasCreatedBy", hasCreatedBy);
    request.setAttribute("hasApprovedBy", hasApprovedBy);
    request.setAttribute("hasCreatedAt", hasCreatedAt);
    request.setAttribute("hasApprovedAt", hasApprovedAt);
    request.setAttribute("hasUpdatedAt", hasUpdatedAt);
    // ...
}
```

**Lợi ích:**
- Tất cả logic kiểm tra null được xử lý ở Controller
- JSP chỉ cần kiểm tra boolean flags đơn giản
- Dễ test và maintain

#### d. Enhanced `editPost()` và `updatePost()`
Tương tự như trên, đã thêm:
- Detailed validation với error messages cụ thể
- Null-safe calculations
- Boolean flags cho conditional rendering
- Data trimming

### 2. **recruitmentManagement.jsp** - View Layer

#### a. Loại bỏ Logic Tính Toán
**Trước:**
```jsp
<span class="badge badge-success">Total: ${totalPosts != null ? totalPosts : 0} posts</span>
```

**Sau:**
```jsp
<span class="badge badge-success">Total: ${totalPosts} posts</span>
```

#### b. Sử dụng Flags từ Controller
**Trước:**
```jsp
<c:when test="${not empty approvedPosts}">
```

**Sau:**
```jsp
<c:when test="${hasApprovedPosts}">
```

#### c. Loại bỏ Client-side Validation
**Trước:**
```javascript
// Form validation
$('#createPostForm').on('submit', function(e) {
    var title = $('#title').val().trim();
    var content = $('#content').val().trim();
    var depId = $('#depId').val();
    
    if (!title || !content || !depId) {
        e.preventDefault();
        alert('Please fill in all required fields.');
        return false;
    }
});
```

**Sau:**
```javascript
// Removed - validation now handled on server-side
```

**Lợi ích:**
- Bảo mật tốt hơn (không thể bypass validation)
- Single source of truth cho validation rules
- HTML5 validation attributes vẫn được giữ lại cho UX

#### d. Loại bỏ Debug Code
**Trước:**
```jsp
<!-- Debug info -->
<small class="text-muted">
    Debug: Departments count = ${departments.size()}
    <c:if test="${not empty departments}">
        | First dept: ${departments[0].depName}
    </c:if>
</small>
```

**Sau:**
```jsp
<!-- Removed debug code -->
```

### 3. **recruitmentPostDetail.jsp** - View Layer

#### Sử dụng Flags thay vì Logic Điều Kiện
**Trước:**
```jsp
<c:when test="${not empty post}">
<c:if test="${not empty post.department}">
<c:if test="${not empty post.approvedAt}">
<c:when test="${not empty post.createdBy}">
```

**Sau:**
```jsp
<c:when test="${hasPost}">
<c:if test="${hasDepartment}">
<c:if test="${hasApprovedAt}">
<c:when test="${hasCreatedBy}">
```

## Lợi Ích Tổng Thể

### 1. **Tuân Thủ MVC**
- ✅ **Model**: Chỉ chứa data (RecruitmentPost, Department, Employee)
- ✅ **View**: Chỉ hiển thị, không có business logic
- ✅ **Controller**: Xử lý tất cả business logic, validation, data processing

### 2. **Maintainability**
- Code dễ đọc và hiểu hơn
- Logic tập trung ở một nơi (Controller)
- Dễ tìm và sửa bugs

### 3. **Testability**
- Có thể unit test Controller logic
- Không phụ thuộc vào JSP để test business rules

### 4. **Security**
- Server-side validation không thể bypass
- Input sanitization (trim) trước khi lưu DB
- Null-safe operations

### 5. **Reusability**
- Logic có thể tái sử dụng cho API endpoints
- Flags có thể dùng cho nhiều views khác nhau

### 6. **Performance**
- Tính toán thực hiện một lần ở Controller
- JSP chỉ cần render, không cần tính toán

## Best Practices Được Áp Dụng

1. **Single Responsibility Principle**
   - Controller: Business logic
   - View: Presentation logic only

2. **Defensive Programming**
   - Null checks
   - Input validation
   - Error handling

3. **DRY (Don't Repeat Yourself)**
   - Validation logic ở một nơi
   - Flags được tính toán một lần

4. **Separation of Concerns**
   - Business logic tách biệt khỏi presentation
   - Server-side validation tách biệt khỏi client-side

## Migration Checklist

- [x] Chuyển logic tính toán từ JSP về Servlet
- [x] Thêm server-side validation chi tiết
- [x] Tạo boolean flags cho conditional rendering
- [x] Loại bỏ client-side validation (giữ HTML5 attributes)
- [x] Loại bỏ debug code khỏi production JSP
- [x] Null-safe operations trong Controller
- [x] Input sanitization (trim)
- [x] Cập nhật JSP để sử dụng flags

## Testing Recommendations

1. **Unit Tests cho Controller**
   ```java
   @Test
   public void testCreatePost_WithEmptyTitle_ShouldReturnError() {
       // Test validation logic
   }
   
   @Test
   public void testShowApprovedPostsList_WithNullList_ShouldHandleGracefully() {
       // Test null handling
   }
   ```

2. **Integration Tests**
   - Test full request/response cycle
   - Verify error messages are displayed correctly

3. **Manual Testing**
   - Test form submission với valid/invalid data
   - Verify error messages
   - Check data is trimmed correctly
