package com.example.storemanagementbackend.dto;

public class BreakStartRequest {
    private String employeeId;
    private String breakType;
    private Double latitude;
    private Double longitude;

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    
    public String getBreakType() { return breakType; }
    public void setBreakType(String breakType) { this.breakType = breakType; }
    
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}