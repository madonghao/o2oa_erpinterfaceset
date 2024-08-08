package com.x.erpinterfaceset.assemble.control.material;

import com.google.gson.annotations.SerializedName;
import com.x.base.core.project.gson.GsonPropertyObject;

public class MaterialJson extends GsonPropertyObject {
    private int FMATERIALID;
    private int FUseOrgId;
    private String FNumber;
    private String FName;
    private String FSpecification;
    private int FMaterialGroup;
    @SerializedName("F.YLAH.GKBUM")
    private String FYLAHGKBUM;
    private int FBaseUnitId;
    private int FAuxUnitID;
    private int FPurchaseUnitId;
    private int FPurchasePriceUnitId;

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

    public String getFSpecification() {
        return FSpecification;
    }

    public void setFSpecification(String FSpecification) {
        this.FSpecification = FSpecification;
    }

    public int getFMaterialGroup() {
        return FMaterialGroup;
    }

    public void setFMaterialGroup(int FMaterialGroup) {
        this.FMaterialGroup = FMaterialGroup;
    }

    public int getFBaseUnitId() {
        return FBaseUnitId;
    }

    public void setFBaseUnitId(int FBaseUnitId) {
        this.FBaseUnitId = FBaseUnitId;
    }

    public int getFAuxUnitID() {
        return FAuxUnitID;
    }

    public void setFAuxUnitID(int FAuxUnitID) {
        this.FAuxUnitID = FAuxUnitID;
    }

    public int getFPurchaseUnitId() {
        return FPurchaseUnitId;
    }

    public void setFPurchaseUnitId(int FPurchaseUnitId) {
        this.FPurchaseUnitId = FPurchaseUnitId;
    }

    public int getFPurchasePriceUnitId() {
        return FPurchasePriceUnitId;
    }

    public void setFPurchasePriceUnitId(int FPurchasePriceUnitId) {
        this.FPurchasePriceUnitId = FPurchasePriceUnitId;
    }

    public int getFUseOrgId() {
        return FUseOrgId;
    }

    public void setFUseOrgId(int FUseOrgId) {
        this.FUseOrgId = FUseOrgId;
    }

    public int getFMATERIALID() {
        return FMATERIALID;
    }

    public void setFMATERIALID(int FMATERIALID) {
        this.FMATERIALID = FMATERIALID;
    }

    public String getFYLAHGKBUM() {
        return FYLAHGKBUM;
    }

    public void setFYLAHGKBUM(String FYLAHGKBUM) {
        this.FYLAHGKBUM = FYLAHGKBUM;
    }
}
