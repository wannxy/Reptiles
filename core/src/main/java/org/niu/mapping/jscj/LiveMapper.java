package org.niu.mapping.jscj;

import org.niu.bean.jscj.Live;

import java.util.List;

public interface LiveMapper {

    Live getLive(int id);

    List<Live> getLives();

    void insertLive(Live live);
}
