package org.niu.task.tasks.jscj;

import org.junit.jupiter.api.Test;
import org.niu.task.CanceledToken;

import static org.junit.jupiter.api.Assertions.*;

class JSCJTaskTest {

    @Test
    void run() {
        JSCJTask task = new JSCJTask(1000);
        CanceledToken canceledToken = new CanceledToken();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(500);
                canceledToken.Canceled();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        task.Run(canceledToken);
    }
}