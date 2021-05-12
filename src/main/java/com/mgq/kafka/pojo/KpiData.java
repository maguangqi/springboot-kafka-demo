package com.mgq.kafka.pojo;

import java.io.Serializable;
import java.util.Map;

public class KpiData implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * KPI Code（*）
	 */
	private String kpiCode;
	/**
	 * 对象名
	 */
	private String objectName;
	/**
	 * KPI VALUE（*），null &gt; "NA"
	 */
	private Object Data;
	/**
	 * 数据类型（1表示数值型，0表示字符型）
	 */
//	private Integer dataType;
	/**
	 * 是否生成告警
	 */
//	private Boolean generateAlarm;
	/**
	 * 告警内容
	 */
//	private String alarmMessage;
	private Long collectTime;

	/**
	 * KPI 扩展数据
	 */
	private Map<String,Object> extData;

	public String getKpiCode() {
		return kpiCode;
	}
	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public Object getData() {
		return Data;
	}
	public void setData(Object data) {
		Data = data;
	}
//	public Integer getDataType() {
//		return dataType;
//	}
//	public void setDataType(Integer dataType) {
//		this.dataType = dataType;
//	}
//	public Boolean getGenerateAlarm() {
//		return generateAlarm;
//	}
//	public void setGenerateAlarm(Boolean generateAlarm) {
//		this.generateAlarm = generateAlarm;
//	}
//	public String getAlarmMessage() {
//		return alarmMessage;
//	}
//	public void setAlarmMessage(String alarmMessage) {
//		this.alarmMessage = alarmMessage;
//	}
	public Map<String, Object> getExtData() {
		return extData;
	}
	public void setExtData(Map<String, Object> extData) {
		this.extData = extData;
	}
	public Long getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Long collectTime) {
		this.collectTime = collectTime;
	}
}
