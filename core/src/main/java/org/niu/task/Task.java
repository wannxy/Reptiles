package org.niu.task;

public interface Task {
    /**
     * 启动任务
     */
    void StartUp();

    /**
     * 停止任务
     */
    void Stop();

    /**
     * 获得当前任务的名称
     * @return 任务名
     */
    String getTaskName();

    /**
     * 获得任务的启动时间
     * @return 启动时间的毫秒形式
     */
    long getStartTime();

    /**
     * 获得此任务包装的线程的状态
     * @return 任务内部的线程状态
     */
    Thread.State getState();

}
