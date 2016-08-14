package eg.edu.alexu.csd.ds.maze.cs45_65;

public class LLQueue implements MyQueue{
	private MyLinkedList list = new DLinkedList();
	
	public void enqueue(Object item){
		list.add(item);
	}
	
	public Object dequeue(){
		if (list.isEmpty())
			throw new IndexOutOfBoundsException();
		Object temp = list.get(0);
		list.remove(0);
		return temp;
	}
	
	public boolean isEmpty(){
		return list.isEmpty();
	}
	
	public int size(){
		return list.size();
	}
}