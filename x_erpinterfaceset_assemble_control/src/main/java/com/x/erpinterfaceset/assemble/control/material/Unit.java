package com.x.erpinterfaceset.assemble.control.material;

import com.x.base.core.project.gson.GsonPropertyObject;

public class Unit extends GsonPropertyObject {
    private Integer FUNITID;
    private String FNumber;
    private String FName;

    public Integer getFUNITID() {
        return FUNITID;
    }

    public void setFUNITID(Integer FUNITID) {
        this.FUNITID = FUNITID;
    }

    public String getFNumber() {
        return FNumber;
    }

    public void setFNumber(String FNumber) {
        this.FNumber = FNumber;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }
}
