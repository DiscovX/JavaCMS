package core;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import core.pojo.Module;

public class ModuleDAO extends HibernateDaoSupport {
	
	public Long saveModule(Module module) {
		return (Long) this.getHibernateTemplate().save(module);
	}
	
	public List<Module> findAllModules() {
		String hql = "from module";
		return this.getHibernateTemplate().find(hql);
	}
}
