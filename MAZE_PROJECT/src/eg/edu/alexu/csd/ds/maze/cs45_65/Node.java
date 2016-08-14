package eg.edu.alexu.csd.ds.maze.cs45_65;

public class Node {
	private Object value;
	private Node next;
	
	public Node(Object element, Node x){
		this.value = element;
		this.next = x;
	}
	
	public Object getValue(){return value;}
	
	public Node getNext(){return next;}
		
	public void setValue(Object newValue){ value =  newValue;}
	
	public void setNext(Node newNext) { next = newNext;}	
}
