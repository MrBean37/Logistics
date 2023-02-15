package com.example.logistics;

import java.io.Serializable;

public class GoodsInformation implements Serializable {

    private int goodsID;
    private String goodsCode;   //barcode
    private String goodsName;
    private String goodsType;   //food, document....
    private String goodsSts;    //on delivery, on store, already handover to customer
    private String goodsQuantity; // 1/2
    private String goodsUnit;     //box
    private String goodsWeight;
    private String goodsValue;     //value of good

    //goodsMoney = goodsMoneyDelivery + goodsShipFee
    private String goodsMoney;     //total money customer have to pay
    private String goodsMoneyDelivery;     // Tien thu ho (COD)
    private String goodsShipFee;     //tien phi ship
    private String goodsDate;
    private String goodsLocation;
    private String goodsNote;

    private String goodsSendName;
    private String goodsSendID;
    private String goodsSendPhone;
    private String goodsSendCity;
    private String goodsSendDistrict;
    private String goodsSendProvince;
    private String goodsSendCalled;
    private String goodsSendDate;
    private String goodsSendNote;

    private String goodsReceiveName;
    private String goodsReceiveID;
    private String goodsReceivePhone;
    private String goodsReceiveCity;
    private String goodsReceiveDistrict;
    private String goodsReceiveProvince;
    private String goodsReceiveCalled;
    private String goodsReceiveDate;
    private String goodsReceiveNote;


    public GoodsInformation()  {

    }

    public GoodsInformation(int goodsID, String goodsCode, String goodsName, String goodsType, String goodsSts, String goodsQuantity, String goodsUnit, String goodsWeight,
                            String goodsValue, String goodsMoney, String goodsMoneyDelivery, String goodsShipFee, String goodsDate, String goodsLocation, String goodsNote,
                            String goodsSendName, String goodsSendID, String goodsSendPhone, String goodsSendCity, String goodsSendDistrict, String goodsSendProvince,
                            String goodsSendCalled, String goodsSendDate, String goodsSendNote, String goodsReceiveName, String goodsReceiveID, String goodsReceivePhone,
                            String goodsReceiveCity, String goodsReceiveDistrict, String goodsReceiveProvince, String goodsReceiveCalled, String goodsReceiveDate,
                            String goodsReceiveNote) {
        this.goodsID = goodsID;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsType = goodsType;
        this.goodsSts = goodsSts;
        this.goodsQuantity = goodsQuantity;
        this.goodsUnit = goodsUnit;
        this.goodsWeight = goodsWeight;
        this.goodsValue = goodsValue;
        this.goodsMoney = goodsMoney;
        this.goodsMoneyDelivery = goodsMoneyDelivery;
        this.goodsShipFee = goodsShipFee;
        this.goodsDate = goodsDate;
        this.goodsLocation = goodsLocation;
        this.goodsNote = goodsNote;
        this.goodsSendName = goodsSendName;
        this.goodsSendID = goodsSendID;
        this.goodsSendPhone = goodsSendPhone;
        this.goodsSendCity = goodsSendCity;
        this.goodsSendDistrict = goodsSendDistrict;
        this.goodsSendProvince = goodsSendProvince;
        this.goodsSendCalled = goodsSendCalled;
        this.goodsSendDate = goodsSendDate;
        this.goodsSendNote = goodsSendNote;
        this.goodsReceiveName = goodsReceiveName;
        this.goodsReceiveID = goodsReceiveID;
        this.goodsReceivePhone = goodsReceivePhone;
        this.goodsReceiveCity = goodsReceiveCity;
        this.goodsReceiveDistrict = goodsReceiveDistrict;
        this.goodsReceiveProvince = goodsReceiveProvince;
        this.goodsReceiveCalled = goodsReceiveCalled;
        this.goodsReceiveDate = goodsReceiveDate;
        this.goodsReceiveNote = goodsReceiveNote;
    }

    public GoodsInformation(String goodsCode, String goodsName, String goodsType, String goodsSts, String goodsQuantity, String goodsUnit, String goodsWeight,
                            String goodsValue, String goodsMoney, String goodsMoneyDelivery, String goodsShipFee, String goodsDate, String goodsLocation, String goodsNote,
                            String goodsSendName, String goodsSendID, String goodsSendPhone, String goodsSendCity, String goodsSendDistrict, String goodsSendProvince,
                            String goodsSendCalled, String goodsSendDate, String goodsSendNote, String goodsReceiveName, String goodsReceiveID, String goodsReceivePhone,
                            String goodsReceiveCity, String goodsReceiveDistrict, String goodsReceiveProvince, String goodsReceiveCalled, String goodsReceiveDate,
                            String goodsReceiveNote) {
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsType = goodsType;
        this.goodsSts = goodsSts;
        this.goodsQuantity = goodsQuantity;
        this.goodsUnit = goodsUnit;
        this.goodsWeight = goodsWeight;
        this.goodsValue = goodsValue;
        this.goodsMoney = goodsMoney;
        this.goodsMoneyDelivery = goodsMoneyDelivery;
        this.goodsShipFee = goodsShipFee;
        this.goodsDate = goodsDate;
        this.goodsLocation = goodsLocation;
        this.goodsNote = goodsNote;
        this.goodsSendName = goodsSendName;
        this.goodsSendID = goodsSendID;
        this.goodsSendPhone = goodsSendPhone;
        this.goodsSendCity = goodsSendCity;
        this.goodsSendDistrict = goodsSendDistrict;
        this.goodsSendProvince = goodsSendProvince;
        this.goodsSendCalled = goodsSendCalled;
        this.goodsSendDate = goodsSendDate;
        this.goodsSendNote = goodsSendNote;
        this.goodsReceiveName = goodsReceiveName;
        this.goodsReceiveID = goodsReceiveID;
        this.goodsReceivePhone = goodsReceivePhone;
        this.goodsReceiveCity = goodsReceiveCity;
        this.goodsReceiveDistrict = goodsReceiveDistrict;
        this.goodsReceiveProvince = goodsReceiveProvince;
        this.goodsReceiveCalled = goodsReceiveCalled;
        this.goodsReceiveDate = goodsReceiveDate;
        this.goodsReceiveNote = goodsReceiveNote;
    }

