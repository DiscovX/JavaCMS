package module.navigation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class NavigationService {
	
	private NavigationDAO navigationDAO;
	
	@Autowired
	public NavigationService(NavigationDAO navigationDAO) {
		this.navigationDAO = navigationDAO;
	}
	
	public List<Link> getAllLinks() {
//		Link rootLink = new Link();
//		rootLink.setId(0);
		//buildLinkTree(rootLink);
		return this.navigationDAO.findAllLinksByParent(0);
	}
	
//	private void buildLinkTree(Link link) {
//		ArrayList<Link> children = this.navigationDAO.findAllLinksByParent(link.getId());
//		if (children == null) 
//			return ;
//		link.setChildren(children);
//		for (Link child : children) {
//			buildLinkTree(child);
//		}
//	}
}
