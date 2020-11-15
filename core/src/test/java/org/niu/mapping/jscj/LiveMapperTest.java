package org.niu.mapping.jscj;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.*;
import org.mybatis.BatisHelper;
import org.niu.bean.jscj.Live;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LiveMapperTest {

    private static SqlSession session;
    private static LiveMapper mapper;

    @BeforeAll
    static void setSession() {
        session = BatisHelper.getHelper().getSession();
        mapper = session.getMapper(LiveMapper.class);
    }

    @AfterAll
    static void closeSession() {
        session.close();
    }

    @Test
    void getLive() {
        Live live = mapper.getLive(1);
        System.out.println(live);
    }

    @Test
    void getLives() {
        List<Live> lists = mapper.getLives();
        for (Live live : lists){
            System.out.println(live);
        }
    }
}