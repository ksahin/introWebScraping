package blog.article6;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class SchemaScraper {
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		String productUrl = "https://www.asos.com/the-north-face/the-north-face-vault-backpack-28-litres-in-black/prd/10253008";
		
		HtmlPage page = client.getPage(productUrl);
		HtmlElement productNode = ((HtmlElement) page
				.getFirstByXPath("//*[@itemtype='https://schema.org/Product']"));
		URL imageUrl = new URL((((HtmlElement) productNode.getFirstByXPath("./img")))
				.getAttribute("src"));
		HtmlElement offers = ((HtmlElement) productNode.getFirstByXPath("./span[@itemprop='offers']"));
		
		BigDecimal price = new BigDecimal(((HtmlElement) offers.getFirstByXPath("./span[@itemprop='price']")).asText());
		String productName = (((HtmlElement) productNode.getFirstByXPath("./span[@itemprop='name']")).asText());
		String currency = (((HtmlElement) offers.getFirstByXPath("./*[@itemprop='priceCurrency']")).getAttribute("content"));
		String productSKU = (((HtmlElement) productNode.getFirstByXPath("./span[@itemprop='sku']")).asText());
		
		Product product = new Product(price, productName, productSKU, imageUrl, currency);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(product) ;
		System.out.println(jsonString);
	}
	
	
}
