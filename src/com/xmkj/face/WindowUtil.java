package com.xmkj.face;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.highgui.ImageWindow;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

/**
 *	关于窗体程序的封装
 * @author 石嘉懿
 *
 */
public class WindowUtil {
	private static WindowUtil fidTool = null;

	private WindowUtil() {}
	/**
	 *  单例模式获取
	 * @author 训码科技 石嘉懿
	 * @date 2021年1月18日
	 * @return RfidTool  
	 * @throws
	 */
	public static WindowUtil getInstance() {
		if (fidTool == null) {
			fidTool = new WindowUtil();
		}
		return fidTool;
	}
	
	/**
	  * 打开相机获取相机实例
	 * @param captureId
	  * 相机id 笔记本默认是0 如果有多个往上加
	 * @author 训码科技 石嘉懿
	 * @date 2021年1月18日
	 * @return VideoCapture  
	 * @throws成功返回对象失败返回null
	 */
	public VideoCapture openCamera(int captureId) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	// 实例化相机
        VideoCapture videoCapture = new VideoCapture();
        // 如果要从摄像头获取视频 则要在 VideoCapture 的构造方法写 0
        if (!videoCapture.open(captureId)) {
        	//失败
            return null;	
        }else {
        	return videoCapture;
        }
	}
	
	/**
	  * 读取本地视频
	 * @param fileUrl
	  * 视频地址 如 G:\video\1.avi
	 * @author 训码科技 石嘉懿
	 * @date 2021年1月18日
	 * @return VideoCapture   
	 * @throws成功返回对象失败返回null
	 */
	public VideoCapture openVideo(String fileUrl) {
	   System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
   	   // 实例化相机
       VideoCapture videoCapture = new VideoCapture();
       // 如果要从摄像头获取视频 则要在 VideoCapture 的构造方法写 0
       if (!videoCapture.open(fileUrl)) {
       	//失败
           return null;	
       }else {
       	return videoCapture;
       }
	}
	
	/**
     * 图像灰度化 有利于提高计算机识别的性能
	 * @paramoldMat
     *  老图像
	 * @author 训码科技 石嘉懿
	 * @date 2021年1月18日
	 * @return Mat 灰度好了的图像  
	 * @throws
	 */
	public Mat cvtColor(Mat mat) {
		 Mat rgb = new Mat();
         // 灰度化
         Imgproc.cvtColor(mat, rgb, Imgproc.COLOR_BGR2RGB);
         return rgb;
	}
	
	/**
	 * 把Mat显示在窗体上
	 * @param mat
	 * 多维数组
	 * @param name
	 * 窗体名
	 * @param x
	 * x坐标
	 * @param y
	 * y坐标
	 * @author 训码科技 石嘉懿
	 * @date 2021年1月18日
	 * @return void  
	 * @throws
	 */
	public void showWindow(Mat mat,String name,int x,int y) {
    	HighGui.imshow(name, mat);
    	HighGui.namedWindow(name, HighGui.WINDOW_AUTOSIZE);
        HighGui.moveWindow(name, x, y);
        HighGui.waitKey(1);
	}

}
