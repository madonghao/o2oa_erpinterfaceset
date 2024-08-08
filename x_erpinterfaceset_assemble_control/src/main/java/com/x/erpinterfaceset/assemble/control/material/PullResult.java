package com.x.erpinterfaceset.assemble.control.material;

import com.x.base.core.project.gson.GsonPropertyObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PullResult extends GsonPropertyObject {
    private Date start = new Date();

    private Date end = new Date();

    private Long elapsed;

    private List<String> createMaterialList = new ArrayList<>();
    private List<String> updateMaterialList = new ArrayList<>();

    public void end() {
        this.end = new Date();
        this.elapsed = end.getTime() - start.getTime();
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Long getElapsed() {
        return elapsed;
    }

    public void setElapsed(Long elapsed) {
        this.elapsed = elapsed;
    }

    public List<String> getCreateMaterialList() {
        return createMaterialList;
    }

    public void setCreateMaterialList(List<String> createMaterialList) {
        this.createMaterialList = createMaterialList;
    }

    public List<String> getUpdateMaterialList() {
        return updateMaterialList;
    }

    public void setUpdateMaterialList(List<String> updateMaterialList) {
        this.updateMaterialList = updateMaterialList;
    }
}
