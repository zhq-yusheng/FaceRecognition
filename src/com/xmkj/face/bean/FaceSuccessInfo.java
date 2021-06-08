package com.xmkj.face.bean;
/**
 * 人脸匹配成功返回类
 * @author 石嘉懿
 *
 */

public class FaceSuccessInfo {
	/**
	 * 人脸注册时id
	 */
	private int faceId;
	/**
	 * 人脸注册时姓名
	 */
	private String name;
	/**
	 * 人脸相似程度
	 */
	private int faceSimilarity;
	/**
	 * 性别 未知性别=-1 、男性=0 、女性=1
	 */
	private int sex;
	/**
	 * 年龄
	 */
	private int age;
	/**
	 * 判断是否是罪犯
	 * 是 true 否 false
	 */
	private boolean criminal;
	/**
	 * 当前识别到的人数
	 * 从进入屏幕到出屏幕只算一人
	 */
	private int currentNumber;
	
	
	@Override
	public String toString() {
		return "FaceSuccessInfo [人脸id=" + faceId + ", 姓名=" + name + ", 相似度=" + faceSimilarity + ", 预测性别="
				+ sex + ", 预测年龄=" + age + ", 是否是罪犯=" + criminal + ", 当然识别人数=" + currentNumber + "]";
	}
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
	public int getFaceSimilarity() {
		return faceSimilarity;
	}
	public void setFaceSimilarity(int faceSimilarity) {
		this.faceSimilarity = faceSimilarity;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isCriminal() {
		return criminal;
	}
	public void setCriminal(boolean criminal) {
		this.criminal = criminal;
	}
	public int getCurrentNumber() {
		return currentNumber;
	}
	public void setCurrentNumber(int currentNumber) {
		this.currentNumber = currentNumber;
	}

}
