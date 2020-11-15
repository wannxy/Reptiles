package org.niu.task;

public class TaskState {
    private String name;
    private long time;
    private Thread.State state;

    public TaskState(String name, long time, Thread.State state) {
        this.name = name;
        this.time = time;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public Thread.State getState() {
        return state;
    }

    @Override
    public String toString() {
        return "TaskState{" +
                "name='" + name + '\'' +
                ", time=" + time +
                ", state=" + state +
                '}';
    }
}
