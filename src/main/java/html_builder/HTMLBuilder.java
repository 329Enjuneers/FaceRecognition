package html_builder;

import java.util.ArrayList;

public class HTMLBuilder {
  private ArrayList<String> body;
  private ArrayList<String> head;
  private ArrayList<String> scripts;
  private boolean headIsSet;

  public HTMLBuilder() {
      body = new ArrayList<String>();
      head = new ArrayList<String>();
      scripts = new ArrayList<String>();
      headIsSet = false;
  }

  public String build() {
    StringBuilder str = new StringBuilder();
    str.append("<html>");
    str.append(buildHead());
    str.append(buildBody());
    str.append(buildScripts());
    str.append("</html>");
    return str.toString();
  }
  
  public void addForm(Form form) {
	  body.add(form.toString());
  }

  public void addToBody(String line) {
	  body.add(line);
  }

  public void addToHead(String line) {
	  head.add(line);
  }
  
  public Form getNewForm() {
	  return new Form();
  }

  public void setTitle(String title) throws Exception {
	  if (headIsSet) {
		  throw new Exception("Title already set!");
	  }
	  headIsSet = true;
	  head.add("<title>" + title + "</title>");
  }

  public void addScript(String script) {
	  scripts.add(script);
  }

  public String getNavigationBar() {
    return "";
  }
  
  private String buildHead() {
	  StringBuilder str = new StringBuilder();
	  str.append("<head>");
	  for (String line : head) {
		  str.append(line + "\n");
	  }
	  str.append("</head>");
	  return str.toString();
  }
  
  private String buildBody() {
	  StringBuilder str = new StringBuilder();
	  str.append("<body>");
	  for (String line : body) {
		  str.append(line + "\n");
	  }
	  str.append("</body>");
	  return str.toString();
  }
  
  private String buildScripts() {
	  StringBuilder str = new StringBuilder();
	  for (String line : scripts) {
		  str.append(line + "\n");
	  }
	  return str.toString();
  }
}