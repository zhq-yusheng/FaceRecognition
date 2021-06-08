package com.xmkj.face.demo;

import org.opencv.videoio.VideoCapture;

import com.xmkj.face.FaceUtil;
import com.xmkj.face.WindowUtil;
import com.xmkj.face.bean.InitBean;

/**
  * 两种人脸注册方法
 * @author Administrator
 *
 */
public class TwoFaceRegister {
    
   /* 
    * 	两种注册的特点及使用场景
    * 1.第一种直接通过照片方法进行注册
    * 	可以把所有照片放到一个文件夹内
    * 	写个循环遍历文件名进行注册
    * 	适合离线设备使用
    * 2.获取照片特征码进行注册
    * 	获取完特征码，通常会把特征码保存到数据库
    * 	在程序启动时再从数据库获取然后注册
    * 	适合在线设备
    */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
        //获取人脸工具类
        FaceUtil faceInstance = FaceUtil.getInstance();
        //第一种人脸注册
        //FaceUtil.faceFileRegister(999, "小石", "D:\\1.jpg");
        
        //第二种人脸注册
        //获取人脸特征信息
		byte[] faceEngine = faceInstance.getFaceEngine("D:\\1.jpg");
		System.out.println(faceEngine);
        //通过人脸特征信息注册
        faceInstance.faceByteRegister(1000, "小石", faceEngine);

	}
}
