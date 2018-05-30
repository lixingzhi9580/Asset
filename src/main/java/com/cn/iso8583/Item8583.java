package com.cn.iso8583;

public class Item8583 {
	private String fieldId;
	private String propertyName;
	private String lengthType;
	private String dataType;
	private String length;
	private String alignMode;
	private String varType;
	private String convertor;

	public String getConvertor() {
		return convertor;
	}

	public void setConvertor(String convertor) {
		this.convertor = convertor;
	}
	
	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getLengthType() {
		return lengthType;
	}

	public void setLengthType(String lengthType) {
		this.lengthType = lengthType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getAlignMode() {
		return alignMode;
	}

	public void setAlignMode(String alignMode) {
		this.alignMode = alignMode;
	}

	public String getVarType() {
		return varType;
	}

	public void setVarType(String varType) {
		this.varType = varType;
	}

	@Override
	public String toString() {
		return "Item8583 [fieldId=" + fieldId + ", propertyName="
				+ propertyName + ", lengthType=" + lengthType + ", dataType="
				+ dataType + ", length=" + length + ", alignMode=" + alignMode
				+ ", varType=" + varType + "]";
	}

}
