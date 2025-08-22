// package com.example.storemanagementbackend.model;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "breaks")
// public class Break {
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;

// @Column(nullable = false)
// private String employeeId;

// @Enumerated(EnumType.STRING)
// @Column(nullable = false)
// private BreakType breakType;

// @Column(nullable = false)
// private LocalDateTime startTime;

// private LocalDateTime endTime;

// @Column(nullable = false)
// private boolean active = true;

// private boolean hrNotified = false;

// private Double startLatitude;
// private Double startLongitude;
// private Double endLatitude;
// private Double endLongitude;

// public enum BreakType {
// MORNING_TEA("11:00", "11:15", 15),
// LUNCH("13:15", "14:00", 45),
// EVENING_TEA("16:00", "16:15", 15);

// private final String startWindow;
// private final String endWindow;
// private final int maxDurationMinutes;

// BreakType(String startWindow, String endWindow, int maxDurationMinutes) {
// this.startWindow = startWindow;
// this.endWindow = endWindow;
// this.maxDurationMinutes = maxDurationMinutes;
// }

// public String getStartWindow() { return startWindow; }
// public String getEndWindow() { return endWindow; }
// public int getMaxDurationMinutes() { return maxDurationMinutes; }
// }

// // Constructors
// public Break() {}

// public Break(String employeeId, BreakType breakType) {
// this.employeeId = employeeId;
// this.breakType = breakType;
// this.startTime = LocalDateTime.now();
// }

// // Getters and Setters
// public Long getId() { return id; }
// public void setId(Long id) { this.id = id; }

// public String getEmployeeId() { return employeeId; }
// public void setEmployeeId(String employeeId) { this.employeeId = employeeId;
// }

// public BreakType getBreakType() { return breakType; }
// public void setBreakType(BreakType breakType) { this.breakType = breakType; }

// public LocalDateTime getStartTime() { return startTime; }
// public void setStartTime(LocalDateTime startTime) { this.startTime =
// startTime; }

// public LocalDateTime getEndTime() { return endTime; }
// public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

// public boolean isActive() { return active; }
// public void setActive(boolean active) { this.active = active; }

// public boolean isHrNotified() { return hrNotified; }
// public void setHrNotified(boolean hrNotified) { this.hrNotified = hrNotified;
// }

// public Double getStartLatitude() { return startLatitude; }
// public void setStartLatitude(Double startLatitude) { this.startLatitude =
// startLatitude; }

// public Double getStartLongitude() { return startLongitude; }
// public void setStartLongitude(Double startLongitude) { this.startLongitude =
// startLongitude; }

// public Double getEndLatitude() { return endLatitude; }
// public void setEndLatitude(Double endLatitude) { this.endLatitude =
// endLatitude; }

// public Double getEndLongitude() { return endLongitude; }
// public void setEndLongitude(Double endLongitude) { this.endLongitude =
// endLongitude; }
// }
