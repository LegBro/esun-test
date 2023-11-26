package com.example.demo.fromJson;

public class UpdateSeatModel {
    
    private String employeeId;
    private String seatId;

    public UpdateSeatModel(){

    }

    public UpdateSeatModel(String employeeId, String seatId) {
        this.employeeId = employeeId;
        this.seatId = seatId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public String getSeatId() {
        return seatId;
    }
    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }
}
