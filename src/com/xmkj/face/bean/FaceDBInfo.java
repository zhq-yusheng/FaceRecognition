package com.xmkj.face.bean;

import com.arcsoft.face.FaceFeature;

public class FaceDBInfo {
	//人脸id
	private int faceId;
	//姓名
	private String name;
	//人脸特征值
	private FaceFeature faceFeature;
	public int getFaceId() {
		return faceId;
	}
	public void setFaceId(int faceId) {
		this.faceId = faceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public FaceFeature getFaceFeature() {
		return faceFeature;
	}
	public void setFaceFeature(FaceFeature faceFeature) {
		this.faceFeature = faceFeature;
	}
}
