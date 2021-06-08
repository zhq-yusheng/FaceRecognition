package com.xmkj.face;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.EngineConfiguration;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.FunctionConfiguration;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import com.xmkj.face.bean.FaceDB;
import com.xmkj.face.bean.FaceDBInfo;
import com.xmkj.face.bean.FaceDetectInfo;
import com.xmkj.face.bean.FaceSuccessInfo;
import com.xmkj.face.bean.InitBean;

/**
 * 工具类只供教学使用，请勿用于商业用途
 * @author 训码科技
 *
 */
public class FaceUtil {
	
	private static FaceEngine faceEngine = null;
	private static FaceUtil faceTool = null;
	//默认人脸匹配度
	private static int FACE_SIMILARITY = 95;
	//识别过后对人脸的采集
	private static boolean saveImage = false;
	//识别成功过后保存场景
	private static boolean saveImagesAndScene = true;
	//采集过后保存到哪个文件夹
	private static String saveImageUrl = "";
	//当前系统保存人脸的数量
	private static int saveNumber = 1;
	//多大尺寸的人脸才保存
	private static int saveImageSize;
	//人脸识别成功颜色
	private static Scalar recognitionColor;
	//人脸对比成功颜色
	private static Scalar contrastColor;
	
	private FaceUtil() {}
	/**
	 * 单例模式获取人脸工具类对象
	 * @author 训码科技 石嘉懿
	 * @date 2021年1月18日
	 * @return RfidTool  
	 * @throws
	 */
	public static FaceUtil getInstance() {
		if (faceTool == null) {
			faceTool = new FaceUtil();
		}
		return faceTool;
	}
	
