package com.tritonsfs.cac.sso.entity;

public class TestEntity {

	private String name;

	private String shortName;

	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "TestEntity [name=" + name + ", shortName=" + shortName + ", url=" + url + "]";
	}

}
