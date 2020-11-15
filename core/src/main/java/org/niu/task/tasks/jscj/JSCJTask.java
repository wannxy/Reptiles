package org.niu.task.tasks.jscj;

import org.niu.task.BaseTask;
import org.niu.task.CanceledToken;
import org.niu.log.XLog;

/**
 * 金色财经爬虫任务
 */
public class JSCJTask extends BaseTask {

    public static String taskName = "金色财经爬虫";
    public static String TAG = "JSCJTask";

    public JSCJTask(int interval) {
        super(interval,taskName);
    }

    @Override
    protected void Run(CanceledToken canceledToken) {
        Api_Lives lives = new Api_Lives();
        while (true){
            try {
                if (canceledToken.isCancel()){
                    break;
                }
                lives.Get();
                Thread.sleep(getInterval());
            } catch (InterruptedException ex) {
                XLog.E(TAG,"[%s] Ex: %s",getTaskName(),ex.getMessage());
            }
        }
    }
}
