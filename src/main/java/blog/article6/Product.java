package blog.article6;

import java.math.BigDecimal;

import com.gargoylesoftware.htmlunit.javascript.host.URL;

public class Product {
	
	private BigDecimal price;
	private String name;
	private String sku;
	private java.net.URL imageUrl;
	private String currency;
	
	public BigDecimal getPrice() {
		return price;
	}
	public Product(BigDecimal price, String name, String sku, java.net.URL imageUrl, String currency) {
		super();
		this.price = price;
		this.name = name;
		this.sku = sku;
		this.imageUrl = imageUrl;
		this.currency = currency;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public java.net.URL getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(java.net.URL imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}	
	
}
