///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package helper;
//
//import dal.*;
//import java.sql.Time;
//import java.sql.Date;
//import java.time.Duration;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//import model.*;
//
///**
// *
// * @author admin
// */
//public class AttendanceCalculator {
//
//    public static List<DailyAttendance> calculate(List<AttendanceRaw> raws, List<Holiday> holidays, List<LeaveRequest> leaves, List<OTRequest> otRequests) {
//        EmployeeDAO empDAO = new EmployeeDAO();
//
//        Map<String, List<AttendanceRaw>> grouped = raws.stream().collect(Collectors.groupingBy(r -> r.getEmp().getEmpId() + "_" + r.getDate()));
//        List<DailyAttendance> results = new ArrayList<>();
//        for (String key : grouped.keySet()) {
//            List<AttendanceRaw> dayLogs = grouped.get(key);
//            if (dayLogs.isEmpty()) {
//                continue;
//            }
//            AttendanceRaw first = Collections.min(dayLogs, Comparator.comparing(r -> r.getCheckTime()));
//            AttendanceRaw last = Collections.max(dayLogs, Comparator.comparing(r -> r.getCheckTime()));
//            Employee e = first.getEmp();
//            Date date = first.getDate();
//            DailyAttendance daily = new DailyAttendance();
//            daily.setEmployee(e);
//            daily.setDate(new java.sql.Date(date.getTime()));
//            daily.setCheckInTime(first.getCheckTime());
//            daily.setCheckOutTime(last.getCheckTime());
//
//            //Holiday
//            if (isHoliday(date, holidays)) {
//                daily.setWorkDay(1.0);
//                daily.setStatus("Holiday");
//                daily.setOtHours(0.0);
//                results.add(daily);
//                continue;
//            }
//
//            //Have log
//            if (dayLogs != null && !dayLogs.isEmpty()) {
//                double workedHours = calculateWorkHours(first.getCheckTime(), last.getCheckTime());
//                double workDay = calcWorkDay(workedHours);
//                daily.setWorkDay(workDay);
//                daily.setStatus("Present");
//                double otHours = calcOTHours(e, date, last.getCheckTime(), otRequests);
//                daily.setOtHours(otHours);
//                results.add(daily);
//                continue;
//            }
//        }
//
//        //All leave request
//        Map<String, LeaveRequest> leaveMap = new HashMap<>();
//        for (LeaveRequest l : leaves) {
//            Date start = l.getStartDate();
//            Date end = l.getEndDate();
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(start);
//            while (!cal.getTime().after(end)) {
//                Date d = new java.sql.Date(cal.getTimeInMillis());
//                String key = l.getEmployee().getEmpId() + "_" + d.toString();
//                leaveMap.put(key, l);
//                cal.add(Calendar.DATE, 1);
//            }
//        }
//
//        Set<String> existingKeys = results.stream()
//                .map(d -> d.getEmployee().getEmpId() + "_" + d.getDate().toString())
//                .collect(Collectors.toSet());
//
//        for (Map.Entry<String, LeaveRequest> entry : leaveMap.entrySet()) {
//            //Have log, have leave request
//            if (existingKeys.contains(entry.getKey())) {
//                continue;
//            }
//
//            //No log, have leave request
//            LeaveRequest leave = entry.getValue();
//            String[] parts = entry.getKey().split("_");
//            Employee e = empDAO.getEmployeeByEmpId(Integer.parseInt(parts[0]));
//            Date date = Date.valueOf(parts[1]);
//            DailyAttendance daily = new DailyAttendance();
//            daily.setEmployee(e);
//            daily.setDate(date);
//
//            if ("Approved".equalsIgnoreCase(leave.getStatus())) {
//                daily.setStatus("Leave");
//                if ("annual leave".equalsIgnoreCase(leave.getLeaveType())) {
//                    daily.setWorkDay(1.0);
//                } else {
//                    daily.setWorkDay(0.0);
//                }
//            } else if ("Rejected".equalsIgnoreCase(leave.getStatus())) {
//                daily.setStatus("Absent");
//                daily.setWorkDay(0.0);
//            }
//            daily.setOtHours(0.0);
//            results.add(daily);
//        }
//
//        Set<String> existingKeysAll = results.stream().map(d -> d.getEmployee().getEmpId() + "_" + d.getDate().toString()).collect(Collectors.toSet());
//        Set<Integer> allEmpIds = new HashSet<>();
//        raws.forEach(r -> allEmpIds.add(r.getEmp().getEmpId()));
//        leaves.forEach(l -> allEmpIds.add(l.getEmployee().getEmpId()));
//
//        Set<Date> allDates = raws.stream().map(AttendanceRaw::getDate).collect(Collectors.toSet());
//        for (LeaveRequest l : leaves) {
//            Date start = l.getStartAt();
//            Date end = l.getEndAt();
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(start);
//            while (!cal.getTime().after(end)) {
//                allDates.add(cal.getTime());
//                cal.add(Calendar.DATE, 1);
//            }
//        }
//
//// Với mỗi emp + mỗi ngày → nếu chưa có record nào → absent
//        for (Integer empId : allEmpIds) {
//            for (Date date : allDates) {
//                String key = empId + "_" + date.toString();
//                if (existingKeysAll.contains(key)) {
//                    continue;
//                }
//                if (isHoliday(date, holidays)) {
//                    continue; // bỏ qua lễ
//                }
//                DailyAttendance daily = new DailyAttendance();
//                daily.setEmpId(empId);
//                daily.setDate(date);
//                daily.setWorkDay(0.0);
//                daily.setStatus("absent");
//                daily.setOtHours(0.0);
//                results.add(daily);
//            }
//        }
//
//        return results;
//    }
//
//    private static boolean isHoliday(Date date, List<Holiday> holidays) {
//        for (Holiday h : holidays) {
//            if (h.getDate() != null && h.getDate().equals(date)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private static double calcOTHours(Employee employee, Date date, Time checkout, List<OTRequest> otRequests) {
//        for (OTRequest ot : otRequests) {
//            if (ot.getEmployee() != null
//                    && ot.getEmployee().getEmpId() == employee.getEmpId()
//                    && ot.getDate().equals(date)
//                    && "approved".equalsIgnoreCase(ot.getStatus())) {
//                LocalTime out = checkout.toLocalTime();
//                LocalTime base = LocalTime.of(18, 0);
//                double diff = Duration.between(base, out).toMinutes() / 60.0;
//                return Math.max(0, Math.min(diff, ot.getOtHours()));
//            }
//        }
//        return 0;
//    }
//
//    private static double calculateWorkHours(Time in, Time out) {
//        LocalTime tIn = in.toLocalTime();
//        LocalTime tOut = out.toLocalTime();
//
//        LocalTime workStart = LocalTime.of(8, 0);
//        LocalTime workEnd = LocalTime.of(17, 30);
//        LocalTime noonStart = LocalTime.of(12, 0);
//        LocalTime noonEnd = LocalTime.of(13, 30);
//
//        if (tIn.isBefore(workStart)) {
//            tIn = workStart;
//        }
//        if (tOut.isAfter(workEnd)) {
//            tOut = workEnd;
//        }
//        if (!tOut.isAfter(tIn)) {
//            return 0.0;
//        }
//        double hours = Duration.between(tIn, tOut).toMinutes() / 60.0;
//
//        LocalTime overlapStart = tIn.isAfter(noonStart) ? tIn : noonStart;
//        LocalTime overlapEnd = tOut.isBefore(noonEnd) ? tOut : noonEnd;
//        if (overlapEnd.isAfter(overlapStart)) {
//            double overlapHours = Duration.between(overlapStart, overlapEnd).toMinutes() / 60.0;
//            hours -= overlapHours;
//        }
//        return Math.max(0, hours);
//    }
//
//    private static double calcWorkDay(double hoursWorked) {
//        if (hoursWorked < 3) {
//            return 0.0;
//        }
//        if (hoursWorked < 7) {
//            return 0.5;
//        }
//        return 1.0;
//    }
//}
