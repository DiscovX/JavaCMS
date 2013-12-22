package module.user;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import util.MD5;

@Component
@Scope("prototype")
public class UserService {
	
	private UserDAO userDAO;
	
	@Autowired
	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public void register(User user) throws UserNameExistedException {
		User temp = userDAO.findUserByName(user.getUsername());
		if (temp != null) {
			throw new UserNameExistedException();
		}
		
		user.setPassword(MD5.getMD5String(user.getPassword()));
		userDAO.insertUser(user);
	}
	
	public User check(User user) {
		user.setPassword(MD5.getMD5String(user.getPassword()));
		return userDAO.findUser(user.getUsername(), user.getPassword());
	}
	
	public User getUser(Long id) {
		User user = userDAO.findUserById(id);
		return user;
	}
}
