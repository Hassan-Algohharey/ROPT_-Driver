package com.algohary.ropt_driver.Models;

public class ModelHistory {
    private String oCar,oDis,oFrom,oId,oPrice,oStatus,oTime, oTo, uId,uPhone,cId;

    public ModelHistory() {
    }

    public ModelHistory(String oCar, String oDis, String oFrom, String oId, String oPrice, String oStatus, String oTime, String oTo, String uId, String uPhone, String cId) {
        this.oCar = oCar;
        this.oDis = oDis;
        this.oFrom = oFrom;
        this.oId = oId;
        this.oPrice = oPrice;
        this.oStatus = oStatus;
        this.oTime = oTime;
        this.oTo = oTo;
        this.uId = uId;
        this.uPhone = uPhone;
        this.cId = cId;
    }

    public String getoCar() {
        return oCar;
    }

    public void setoCar(String oCar) {
        this.oCar = oCar;
    }

    public String getoDis() {
        return oDis;
    }

    public void setoDis(String oDis) {
        this.oDis = oDis;
    }

    public String getoFrom() {
        return oFrom;
    }

    public void setoFrom(String oFrom) {
        this.oFrom = oFrom;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getoPrice() {
        return oPrice;
    }

    public void setoPrice(String oPrice) {
        this.oPrice = oPrice;
    }

    public String getoStatus() {
        return oStatus;
    }

    public void setoStatus(String oStatus) {
        this.oStatus = oStatus;
    }

    public String getoTime() {
        return oTime;
    }

    public void setoTime(String oTime) {
        this.oTime = oTime;
    }

    public String getoTo() {
        return oTo;
    }

    public void setoTo(String oTo) {
        this.oTo = oTo;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }
}
