///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package helper;
//
//import dal.DailyAttendanceDAO;
//import java.sql.Date;
//import java.sql.Time;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import model.AttendanceRaw;
//
///**
// *
// * @author admin
// */
//public class AttendanceService {
//
//    private DailyAttendanceDAO dailyDAO;
//
//    public AttendanceService() {
//        this.dailyDAO = new DailyAttendanceDAO();
//    }
//
//    public static List<AttendanceDaily> calculate(List<AttendanceRaw> raws) {
//        Map<String, List<AttendanceRaw>> grouped
//                = raws.stream().collect(Collectors.groupingBy(r -> r.getEmployee().getEmpId() + "_" + r.getDate()));
//
//        List<AttendanceDaily> result = new ArrayList<>();
//
//        for (String key : grouped.keySet()) {
//            List<AttendanceRaw> list = grouped.get(key);
//            String empId = list.get(0).getEmployee().getEmpId();
//            Date date = list.get(0).getDate();
//
//            // Giờ vào đầu tiên, giờ ra cuối cùng
//            Time checkIn = list.stream().map(AttendanceRaw::getCheckTime).min(Time::compareTo).orElse(null);
//            Time checkOut = list.stream().map(AttendanceRaw::getCheckTime).max(Time::compareTo).orElse(null);
//
//            double workDay = 1.0; // bạn có thể tính logic thực tế ở đây
//            double otHours = 0.0; // tạm thời chưa tính OT
//
//            String status = (checkIn == null || checkOut == null) ? "Absent" : "Present";
//
//            AttendanceDaily daily = new AttendanceDaily();
//            daily.setEmpId(empId);
//            daily.setDate(date);
//            daily.setCheckInTime(checkIn);
//            daily.setCheckOutTime(checkOut);
//            daily.setWorkDay(workDay);
//            daily.setOtHours(otHours);
//            daily.setStatus(status);
//
//            result.add(daily);
//        }
//
//        return result;
//    }
//
//    // 🔹 Hàm thao tác DB (non-static)
//    public void saveCalculatedData(List<AttendanceDaily> dailyList) {
//        for (AttendanceDaily d : dailyList) {
//            dailyDAO.upsert(d); // ví dụ: nếu có rồi thì update, chưa có thì insert
//        }
//    }
