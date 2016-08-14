package eg.edu.alexu.csd.ds.maze.cs45_65;

import java.util.EmptyStackException;

public class Stack implements MyStack {
	
	private MyLinkedList stack = new SLinkedList();
	
	public void add(int index, Object element) {
		if (index>stack.size() || index < 0)
			throw new RuntimeException();
		stack.add(stack.size()-index, element);
	}
	
	public Object peek() {
		if (stack.isEmpty())
			throw new EmptyStackException();
		return stack.get(0);
	}
	
	public int size(){return stack.size();}
	
	public boolean isEmpty(){return stack.isEmpty();}
	
	public void push(Object element){stack.add(0, element);}	
	
	public Object pop(){
		if (stack.isEmpty())
			throw new EmptyStackException();
		Object value = stack.get(0);
		stack.remove(0);
		return value;
	}
	
}
