package blog.article3;

import java.math.BigDecimal;
import java.util.Date;

public class Bill {
	
	private String label ;
	private BigDecimal amount ; 
	private Date date;
	private String url ;
	
	public Bill(String label, BigDecimal amount, Date date, String url){
		this.label = label;
		this.amount = amount ;
		this.date = date ;
		this.url = url ;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
