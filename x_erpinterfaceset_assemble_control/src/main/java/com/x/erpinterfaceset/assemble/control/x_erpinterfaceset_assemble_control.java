package com.x.erpinterfaceset.assemble.control;

import com.x.base.core.project.Deployable;
import com.x.base.core.project.annotation.Module;
import com.x.base.core.project.annotation.ModuleCategory;
import com.x.base.core.project.annotation.ModuleType;

/**
 * web应用工程信息
 * name 应用工程业务简要描述
 * packageName web应用工程类包路径
 * containerEntities 业务需要访问的实体类（需全路径，多个类逗号隔开）
 * storeJars 需要访问平台的实体类工程
 * customJars 需要访问的自定义工程
 * @author sword
 */
@Module(type = ModuleType.ASSEMBLE, category = ModuleCategory.CUSTOM, name = "金蝶云星空ERP接口", packageName = "com.x.erpinterfaceset.assemble.control", containerEntities = {
		"com.x.erpinterfaceset.core.entity.Material" }, storeJars = { "x_organization_core_entity",
				"x_organization_core_express" }, customJars = { "x_erpinterfaceset_core_entity" })
public class x_erpinterfaceset_assemble_control extends Deployable {
}
