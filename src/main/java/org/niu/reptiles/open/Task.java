package org.niu.reptiles.open;

import org.niu.result.CommonResult;
import org.niu.result.Result;
import org.niu.task.TaskManager;
import org.niu.task.TaskState;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Task {

    /**
     * 请求当前所有的可用任务
     * @return 任务名列表
     */
    @GetMapping("/task/names")
    public Result TaskNames(){
        String[] tasks = TaskManager.getManager().getAvailableTasks().keySet().toArray(new String[0]);
        return new CommonResult(tasks);
    }

    /**
     * 请求当前所有可用任务的任务状态
     * @return 状态列表
     */
    @GetMapping("/task/states")
    public Result TaskStates(){
        TaskState[] states = TaskManager.getManager().getAllStates();
        return new CommonResult(states);
    }

    /**
     * 任务调度接口
     * @param taskName 任务名
     * @param execute 对任务执行的操作
     */
    @GetMapping("/task")
    public Result RunTask
            (@RequestParam(value = "taskName",defaultValue = "")String taskName,
             @RequestParam(value = "execute",defaultValue = "") String execute,
             @RequestParam(value = "interval",defaultValue = "0")int interval){
        TaskState taskState = TaskManager.getManager().Schedule(taskName,execute,interval);
        return new CommonResult(taskState);
    }
}
