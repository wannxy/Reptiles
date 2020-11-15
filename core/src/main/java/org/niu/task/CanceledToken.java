package org.niu.task;

public class CanceledToken {

    private boolean cancel = false;

    public void Canceled(){
        cancel = true;
    }

    public boolean isCancel(){
        return cancel;
    }

}
