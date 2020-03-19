package cn.tedu.naver.DAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Scheduler {
	public void schedule(Digraph digraph) {
        while (true) {
            List<Task> e = new ArrayList<Task>();
            for (Task task : digraph.getTasks()) {
                if (!task.hasExecuted()) {
                    Set<Task> prevs = digraph.getMap().get(task);
                    if (prevs != null && !prevs.isEmpty()) {
                        boolean toAdd = true;
                        for (Task task1 : prevs) {
                            if (!task1.hasExecuted()) {
                                toAdd = false;
                                break;
                            }
                        }
                        if (toAdd) {
                            e.add(task);
                        }
                    } else {
                        e.add(task);
                    }
                }
            }
            if (!e.isEmpty()) {
                for (Task task : e) {
                    if (!task.execute()) {
                        throw new RuntimeException();
                    }
                }
            } else {
                break;
            }
        }
    }
 
    public static void main(String[] args) throws Exception {
        Digraph digraph = new Digraph();
        Task taskA = new Task("A", 0);
        Task taskB = new Task("B", 0);
        Task taskC = new Task("C", 0);
        Task taskD = new Task("D", 0);
        Task taskE = new Task("E", 0);
        Task taskF = new Task("F", 0);
        Task taskG = new Task("G", 0);
        Task taskH = new Task("H", 0);
        digraph.addTask(taskA);
        digraph.addTask(taskB);
        digraph.addTask(taskC);
        digraph.addTask(taskD);
        digraph.addTask(taskE);
        digraph.addTask(taskF);
        digraph.addTask(taskG);
        digraph.addTask(taskH);
        digraph.addEdge(taskF, taskD);
        digraph.addEdge(taskF, taskE);
        digraph.addEdge(taskD, taskA);
        digraph.addEdge(taskE, taskG);
        digraph.addEdge(taskE, taskB);
        digraph.addEdge(taskA, taskC);
        digraph.addEdge(taskA, taskG);
        digraph.addEdge(taskG, taskB);
        digraph.addEdge(taskC, taskH);
        Scheduler scheduler = new Scheduler();
        scheduler.schedule(digraph);
    }
}
