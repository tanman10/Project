package com.example.walkinclinic;

public class Service {

    private String id;
    private String servicename;
    private String role;


    public Service() {
    }
    public Service(String id, String servicename, String role) {
        this.id = id;
        this.servicename = servicename;
        this.role = role;
    }
    public Service(String servicename, String role) {
        this.servicename = servicename;
        this.role = role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getServicename() {
        return servicename;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
