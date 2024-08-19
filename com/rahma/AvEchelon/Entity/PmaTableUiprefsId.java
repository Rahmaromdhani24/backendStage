package com.rahma.AvEchelon.Entity;
// Generated Jun 13, 2024, 2:26:21 AM by Hibernate Tools 5.6.7.Final

/**
 * PmaTableUiprefsId generated by hbm2java
 */
public class PmaTableUiprefsId implements java.io.Serializable {

	private String username;
	private String dbName;
	private String tableName;

	public PmaTableUiprefsId() {
	}

	public PmaTableUiprefsId(String username, String dbName, String tableName) {
		this.username = username;
		this.dbName = dbName;
		this.tableName = tableName;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDbName() {
		return this.dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PmaTableUiprefsId))
			return false;
		PmaTableUiprefsId castOther = (PmaTableUiprefsId) other;

		return ((this.getUsername() == castOther.getUsername()) || (this.getUsername() != null
				&& castOther.getUsername() != null && this.getUsername().equals(castOther.getUsername())))
				&& ((this.getDbName() == castOther.getDbName()) || (this.getDbName() != null
						&& castOther.getDbName() != null && this.getDbName().equals(castOther.getDbName())))
				&& ((this.getTableName() == castOther.getTableName()) || (this.getTableName() != null
						&& castOther.getTableName() != null && this.getTableName().equals(castOther.getTableName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUsername() == null ? 0 : this.getUsername().hashCode());
		result = 37 * result + (getDbName() == null ? 0 : this.getDbName().hashCode());
		result = 37 * result + (getTableName() == null ? 0 : this.getTableName().hashCode());
		return result;
	}

}
