package com.xmkj.face.jdbc.jdbcutils;

import java.sql.*;

/**
 * mysql的工具类
 *@author ：钟洪强
 */

public class JdbcUtil {
    public static Connection connection;
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.  getConnection("jdbc:mysql://192.168.1.107:3306/face?characterEncoding=UTF-8&serverTimezone=GMT&useUnicode=true", "root", "123456");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  static Connection getConnection(){
        return connection;
    }

    public   static void close(Connection con, PreparedStatement pst){
        close(con,null,pst);
    }
    public   static void close(Connection con, ResultSet resultSet, PreparedStatement pst){
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(pst!=null){
            try {
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
