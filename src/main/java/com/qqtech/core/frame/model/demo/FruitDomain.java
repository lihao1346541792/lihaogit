package com.qqtech.core.frame.model.demo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "fruit")
public class FruitDomain {

	String name;
	int quality;

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public int getQuality() {
		return quality;
	}

	@XmlElement
	public void setQuality(int quality) {
		this.quality = quality;
	}

	public FruitDomain(String name, int quality) {
		this.name = name;
		this.quality = quality;
	}

	public FruitDomain() {
	}

}