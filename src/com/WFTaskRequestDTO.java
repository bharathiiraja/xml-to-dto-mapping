package com;

import java.util.HashMap;

public class WFTaskRequestDTO {
	public String applicationName;
	public HashMap<String,String> wfIndentifier;
	public String fromStage;
	public String assignedBy;
	public String assignedTo;
	public String sla;
	public HashMap<String,String> data;
	public String dataStr;

	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public HashMap<String, String> getWfIndentifier() {
		return wfIndentifier;
	}
	public void setWfIndentifier(HashMap<String, String> wfIndentifier) {
		this.wfIndentifier = wfIndentifier;
	}
	public String getFromStage() {
		return fromStage;
	}
	public void setFromStage(String fromStage) {
		this.fromStage = fromStage;
	}
	public String getAssignedBy() {
		return assignedBy;
	}
	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getSla() {
		return sla;
	}
	public void setSla(String sla) {
		this.sla = sla;
	}
	public HashMap<String, String> getData() {
		return data;
	}
	public void setData(HashMap<String, String> data) {
		this.data = data;
	}
	public String getDataStr() {
		return dataStr;
	}
	public void setDataStr(String dataStr) {
		this.dataStr = dataStr;
	}
	
	@Override
	public String toString() {
		return "WFTaskRequestDTO [applicationName=" + applicationName
				+ ", wfIndentifier=" + wfIndentifier + ", fromStage="
				+ fromStage + ", assignedBy=" + assignedBy + ", assignedTo="
				+ assignedTo + ", sla=" + sla + ", data=" + data + ", dataStr="
				+ dataStr + "]";
	}
	
}
