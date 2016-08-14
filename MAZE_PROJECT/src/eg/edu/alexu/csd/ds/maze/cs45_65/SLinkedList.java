package eg.edu.alexu.csd.ds.maze.cs45_65;

public class SLinkedList implements MyLinkedList {
	
	private Node head = null;
	private int size = 0;
	
	@Override
	public void add(int index, Object element){
		if (index>size || index<0)
			throw new RuntimeException("List out of bounds");
		else{
			
			if (index==0){
				Node x = new Node(element, head);
				head=x;
			}
			else{
				Node x = head; 
				for (int i=0 ; i<index-1 ; i++)
					x = x.getNext();
				Node newNode = new Node(element, x.getNext());
				x.setNext(newNode);
			}
			size++;
		}
	}
	
	@Override
	public void add(Object element){
		Node x = head;
		Node newNode = new Node(element, null);
		if (head == null)
			head = newNode;
		else{
			for (int i=0 ; i<size-1 ; i++)
				x=x.getNext();
			x.setNext(newNode);
		}
		size++;
	}
	
	@Override
	public Object get(int index){
		if (index>size-1 || index<0)
			throw new RuntimeException("List out of bounds");
		else{
			Node x = head;
			for (int i=0 ; i<index ; i++)
				x=x.getNext();
			return x.getValue();
		}
	}
	
	@Override
	public void set(int index, Object element){
		if (index>size-1 || index<0){
			throw new RuntimeException("List out of bounds");
		}
		else{
			Node x = head, y=head;
			for (int i=0 ; i<index ; i++)
				x = x.getNext();
			Node newNode = new Node(element, x.getNext());
			if (index == 0){
				head = newNode;
			}
			else{
				for (int i=0 ; i<index-2 ; i++)
					y=y.getNext();
				y.setNext(newNode);
			}
		}
	}
	
	@Override
	 public void clear(){
		 head = null;
		 size = 0;
	 }
	
	 @Override
	public boolean isEmpty(){
		if (head==null)
			return true;
		return false;
	}

	
	@Override
	public void remove(int index){
			if (head == null || index>size-1 || index<0)
				throw new RuntimeException("List out of bounds");
		else{
			if (index == 0){
				Node j = head;
				head = head.getNext();
				j.setNext(null);
			}
			else{
				Node x = head;
				for (int i=0 ; i<index-1 ; i++)
					x = x.getNext();
				Node j = x.getNext();
				x.setNext(j.getNext());
				j.setNext(null);
			}
			size--;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public MyLinkedList sublist(int fromIndex, int toIndex) {
		if (fromIndex>toIndex || fromIndex<0 || toIndex<0 || toIndex>size-1)
			throw new RuntimeException("List out of bounds");
		Node v = this.head;
		SLinkedList list = new SLinkedList();
		for (int i=0 ; i<fromIndex ; i++)
			v=v.getNext();
		for (int j=0 ; j<=toIndex-fromIndex ; j++){
			list.add(j, v.getValue());
			v=v.getNext();
		}
		list.size = toIndex - fromIndex +1;
		return list;
	}

	@Override
	public boolean contains(Object o) {
		Node x = head;
		while(x != null){
			if (x.getValue().equals(o))
				return true;
			x=x.getNext();
		}
		return false;
	}
	
}