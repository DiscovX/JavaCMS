package core;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletContext;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class RegionBlocksMetaInfoReader {

	public static ArrayList<String> read(String regionName, ServletContext servletContext){
		String rootPath = servletContext.getRealPath("/"); // E:\eclipse-workspace-backup\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\cms\
		String path = rootPath + "regions/" + regionName + ".xml";
		File file = new File(path);
		SAXReader reader = new SAXReader();
		try {
			ArrayList<String> blocks = new ArrayList<String>();
			Document document = reader.read(file);
			Element element = document.getRootElement();
			for (Iterator<Element> it = (Iterator<Element>) element.elementIterator("block");it.hasNext();) {
				element = (Element) it.next();
				String blockName = element.attributeValue("name");
				blocks.add(blockName);
			}
			return blocks;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
