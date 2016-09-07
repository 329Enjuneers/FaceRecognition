package pages.html_builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Div {
	
	private Map<String, String> propertyMap;
	private ArrayList<String> elements;
	private static final String DIV_FOOTER = "</div>";
	
	public Div() {
		propertyMap = new HashMap<String, String>();
		elements = new ArrayList<String>();
	}
	
	public void addProperty(String propertyName, String propertyValue) {
		propertyMap.put(propertyName, propertyValue);
	}
	
	public void addElement(String element) {
		elements.add(element);
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(buildFormDeclaration());
		str.append(buildElements());
		str.append(DIV_FOOTER);
		return str.toString();
	}
	
	private String buildFormDeclaration() {
		StringBuilder str = new StringBuilder();
		str.append("<div ");
		for (Entry<String, String> entry : propertyMap.entrySet()) {
		    String property = entry.getKey();
		    String value = (String) entry.getValue();
		    str.append(property + "='" + value + "' ");
		}
		str.append(">");
		return str.toString();
	}
	
	private String buildElements() {
		StringBuilder str = new StringBuilder();
		for (String element : elements) {
			str.append(element + "\n");
		}
		return str.toString();
	}
}
