package com.unicard.rabbit;

import java.util.Date;
import java.util.List;

public class RequestBean {
	public RequestBean() {

	}
	private Long messageId;

	private List<Inner> list;
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public List<Inner> getList() {
		return this.list;
	}
	public void setList(List<Inner> list) {
		this.list = list;
	}

	static class Inner {
		public Inner() {
			
		}
		private String customerId;
		private Date usedDate;
		private String xmlData;
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
			return xmlData;
		}
		public void setXmlData(String xmlData) {
			this.xmlData = xmlData;
		}
	}
}
