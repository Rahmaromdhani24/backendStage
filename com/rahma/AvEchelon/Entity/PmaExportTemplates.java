package com.rahma.AvEchelon.Entity;
// Generated Jun 13, 2024, 2:26:21 AM by Hibernate Tools 5.6.7.Final

/**
 * PmaExportTemplates generated by hbm2java
 */
public class PmaExportTemplates implements java.io.Serializable {

	private Integer id;
	private String username;
	private String exportType;
	private String templateName;
	private String templateData;

	public PmaExportTemplates() {
	}

	public PmaExportTemplates(String username, String exportType, String templateName, String templateData) {
		this.username = username;
		this.exportType = exportType;
		this.templateName = templateName;
		this.templateData = templateData;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getExportType() {
		return this.exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateData() {
		return this.templateData;
	}

	public void setTemplateData(String templateData) {
		this.templateData = templateData;
	}

}
