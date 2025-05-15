package com.ceir.CeirCode.model.app;

public class ShFeature {

	private long id;
	private String category;
	private String name;
	private String logo;
	private String link;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return "ShFeature [id=" + id + ", category=" + category + ", name=" + name + ", logo=" + logo + ", link=" + link
				+ "]";
	}


}
