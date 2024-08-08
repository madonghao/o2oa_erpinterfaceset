package com.x.erpinterfaceset.assemble.control.material;

import com.x.base.core.project.gson.GsonPropertyObject;

public class Group extends GsonPropertyObject {
    private Integer FID;
    private String FNumber;
    private String FName;
    private Integer FParentId;

    public Integer getFID() {
        return FID;
    }

    public void setFID(Integer FID) {
        this.FID = FID;
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

    public Integer getFParentId() {
        return FParentId;
    }

    public void setFParentId(Integer FParentId) {
        this.FParentId = FParentId;
    }
}
