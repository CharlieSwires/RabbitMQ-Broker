package com.mongodb;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Entries")
public class UniCardMongoBean{
	private static Base64.Encoder b64e;
	private static Base64.Decoder b64d;
	
	public UniCardMongoBean() {
	}

	@Id
	private String id;
	private String customerId;
	private Date usedDate;
	private String xmlData;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Date getUsedDate() {
		return usedDate;
	}
	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}
	public String getXmlData() {
//		b64d = Base64.getDecoder();
//		if (this.xmlData != null && !this.xmlData.isEmpty()) {
//			String result = new String(b64d.decode(this.xmlData));
//			return result;	
//		}
//		return null;
		return this.xmlData;
	}
	public void setXmlData(String data) {
//		b64e = Base64.getEncoder();
//		if (data != null && !data.isEmpty()) {
//			String result = b64e.encodeToString(data.getBytes(Charset.forName("UTF-8")));
//			this.xmlData = result;
//		} else {
//			this.xmlData = null;
//		}
		this.xmlData = data;
	}
}