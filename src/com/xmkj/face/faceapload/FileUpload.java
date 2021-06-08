package com.xmkj.face.faceapload;

import com.xmkj.face.FaceUtil;
import com.xmkj.face.bean.InitBean;
import com.xmkj.face.jdbc.FaceJdbc;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;


public class FileUpload {
    public static void main(String[] args) throws SQLException {
        String filePath = "C:\\images";
        File file = new File(filePath);
        FaceUtil faceInstance = FaceUtil.getInstance();

        InitBean ib = new InitBean();
        //人脸识别引擎初始化加入配置信息
        faceInstance.init(ib);

        String[] list = file.list();
        for (String s : list) {
            String newPath = file + "\\" + s;
            String name = s.substring(0, s.length() - 4);
            byte[] faceEngine = faceInstance.getFaceEngine(newPath);
            System.out.println(name + "："+ Arrays.toString(faceEngine));
            FaceJdbc.add(Integer.parseInt(name),faceEngine);

        }
    }
}
