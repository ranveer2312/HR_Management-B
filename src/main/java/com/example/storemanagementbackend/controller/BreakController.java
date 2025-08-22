// package com.example.storemanagementbackend.controller;

// import com.example.storemanagementbackend.dto.BreakDTO;
// import com.example.storemanagementbackend.dto.BreakStartRequest;
// import com.example.storemanagementbackend.dto.BreakStopRequest;
// import com.example.storemanagementbackend.model.Break;
// import com.example.storemanagementbackend.service.BreakService;
// import com.example.storemanagementbackend.service.JwtService;
// import com.example.storemanagementbackend.repository.EmployeeRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import jakarta.servlet.http.HttpServletRequest;

// @RestController
// @RequestMapping("/api/breaks")
// @CrossOrigin(origins = {"https://idmstiranga.online",
// "http://idmstiranga.online", "http://localhost:3000"})
// public class BreakController {

// @Autowired
// private BreakService breakService;

// @Autowired
// private JwtService jwtService;

// @Autowired
// private EmployeeRepository employeeRepository;

// @PostMapping("/start")
// public ResponseEntity<?> startBreak(@RequestBody BreakStartRequest request,
// HttpServletRequest httpRequest) {
// try {
// String employeeId = request.getEmployeeId();
// if (employeeId == null || employeeId.trim().isEmpty()) {
// employeeId = extractEmployeeIdFromToken(httpRequest);
// if (employeeId == null || employeeId.trim().isEmpty()) {
// employeeId = "EMP001";
// }
// }

// if (request.getBreakType() == null ||
// request.getBreakType().trim().isEmpty()) {
// return ResponseEntity.badRequest().body(java.util.Map.of(
// "error", true,
// "message", "Break type is required",
// "field", "breakType"
// ));
// }

// if (request.getLatitude() == null || request.getLongitude() == null) {
// return ResponseEntity.badRequest().body(java.util.Map.of(
// "error", true,
// "message", "Location coordinates are required",
// "field", "location"
// ));
// }

// Break.BreakType breakType = Break.BreakType.valueOf(request.getBreakType());
// Break breakRecord = breakService.startBreak(
// employeeId.trim(),
// breakType,
// request.getLatitude(),
// request.getLongitude()
// );
// return ResponseEntity.ok(new BreakDTO(breakRecord));
// } catch (Exception e) {
// return ResponseEntity.badRequest().body(java.util.Map.of(
// "error", true,
// "message", e.getMessage()
// ));
// }
// }

// @PostMapping("/stop")
// public ResponseEntity<?> stopBreak(@RequestBody BreakStopRequest request,
// HttpServletRequest httpRequest) {
// try {
// String employeeId = request.getEmployeeId();
// if (employeeId == null || employeeId.trim().isEmpty()) {
// employeeId = extractEmployeeIdFromToken(httpRequest);
// if (employeeId == null || employeeId.trim().isEmpty()) {
// employeeId = "EMP001";
// }
// }

// if (request.getLatitude() == null || request.getLongitude() == null) {
// return ResponseEntity.badRequest().body(java.util.Map.of(
// "error", true,
// "message", "Location coordinates are required"
// ));
// }

// Break breakRecord = breakService.stopBreak(
// employeeId.trim(),
// request.getLatitude(),
// request.getLongitude()
// );
// return ResponseEntity.ok(new BreakDTO(breakRecord));
// } catch (Exception e) {
// return ResponseEntity.badRequest().body(java.util.Map.of(
// "error", true,
// "message", e.getMessage()
// ));
// }
// }

// @GetMapping("/types")
// public ResponseEntity<?> getBreakTypes() {
// return ResponseEntity.ok(Break.BreakType.values());
// }

// @GetMapping("/schedule")
// public ResponseEntity<?> getBreakSchedule() {
// return ResponseEntity.ok(java.util.Arrays.stream(Break.BreakType.values())
// .map(type -> java.util.Map.of(
// "name", type.name(),
// "displayName", type.name().replace("_", " "),
// "startWindow", type.getStartWindow(),
// "endWindow", type.getEndWindow(),
// "maxDurationMinutes", type.getMaxDurationMinutes()
// ))
// .toList());
// }

// @GetMapping("/history/{employeeId}")
// public ResponseEntity<?> getBreakHistory(@PathVariable String employeeId) {
// try {
// java.util.List<Break> history = breakService.getBreakHistory(employeeId);
// java.util.List<BreakDTO> historyDTOs = history.stream()
// .map(BreakDTO::new)
// .toList();
// return ResponseEntity.ok(historyDTOs);
// } catch (Exception e) {
// return ResponseEntity.badRequest().body(e.getMessage());
// }
// }

// @GetMapping("/history/all")
// public ResponseEntity<?> getAllBreakHistory() {
// try {
// java.util.List<Break> allHistory = breakService.getAllBreakHistory();
// java.util.List<BreakDTO> historyDTOs = allHistory.stream()
// .map(BreakDTO::new)
// .toList();
// return ResponseEntity.ok(historyDTOs);
// } catch (Exception e) {
// return ResponseEntity.badRequest().body(e.getMessage());
// }
// }

// @PostMapping("/debug")
// public ResponseEntity<?> debugBreakRequest(@RequestBody BreakStartRequest
// request) {
// return ResponseEntity.ok(java.util.Map.of(
// "receivedEmployeeId", request.getEmployeeId() != null ?
// request.getEmployeeId() : "null",
// "breakType", request.getBreakType() != null ? request.getBreakType() :
// "null",
// "latitude", request.getLatitude(),
// "longitude", request.getLongitude()
// ));
// }

// private String extractEmployeeIdFromToken(HttpServletRequest request) {
// try {
// String authHeader = request.getHeader("Authorization");
// if (authHeader != null && authHeader.startsWith("Bearer ")) {
// String token = authHeader.substring(7);
// String email = jwtService.extractUsername(token);
// if (email != null) {
// return employeeRepository.findByEmail(email)
// .map(employee -> employee.getEmployeeId())
// .orElse(null);
// }
// }
// } catch (Exception e) {
// // Token extraction failed, return null
// }
// return null;
// }
// }
