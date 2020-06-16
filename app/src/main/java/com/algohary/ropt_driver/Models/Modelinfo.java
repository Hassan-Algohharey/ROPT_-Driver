package com.algohary.ropt_driver.Models;

public class Modelinfo {
    private String oCar,oDis,oEnd,oFrom,oId,oPrice,oStart,oStatus,oTime, oTo, uId,uPhone,cId;

    public Modelinfo() {
    }

    public Modelinfo(String oCar, String oDis, String oEnd, String oFrom, String oId, String oPrice, String oStart, String oStatus, String oTime, String oTo, String uId, String uPhone, String cId) {
        this.oCar = oCar;
        this.oDis = oDis;
        this.oEnd = oEnd;
        this.oFrom = oFrom;
        this.oId = oId;
        this.oPrice = oPrice;
        this.oStart = oStart;
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

    public String getoEnd() {
        return oEnd;
    }

    public void setoEnd(String oEnd) {
        this.oEnd = oEnd;
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

    public String getoStart() {
        return oStart;
    }

    public void setoStart(String oStart) {
        this.oStart = oStart;
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
