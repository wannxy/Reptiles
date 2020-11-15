package org.niu.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XLog {

    private static int level = 3;

    public static void E(String TAG,String format,Object... objects){
        if (level >= 1){
            System.err.printf(String.format("E:/ %s %s %s \r\n",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),TAG,format),objects);
        }
    }

    public static void W(String TAG,String format,Object... objects){
        if (level >= 2){
            System.out.printf(String.format("W:/ %s %s %s \r\n",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),TAG,format),objects);
        }
    }

    public static void I(String TAG,String format,Object... objects){
        if (level >= 3){
            System.out.printf(String.format("I:/ %s %s %s \r\n",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),TAG,format),objects);
        }
    }
}
