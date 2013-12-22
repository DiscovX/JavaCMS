package core;

import java.util.ArrayList;

public class RegionContent {
	
	private ArrayList<ModuleContent> modules;
	
	private String content;

	public ArrayList<ModuleContent> getModules() {
		return modules;
	}

	public void setModules(ArrayList<ModuleContent> modules) {
		this.modules = modules;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
