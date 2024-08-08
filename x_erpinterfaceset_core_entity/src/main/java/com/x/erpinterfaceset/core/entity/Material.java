package com.x.erpinterfaceset.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.x.base.core.entity.annotation.*;
import org.apache.openjpa.persistence.jdbc.Index;

import com.x.base.core.entity.JpaObject;
import com.x.base.core.entity.SliceJpaObject;
import com.x.base.core.project.annotation.FieldDescribe;

/**
 * 示例实体类
 * @author sword
 */
@ContainerEntity
@Entity
@Table(name = PersistenceProperties.Material.table, uniqueConstraints = {
		@UniqueConstraint(name = PersistenceProperties.Material.table + JpaObject.IndexNameMiddle
				+ JpaObject.DefaultUniqueConstraintSuffix, columnNames = { JpaObject.IDCOLUMN,
						JpaObject.CREATETIMECOLUMN, JpaObject.UPDATETIMECOLUMN, JpaObject.SEQUENCECOLUMN }) })
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Material extends SliceJpaObject {

	private static final long serialVersionUID = 1325197931747463979L;
	private static final String TABLE = PersistenceProperties.Material.table;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@FieldDescribe("数据库主键,自动生成.")
	@Id
	@Column(length = length_id, name = ColumnNamePrefix + id_FIELDNAME)
	private String id = createId();

	@Override
	public void onPersist() {
	}
	/*
	 * =============================================================================
	 * ===== 以上为 JpaObject 默认字段
	 * =============================================================================
	 */

	/*
	 * =============================================================================
	 * ===== 以下为具体不同的业务及数据表字段要求
	 * =============================================================================
	 */

	public static final String GROUP_LARGE_FIELDNAME = "groupLarge";
	@FieldDescribe("大类")
	@Index(name = TABLE + IndexNameMiddle + GROUP_LARGE_FIELDNAME)
	@Column(length = length_255B, name = ColumnNamePrefix + GROUP_LARGE_FIELDNAME)
	private String groupLarge;

	public static final String GROUP_MEDIUM_FIELDNAME = "groupMedium";
	@FieldDescribe("中类")
	@Index(name = TABLE + IndexNameMiddle + GROUP_MEDIUM_FIELDNAME)
	@Column(length = length_255B, name = ColumnNamePrefix + GROUP_MEDIUM_FIELDNAME)
	private String groupMedium;

	public static final String GROUP_SMALL_FIELDNAME = "groupSmall";
	@FieldDescribe("小类")
	@Index(name = TABLE + IndexNameMiddle + GROUP_SMALL_FIELDNAME)
	@Column(length = length_255B, name = ColumnNamePrefix + GROUP_SMALL_FIELDNAME)
	private String groupSmall;

	public static final String GROUP_FIELDNAME = "group";
	@FieldDescribe("物料分类")
	@Index(name = TABLE + IndexNameMiddle + GROUP_FIELDNAME)
	@Column(length = length_255B, name = ColumnNamePrefix + GROUP_FIELDNAME)
	private String group;

	public static final String NAME_FIELDNAME = "name";
	@FieldDescribe("名称")
	@Index(name = TABLE + IndexNameMiddle + NAME_FIELDNAME)
	@Column(length = length_255B, name = ColumnNamePrefix + NAME_FIELDNAME)
	@CheckPersist(allowEmpty = false)
	private String name;

	public static final String NUMBER_FIELDNAME = "number";
	@FieldDescribe("编码")
	@Index(name = TABLE + IndexNameMiddle + NUMBER_FIELDNAME)
	@Column(length = length_255B, name = ColumnNamePrefix + NUMBER_FIELDNAME)
	@CheckPersist(allowEmpty = false)
	private String number;

	public static final String SPECIFICATION_FIELDNAME = "specification";
	@FieldDescribe("规格型号")
	@Index(name = TABLE + IndexNameMiddle + SPECIFICATION_FIELDNAME)
	@Column(length = length_255B, name = ColumnNamePrefix + SPECIFICATION_FIELDNAME)
	private String specification;

	public static final String PURCHASEUNIT_FIELDNAME = "purchaseunit";
	@FieldDescribe("采购单位")
	@Index(name = TABLE + IndexNameMiddle + PURCHASEUNIT_FIELDNAME)
	@Column(length = length_255B, name = ColumnNamePrefix + PURCHASEUNIT_FIELDNAME)
	private String purchaseunit;

	public static final String PURCHASEPRICEUNIT_FIELDNAME = "purchasepriceunit";
	@FieldDescribe("采购计价单位")
	@Index(name = TABLE + IndexNameMiddle + PURCHASEPRICEUNIT_FIELDNAME)
	@Column(length = length_255B, name = ColumnNamePrefix + PURCHASEPRICEUNIT_FIELDNAME)
	private String purchasepriceunit;

	public static final String YLAHGKBUM_FIELDNAME = "ylahgkbum";
	@FieldDescribe("归口部门(在用)")
	@Index(name = TABLE + IndexNameMiddle + YLAHGKBUM_FIELDNAME)
	@Column(length = length_255B, name = ColumnNamePrefix + YLAHGKBUM_FIELDNAME)
	private String ylahgkbum;

	public static final String BASEUNIT_FIELDNAME = "baseunit";
	@FieldDescribe("基本单位")
	@Index(name = TABLE + IndexNameMiddle + BASEUNIT_FIELDNAME)
	@Column(length = length_255B, name = ColumnNamePrefix + BASEUNIT_FIELDNAME)
	private String baseunit;

	public static final String AUXUNIT_FIELDNAME = "auxunit";
	@FieldDescribe("辅助单位")
	@Index(name = TABLE + IndexNameMiddle + AUXUNIT_FIELDNAME)
	@Column(length = length_255B, name = ColumnNamePrefix + AUXUNIT_FIELDNAME)
	private String auxunit;

	public static final String erpMaterialId_FIELDNAME = "erpMaterialId";
	@FieldDescribe("ERP物料ID.")
	@Column(length = length_255B, name = ColumnNamePrefix + erpMaterialId_FIELDNAME)
	@Index(name = TABLE + IndexNameMiddle + erpMaterialId_FIELDNAME)
	private String erpMaterialId;

	public static final String erpMaterialHash_FIELDNAME = "erpMaterialHash";
	@FieldDescribe("ERP物料哈希特征.")
	@Column(length = length_255B, name = ColumnNamePrefix + erpMaterialHash_FIELDNAME)
	private String erpMaterialHash;

	public String getGroupLarge() {
		return groupLarge;
	}

	public void setGroupLarge(String groupLarge) {
		this.groupLarge = groupLarge;
	}

	public String getGroupMedium() {
		return groupMedium;
	}

	public void setGroupMedium(String groupMedium) {
		this.groupMedium = groupMedium;
	}

	public String getGroupSmall() {
		return groupSmall;
	}

	public void setGroupSmall(String groupSmall) {
		this.groupSmall = groupSmall;
	}

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

	public String getPurchaseunit() {
		return purchaseunit;
	}

	public void setPurchaseunit(String purchaseunit) {
		this.purchaseunit = purchaseunit;
	}

	public String getPurchasepriceunit() {
		return purchasepriceunit;
	}

	public void setPurchasepriceunit(String purchasepriceunit) {
		this.purchasepriceunit = purchasepriceunit;
	}

	public String getYLAHGKBUM() {
		return ylahgkbum;
	}

	public void setYLAHGKBUM(String ylahgkbum) {
		this.ylahgkbum = ylahgkbum;
	}

	public String getBaseunit() {
		return baseunit;
	}

	public void setBaseunit(String baseunit) {
		this.baseunit = baseunit;
	}

	public String getAuxunit() {
		return auxunit;
	}

	public void setAuxunit(String auxunit) {
		this.auxunit = auxunit;
	}

	public String getErpMaterialId() {
		return erpMaterialId;
	}

	public void setErpMaterialId(String erpMaterialId) {
		this.erpMaterialId = erpMaterialId;
	}

	public String getErpMaterialHash() {
		return erpMaterialHash;
	}

	public void setErpMaterialHash(String erpMaterialHash) {
		this.erpMaterialHash = erpMaterialHash;
	}
}
