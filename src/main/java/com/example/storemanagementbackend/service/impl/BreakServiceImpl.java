// package com.example.storemanagementbackend.service.impl;

// import com.example.storemanagementbackend.model.Break;
// import com.example.storemanagementbackend.model.Employee;
// import com.example.storemanagementbackend.repository.BreakRepository;
// import com.example.storemanagementbackend.repository.EmployeeRepository;
// import com.example.storemanagementbackend.service.BreakService;
// import com.example.storemanagementbackend.service.EmailService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Service;
// import java.time.Duration;
// import java.time.LocalDateTime;
// import java.time.LocalTime;
// import java.time.LocalDate;
// import java.util.List;

// @Service
// public class BreakServiceImpl implements BreakService {

// @Autowired
// private BreakRepository breakRepository;

// @Autowired
// private EmployeeRepository employeeRepository;

// @Autowired
// private EmailService emailService;

// @Override
// public Break startBreak(String employeeId, Break.BreakType breakType, Double
// latitude, Double longitude) {
// if (!isBreakTimeValid(breakType)) {
// throw new RuntimeException("Break can only be started during allowed time
// window");
// }

// if (!isLocationValid(latitude, longitude)) {
// throw new RuntimeException("Break can only be started from office location");
// }

// breakRepository.findByEmployeeIdAndActiveTrue(employeeId)
// .ifPresent(activeBreak -> {
// throw new RuntimeException("Employee already has an active break");
// });

// if (hasAlreadyTakenBreakToday(employeeId, breakType)) {
// String breakName = breakType.name().replace("_", " ").toLowerCase();
// throw new RuntimeException("You have already taken " + breakName + " break
// today. Each break type can only be taken once per day.");
// }

// Employee employee = employeeRepository.findByEmployeeId(employeeId)
// .orElseThrow(() -> new RuntimeException("Employee not found"));

// employee.setBreakStatus("ON_BREAK");
// employeeRepository.save(employee);

// Break breakRecord = new Break(employeeId, breakType);
// breakRecord.setStartLatitude(latitude);
// breakRecord.setStartLongitude(longitude);
// return breakRepository.save(breakRecord);
// }

// @Override
// public Break stopBreak(String employeeId, Double latitude, Double longitude)
// {
// Break activeBreak = breakRepository.findByEmployeeIdAndActiveTrue(employeeId)
// .orElseThrow(() -> new RuntimeException("No active break found"));

// if (!isLocationValid(latitude, longitude)) {
// throw new RuntimeException("Break can only be stopped from office location");
// }

// LocalDateTime endTime = LocalDateTime.now();
// activeBreak.setEndTime(endTime);
// activeBreak.setActive(false);
// activeBreak.setEndLatitude(latitude);
// activeBreak.setEndLongitude(longitude);

// Employee employee = employeeRepository.findByEmployeeId(employeeId)
// .orElseThrow(() -> new RuntimeException("Employee not found"));

// employee.setBreakStatus("AVAILABLE");
// employeeRepository.save(employee);

// // Check if break ended after allowed window
// checkBreakWindowViolation(activeBreak, employee);

// return breakRepository.save(activeBreak);
// }

// @Scheduled(fixedRate = 60000) // Check every minute
// @Override
// public void checkOverdueBreaks() {
// LocalDateTime now = LocalDateTime.now();
// List<Break> overdueBreaks =
// breakRepository.findOverdueBreaks(now.minusMinutes(60));

// for (Break breakRecord : overdueBreaks) {
// long durationMinutes = Duration.between(breakRecord.getStartTime(),
// now).toMinutes();
// int maxAllowed = breakRecord.getBreakType().getMaxDurationMinutes();

// if (durationMinutes > maxAllowed) {
// Employee employee =
// employeeRepository.findByEmployeeId(breakRecord.getEmployeeId()).orElse(null);
// if (employee != null) {
// emailService.sendBreakViolationEmail(
// employee.getEmployeeId(),
// employee.getEmployeeName(),
// breakRecord.getBreakType().name(),
// (int) durationMinutes,
// maxAllowed
// );
// breakRecord.setHrNotified(true);
// breakRepository.save(breakRecord);
// }
// }
// }
// }

// @Override
// public List<Break> getBreakHistory(String employeeId) {
// return breakRepository.findByEmployeeIdOrderByStartTimeDesc(employeeId);
// }

// @Override
// public List<Break> getAllBreakHistory() {
// return breakRepository.findAllByOrderByStartTimeDesc();
// }

// private boolean isBreakTimeValid(Break.BreakType breakType) {
// LocalTime now = LocalTime.now();
// LocalTime startWindow = LocalTime.parse(breakType.getStartWindow());
// LocalTime endWindow = LocalTime.parse(breakType.getEndWindow());
// return !now.isBefore(startWindow) && !now.isAfter(endWindow);
// }

// private void checkBreakWindowViolation(Break breakRecord, Employee employee)
// {
// LocalTime endTime = breakRecord.getEndTime().toLocalTime();
// LocalTime allowedEndTime =
// LocalTime.parse(breakRecord.getBreakType().getEndWindow());

// if (endTime.isAfter(allowedEndTime)) {
// long durationMinutes = Duration.between(breakRecord.getStartTime(),
// breakRecord.getEndTime()).toMinutes();
// long minutesOverWindow = Duration.between(allowedEndTime,
// endTime).toMinutes();

// String startTimeStr = breakRecord.getStartTime().toString().replace("T", "
// ");
// String endTimeStr = breakRecord.getEndTime().toString().replace("T", " ");

// emailService.sendBreakWindowViolationEmail(
// employee.getEmployeeId(),
// employee.getEmployeeName(),
// breakRecord.getBreakType().name(),
// (int) durationMinutes,
// (int) minutesOverWindow,
// startTimeStr,
// endTimeStr
// );
// }
// }

// private boolean isLocationValid(Double latitude, Double longitude) {
// if (latitude == null || longitude == null) {
// return false;
// }

// // Office 1 coordinates
// final double OFFICE1_LAT = 13.0864052;
// final double OFFICE1_LON = 77.5484916;

// // Office 2 coordinates
// final double OFFICE2_LAT = 13.07955820797966;
// final double OFFICE2_LON = 77.55963718650905;

// final double ALLOWED_RADIUS_METERS = 100; // 100 meters radius

// double distance1 = calculateDistance(latitude, longitude, OFFICE1_LAT,
// OFFICE1_LON);
// double distance2 = calculateDistance(latitude, longitude, OFFICE2_LAT,
// OFFICE2_LON);

// return distance1 <= ALLOWED_RADIUS_METERS || distance2 <=
// ALLOWED_RADIUS_METERS;
// }

// private double calculateDistance(double lat1, double lon1, double lat2,
// double lon2) {
// final int R = 6371000; // Earth's radius in meters
// double latDistance = Math.toRadians(lat2 - lat1);
// double lonDistance = Math.toRadians(lon2 - lon1);
// double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
// + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
// * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
// double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
// return R * c;
// }

// private boolean hasAlreadyTakenBreakToday(String employeeId, Break.BreakType
// breakType) {
// LocalDate today = LocalDate.now();
// LocalDateTime startOfDay = today.atStartOfDay();
// LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();

// return breakRepository.existsByEmployeeIdAndBreakTypeAndStartTimeBetween(
// employeeId, breakType, startOfDay, endOfDay
// );
// }
// }
