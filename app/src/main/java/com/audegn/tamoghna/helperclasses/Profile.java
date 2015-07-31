package com.audegn.tamoghna.helperclasses;

public class Profile {
    String name;
    String email;
    String phoneno;
    String fax;
    String website;
    String address;

    public Profile(String name, String email, String phoneno, String fax, String website, String address) {
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
        this.fax = fax;
        this.website = website;
        this.address = address;
    }

    public Profile(){

    }

    public String getName() {
        return name;
    }

    public Profile setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Profile setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public Profile setPhoneno(String phoneno) {
        this.phoneno = phoneno;
        return this;
    }

    public String getFax() {
        return fax;
    }

    public Profile setFax(String fax) {
        this.fax = fax;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public Profile setWebsite(String website) {
        this.website = website;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Profile setAddress(String address) {
        this.address = address;
        return this;
    }
}
