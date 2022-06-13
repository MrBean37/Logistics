package com.example.logistics;

import java.io.Serializable;

public class GeneralInformation implements Serializable {

    private int id;
    private String column01;
    private String column02;
    private String column03;
    private String column04;
    private String column05;
    private String column06;
    private String column07;
    private String column08;
    private String column09;
    private String column10;

    public GeneralInformation()  {

    }

    public GeneralInformation(int id, String column01, String column02, String column03, String column04, String column05, String column06, String column07, String column08, String column09, String column10) {
        this.id = id;
        this.column01 = column01;
        this.column02 = column02;
        this.column03 = column03;
        this.column04 = column04;
        this.column05 = column05;
        this.column06 = column06;
        this.column07 = column07;
        this.column08 = column08;
        this.column09 = column09;
        this.column10 = column10;
    }

    public GeneralInformation(String column01, String column02, String column03, String column04, String column05, String column06, String column07, String column08, String column09, String column10) {
        this.column01 = column01;
        this.column02 = column02;
        this.column03 = column03;
        this.column04 = column04;
        this.column05 = column05;
        this.column06 = column06;
        this.column07 = column07;
        this.column08 = column08;
        this.column09 = column09;
        this.column10 = column10;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColumn01() {
        return column01;
    }

    public void setColumn01(String column01) {
        this.column01 = column01;
    }

    public String getColumn02() {
        return column02;
    }

    public void setColumn02(String column02) {
        this.column02 = column02;
    }

    public String getColumn03() {
        return column03;
    }

    public void setColumn03(String column03) {
        this.column03 = column03;
    }

    public String getColumn04() {
        return column04;
    }

    public void setColumn04(String column04) {
        this.column04 = column04;
    }

    public String getColumn05() {
        return column05;
    }

    public void setColumn05(String column05) {
        this.column05 = column05;
    }

    public String getColumn06() {
        return column06;
    }

    public void setColumn06(String column06) {
        this.column06 = column06;
    }

    public String getColumn07() {
        return column07;
    }

    public void setColumn07(String column07) {
        this.column07 = column07;
    }

    public String getColumn08() {
        return column08;
    }

    public void setColumn08(String column08) {
        this.column08 = column08;
    }

    public String getColumn09() {
        return column09;
    }

    public void setColumn09(String column09) {
        this.column09 = column09;
    }

    public String getColumn10() {
        return column10;
    }

    public void setColumn10(String column10) {
        this.column10 = column10;
    }
}

