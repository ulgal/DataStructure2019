public class Node<T> {
    private T item;
    private Node<T> next;

    public Node(T obj) {
        this.item = obj;
        this.next = null;
    }

    public Node(T obj, Node<T> next) {
    	this.item = obj;
    	this.next = next;
    }

    public final T getItem() {
    	return item;
    }

    public final void setItem(T item) {
    	this.item = item;
    }

    public final void setNext(Node<T> next) {
    	this.next = next;
    }

    public Node<T> getNext() {
    	return this.next;
    }
    //추가부분
	  public boolean hasNext() {
		    return this.getNext() != null;
	  }
    public final void insertNext(T obj) {
    	if(this.getNext()==null) {
    		this.setNext(new Node<T> (obj));
    	}
    	else {
    		Node<T> insertedNode = new Node<T> (obj, this.getNext());
    		this.setNext(insertedNode);
    	}
		//throw new UnsupportedOperationException("not implemented yet");
    }

    public final void removeNext() {
		if(this.getNext().getNext()==null) {
			this.setNext(null);
		}
		else {
			this.setNext(this.getNext().getNext());
		}
    	//throw new UnsupportedOperationException("not implemented yet");
    }
}
