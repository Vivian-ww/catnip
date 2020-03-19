package cn.tedu.naver.DAG;

public class Task implements Executor{

    private String name;
    private int state;
 
    public Task(String name, int state) {
        this.name = name;
        this.state = state;
    }
 
    public boolean execute() {
        System.out.println("Task"+name);
        state = 1;
        return true;
    }
 
    public boolean hasExecuted() {
        return state == 1;
    }
}
