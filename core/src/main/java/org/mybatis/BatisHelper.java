package org.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.niu.task.Task;

import java.io.IOException;
import java.io.InputStream;

public class BatisHelper {

    private final String Locked = "";
    private static SqlSessionFactory sqlSessionFactory;
    private static BatisHelper helper;

    static {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BatisHelper getHelper(){
        if (helper == null){
            helper = new BatisHelper();
        }
        return helper;
    }

    public SqlSession getSession(){
        synchronized (Locked){
            return sqlSessionFactory.openSession();
        }
    }

}
