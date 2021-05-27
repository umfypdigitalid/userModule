package com.fyp.digitalid;


public class UserData {

    //private String fullName, ic, email, address;
    private String fullName, ic, birthDate, email, address;


    public UserData (String fullName, String ic, String birthDate, String email, String address){

        this.fullName = fullName;
        this.ic = ic;
        this.birthDate = birthDate;
        this.email = email;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
