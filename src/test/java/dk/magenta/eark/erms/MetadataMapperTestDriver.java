package dk.magenta.eark.erms;

import java.io.InputStream;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import dk.magenta.eark.erms.ead.Hook;
import dk.magenta.eark.erms.ead.MappingParser;
import dk.magenta.eark.erms.ead.MetadataMapper;

public class MetadataMapperTestDriver {

	public static void main(String[] args) {
		InputStream in = MappingParser.class.getClassLoader().getResourceAsStream("mapping.xml");
		MappingParser mp = new MappingParser("xyz", in);
		Element c = mp.getCElements().get("series");

		Namespace ead = Namespace.getNamespace("ead", Constants.EAD_NAMESPACE);
		XPathFactory factory = XPathFactory.instance();
		XPathExpression exp = factory.compile("//child::ead:did/child::ead:unitdate/attribute::datechar", Filters.attribute(), null, ead);
	
		List<Attribute> list = exp.evaluate(c);
		// System.out.println(list.get(0).getName());
		
		// Testing the MetadataMapper
		CmisObject cmisObj = new CmisObjectImpl<Object>();
		// System.out.println(cmisObj.getProperty("").getValuesAsString());
		
		List<Hook> hooks = mp.getHooks().get("series");
		MetadataMapper metadataMapper = new MetadataMapper();
		Element e = metadataMapper.mapCElement(cmisObj, hooks, c);
		Element clone = e.clone();
		
		
		System.out.println(clone.getName());
		System.out.println(clone.getParentElement());
		System.out.println(clone.getNamespace());
		System.out.println(clone.getChild("did", ead).getChild("unitdate", ead).getTextTrim());
	}

}
