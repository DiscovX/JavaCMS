package module.navigation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class NavigationDAO {
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public NavigationDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List<Link> findAllLinksByParent(int parentId) {
		Session session = this.sessionFactory.openSession();
		Link rootLink = new Link();
		rootLink.setId(new Long(0));
		Query query = session.createQuery("from Link as link where parentLink.id=:id").setInteger("id", 0);
		return query.list();
	}
}
