package com.watea.creditservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
		name = "headerLogin",
		propOrder = {"destination", "expirationTime", "generationTime", "source", "uniqueId"}
)
public class HeaderLogin {
	protected String destination;
	@XmlElement(
			required = true
	)
	@XmlSchemaType(
			name = "dateTime"
	)
	protected XMLGregorianCalendar expirationTime;
	@XmlElement(
			required = true
	)
	@XmlSchemaType(
			name = "dateTime"
	)
	protected XMLGregorianCalendar generationTime;
	protected String source;
	protected long uniqueId;

	public HeaderLogin() {
	}

	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String var1) {
		this.destination = var1;
	}

	public XMLGregorianCalendar getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(XMLGregorianCalendar var1) {
		this.expirationTime = var1;
	}

	public XMLGregorianCalendar getGenerationTime() {
		return this.generationTime;
	}

	public void setGenerationTime(XMLGregorianCalendar var1) {
		this.generationTime = var1;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String var1) {
		this.source = var1;
	}

	public long getUniqueId() {
		return this.uniqueId;
	}

	public void setUniqueId(long var1) {
		this.uniqueId = var1;
	}
}

