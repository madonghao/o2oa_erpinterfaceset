package com.x.erpinterfaceset.assemble.control.material;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.sun.jna.platform.win32.NTSecApi;
import com.x.base.core.project.logger.Logger;
import com.x.base.core.project.logger.LoggerFactory;
import com.kingdee.bos.webapi.sdk.K3CloudApi;

public class MaterialFactory {
    private static Logger logger = LoggerFactory.getLogger(MaterialFactory.class);

    private List<Group> groups;

    private List<Unit> units;

    private List<MaterialJson> materialJsons;

    private K3CloudApi api;

    private String limit;

    private String orgId;

    private Gson gson = new Gson();

    Type groupType = new TypeToken<List<Group>>(){}.getType();

    Type unitType = new TypeToken<List<Unit>>(){}.getType();

    Type materialJsonType = new TypeToken<List<MaterialJson>>(){}.getType();

    public MaterialFactory() throws Exception {
        Properties properties = new Properties();

        File file = new File((new File("./config")).getCanonicalPath() + "/kdwebapi.properties");
        System.out.println("读取根目录下的配置文件->" + file.getPath());
        InputStream inputStream = new FileInputStream(file.getPath());
        properties.load(new InputStreamReader(inputStream, "utf-8"));
        inputStream.close();

        IdentifyInfo iden = new IdentifyInfo();
        iden.setUserName(properties.getProperty("X-KDApi-UserName", ""));
        iden.setAppId(properties.getProperty("X-KDApi-AppID", ""));
        iden.setdCID(properties.getProperty("X-KDApi-AcctID", ""));
        iden.setAppSecret(properties.getProperty("X-KDApi-AppSec", ""));
        iden.setlCID(2052);
        iden.setServerUrl(properties.getProperty("X-KDApi-ServerUrl", ""));

        limit = properties.getProperty("X-KDApi-Limit", "10000");

        orgId = properties.getProperty("X-KDApi-OrgID", "");

        api = new K3CloudApi(iden);

        groups = this.groups();
        units = this.units();
        materialJsons = this.materialJsons();
    }

    public List<Group> roots() {
        return groups.stream().filter(o -> 0 == o.getFParentId()).collect(Collectors.toList());
    }

    public List<MaterialJson> listMaterial(Group group) throws Exception {
        return materialJsons.stream().filter(o -> Objects.equals(o.getFMaterialGroup(), group.getFID()))
                .collect(Collectors.toList());
    }

    public List<Group> listSub(Group group) throws Exception {
        return groups.stream().filter(o -> Objects.equals(o.getFParentId(), group.getFID()))
                .collect(Collectors.toList());
    }

    public String getUnitName(int id) throws Exception {
        return units.stream().filter(o -> Objects.equals(o.getFUNITID(), id))
                .map(o -> o.getFName())
                .findFirst()
                .orElse("");
    }

    public List<Map<String, Object>> getLevel(Group group) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        buildLevel(group, result);
        List<Map<String, Object>> reversedResult = new ArrayList<>(result);
        Collections.reverse(reversedResult);
        return reversedResult;
    }

    private void buildLevel(Group group, List<Map<String, Object>> result) {
        int currentLevel = calculateLevel(group);
        Map<String, Object> levelInfo = new HashMap<>();
        levelInfo.put("FID", group.getFID());
        levelInfo.put("FNumber", group.getFNumber());
        levelInfo.put("FName", group.getFName());
        levelInfo.put("Level", currentLevel);
        result.add(levelInfo);

        if (group.getFParentId()!= 0) {
            Group parentGroup = findGroupByFID(group.getFParentId());
            if (parentGroup!= null) {
                buildLevel(parentGroup, result);
            }
        }
    }

    private int calculateLevel(Group group) {
        int level = 1;
        Group current = group;
        while (current.getFParentId()!= 0) {
            current = findGroupByFID(current.getFParentId());
            level++;
        }
        return level;
    }

    private Group findGroupByFID(Integer fid) {
        for (Group group : groups) {
            if (group.getFID().equals(fid)) {
                return group;
            }
        }
        return null;
    }

    public List<Group> getGroups() { return this.groups; }

    public List<Unit> getUnits() { return this.units; }

    public List<MaterialJson> getMaterialJsons() { return this.materialJsons; }

    private List<Group> groups() throws Exception {
        logger.info("开始请求物料分组数据");
        String data = "{\"FormId\": \"SAL_MATERIALGROUP\",\"FieldKeys\": \"FID, FNumber, FName, FParentId\",\"FilterString\": [],\"OrderString\": \"\",\"TopRowCount\": 0,\"StartRow\": 0,\"Limit\": "+ limit +",\"SubSystemId\": \"\"}";
        String res = api.billQuery(data);
        List<Group> resp = gson.fromJson(res, groupType);
        logger.debug("groups response:{}.", resp);
        return resp;
    }

    private List<Unit> units() throws Exception {
        logger.info("开始请求物料单位数据");
        String data = "{\"FormId\": \"BD_UNIT\",\"FieldKeys\": \"FUNITID, FNumber, FName\",\"FilterString\": [],\"OrderString\": \"\",\"TopRowCount\": 0,\"StartRow\": 0,\"Limit\": "+ limit +",\"SubSystemId\": \"\"}";
        String res = api.billQuery(data);
        List<Unit> resp = gson.fromJson(res, unitType);
        logger.debug("groups response:{}.", resp);
        return resp;
    }

    private List<MaterialJson> materialJsons() throws Exception {
        logger.info("开始请求物料数据");
        String data = "{\"FormId\": \"BD_MATERIAL\",\"FieldKeys\": \"FMATERIALID, FUseOrgId, FNumber, FName, FSpecification, FMaterialGroup, F_YLAH_GKBUM, FBaseUnitId, FAuxUnitID, FPurchaseUnitId, FPurchasePriceUnitId\",\"FilterString\": \"FUseOrgId=\\'" + orgId + "\\'\",\"OrderString\": \"\",\"TopRowCount\": 0,\"StartRow\": 0,\"Limit\": "+ limit +",\"SubSystemId\": \"\"}";
        String res = api.billQuery(data);
        List<MaterialJson> resp = gson.fromJson(res, materialJsonType);
        logger.debug("groups response:{}.", resp);
        return resp;
    }
}
