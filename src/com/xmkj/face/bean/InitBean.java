package com.xmkj.face.bean;

import org.opencv.core.Scalar;

import com.arcsoft.face.enums.DetectMode;

/**
 * 用于初始化引擎
 * @author 石嘉懿
 *
 */
public class InitBean {
	/**
	 * 最大同屏人脸
	 * 默认10
	 */
	private int maxFaceNum = 10;
	/**
	 * 监测模式
	 * DetectMode.ASF_DETECT_MODE_IMAGE 图片
	 * DetectMode.ASF_DETECT_MODE_VIDEO 视频
	 * 默认DetectMode.ASF_DETECT_MODE_VIDEO
	 */
	private DetectMode detectMode = DetectMode.ASF_DETECT_MODE_VIDEO;
	/**
	 * 人脸对比时匹配程度
	 * 范围 70 - 100
	 * 默认90
	 */
	private int faceSimilarity = 75;
	/**
	 * 人脸检测成功后人脸图片保存
	 * 默认 false
	 */
	private boolean saveImages = true;
	/**
	 * 人脸检测成功后人脸图片保存并同时保存场景信息
	 * 默认 true
	 */
	private boolean saveImagesAndScene = true;
	/**
	 * 人脸检测成功后人脸图片保存路径
	 * 默认C:\\1
	 */
	private String saveImagesUrl = "C:\\images";
	/**
	 * 人脸检测成功后大于多少像素才保存
	 * 默认100
	 */
	private int saveImageSize = 100;
	/**
	 * 人脸识别成功人脸颜色
	 * 默认绿色 new Scalar(17, 237, 17);
	 * BGR
	 */
	private Scalar recognitionColor = new Scalar(17, 237, 17);
	/**
	 * 人脸对比成功人脸颜色
	 * 默认红色 new Scalar(42, 9, 209);
	 * BGR
	 */
	private Scalar contrastColor = new Scalar(42, 9, 209);
	
	
	
	
	
	public Scalar getRecognitionColor() {
		return recognitionColor;
	}
	public void setRecognitionColor(Scalar recognitionColor) {
		this.recognitionColor = recognitionColor;
	}
	public Scalar getContrastColor() {
		return contrastColor;
	}
	public void setContrastColor(Scalar contrastColor) {
		this.contrastColor = contrastColor;
	}
	public boolean isSaveImagesAndScene() {
		return saveImagesAndScene;
	}
	public void setSaveImagesAndScene(boolean saveImagesAndScene) {
		this.saveImagesAndScene = saveImagesAndScene;
	}
	public int getMaxFaceNum() {
		return maxFaceNum;
	}
	public void setMaxFaceNum(int maxFaceNum) {
		this.maxFaceNum = maxFaceNum;
	}
	public DetectMode getDetectMode() {
		return detectMode;
	}
	public void setDetectMode(DetectMode detectMode) {
		this.detectMode = detectMode;
	}
	public int getFaceSimilarity() {
		return faceSimilarity;
	}
	public void setFaceSimilarity(int faceSimilarity) {
		this.faceSimilarity = faceSimilarity;
	}
	public boolean isSaveImages() {
		return saveImages;
	}
	public void setSaveImages(boolean saveImages) {
		this.saveImages = saveImages;
	}
	public String getSaveImagesUrl() {
		return saveImagesUrl;
	}
	public void setSaveImagesUrl(String saveImagesUrl) {
		this.saveImagesUrl = saveImagesUrl;
	}
	public int getSaveImageSize() {
		return saveImageSize;
	}
	public void setSaveImageSize(int saveImageSize) {
		this.saveImageSize = saveImageSize;
	}
}
