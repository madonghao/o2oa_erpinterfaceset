package com.x.erpinterfaceset.assemble.control.jaxrs.material;

import com.x.base.core.container.EntityManagerContainer;
import com.x.base.core.container.factory.EntityManagerContainerFactory;
import com.x.base.core.entity.annotation.CheckPersistType;
import com.x.base.core.project.gson.XGsonBuilder;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.http.EffectivePerson;
import com.x.base.core.project.logger.Logger;
import com.x.base.core.project.logger.LoggerFactory;
import com.x.erpinterfaceset.assemble.control.Business;
import com.x.erpinterfaceset.assemble.control.material.*;
import com.x.erpinterfaceset.core.entity.Material;
import com.x.base.core.entity.JpaObject;
import com.x.base.core.project.bean.WrapCopier;
import com.x.base.core.project.bean.WrapCopierFactory;
import com.x.erpinterfaceset.core.entity.Material_;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

public class ActionPullSync extends BaseAction {
    private Logger logger = LoggerFactory.getLogger( ActionPullSync.class );

    protected ActionResult<ActionPullSync.Wo> execute(EffectivePerson effectivePerson) throws Exception {
        try (EntityManagerContainer emc = EntityManagerContainerFactory.instance().create()) {
            Business business = new Business(emc);

            logger.info("execute action 'ActionPullSync'......");
            ActionResult<ActionPullSync.Wo> result = new ActionResult<>();
            ActionPullSync.Wo wrap = null;
            PullResult pullResult = new PullResult();
            MaterialFactory factory = new MaterialFactory();
            for (Group root : factory.roots()) {
                logger.debug("开始同步ERP物料信息:{}", XGsonBuilder.toJson(root));
                this.check(business, root, factory, pullResult);
            }
            pullResult.end();
            if (!pullResult.getCreateMaterialList().isEmpty()) {
                logger.debug("创建物料({}):{}.", pullResult.getCreateMaterialList().size(),
                        StringUtils.join(pullResult.getCreateMaterialList(), ","));
            }
            if (!pullResult.getUpdateMaterialList().isEmpty()) {
                logger.debug("更新物料({}):{}.", pullResult.getUpdateMaterialList().size(),
                        StringUtils.join(pullResult.getUpdateMaterialList(), ","));
            }
            logger.info("action 'ActionPullSync' execute completed!");
            wrap = XGsonBuilder.convert(pullResult, ActionPullSync.Wo.class);
            result.setData(wrap);
            return result;
        }
    }

    private void check(Business business, Group group, MaterialFactory factory, PullResult result) throws Exception {
        // 遍历分组下所有物料
        EntityManagerContainer emc = business.entityManagerContainer();
        EntityManager em = emc.get(Material.class);
        for (MaterialJson o : factory.listMaterial(group)) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Material> cq = cb.createQuery(Material.class);
            Root<Material> root = cq.from(Material.class);
            Predicate p = cb.equal(root.get(Material_.number), o.getFNumber());
            List<Material> os = em.createQuery(cq.select(root).where(p)).setMaxResults(1).getResultList();
            Material material = null;
            if (os.size() == 0) {
                this.createMaterial(business, factory, o, group, result);
            } else {
                material = os.get(0);
                if (!StringUtils.equals(DigestUtils.sha256Hex(XGsonBuilder.toJson(o)), material.getErpMaterialHash())) {
                    this.updateMaterial(business, factory, o, group, material, result);
                }
            }
        }

        for (Group o : factory.listSub(group)) {
            this.check(business, o, factory, result);
        }
    }

    private void formatUnitName(MaterialFactory factory, Material material, Group group) throws Exception {
        for (Map<String, Object> l : factory.getLevel(group)) {
            int levelValue = (Integer) l.get("Level");
            String name = l.get("FNumber") + " " + l.get("FName");
            switch (levelValue) {
                case 1:
                    material.setGroupLarge(name);
                    break;
                case 2:
                    material.setGroupMedium(name);
                    break;
                case 3:
                    material.setGroupSmall(name);
                    break;
                case 4:
                    material.setGroup(name);
                    break;
                default:
                    break;
            }
        }
    }

    private Material createMaterial(Business business, MaterialFactory factory, MaterialJson materialJson, Group group, PullResult result) throws Exception {
        EntityManagerContainer emc = business.entityManagerContainer();
        emc.beginTransaction(Material.class);
        Material material = new Material();
        this.formatUnitName(factory, material, group);
        material.setNumber(materialJson.getFNumber());
        material.setName(materialJson.getFName());
        material.setSpecification(materialJson.getFSpecification());
        material.setYLAHGKBUM(materialJson.getFYLAHGKBUM());
        material.setBaseunit(factory.getUnitName(materialJson.getFBaseUnitId()));
        material.setAuxunit(factory.getUnitName(materialJson.getFAuxUnitID()));
        material.setPurchaseunit(factory.getUnitName(materialJson.getFPurchaseUnitId()));
        material.setPurchasepriceunit(factory.getUnitName(materialJson.getFPurchasePriceUnitId()));
        material.setErpMaterialId("" + materialJson.getFMATERIALID());
        material.setErpMaterialHash(DigestUtils.sha256Hex(XGsonBuilder.toJson(materialJson)));
        emc.persist(material, CheckPersistType.all);
        emc.commit();
        result.getCreateMaterialList().add(materialJson.getFName());
        return material;
    }

    private Material updateMaterial(Business business, MaterialFactory factory, MaterialJson materialJson, Group group, Material material, PullResult result) throws Exception {
        EntityManagerContainer emc = business.entityManagerContainer();
        emc.beginTransaction(Material.class);
        material.setGroupLarge("");
        material.setGroupMedium("");
        material.setGroupSmall("");
        material.setGroup("");
        this.formatUnitName(factory, material, group);
        material.setNumber(materialJson.getFNumber());
        material.setName(materialJson.getFName());
        material.setSpecification(materialJson.getFSpecification());
        material.setYLAHGKBUM(materialJson.getFYLAHGKBUM());
        material.setBaseunit(factory.getUnitName(materialJson.getFBaseUnitId()));
        material.setAuxunit(factory.getUnitName(materialJson.getFAuxUnitID()));
        material.setPurchaseunit(factory.getUnitName(materialJson.getFPurchaseUnitId()));
        material.setPurchasepriceunit(factory.getUnitName(materialJson.getFPurchasePriceUnitId()));
        material.setErpMaterialId("" + materialJson.getFMATERIALID());
        material.setErpMaterialHash(DigestUtils.sha256Hex(XGsonBuilder.toJson(materialJson)));
        emc.persist(material, CheckPersistType.all);
        emc.commit();
        result.getUpdateMaterialList().add(materialJson.getFName());
        return material;
    }

    public static class Wo extends PullResult {

//        private static final long serialVersionUID = -5076990764713538973L;
//
//        public static List<String> Excludes = new ArrayList<String>();
//
//        public static final WrapCopier<Material, ActionPullSync.Wo> copier = WrapCopierFactory.wo( Material.class, ActionPullSync.Wo.class, null, JpaObject.FieldsInvisible);
    }

}
