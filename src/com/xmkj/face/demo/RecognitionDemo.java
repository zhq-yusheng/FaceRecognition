package com.xmkj.face.demo;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import com.xmkj.face.FaceUtil;
import com.xmkj.face.WindowUtil;
import com.xmkj.face.bean.FaceDetectInfo;
import com.xmkj.face.bean.InitBean;

/**
 * 人脸识别DEMO
 * @author 石嘉懿
 *
 */
public class RecognitionDemo {
	public static void main(String[] args) {
		//获取窗体工具类
        WindowUtil windowInstance = WindowUtil.getInstance();
        //获取人脸工具类
        FaceUtil faceInstance = FaceUtil.getInstance();
        //实例化人脸配置类
        InitBean ib = new InitBean();
        //人脸识别引擎初始化加入配置信息
        @SuppressWarnings("static-access")
		int code = faceInstance.init(ib);
        //成功时状态码为0
        System.out.println("状态码 = " + code);
        //窗体工具类打开摄像头
        VideoCapture videoCapture = windowInstance.openCamera(0);
        //判断摄像头是否未找到
        if(videoCapture == null) {return;}
        //通过死循环去读摄像头的帧
        while (true) {
            //Mat对象用于保存帧
            Mat img = new Mat();
            //videoCapture.read方法读一帧 把图像写到Mat里
            if (videoCapture == null || !videoCapture.read(img)) {
                System.out.println("未找到相机");
                break;
            }
            //调用人脸识别方法传入当前画面
            @SuppressWarnings("static-access")
			List<FaceDetectInfo> detectFaces = faceInstance.detectFaces(img);
            //识别结果在窗体上显示
            windowInstance.showWindow(img, "监控1", 0, 0);
        }
	}
}
