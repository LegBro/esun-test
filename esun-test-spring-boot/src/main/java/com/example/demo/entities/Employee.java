package com.example.demo.entities;


public class Employee {
    private String id;
    private String name;
    private String email;
    private String floorSeatSeq;

    public Employee(){

    }
    
    public Employee(String id, String name, String email, String floorSeatSeq) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.floorSeatSeq = floorSeatSeq;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFloorSeatSeq() {
        return floorSeatSeq;
    }
    public void setFloorSeatSeq(String floorSeatSeq) {
        this.floorSeatSeq = floorSeatSeq;
    }
}
