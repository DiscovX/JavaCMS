package core;

import module.user.User;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("authenticator")
@Scope("singleton")
public class Authenticator {
	private SessionFactory sessionFactory;
	
	@Autowired
	public Authenticator(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public boolean authenticate(Resource res, User user) {
		if (res.isPrivate()) {
			
		}
		
		// look up the resources_by_roles table
		RoleResourceDAO rrdao = new RoleResourceDAO(sessionFactory);
		RoleResource rr = (RoleResource) rrdao.find(user.getRole(), res);
		if (rr == null)
			return false;
		return true;
	}
}
