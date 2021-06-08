package com.xmkj.face.jdbc;

import com.xmkj.face.jdbc.jdbcutils.JdbcUtil;
import com.xmkj.face.pojo.Face;

import javax.sql.rowset.serial.SerialBlob;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 作用： mysql的具体操作类
 * @author 钟洪强
 */
public class FaceJdbc {

    /**
     *
     * @param id 姓名
     * @param facEngine 人脸特征码

     * @throws SQLException
     */
    public static void add(int id,byte[] facEngine) throws SQLException {

        Connection connection = JdbcUtil.getConnection();
        String sql = "update criminal_information set face_engine = ? where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        SerialBlob blob = new SerialBlob(facEngine);
        statement.setBlob(1,blob);
        statement.setInt(2,id);
        statement.executeUpdate();
        System.out.println("特征码更新完毕！！");
    }
    public static List<Face> query() throws SQLException, UnsupportedEncodingException {
        List<Face> faceList =  new ArrayList<>();

        Connection connection = JdbcUtil.getConnection();
        String sql = "select id,Crime_name,face_engine from criminal_information";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("Crime_name");
            Blob blob = resultSet.getBlob("face_engine");
            byte[] bytes = blob.getBytes(1, (int) blob.length());
            faceList.add(new Face(id,name,bytes));
        }
        return faceList;
    }

}
