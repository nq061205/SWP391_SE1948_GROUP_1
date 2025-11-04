/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DailyAttendanceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.DailyAttendance;
import model.Employee;

/**
 *
 * @author Lenovo
 */
public class AttendanceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employee user = (Employee) session.getAttribute("user");
        if (user == null) {
            request.getRequestDispatcher("Views/login.jsp").forward(request, response);
            return;
        }
        DailyAttendanceDAO dailyDAO = new DailyAttendanceDAO();
        String monthParam = request.getParameter("month");
        String yearParam = request.getParameter("year");

        LocalDate today = LocalDate.now();
        int month = (monthParam == null || monthParam.isEmpty())
                ? today.getMonthValue()
                : Integer.parseInt(monthParam);
        int year = (yearParam == null || yearParam.isEmpty())
                ? today.getYear()
                : Integer.parseInt(yearParam);

        // --- Xác định ngày đầu tháng ---
        LocalDate firstDay = LocalDate.of(year, month, 1);
        int firstDayOfWeek = firstDay.getDayOfWeek().getValue(); // 1=Mon ...7=Sun
        int daysInMonth = firstDay.lengthOfMonth();

        // --- Tạo danh sách các ngày ---
        List<Map<String, Object>> days = new ArrayList<>();

        for (int i = 1; i <= daysInMonth; i++) {
            LocalDate current = LocalDate.of(year, month, i);
            int dayOfWeek = current.getDayOfWeek().getValue();

            Map<String, Object> dayInfo = new HashMap<>();
            dayInfo.put("day", i);
            dayInfo.put("dayOfWeek", dayOfWeek);
            dayInfo.put("isToday", current.equals(today));

            // 🟩 1️⃣ Kiểm tra cuối tuần TRƯỚC
            if (current.getDayOfWeek() == DayOfWeek.SATURDAY || current.getDayOfWeek() == DayOfWeek.SUNDAY) {
                dayInfo.put("status", "weekend");
                dayInfo.put("checkIn", "-");
                dayInfo.put("checkOut", "-");
                dayInfo.put("workday", "0");
            } else {
                // 🟨 2️⃣ Chỉ query DB cho ngày làm việc
                DailyAttendance attendance = dailyDAO.getDailyAttendentByTime(user.getEmpId(), i, month, year);

                if (attendance != null) {
                    dayInfo.put("status", attendance.getStatus());
                    dayInfo.put("checkIn", attendance.getCheckInTime() != null ? attendance.getCheckInTime().toString() : "-");
                    dayInfo.put("checkOut", attendance.getCheckOutTime() != null ? attendance.getCheckOutTime().toString() : "-");
                    dayInfo.put("workday", attendance.getWorkDay());
                } else {
                    dayInfo.put("status", "");
                    dayInfo.put("checkIn", "-");
                    dayInfo.put("checkOut", "-");
                    dayInfo.put("workday", "0");
                }
            }

            days.add(dayInfo);
        }

        request.setAttribute("month", month);
        request.setAttribute("year", year);
        request.setAttribute("firstDayOfWeek", firstDayOfWeek);
        request.setAttribute("days", days);

        request.getRequestDispatcher("Views/attendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
