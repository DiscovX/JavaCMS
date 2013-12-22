package module.user;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UserDAO {
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public UserDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void insertUser(User user) {
		Session session = this.sessionFactory.openSession();
		//Transaction t =  session.beginTransaction();
		session.save(user);
		//t.commit();
		session.close();
	}
	
	public User findUserByName(String username) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from User user where username=:username").setParameter("username", username);
		User user = (User) query.uniqueResult();
		return user;
	}
	
	public User findUser(String username, String password) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from User user where username=:username and password=:password")
				.setParameter("username", username).setParameter("password", password);
		User user = (User) query.uniqueResult();
		return user;
	}
	
	public User findUserById(Long id) {
		Session session = this.sessionFactory.openSession();
		User user = (User) session.get(User.class, id);
		return user;
	}
}
