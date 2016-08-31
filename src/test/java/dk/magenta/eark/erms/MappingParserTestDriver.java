package dk.magenta.eark.erms;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;

import dk.magenta.eark.erms.parser.Hook;
import dk.magenta.eark.erms.parser.MappingParser;
import dk.magenta.eark.erms.parser.ObjectTypeMap;

public class MappingParserTestDriver {

	public static void main(String[] args) {
		InputStream in = MappingParser.class.getClassLoader().getResourceAsStream("mapping.xml");
		MappingParser mp = new MappingParser("xyz", in);
		
		ObjectTypeMap objectTypeMap = mp.getObjectTypes();
		System.out.println(objectTypeMap);
		
		Map<String, List<Hook>> hooks = mp.getHooks();
		for (String key : hooks.keySet()) {
			System.out.println(hooks.get(key));
		}		
		
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
