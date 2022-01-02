package com.fyp.digitalid;

import java.time.LocalDateTime;

public class History {
    private String name, scannedon, fullname, ic;

    public History(String name, String scannedon, String fullname, String ic) {
        this.name = name;
        this.scannedon = scannedon;
        this.fullname = fullname;
        this.ic = ic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScannedon() {
        return scannedon;
    }

    public void setScannedon(String scannedon) {
        this.scannedon = scannedon;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }
}
