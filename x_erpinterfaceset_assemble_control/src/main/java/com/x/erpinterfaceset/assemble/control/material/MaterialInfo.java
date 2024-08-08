package com.x.erpinterfaceset.assemble.control.material;

import com.x.base.core.project.gson.GsonPropertyObject;

public class MaterialInfo extends GsonPropertyObject {
    // 物料分组
    private String group;
    // 物料名称
    private String name;
    // 物料编号
    private String number;
    // 物料规格
    private String specification;
    // 物料采购单位
    private String purchaseUnit;
    // 物料采购计价单位
    private String purchasepriceUnit;
    // 物料基本单位
    private String baseUnit;
    // 物料辅助单位
    private String auxUnit;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getPurchaseUnit() {
        return purchaseUnit;
    }

    public void setPurchaseUnit(String purchaseUnit) {
        this.purchaseUnit = purchaseUnit;
    }

    public String getPurchasepriceUnit() {
        return purchasepriceUnit;
    }

    public void setPurchasepriceUnit(String purchasepriceUnit) {
        this.purchasepriceUnit = purchasepriceUnit;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public String getAuxUnit() {
        return auxUnit;
    }

    public void setAuxUnit(String auxUnit) {
        this.auxUnit = auxUnit;
    }
}