	/**
	 * 激活人脸识别引擎（需要联网）
	 * 注意：只需要执行一次，最好放在main方法执行一次就可以了
	 * @author 训码科技 石嘉懿
	 * @date 2021年1月14日
	 * @return boolean  
	 * @throws
	 */
	public static int faceActivation(String appId,String sdkKey){
		faceEngine = new FaceEngine();
        //激活引擎
        int errorCode = faceEngine.activeOnline(appId, sdkKey);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            return errorCode;
        }else {
        	return errorCode;
        }
	}
	

	/**
	 * 人脸识别引擎初始化
	 * @param maxFaceNum
	 * 引擎最多能检测出的人脸数
	 * @param detectMode
	 * 检测模式
	 * @param faceSimilarity
	 * 两个人脸匹配通过的相似程度 0-100 默认95
	 * DetectMode.ASF_DETECT_MODE_IMAGE 图片
	 * DetectMode.ASF_DETECT_MODE_VIDEO 视频
	 * @param saveImages 
	 * 识别成功过后是否保存图片
	 * @param saveImagesUrl
	 * 保存图片的路径
	 * @author 训码科技 石嘉懿
	 * @date 2021年1月14日
	 * @return String 成功或失败信息
	 * @throws 
	 */
	public static int init(InitBean initBean){
		saveImage = initBean.isSaveImages();
		saveImageUrl = initBean.getSaveImagesUrl();
		saveImageSize = initBean.getSaveImageSize();
		saveImagesAndScene = initBean.isSaveImagesAndScene();
		recognitionColor = initBean.getRecognitionColor();
		contrastColor = initBean.getContrastColor();
		faceEngine = new FaceEngine();
		//引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(initBean.getDetectMode());
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(initBean.getMaxFaceNum());
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);
        
        FACE_SIMILARITY = initBean.getFaceSimilarity();
        //初始化引擎
        int errorCode = faceEngine.init(engineConfiguration);
        if (errorCode != ErrorInfo.MOK.getValue()) {
            return errorCode;
        }else {
        	return errorCode;
        }
	}
	
	/**
	 * 未优化版本
	 * @author 训码科技 石嘉懿
	 * @date 2021年4月26日
	 * @return BufferedImage  
	 * @param mat
	 * @return  
	 * @throws
	 */
	/*private static BufferedImage matToBufferImag(Mat mat) {
        return (BufferedImage) HighGui.toBufferedImage(mat);
	}*/
	
	/**
	 * Mat转换成BufferedImage
	 *
	 * @param matrix
	 *            要转换的Mat
	 * @param fileExtension
	 *            格式为 ".jpg", ".png", etc
	 * @return
	 */
	public static BufferedImage matToBufferImag (Mat matrix) {
		MatOfByte mob = new MatOfByte();
		Imgcodecs.imencode(".png", matrix, mob);
		byte[] byteArray = mob.toArray();
		BufferedImage bufImage = null;
		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bufImage;
	}
	
	 private static String getRandomName(){
		 SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		 String uuid = UUID.randomUUID().toString().replace("-","");
        String uuidFileName="-xmkj-"+df.format(new Date())+"-"+uuid.substring(0,20);
        return uuidFileName;
    }
    
	/**
	 * 图片截取并保存
	 * @author 训码科技 石嘉懿
	 * @date 2021年1月20日
	 * @return void  
	 * @param images 
	 * @throws
	 */
	private static void imageShear(Mat image,com.arcsoft.face.Rect faceRect) {
		try {
			int x = faceRect.getLeft();
			if(x < 0)
				x = x * -1;
			int y = faceRect.getTop();
			if(y < 0)
				y = y * -1;
			int width = faceRect.getRight() - x;
			int height = faceRect.getBottom() - y;
			
			//判断截图不能超过图片大小
			if(image.cols()<(x+width))
				width = image.cols() - x;
			if(image.rows() <(y+height))
				height = image.rows() - y;
			//小于规定尺寸不要
			if(width < saveImageSize || height < saveImageSize) {
				saveNumber--;
				return;
			}
			Rect rect = new Rect(x,y,width,height);  
	        Mat roiImg = new Mat(image,rect); 
	        //设置日期格式
	        SimpleDateFormat df = new SimpleDateFormat("HHmmss");
	        // new Date()为获取当前系统时间
	        String nowdata = df.format(new Date());
	        //克隆一张
	        Mat dstImage = roiImg.clone();
	        //写出人头图片
	        //避免重复
	        String fileName = saveImageUrl +"\\"+saveNumber+getRandomName()+".jpg";
	        Imgcodecs.imwrite(fileName, dstImage);
	        if(saveImagesAndScene) {
	        	String fileName1 = saveImageUrl +"\\"+saveNumber+getRandomName()+".jpg";
		        //保存当前场景图片
		        Imgcodecs.imwrite(fileName1, image);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 人脸检测
	 * @author 训码科技 石嘉懿
	 * @date 2021年1月18日
	 * @return List<FaceDetectInfo> 人脸检测数据  
	 * @throws
	 */
	public static List<FaceDetectInfo> detectFaces(Mat mat) {
		List<FaceDetectInfo> list = new ArrayList<FaceDetectInfo>();
		//人脸检测
	    ImageInfo imageInfo = ImageFactory.bufferedImage2ImageInfo(matToBufferImag(mat));
	    List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
	    faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
	    //人脸属性检测
        FunctionConfiguration configuration = new FunctionConfiguration();
        //年龄
        configuration.setSupportAge(true);
        //性别
        configuration.setSupportGender(true);
        faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);
        //年龄
	    List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
		//性别
	    List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
	    faceEngine.getAge(ageInfoList);
	    faceEngine.getGender(genderInfoList);
	    for (int i = 0; i<faceInfoList.size(); i++) {
	    	FaceDetectInfo fdi = new FaceDetectInfo();
	    	if(faceInfoList.size() <= 0 ||ageInfoList.size() <= 0 || faceInfoList.size() <= 0)
	    		break;
	    	
	    	fdi.setAge(ageInfoList.get(i).getAge());
	    	fdi.setSex(genderInfoList.get(i).getGender());
	    	fdi.setSystemId(faceInfoList.get(i).getFaceId());
	    	list.add(fdi);
	    	//绘制方框
	    	Imgproc.rectangle(mat, 
	    			new Point(faceInfoList.get(i).getRect().getLeft(),faceInfoList.get(i).getRect().getTop()), 
	    			new Point(faceInfoList.get(i).getRect().getRight(),faceInfoList.get(i).getRect().getBottom()),  
	    			recognitionColor, 2);
	    	
	    	
	    	if(saveImage && ((Integer.valueOf(faceInfoList.get(i).getFaceId()) + 1) > saveNumber)) {
	    		//保存量
	    		saveNumber++;
				imageShear(mat,faceInfoList.get(i).getRect());
	    	}
	    	
		}
	    return list;
	}
	
	/**
	 * 获取人脸特征信息
	 * @author 训码科技 石嘉懿
	 * @date 2021年1月19日
	 * @return byte[]  
	 * 人脸特征信息
	 * @param fileUrl
	 * 人脸地址
	 * @return 返回数据及成功，返回null失败
	 * @throws
	 */
	public static byte[] getFaceEngine(String fileUrl) {
		ImageInfo imageInfo = getRGBData(new File(fileUrl));
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        int errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        if(errorCode != 0)
        	return null;
        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        if(errorCode != 0)
        	return null;
        return faceFeature.getFeatureData();
	}
	
	/**
	 * 文件形式人脸注册
	 * @author 训码科技 石嘉懿
	 * @date 2021年1月18日
	 * @return int 0成功其它失败
	 * @throws
	 */
	public static int faceFileRegister(int id,String name,String fileUrl) {
		 //人脸检测
        ImageInfo imageInfo = getRGBData(new File(fileUrl));
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        int errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        if(errorCode != 0)
        	return errorCode;
        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        if(errorCode != 0)
        	return errorCode;
        
        FaceDBInfo fdbi = new FaceDBInfo();
        fdbi.setFaceId(id);
        fdbi.setName(name);
        //添加特征信息
        fdbi.setFaceFeature(faceFeature);
        FaceDB.facedb.add(fdbi);
        return 0;
	}
	
	/**
	 * 特征码人脸注册
	 * @author 训码科技 石嘉懿
	 * @date 2021年1月19日
	 * @return int  
	 * @param id 人脸id
	 * @param name 姓名
	 * @param data 人脸特征
	 * @return  0成功
	 * @throws
	 */
	public static int faceByteRegister(int id,String name,byte[] data) {
        FaceFeature faceFeature = new FaceFeature();
        faceFeature.setFeatureData(data);
        
        FaceDBInfo fdbi = new FaceDBInfo();
        fdbi.setFaceId(id);
        fdbi.setName(name);
        //添加特征信息
        fdbi.setFaceFeature(faceFeature);
        FaceDB.facedb.add(fdbi);
        return 0;
	}
	
	/**
	 * 人脸对比 把人脸与注册库人脸进行对比
	 * @author 训码科技 石嘉懿
	 * @date 2021年1月18日
	 * @return List<FaceDB> 匹配成功的人脸信息  为空代表没有识别到人脸
	 * @throws
	 */
	public static List<FaceSuccessInfo> faceEngine(Mat mat) {
		List<FaceSuccessInfo> list = new ArrayList<FaceSuccessInfo>();
		
		ImageInfo imageInfo = ImageFactory.bufferedImage2ImageInfo(matToBufferImag(mat));
		
		List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
		//人脸检测
	    int errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(),imageInfo.getImageFormat(), faceInfoList);
	    //年龄
	    List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
		//性别
	    List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
	    if(faceInfoList.size() <= 0)
	    	return null;
	    //特征提取2
	    FaceFeature faceFeature = new FaceFeature();
	    //循环遍历识别到的人脸
	    for (int i = 0; i<faceInfoList.size(); i++) {
	    	errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(i), faceFeature);
	 	    FaceFeature targetFaceFeature = new FaceFeature();
	        targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
	        boolean faceIs = false;
	        //得到复制好了的常量变量
	 		for (int j = 0; j < FaceDB.facedb.size(); j++) {
	 			FaceSimilar faceSimilar = new FaceSimilar();
	 		    errorCode = faceEngine.compareFaceFeature(targetFaceFeature, FaceDB.facedb.get(j).getFaceFeature(), faceSimilar);
	 		    //人脸属性检测
	 	        FunctionConfiguration configuration = new FunctionConfiguration();
	 	        //年龄
	 	        configuration.setSupportAge(true);
	 	        //性别
	 	        configuration.setSupportGender(true);
	 	        errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);
	 	    	faceEngine.getAge(ageInfoList);
	 	    	faceEngine.getGender(genderInfoList);
	 		    //小数变百分比
		    	int number = Integer.parseInt(new DecimalFormat("0").format(faceSimilar.getScore()*100));
		    	if(number > FACE_SIMILARITY) {
		    		faceIs = true;
		    		 //绘制方框
		        	Imgproc.rectangle(mat, 
		        			new Point(faceInfoList.get(i).getRect().getLeft(),faceInfoList.get(i).getRect().getTop()), 
		        			new Point(faceInfoList.get(i).getRect().getRight(),faceInfoList.get(i).getRect().getBottom()),  
		        			contrastColor, 2);
		        	
		        	Imgproc.putText(mat, 
		        			"ID:"+FaceDB.facedb.get(j).getFaceId(), 
		        			new Point(faceInfoList.get(i).getRect().getLeft(), faceInfoList.get(i).getRect().getBottom()+30),
		        			1,
		        			1.0,
		        			contrastColor);
		        	
		        	Imgproc.putText(mat, 
		        			"Similarity:"+number+"%", 
		        			new Point(faceInfoList.get(i).getRect().getLeft(), faceInfoList.get(i).getRect().getBottom()+50),
		        			1,
		        			1.0,
		        			contrastColor);
		        	if(ageInfoList.size() > i) {
		        		Imgproc.putText(mat, 
			        			"age:"+ageInfoList.get(i).getAge(), 
			        			new Point(faceInfoList.get(i).getRect().getLeft(), faceInfoList.get(i).getRect().getBottom()+70),
			        			1,
			        			1.0,
			        			contrastColor);
		        	}
		        	if(genderInfoList.size() > i) {
		        		Imgproc.putText(mat, 
			        			"sex:"+genderInfoList.get(i).getGender(), 
			        			new Point(faceInfoList.get(i).getRect().getLeft(), faceInfoList.get(i).getRect().getBottom()+90),
			        			1,
			        			1.0,
			        			contrastColor);
		        	}
		        	
		        	//添加返回数据
		        	FaceSuccessInfo fsi = new FaceSuccessInfo();
		        	try {
                        fsi.setAge(ageInfoList.get(i).getAge());
                        fsi.setFaceId(FaceDB.facedb.get(j).getFaceId());
                        fsi.setFaceSimilarity(number);
                        fsi.setName(FaceDB.facedb.get(j).getName());
                        fsi.setSex(genderInfoList.get(i).getGender());
                        fsi.setCriminal(true);
                        fsi.setCurrentNumber(faceInfoList.get(i).getFaceId());
                        list.add(fsi);
                    }catch (Exception e){}

		    		break;
		    	}
	 		}
	 		if(!faceIs) {
	    		 //绘制方框
		    	Imgproc.rectangle(mat, 
		    			new Point(faceInfoList.get(i).getRect().getLeft(),faceInfoList.get(i).getRect().getTop()), 
		    			new Point(faceInfoList.get(i).getRect().getRight(),faceInfoList.get(i).getRect().getBottom()),  
		    			recognitionColor, 2);
		    	if(ageInfoList.size() <= 0) {
		    		break;
		    	}
		    	Imgproc.putText(mat, 
		    			"age:"+ageInfoList.get(i).getAge()+"sex:"+genderInfoList.get(i).getGender(), 
		    			new Point(faceInfoList.get(i).getRect().getLeft(), faceInfoList.get(i).getRect().getBottom()+30),
		    			1,
		    			1.0,
		    			contrastColor);
		    	
		    	FaceSuccessInfo fsi = new FaceSuccessInfo();
	        	fsi.setAge(ageInfoList.get(i).getAge());
	        	fsi.setSex(genderInfoList.get(i).getGender());
	        	fsi.setCriminal(false);
	        	fsi.setCurrentNumber(faceInfoList.get(i).getFaceId());
	        	list.add(fsi);
	    	}
		}
		return list;
	}

}
