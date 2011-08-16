package org.vatvit.irccloud;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class NameValuePair {
	private String name;
	private String value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public NameValuePair(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	@Override
	public String toString() {
		try {
			return URLEncoder.encode(name, "UTF-8")+"="+URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name+"="+value;
	}
	
}
