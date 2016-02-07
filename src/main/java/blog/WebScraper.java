package blog;

import java.net.URLEncoder;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WebScraper {

	public static void main(String[] args) {
		
	    String searchQuery = "iphone 6s" ;
		
		WebClient client = new WebClient();
		client.setJavaScriptEnabled(false);
		try {
			String searchUrl = "https://newyork.craigslist.org/search/sss?sort=rel&query=" + URLEncoder.encode(searchQuery, "UTF-8");
			HtmlPage page = client.getPage(searchUrl);
			
			List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//span[@class='rows']/p") ;
			if(items.isEmpty()){
				System.out.println("No items found !");
			}else{
				for(HtmlElement item : items){
					HtmlAnchor itemAnchor = ((HtmlAnchor) item.getFirstByXPath(".//span[@class='txt']/span[@class='pl']/a"));
					
					String itemName = itemAnchor.asText();
					String itemUrl = itemAnchor.getHrefAttribute() ;
					
					HtmlElement spanPrice = ((HtmlElement) item.getFirstByXPath(".//span[@class='txt']/span[@class='l2']/span[@class='price']")) ;
					// It is possible that an item doesn't have any price
					String itemPrice = spanPrice == null ? "no price" : spanPrice.asText() ;
					System.out.println( String.format("Name : %s Url : %s Price : %s", itemName, itemPrice, itemUrl));
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}

	}

}
