package core;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletContext;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import core.pojo.Module;
import core.pojo.ModuleLink;

public class ModuleManager {
	
	public static void registerModule(String path, ServletContext servletContext) {
		Module module = null;
		ArrayList<ModuleLink> list = null;
		ModuleLink link = null;
		
		File file = new File(path);
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(file);
			Element element = document.getRootElement();
			module = new Module();
			module.setName(element.attributeValue("name"));
			element = element.element("links");
			list = new ArrayList<ModuleLink>();
			for (Iterator<Element> it = element.elementIterator("link");it.hasNext();) {
				element = (Element)it.next();
				link = new ModuleLink();
				link.setModule(module);
				link.setName(element.attributeValue("name"));
				link.setValue(element.attributeValue("value"));
				list.add(link);
			}
			
			ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			ModuleDAO moduleDAO = (ModuleDAO) applicationContext.getBean("moduleDAO");
			Long id = moduleDAO.saveModule(module);
			module.setId(id);
			
			ModuleLinkDAO moduleLinkDAO = (ModuleLinkDAO) applicationContext.getBean("moduleLinkDAO");
			for (int i = 0;i < list.size();i++) {
				link = list.get(i);
				moduleLinkDAO.saveModuleLink(link);
			}
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
