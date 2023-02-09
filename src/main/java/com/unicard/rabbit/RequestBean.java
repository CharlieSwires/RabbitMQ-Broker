package com.unicard.rabbit;

import java.util.Date;
import java.util.List;

public class RequestBean {
	public RequestBean() {

	}
	private Long messageId;
	private List<Inner> list;
	private String dateStart;
	private String dateEnd;
	private Integer psge;
	
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public Integer getPsge() {
		return psge;
	}
	public void setPsge(Integer psge) {
		this.psge = psge;
	}
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
			return xmlData;
		}
		public void setXmlData(String xmlData) {
			this.xmlData = xmlData;
		}
	}
}
