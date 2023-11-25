package com.example.demo.entities;

public class SeatingChart {
    private String floorSeatSeq;
    private int floorNo;
    private int seatNo;
    
    public String getFloorSeatSeq() {
        return floorSeatSeq;
    }
    public void setFloorSeatSeq(String floorSeatSeq) {
        this.floorSeatSeq = floorSeatSeq;
    }
    public int getFloorNo() {
        return floorNo;
    }
    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }
    public int getSeatNo() {
        return seatNo;
    }
    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }
}
