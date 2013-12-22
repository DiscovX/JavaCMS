package module.navigation;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import core.pojo.ModuleLink;

@Entity
@Table(name = "navigation")
public class Link {
	
	private Long id;
	
	private String name;
	
	private ModuleLink moduleLink;
	
	private Link parentLink;
	
	private Set<Link> children;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	public Link getParentLink() {
		return parentLink;
	}

	public void setParentLink(Link parentLink) {
		this.parentLink = parentLink;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "module_link_id")
	public ModuleLink getModuleLink() {
		return moduleLink;
	}

	public void setModuleLink(ModuleLink moduleLink) {
		this.moduleLink = moduleLink;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parentLink")
	public Set<Link> getChildren() {
		return children;
	}

	public void setChildren(Set<Link> children) {
		this.children = children;
	}
}
