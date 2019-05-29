package com.watea.creditservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
		name = "credenciales",
		propOrder = {"sign", "token"}
)
public class Credenciales {
	@XmlElement(
			required = true
	)
	protected String sign;
	@XmlElement(
			required = true
	)
	protected String token;

	public Credenciales() {
	}

	public String getSign() {
		return this.sign;
	}

	public void setSign(String var1) {
		this.sign = var1;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String var1) {
		this.token = var1;
	}
}
