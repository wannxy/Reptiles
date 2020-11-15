package org.niu.result;

public abstract class Result {
    private int code;
    private Msg msg;
    private Object data;

    public Result() {
        code = 0;
        msg = Msg.SUCC;
    }

    public Result(Object data) {
        this();
        this.data = data;
    }

    public Result(int code, Msg msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public Msg getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg=" + msg +
                ", data=" + data +
                '}';
    }

    enum Msg{
        SUCC,
        FAIL
    }
}
