package com.mgq.kafka.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DataPackage implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 监控对象（*）
	 */
	private String moId;
	/**
	 * 采集时间（*），format:yyyyMMddHHmmss
	 */
	private Long collectTime;
	/**
	 * 接收时间（*）
	 */
	private Long reveciveTime;

	/**
	 * 采集间隔时间（毫秒）
	 */
	private Long interval;

	public Long getInterval() {
		return interval;
	}

	public void setInterval(Long interval) {
		this.interval = interval;
	}

	/**
	 * 监控对象KPI关系
	 */
	private String relation;
	/**
	 * 监控对象类型
	 */
	private String moType;
	/**
	 * 采集方式
	 */
	private String collectMethod;

	/**
	 * KPI数据
	 */
	private Map<String,List<KpiData>> kpiDatas;

	/**
	 * 扩展数据
	 */
	private Map<String,Object> extData;

	public String getMoId() {
		return moId;
	}

	public void setMoId(String moId) {
		this.moId = moId;
	}

	public Long getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Long collectTime) {
		this.collectTime = collectTime;
	}

	public Long getReveciveTime() {
		return reveciveTime;
	}

	public void setReveciveTime(Long reveciveTime) {
		this.reveciveTime = reveciveTime;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getMoType() {
		return moType;
	}

	public void setMoType(String moType) {
		this.moType = moType;
	}

	public String getCollectMethod() {
		return collectMethod;
	}

	public void setCollectMethod(String collectMethod) {
		this.collectMethod = collectMethod;
	}

	public Map<String, List<KpiData>> getKpiDatas() {
		return kpiDatas;
	}

	public void setKpiDatas(Map<String, List<KpiData>> kpiDatas) {
		this.kpiDatas = kpiDatas;
	}

	public Map<String, Object> getExtData() {
		return extData;
	}

	public void setExtData(Map<String, Object> extData) {
		this.extData = extData;
	}

}
