package com.xmkj.face.demo;

import org.opencv.core.Scalar;

import com.xmkj.face.FaceUtil;
import com.xmkj.face.bean.InitBean;

/**
 * 人脸引擎常用配置
 * @author 石嘉懿
 *
 */
public class FaceConfigure {

	public static void main(String[] args) {
		 //单例模式获取人脸工具类
        FaceUtil faceInstance = FaceUtil.getInstance();
        //初始化
        InitBean ib = new InitBean();
        /**
         * 	设置最大同框能识别的人脸数
         * 	默认为10
         * 	最大50
         * 	最小0
         */
        ib.setMaxFaceNum(20);
        /**
         * 	设置人脸相似度多少对比成功
         * 	默认 90
         * 	最大 100
         * 	最小 50
         */
        ib.setFaceSimilarity(92);
        /**
         * 	设置识别到人脸后是否保存人脸照片
         * 	默认 false
         */
        ib.setSaveImages(true);
        /**
         * 	设置保存人脸照片的路径
         * 	默认 C:\\1
         */
        ib.setSaveImagesUrl("D:\\1");
        /**
         * 	设置像素大于多少的照片才保存
         * 	默认100
         * 	最小0
         * 	最大 1080
         */
        ib.setSaveImageSize(50);
        /**
         * 	设置人脸识别到人后方框的颜色
         * 	默认 new Scalar(17, 237, 17) 绿色
         * 	颜色为BGR 注意不是RGB
         */
        ib.setRecognitionColor(new Scalar(25, 27, 17));
        /**
         * 	设置人脸对比成功后方框颜色
         * 	默认new Scalar(42, 9, 209)红色
         * 	颜色为BGR 注意不是RGB
         */
        ib.setContrastColor(new Scalar(42, 9, 209));
        
        //人脸识别引擎初始化加入配置信息
        @SuppressWarnings("static-access")
		int code = faceInstance.init(ib);
        //成功时状态码为0
        System.out.println("状态码 = " + code);
	}

}
