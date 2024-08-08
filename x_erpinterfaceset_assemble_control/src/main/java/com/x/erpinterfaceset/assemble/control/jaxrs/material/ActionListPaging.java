package com.x.erpinterfaceset.assemble.control.jaxrs.material;

import com.x.base.core.container.EntityManagerContainer;
import com.x.base.core.container.factory.EntityManagerContainerFactory;
import com.x.base.core.entity.JpaObject;
import com.x.base.core.project.bean.WrapCopier;
import com.x.base.core.project.bean.WrapCopierFactory;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.http.EffectivePerson;
import com.x.erpinterfaceset.core.entity.Material;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * 分页查询
 * @author sword
 */
public class ActionListPaging extends BaseAction {

	ActionResult<List<Wo>> execute(EffectivePerson effectivePerson, Integer page, Integer size) throws Exception {
		try (EntityManagerContainer emc = EntityManagerContainerFactory.instance().create()) {
			ActionResult<List<Wo>> result = new ActionResult<>();
			EntityManager em = emc.get(Material.class);
			CriteriaBuilder cb = em.getCriteriaBuilder();
			Predicate p = cb.conjunction();
			List<Wo> wos = emc.fetchDescPaging(Material.class, Wo.copier, p, page, size, Material.sequence_FIELDNAME);
			result.setData(wos);
			result.setCount(emc.count(Material.class, p));
			return result;
		}
	}

	public static class Wo extends Material {

		private static final long serialVersionUID = -4635222902589827154L;

		static final WrapCopier<Material, Wo> copier = WrapCopierFactory.wo(Material.class, Wo.class,
				JpaObject.singularAttributeField(Material.class, true, true), null);

	}
}
