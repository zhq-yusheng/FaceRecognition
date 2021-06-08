package com.xmkj.face.redis;

import redis.clients.jedis.Jedis;

import java.util.List;


public class RedisUtil {
    private static Jedis jedis;
    static {
        //初始化redis
        jedis = new Jedis("192.168.1.107", 6379,10000);
        jedis.auth("123456");
    }
    public static void addIds(String id)  {
        try {
            jedis.rpush("ids", id);
        }catch (Exception e){}
    }
    public  static  void addCount(int number){
        jedis.set("oneZrs",number+"");
    };

    public  static void addMap(String msg){
        jedis.rpush("person",msg);
        System.out.println("添加一个性别信息");
    }

    public static void inint(){
        jedis.del("ids","person","oneZrs","twoZrs");
        jedis.set("oneZrs","0");
        jedis.set("twoZrs","0");
    }

    public static List<String>  geIds(){
        List<String> ids = null;
        try{
            ids = jedis.lrange("ids", 0, -1);
            return  ids;
        }catch (Exception e){}
        return ids;
    }
}
