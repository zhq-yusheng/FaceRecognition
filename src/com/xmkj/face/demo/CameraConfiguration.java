package com.xmkj.face.demo;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import com.xmkj.face.WindowUtil;
import com.xmkj.face.bean.FaceSuccessInfo;

/**
 * 相机配置
 * @author 石嘉懿
 *
 */
public class CameraConfiguration {
	public static void main(String[] args) {
		//获取工具类
        WindowUtil windowInstance = WindowUtil.getInstance();
        /**
         * 	打开摄像头
         * 	第二个摄像头windowInstance.openCamera(1);
         * 	第三个摄像头windowInstance.openCamera(2);
         */
        VideoCapture videoCapture = windowInstance.openCamera(0);
        // 设置分辨率
        videoCapture.set(Videoio.CAP_PROP_FRAME_WIDTH, 1600);
        videoCapture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 900);
        // 设置帧率
        videoCapture.set(Videoio.CAP_PROP_FPS, 30);
        
        //通过死循环去读摄像头的帧
        while (true) {
            //Mat对象用于保存帧
            Mat img = new Mat();
            //videoCapture.read方法读一帧 把图像写到Mat里
            if (videoCapture == null || !videoCapture.read(img)) {
                System.out.println("未找到相机");
                break;
            }
            //图像在窗体上显示
            windowInstance.showWindow(img, "监控1", 0, 0);
        }
	}
}
