package com.xmkj.face.demo;

import com.xmkj.face.FaceUtil;
import com.xmkj.face.WindowUtil;

/**
 * 人脸识别激活
 * @author 石嘉懿
 *
 */
public class RegisterDemo {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
        //单例模式获取人脸工具类
        FaceUtil faceInstance = FaceUtil.getInstance();
        //激活 只需要激活一次（需要联网）
        faceInstance.faceActivation("C5u2VrsAz447VHHrkZhvVN1cJahwR5f7ibaWL75cw81B","HKpLLnHjQQgNqi4RpD9DJ9TCqDrMqe2wkg6YkEJy4J6w");
	}
}
