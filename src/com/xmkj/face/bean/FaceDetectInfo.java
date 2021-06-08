package com.xmkj.face.bean;
/**
 * 人脸识别返回信息
 * @author 石嘉懿
 *
 */
public class FaceDetectInfo {
	/**
	 * 性别 未知性别=-1 、男性=0 、女性=1
	 */
	private int sex;
	/**
	 * 年龄
	 */
	private int age;
	/**
	 * 系统生成id
	 * 人脸从进入识别范围到出去id不会发生改变
	 * 会随着人脸识别人数增加而增加
	 */
	private int systemId;
	
	
	
	
	@Override
	public String toString() {
		return "FaceDetectInfo [sex=" + sex + ", age=" + age + ", systemId=" + systemId + "]";
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
	public int getSystemId() {
		return systemId;
	}
	public void setSystemId(int systemId) {
		this.systemId = systemId;
	}
	
}
