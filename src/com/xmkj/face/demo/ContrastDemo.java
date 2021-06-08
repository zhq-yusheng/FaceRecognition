package com.xmkj.face.demo;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xmkj.face.jdbc.FaceJdbc;
import com.xmkj.face.pojo.Face;
import com.xmkj.face.redis.RedisUtil;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import com.xmkj.face.FaceUtil;
import com.xmkj.face.WindowUtil;
import com.xmkj.face.bean.FaceSuccessInfo;
import com.xmkj.face.bean.InitBean;
import org.opencv.videoio.Videoio;

/**
  * 人脸注册和人脸对比
 *  @author 石嘉懿
 *
 */
public class ContrastDemo {
    //性别异常的id
    private static Set<Integer> personSets = new HashSet<Integer>();

	public static void main(String[] args) throws UnsupportedEncodingException, SQLException {
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
        //拉流
        //判断摄像头是否未找到
        if(videoCapture == null) {return;}
        videoCapture.set(Videoio.CAP_PROP_FPS, 60);
        
        //人脸注册
        //FaceUtil.faceFileRegister(1, "钟洪强", "D:\\1\\1.jpg");



        List<Face> query = FaceJdbc.query();
        for (Face face : query) {
            faceInstance.faceByteRegister(face.getId(),face.getName(),face.getFaceEngine());
        }

        // 初始redis的值
        RedisUtil.inint();

        List<Integer> ids = new ArrayList<>();

        //通过死循环去读摄像头的帧
        while (true) {
            //Mat对象用于保存帧
            Mat img = new Mat();
            //videoCapture.read方法读一帧 把图像写到Mat里
            if (videoCapture == null || !videoCapture.read(img)) {
                System.out.println("未找到相机");
                break;
            }
            //调用人脸对比方法传入当前画面

            try{
            @SuppressWarnings("static-access")
                List<FaceSuccessInfo> faceEngine = faceInstance.faceEngine(img);
                if(faceEngine.size()>0) {
                    for (FaceSuccessInfo faceSuccessInfo : faceEngine) { // 进行遍历查找犯罪的人的信息

                        if (faceSuccessInfo.isCriminal()) { // 判断是否是罪犯
                            int id = faceSuccessInfo.getFaceId();
                            if (!ids.contains(id)) { // 判断是否已经被识别过了的人 取反是代表没被识别过
                                List<String> redisIds = RedisUtil.geIds(); // 获取到redis中的数组
                                if(redisIds != null && !redisIds.contains(id+"&2")){
                                    RedisUtil.addIds(id+"&1");  // 将id放进redis里面
                                    ids.add(id);
                                    System.out.println("添加了一个罪犯信息");
                                }else {
                                    System.out.println("此罪犯已识别到redis中");
                                }

                            } else {
                                System.out.println("信息已经在redis中！！");
                            }
                        }
                    }
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            faceThread(faceEngine);
                        }catch (Exception e){}
                    }
                }).start();

                System.out.println(faceEngine);
                //识别结果在窗体上显示
            }catch (NullPointerException e){
            }

            windowInstance.showWindow(img, "监控1", 0, 0);
        }
	}


    private static int n1 = 0;

	public static void faceThread(List<FaceSuccessInfo> faceEngine){
        try{
            if(faceEngine.size() >0 ) {
                for (FaceSuccessInfo fsi : faceEngine) { // 进行遍历查找犯罪的人的信息
                    //判断是否有没获取到性别的人

                   //if(faceSuccessInfo.getCurrentNumber()+1 > n1){
                        //上次没获取到性别的人脸
                        int mycn =  fsi.getCurrentNumber()+1;
                        if(personSets.contains(mycn) && fsi.getSex() != -1 && fsi.getAge() != -1){
                            String msg = fsi.getAge() + "&" + fsi.getSex();
                            RedisUtil.addMap(msg);
                            System.out.println("二次追加" + mycn);
                            //移除
                            personSets.remove(mycn);
                            System.out.println(personSets);
                        }else if(!fsi.isCriminal()) { // 判断是否是罪犯
                            //判断人数是否增加
                            if(mycn > n1) {
                                RedisUtil.addCount(fsi.getCurrentNumber()+1); // 添加识别到的人数
                                if(fsi.getSex() != -1 && fsi.getAge() != -1){
                                    String msg = fsi.getAge() + "&" + fsi.getSex();
                                    RedisUtil.addMap(msg);
                                    //System.out.println("添加了一个未犯罪的人");
                                }else{
                                    //System.out.println("当前未获取人脸数"+personList.size());
                                    System.out.println("性别-1的id"+mycn);
                                    personSets.add(mycn);
                                }
                                n1 = fsi.getCurrentNumber()+1;

                            }
                        }

                    //}
                }
            }
        }catch (Exception e){
        }
    }
}
