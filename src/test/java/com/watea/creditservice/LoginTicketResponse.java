package com.watea.creditservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import ar.gov.agip.cc.controller.soap.Credenciales;
import ar.gov.agip.cc.controller.soap.HeaderLogin;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
		name = "loginTicketResponse",
		propOrder = {"credentials", "header"}
)
public class LoginTicketResponse {
	@XmlElement(
			required = true
	)
	protected Credenciales credentials;
	@XmlElement(
			required = true
	)
	protected HeaderLogin header;
	@XmlAttribute(
			name = "version",
			required = true
	)
	protected float version;

	public LoginTicketResponse() {
	}

	public Credenciales getCredentials() {
		return this.credentials;
	}

	public void setCredentials(Credenciales var1) {
		this.credentials = var1;
	}

	public HeaderLogin getHeader() {
		return this.header;
	}

	public void setHeader(HeaderLogin var1) {
		this.header = var1;
	}

	public float getVersion() {
		return this.version;
	}

	public void setVersion(float var1) {
		this.version = var1;
	}
}

