// package com.example.storemanagementbackend.dto;

// import com.example.storemanagementbackend.model.Break;
// import java.time.LocalDateTime;

// public class BreakDTO {
// private Long id;
// private String employeeId;
// private Break.BreakType breakType;
// private LocalDateTime startTime;
// private LocalDateTime endTime;
// private boolean active;
// private long durationMinutes;

// public BreakDTO() {}

// public BreakDTO(Break breakRecord) {
// this.id = breakRecord.getId();
// this.employeeId = breakRecord.getEmployeeId();
// this.breakType = breakRecord.getBreakType();
// this.startTime = breakRecord.getStartTime();
// this.endTime = breakRecord.getEndTime();
// this.active = breakRecord.isActive();

// if (breakRecord.getEndTime() != null) {
// this.durationMinutes = java.time.Duration.between(breakRecord.getStartTime(),
// breakRecord.getEndTime()).toMinutes();
// } else if (breakRecord.isActive()) {
// this.durationMinutes = java.time.Duration.between(breakRecord.getStartTime(),
// LocalDateTime.now()).toMinutes();
// }
// }

// // Getters and Setters
// public Long getId() { return id; }
// public void setId(Long id) { this.id = id; }

// public String getEmployeeId() { return employeeId; }
// public void setEmployeeId(String employeeId) { this.employeeId = employeeId;
// }

// public Break.BreakType getBreakType() { return breakType; }
// public void setBreakType(Break.BreakType breakType) { this.breakType =
// breakType; }

// public LocalDateTime getStartTime() { return startTime; }
// public void setStartTime(LocalDateTime startTime) { this.startTime =
// startTime; }

// public LocalDateTime getEndTime() { return endTime; }
// public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

// public boolean isActive() { return active; }
// public void setActive(boolean active) { this.active = active; }

// public long getDurationMinutes() { return durationMinutes; }
// public void setDurationMinutes(long durationMinutes) { this.durationMinutes =
// durationMinutes; }
// }