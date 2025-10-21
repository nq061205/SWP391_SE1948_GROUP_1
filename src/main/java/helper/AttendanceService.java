/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import dal.*;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.*;

/**
 *
 * @author admin
 */
public class AttendanceService {

    public void processDailyAttendance(List<AttendanceRaw> raws) {
        if (raws == null || raws.isEmpty()) {
            return;
        }

        HolidayDAO holidayDAO = new HolidayDAO();
        OTRequestDAO otDAO = new OTRequestDAO();
        LeaveRequestDAO leaveDAO = new LeaveRequestDAO();
        EmployeeDAO empDAO = new EmployeeDAO();
        DailyAttendanceDAO dailyDAO = new DailyAttendanceDAO();

        Date minDate = raws.stream().map(r -> r.getDate()).min((d1, d2) -> d1.compareTo(d2)).orElse(null);
        Date maxDate = raws.stream().map(r -> r.getDate()).max((d1, d2) -> d1.compareTo(d2)).orElse(null);

        int startYear = getYear(minDate);
        int endYear = getYear(maxDate);

        List<Holiday> holidays = new ArrayList<>();
        for (int y = startYear; y <= endYear; y++) {
            holidays.addAll(holidayDAO.getAllHolidayByYear(y));
        }

        List<LeaveRequest> leaves = leaveDAO.getApprovedLeavesBetween(minDate, maxDate);
        List<OTRequest> otRequests = otDAO.getApprovedOTBetween(minDate, maxDate);
        List<Employee> employees = empDAO.getAllEmployees();

        List<DailyAttendance> dailyList = AttendanceCalculator.calculate(raws, holidays, leaves, otRequests, employees);
        dailyDAO.upsertDailyAttendance(dailyList);
    }

    private int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }
}
