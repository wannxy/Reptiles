package org.niu.task;

import org.niu.task.tasks.jscj.JSCJTask;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 管理任务的各种操作
 */
public class TaskManager {
    private final String Locked = "";
    private static TaskManager manager;
    private final HashMap<String,Task> taskMap;
    private static final Map<String, Class<? extends Task>> classMapping;

    static {// 注册任务
        classMapping = new HashMap<>();
        classMapping.put(JSCJTask.taskName, JSCJTask.class);
    }
    private TaskManager() {
        taskMap = new HashMap<>();
    }

    public static TaskManager getManager(){
        if (manager == null){
            manager = new TaskManager();
        }
        return  manager;
    }

    /**
     * 检查当前进行中的任务数量
     * @return 活动任务数量
     */
    public int taskActiveCount(){
        return taskMap.size();
    }

    /**
     * 启动任务，并将其添加入管理器,如果任务已存在，会停止之前的任务并继续执行新的任务
     * @param task 一个待执行的任务
     */
    public void startTask(Task task){
        if (taskMap.containsKey(task.getTaskName())){
            task.Stop();
            taskMap.remove(task.getTaskName());
        }
        task.StartUp();
        taskMap.put(task.getTaskName(),task);
    }

    /**
     * 停止所有任务
     */
    public void stopAll(){
        for (Object taskName:taskMap.keySet()) {
            stopTaskAtName(taskName.toString());
        }
    }

    /**
     * 停止指定任务
     * @param taskName 任务名
     */
    public void stopTaskAtName(String taskName){
        if (taskMap.containsKey(taskName)){
            taskMap.get(taskName).Stop();
            taskMap.remove(taskName);
        }
    }

    /**
     * 获得给定任务
     * @param taskName 任务名
     * @return 如果不存在返回null
     */
    public Task getTaskAtName(String taskName){
        if (taskMap.containsKey(taskName)){
            return taskMap.get(taskName);
        }
        return null;
    }

    /**
     * 从容器中移除任务，注意，此操作并不会停止此任务
     * 若需停止，请显示调用 StopTaskAtName(String taskName)
     * @param taskName 任务名
     * @return 移除成功返回true，不存在返回false
     */
    public boolean removeTaskAtName(String taskName){
        if (taskMap.containsKey(taskName)){
            taskMap.remove(taskName);
            return true;
        }
        return false;
    }

    /**
     * 获得当前所有正在进行中的任务
     * @return 一个包含正在运行任务的数组
     */
    public Task[] getActiveTasks(){
        return taskMap.values().toArray(new Task[0]);
    }

    /**
     * 获得当前所有任务的状态
     * @return
     */
    public TaskState[] getAllStates(){
        String[] names = getAvailableTasks().keySet().toArray(new String[0]);
        List<TaskState> list = new ArrayList<>(names.length);
        for(String name : names){
            list.add(getTaskState(name));
        }
        return list.toArray(list.toArray(new TaskState[0]));
    }

    /**
     * 检查任务是否正在进行中
     * @param taskName 任务名
     * @return 如果正在运行 true，反之亦然
     */
    public boolean IsRun(String taskName){
        return taskMap.containsKey(taskName);
    }

    /**
     * 检查给定任务的状态
     * @param taskName 任务名
     * @return 特例：TERMINATED 不存在或已经终止，其他看心情把，反正任务都在。
     */
    public TaskState getTaskState(String taskName){
        if (taskName == null || !getAvailableTasks().containsKey(taskName)){
            return new TaskState("Undefined",0, Thread.State.TERMINATED);
        }
        if (IsRun(taskName)){
            Task task = getTaskAtName(taskName);
            return new TaskState(taskName,task.getStartTime(),task.getState());
        }
        return new TaskState(taskName,0, Thread.State.TERMINATED);
    }

    /**
     * 获得所有可用的任务
     * @return 包含所有可用的任务
     */
    public Map<String, Class<? extends Task>> getAvailableTasks(){
        return classMapping;
    }

    /**
     * 调度任务的执行终止等各种操作
     * @param taskName 任务名
     * @param execute 执行动作
     */
    public TaskState Schedule(String taskName,String execute,int interval){
        synchronized (Locked){
            if (getAvailableTasks().containsKey(taskName)){
                Execute(taskName,execute,interval);
            }
        }
        return getTaskState(taskName);
    }

    /**
     * 执行任务
     * @param taskName 任务名
     * @param execute 执行动作
     * @param interval 执行间隔
     */
    public void Execute(String taskName,String execute,int interval){
        try {
            Task task = null;
            switch (execute){
                case "Start":
                    if (!IsRun(taskName)) {
                        task = getAvailableTasks().get(taskName).getConstructor(int.class).newInstance(interval);
                        startTask(task);
                    }
                    break;
                case "Stop":
                    if (IsRun(taskName)){
                        task = getTaskAtName(taskName);
                        task.Stop();
                    }
                    break;
                default:break;
            }
        }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
    }

}
