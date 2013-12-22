package core;

import module.user.Role;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RoleResourceDAO {
	private SessionFactory sessionFactory;
	
	public RoleResourceDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public RoleResource find(Role role, Resource resource) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from RoleResource where role=:role and resource=:resource");
		query.setParameter("role", role);
		query.setParameter("resource", resource);
		RoleResource rr = (RoleResource) query.uniqueResult();
		session.close();
		return rr;
	}
}
