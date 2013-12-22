package core;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import core.pojo.ModuleLink;

public class ModuleLinkDAO extends HibernateDaoSupport {
	
	public int saveModuleLink(ModuleLink ml) {
		return (Integer) this.getHibernateTemplate().save(ml);
	}
}
