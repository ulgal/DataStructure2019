/*
*   UgLinkedList는 HW2의 MyLinkedList를 활용하였다.
*/
import java.util.Iterator;
import java.util.NoSuchElementException;
public class UgLinkedList<T>{
	// dummy head
	Node<T> head;
	int numItems;

	public UgLinkedList() {
		head = new Node<T>(null);
	}
	public UgLinkedList(T item) {
		head = new Node<T>(null);
		Node<T> next = new Node<T>(item);
		head.setNext(next);
		numItems = 1;
	}



	public boolean isEmpty() {
		return head.getNext() == null;
	}


	public int size() {
		return numItems;
	}


	public void add(T item) {
		Node<T> addNode = head;
		//예외처리 필요한 부분
		while (addNode.hasNext()) {
			addNode = addNode.getNext();
		}

		addNode.insert(item);
		numItems += 1;
	}



	public void insert(int index, T item) {
		Node<T> indexNode = head;
		//예외처리 필요한 부분
		while (index!=1) {
			indexNode = indexNode.getNext();
      index--;
		}

		indexNode.insert(item);
		numItems += 1;
	}


  public T get(int index){
    Node<T> indexNode = head;
		//예외처리 필요한 부분, if(??) throw new ~~
    while (index!=1) {
			indexNode = indexNode.getNext();
      index--;
		}
    return indexNode.getNext().getItem();
  }


	public T getItem(){
    Node<T> indexNode = head.getNext();
		//예외처리 필요한 부분, if(??) throw new ~~
    return indexNode.getItem();
  }

	public Node<T> getHead(){
		return head;
	}


  public void remove(int index){
    Node<T> deleteNode = head;
		//예외처리 필요한 부분
		while (index!=1) {
			deleteNode = deleteNode.getNext();
			index--;
		}

		deleteNode.remove();
		numItems -= 1;
  }


	public void removeAll() {
		head.setNext(null);
		numItems = 0;
	}


}
