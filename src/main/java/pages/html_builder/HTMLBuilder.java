package pages.html_builder;

import java.util.ArrayList;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import user.User;

public class HTMLBuilder {
  public boolean includeAppHeader;

  private ArrayList<String> body;
  private ArrayList<String> head;
  private ArrayList<String> scripts;
  private boolean headIsSet;
  private String baseUrl;


  public HTMLBuilder(String baseUrl) {
      body = new ArrayList<String>();
      head = new ArrayList<String>();
      scripts = new ArrayList<String>();
      headIsSet = false;
      includeAppHeader = false;
      this.baseUrl = baseUrl;
  }

  public String build() {
    StringBuilder str = new StringBuilder();
    str.append("<!DOCTYPE html><html>");
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
	  if (includeAppHeader) {
		  str.append(getAppHeader());
	  }
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

  private String getAppHeader() {
	  User user = User.getCurrentUser();
	  if (user == null) {
		  return "";
	  }
	  UserService userService = UserServiceFactory.getUserService();
	  Div div = new Div();
	  div.addElement("<h4 style='display: inline'>Welcome, " + user.nickname + "</h4>");
      div.addElement("<span style='float: right'><a href='" + userService.createLogoutURL(baseUrl) + "'> <button>Logout</button></a></span>");

      Div tabs = new Div();
      tabs.addElement("<a href='/'>Home</a>");
      String divString = div.toString();
      String tabsString = tabs.toString();
      String hr = "<hr>";
      return divString + tabsString + hr;
  }
}
