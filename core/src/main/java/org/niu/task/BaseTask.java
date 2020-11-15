package org.niu.task;

import org.niu.log.XLog;

/**
 * 基础任务模板
 */
public abstract class BaseTask implements Task {

    public static String TAG = "BaseTask";
    private String taskName = "";
    private Thread thread = null;
    private long time = 0;
    private int interval = 0;
    private CanceledToken canceledToken;

    /**
     * 构造一个任务
     */
    public BaseTask(String taskName) {
        this.taskName = taskName;
        time = System.currentTimeMillis();
        canceledToken = new CanceledToken();
    }

    /**
     * 构造一个任务
     * @param interval 任务执行间隔
     */
    public BaseTask(int interval,String taskName) {
        this(taskName);
        this.interval = interval;
    }

    /**
     * 获得任务名
     * @return
     */
    @Override
    public String getTaskName() {
        return taskName;
    }

    /**
     * 获得当前任务包裹的线程的执行状态
     * @return 当前任务内线程的状态
     */
    @Override
    public Thread.State getState() {
        if (thread == null){
           return Thread.State.TERMINATED;
        }
        return thread.getState();
    }

    /**
     * 启动任务
     */
    @Override
    public void StartUp() {
        if (thread != null){
            Stop();
            thread =  null;
        }
        canceledToken = new CanceledToken();
        thread = new Thread(()->{
            _Run(canceledToken);
        });
        thread.start();
        XLog.I(TAG,"[ %s ] 任务已启动！",getTaskName());
    }

    /**
     * 暂停任务
     */
    @Override
    public void Stop() {
        if (thread != null) {
            switch (thread.getState()) {
                case BLOCKED:
                    thread.interrupt();
                case WAITING:
                    thread.interrupt();
                case TIMED_WAITING:
                    thread.interrupt();
                case RUNNABLE:
                    canceledToken.Canceled();
                case TERMINATED:
                    break;
            }
            XLog.I(TAG,"[ %s ] 任务已响应终止请求。。。正在终止！",getTaskName());
        }
    }

    /**
     * 将自己从任务容器内移除
     */
    public void PopMe(){
        TaskManager.getManager().removeTaskAtName(taskName);
        XLog.I(TAG,"[ %s ] 任务已从容器内移除！",getTaskName());
    }

    /**
     * 执行体代理，方便执行前后插入内容
     * @param canceledToken 取消token
     */
    private void _Run(CanceledToken canceledToken){
        Run(canceledToken);
        PopMe();
    }

    /**
     * 任务执行入口
     * @param canceledToken 取消令牌
     */
    protected abstract void Run(CanceledToken canceledToken);

    /**
     * 获得任务的执行间隔
     * @return
     */
    protected int getInterval(){
        return interval;
    }

    /**
     * 获得任务的启动时间
     * @return
     */
    @Override
    public long getStartTime() {
        return time;
    }
}
