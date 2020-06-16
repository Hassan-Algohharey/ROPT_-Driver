package com.algohary.ropt_driver.Models;

public class Modelinfo_driver {
    private String Full_name,Phone_num,Truck_type;

    public Modelinfo_driver() {
    }

    public Modelinfo_driver(String full_name, String phone_num, String truck_type) {
        Full_name = full_name;
        Phone_num = phone_num;
        Truck_type = truck_type;
    }

    public String getFull_name() {
        return Full_name;
    }

    public void setFull_name(String full_name) {
        Full_name = full_name;
    }

    public String getPhone_num() {
        return Phone_num;
    }

    public void setPhone_num(String phone_num) {
        Phone_num = phone_num;
    }

    public String getTruck_type() {
        return Truck_type;
    }

    public void setTruck_type(String truck_type) {
        Truck_type = truck_type;
    }
}
