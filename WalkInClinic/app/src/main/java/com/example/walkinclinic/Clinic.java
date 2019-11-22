package com.example.walkinclinic;

public class Clinic {

    private String name;
    private String address;
    private String phoneNum;
    private String insurance;
    private String payment;

    public Clinic (String name, String address, String phoneNum, String insurance, String payment){
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        this.insurance = insurance;
        this.payment = payment;
    }

    public Clinic (){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
