package com.tm.teameverest.models;

/**
 * Created by user on 13/11/16.
 */
public class SignUpModel {

    private String id;
    private String name;
    private String email_address;
    private String password;
    private String sex;
    private String dob;
    private String city;
    private String contact_address;
    private String add_biography;
    private String any_comments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact_address() {
        return contact_address;
    }

    public void setContact_address(String contact_address) {
        this.contact_address = contact_address;
    }

    public String getAdd_biography() {
        return add_biography;
    }

    public void setAdd_biography(String add_biography) {
        this.add_biography = add_biography;
    }

    public String getAny_comments() {
        return any_comments;
    }

    public void setAny_comments(String any_comments) {
        this.any_comments = any_comments;
    }
}
