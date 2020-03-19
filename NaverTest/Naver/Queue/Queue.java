package cn.tedu.naver.Queue;

import java.util.Stack;

public class Queue {
	Stack<Integer> s1 = new Stack<Integer>();
	Stack<Integer> s2 = new Stack<Integer>();
	//»Î’ª
	public void push(int num){
		s1.push(num);
	}
	//≥ˆ’ª
	public int pop(){
		int out = (Integer) null;
		if(!s2.empty()){
			out=s2.pop();
		}else{
			while(!s1.empty()){
				out=s1.pop();
				s2.push(out);
			}
			if(!s2.empty()){
				out=s2.pop();
			}
		}
		return out;
	}
	
}