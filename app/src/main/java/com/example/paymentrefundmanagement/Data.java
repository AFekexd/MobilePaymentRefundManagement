package com.example.paymentrefundmanagement;

public class Data {
    private String Name;
    private String ToWho;
    private String Amount;
    private String Tax;
    private String Date;
    private String StatusDate;
    private String Method;
    private String AccountType;
    private String pOrR;
    private String id;

    public Data(String name, String toWho, String amount, String tax, String date, String statusDate, String method, String accountType, String pOrR2) {
        Name = name;
        ToWho = toWho;
        Amount = amount;
        Tax = tax;
        Date = date;
        StatusDate = statusDate;
        Method = method;
        AccountType = accountType;
        pOrR = pOrR2;
    }

    public Data() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getToWho() {
        return ToWho;
    }

    public void setToWho(String toWho) {
        ToWho = toWho;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTax() {
        return Tax;
    }

    public void setTax(String tax) {
        Tax = tax;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatusDate() {
        return StatusDate;
    }

    public void setStatusDate(String statusDate) {
        StatusDate = statusDate;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpOrR() {
        return pOrR;
    }

    public void setpOrR(String pOrR) {
        this.pOrR = pOrR;
    }
}
