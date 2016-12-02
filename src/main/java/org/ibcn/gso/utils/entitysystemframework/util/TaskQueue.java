package org.ibcn.gso.utils.entitysystemframework.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskQueue {

    protected Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();

    public void queue(Runnable task) {
        tasks.add(task);
    }

    public void execute() {
        tasks.forEach(Runnable::run);
        tasks.clear();
    }

}
