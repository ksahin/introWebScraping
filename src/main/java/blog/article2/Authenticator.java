package blog.article2;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class Authenticator {
	public static void main(String[] args) {
		
		String baseUrl = "https://news.ycombinator.com" ;
		String loginUrl = baseUrl + "/login?goto=news" ; 
		String login = "login";
		String password = "password" ;
		
		try {
			System.out.println("Starting autoLogin on " + loginUrl);
			WebClient client = autoLogin(loginUrl, login, password);
			HtmlPage page = client.getPage(baseUrl) ;
			
			HtmlAnchor logoutLink = page.getFirstByXPath(String.format("//a[@href='user?id=%s']", login)) ;
			if(logoutLink != null ){
				System.out.println("Successfuly logged in !");
				// printing the cookies
				for(Cookie cookie : client.getCookieManager().getCookies()){
					System.out.println(cookie.toString());
				}
			}else{
				System.err.println("Wrong credentials");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static WebClient autoLogin(String loginUrl, String login, String password) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		
		HtmlPage page = client.getPage(loginUrl);
		
		HtmlInput inputPassword = page.getFirstByXPath("//input[@type='password']");
		//The first preceding input that is not hidden
		HtmlInput inputLogin = inputPassword.getFirstByXPath(".//preceding::input[not(@type='hidden')]");
		
		inputLogin.setValueAttribute(login);
		inputPassword.setValueAttribute(password);
		
		//get the enclosing form
		HtmlForm loginForm = inputPassword.getEnclosingForm() ;
		
		//submit the form
		page = client.getPage(loginForm.getWebRequest(null));
		
		//returns the cookies filled client :)
		return client;
	}

}