    public int getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(int goodsID) {
        this.goodsID = goodsID;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsSts() {
        return goodsSts;
    }

    public void setGoodsSts(String goodsSts) {
        this.goodsSts = goodsSts;
    }

    public String getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(String goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(String goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getGoodsValue() {
        return goodsValue;
    }

    public void setGoodsValue(String goodsValue) {
        this.goodsValue = goodsValue;
    }

    public String getGoodsMoney() {
        return goodsMoney;
    }

    public void setGoodsMoney(String goodsMoney) {
        this.goodsMoney = goodsMoney;
    }

    public String getGoodsMoneyDelivery() {
        return goodsMoneyDelivery;
    }

    public void setGoodsMoneyDelivery(String goodsMoneyDelivery) {
        this.goodsMoneyDelivery = goodsMoneyDelivery;
    }

    public String getGoodsShipFee() {
        return goodsShipFee;
    }

    public void setGoodsShipFee(String goodsShipFee) {
        this.goodsShipFee = goodsShipFee;
    }

    public String getGoodsDate() {
        return goodsDate;
    }

    public void setGoodsDate(String goodsDate) {
        this.goodsDate = goodsDate;
    }

    public String getGoodsLocation() {
        return goodsLocation;
    }

    public void setGoodsLocation(String goodsLocation) {
        this.goodsLocation = goodsLocation;
    }

    public String getGoodsNote() {
        return goodsNote;
    }

    public void setGoodsNote(String goodsNote) {
        this.goodsNote = goodsNote;
    }

    public String getGoodsSendName() {
        return goodsSendName;
    }

    public void setGoodsSendName(String goodsSendName) {
        this.goodsSendName = goodsSendName;
    }

    public String getGoodsSendID() {
        return goodsSendID;
    }

    public void setGoodsSendID(String goodsSendID) {
        this.goodsSendID = goodsSendID;
    }

    public String getGoodsSendPhone() {
        return goodsSendPhone;
    }

    public void setGoodsSendPhone(String goodsSendPhone) {
        this.goodsSendPhone = goodsSendPhone;
    }

    public String getGoodsSendCity() {
        return goodsSendCity;
    }

    public void setGoodsSendCity(String goodsSendCity) {
        this.goodsSendCity = goodsSendCity;
    }

    public String getGoodsSendDistrict() {
        return goodsSendDistrict;
    }

    public void setGoodsSendDistrict(String goodsSendDistrict) {
        this.goodsSendDistrict = goodsSendDistrict;
    }

    public String getGoodsSendProvince() {
        return goodsSendProvince;
    }

    public void setGoodsSendProvince(String goodsSendProvince) {
        this.goodsSendProvince = goodsSendProvince;
    }

    public String getGoodsSendCalled() {
        return goodsSendCalled;
    }

    public void setGoodsSendCalled(String goodsSendCalled) {
        this.goodsSendCalled = goodsSendCalled;
    }

    public String getGoodsSendDate() {
        return goodsSendDate;
    }

    public void setGoodsSendDate(String goodsSendDate) {
        this.goodsSendDate = goodsSendDate;
    }

    public String getGoodsSendNote() {
        return goodsSendNote;
    }

    public void setGoodsSendNote(String goodsSendNote) {
        this.goodsSendNote = goodsSendNote;
    }

    public String getGoodsReceiveName() {
        return goodsReceiveName;
    }

    public void setGoodsReceiveName(String goodsReceiveName) {
        this.goodsReceiveName = goodsReceiveName;
    }

    public String getGoodsReceiveID() {
        return goodsReceiveID;
    }

    public void setGoodsReceiveID(String goodsReceiveID) {
        this.goodsReceiveID = goodsReceiveID;
    }

    public String getGoodsReceivePhone() {
        return goodsReceivePhone;
    }

    public void setGoodsReceivePhone(String goodsReceivePhone) {
        this.goodsReceivePhone = goodsReceivePhone;
    }

    public String getGoodsReceiveCity() {
        return goodsReceiveCity;
    }

    public void setGoodsReceiveCity(String goodsReceiveCity) {
        this.goodsReceiveCity = goodsReceiveCity;
    }

    public String getGoodsReceiveDistrict() {
        return goodsReceiveDistrict;
    }

    public void setGoodsReceiveDistrict(String goodsReceiveDistrict) {
        this.goodsReceiveDistrict = goodsReceiveDistrict;
    }

    public String getGoodsReceiveProvince() {
        return goodsReceiveProvince;
    }

    public void setGoodsReceiveProvince(String goodsReceiveProvince) {
        this.goodsReceiveProvince = goodsReceiveProvince;
    }

    public String getGoodsReceiveCalled() {
        return goodsReceiveCalled;
    }

    public void setGoodsReceiveCalled(String goodsReceiveCalled) {
        this.goodsReceiveCalled = goodsReceiveCalled;
    }

    public String getGoodsReceiveDate() {
        return goodsReceiveDate;
    }

    public void setGoodsReceiveDate(String goodsReceiveDate) {
        this.goodsReceiveDate = goodsReceiveDate;
    }

    public String getGoodsReceiveNote() {
        return goodsReceiveNote;
    }

    public void setGoodsReceiveNote(String goodsReceiveNote) {
        this.goodsReceiveNote = goodsReceiveNote;
    }
}

