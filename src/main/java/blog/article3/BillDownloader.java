package blog.article3;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.javascript.host.URL;

import blog.article2.Authenticator;

public class BillDownloader {
	public static void main(String[] args) {
		String baseUrl = "https://cloud.digitalocean.com";
		String login = "email";
		String password = "login" ;
		
		try {
			WebClient client = Authenticator.autoLogin(baseUrl + "/login", login, password);
			
			HtmlPage page = client.getPage("https://cloud.digitalocean.com/settings/billing");
			if(page.asText().contains("You need to sign in for access to this page")){
				throw new Exception(String.format("Error during login on %s , check your credentials", baseUrl));
			}
			List<Bill> bills = new ArrayList<Bill>();
			HtmlTable billsTable = (HtmlTable) page.getFirstByXPath("//table[@class='listing Billing--history']");
			for(HtmlTableRow row : billsTable.getBodies().get(0).getRows()){
				
				String label = row.getCell(1).asText();
				// We only want the invoice row, not the payment one
				if(!label.contains("Invoice")){
					continue ;
				}
				
				Date date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(row.getCell(0).asText());
				BigDecimal amount =new BigDecimal(row.getCell(2).asText().replace("$", ""));
				String url = ((HtmlAnchor) row.getCell(3).getFirstChild()).getHrefAttribute();
				
				Bill bill = new Bill(label, amount, date, url);
				bills.add(bill);
				ObjectMapper mapper = new ObjectMapper();
				String jsonString = mapper.writeValueAsString(bill) ;
				
				System.out.println(jsonString);
				
				
				Page invoicePdf = client.getPage(baseUrl + url);
				if(invoicePdf.getWebResponse().getContentType().equals("application/pdf")){
					IOUtils.copy(invoicePdf.getWebResponse().getContentAsStream(), new FileOutputStream("DigitalOcean" + label + ".pdf"));
				}
				
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
