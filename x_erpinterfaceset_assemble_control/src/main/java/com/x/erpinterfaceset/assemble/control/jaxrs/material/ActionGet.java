package com.x.erpinterfaceset.assemble.control.jaxrs.material;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.*;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.kingdee.bos.webapi.entity.*;
import com.x.base.core.entity.JpaObject;
import com.x.base.core.project.bean.WrapCopier;
import com.x.base.core.project.bean.WrapCopierFactory;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.http.EffectivePerson;
import com.x.base.core.project.logger.Logger;
import com.x.base.core.project.logger.LoggerFactory;
import com.x.erpinterfaceset.assemble.control.material.MaterialInfo;

public class ActionGet extends BaseAction {

	private Logger logger = LoggerFactory.getLogger(ActionGet.class);

	protected ActionResult<Wo> execute(HttpServletRequest request, EffectivePerson effectivePerson, String number) throws Exception {
		logger.info("Executing action 'ActionGet'...");

		ActionResult<Wo> result = new ActionResult<>();
		Wo wrap = null;

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

		K3CloudApi api = new K3CloudApi(iden);
		String data = String.format("{\"CreateOrgId\": 0,\"Number\": \"%s\",\"Id\": \"\",\"IsSortBySeq\": \"false\"}", number);
		String res = api.view("BD_Material", data);

		RepoRet repoRet = gson.fromJson(res, RepoRet.class);

		if (repoRet.getResult().getResponseStatus().isIsSuccess()) {
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(res, JsonObject.class)
					.getAsJsonObject("Result")
					.getAsJsonObject("Result");

			String materialGroup = formatMaterialGroup(jsonObject);
			String name = getJsonString(jsonObject, "MultiLanguageText", 0, "Name");
			String specification = getJsonString(jsonObject, "MultiLanguageText", 0, "Specification");
			String fnumber = jsonObject.get("Number").getAsString();
			String purchaseUnit = getUnitName(jsonObject, "MaterialPurchase", "PurchaseUnitID");
			String baseUnit = getUnitName(jsonObject, "MaterialBase", "BaseUnitId");
			String auxUnit = getUnitName(jsonObject, "MaterialStock", "AuxUnitID");
			String purchasePriceUnit = getUnitName(jsonObject, "MaterialPurchase", "PurchasePriceUnitId");

			MaterialInfo materialInfo = new MaterialInfo();
			materialInfo.setGroup(materialGroup);
			materialInfo.setNumber(fnumber);
			materialInfo.setName(name);
			materialInfo.setSpecification(specification);
			materialInfo.setBaseUnit(baseUnit);
			materialInfo.setAuxUnit(auxUnit);
			materialInfo.setPurchaseUnit(purchaseUnit);
			materialInfo.setPurchasepriceUnit(purchasePriceUnit);

			wrap = Wo.copier.copy(materialInfo);
			result.setData(wrap);

			logger.info("Action 'ActionGet' executed successfully.");
		} else {
			result.setType(ActionResult.Type.error);
			result.setMessage("未找到此物料编码的物料");
		}
		return result;
	}

	private String getJsonString(JsonObject jsonObject, String arrayName, int index, String propertyName) {
		JsonArray array = jsonObject.getAsJsonArray(arrayName);
		if (array == null || array.size() <= index) {
			return "";
		}
		JsonObject obj = array.get(index).getAsJsonObject();
		JsonElement element = obj.get(propertyName);
		if (element == null || element.isJsonNull()) {
			return "";
		}
		return element.getAsString();
	}

	private String getUnitName(JsonObject jsonObject, String arrayName, String unitIdName) {
		JsonArray array = jsonObject.getAsJsonArray(arrayName);
		if (array == null || array.size() == 0) {
			return "";
		}
		JsonObject obj = array.get(0).getAsJsonObject();
		JsonElement unitElement = obj.get(unitIdName);
		if (unitElement == null || unitElement.isJsonNull()) {
			return "";
		}
		JsonObject unitObj = unitElement.getAsJsonObject();
		if (unitObj == null) {
			return "";
		}
		JsonArray languageTextArray = unitObj.getAsJsonArray("MultiLanguageText");
		if (languageTextArray == null || languageTextArray.size() == 0) {
			return "";
		}
		JsonObject languageTextObj = languageTextArray.get(0).getAsJsonObject();
		JsonElement nameElement = languageTextObj.get("Name");
		if (nameElement == null || nameElement.isJsonNull()) {
			return "";
		}
		return nameElement.getAsString();
	}

	private String formatMaterialGroup(JsonObject jsonObject) {
		Object materialGroupObj = jsonObject.get("MaterialGroup");
		if (materialGroupObj == null || materialGroupObj instanceof JsonNull) {
			return "";
		}
		JsonObject jsonMaterialGroupObj = (JsonObject) materialGroupObj;
		String materialGroupName = getJsonString(jsonMaterialGroupObj, "MultiLanguageText", 0, "Name");
		String materialGroupNumber = jsonMaterialGroupObj.get("Number").getAsString();
		return materialGroupNumber + "-" + materialGroupName;
	}

	public static class Wo extends MaterialInfo {

		private static final long serialVersionUID = -5076990764713538973L;

		public static List<String> Excludes = new ArrayList<String>();

		public static final WrapCopier<MaterialInfo, Wo> copier = WrapCopierFactory.wo( MaterialInfo.class, Wo.class, null, JpaObject.FieldsInvisible);
	}


}
