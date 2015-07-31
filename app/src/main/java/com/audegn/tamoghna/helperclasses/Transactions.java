package com.audegn.tamoghna.helperclasses;

public class Transactions {
    private String name,code,date;
    private int amount;
    private short pending;
    public Transactions(String name, String code, int amount, String date, short pending){
        this.name = name;
        this.code = code;
        this.amount = amount;
        this.date = date;
        this.pending = pending;
    }

    public String getName() {
        return name;
    }

    public Transactions setName(String name) {
        this.name = name;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Transactions setDate(String date) {
        this.date = date;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public Transactions setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public short getPending() {
        return pending;
    }

    public Transactions setPending(short pending) {
        this.pending = pending;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Transactions setCode(String code) {
        this.code = code;
        return this;
    }

}
