/*
*   dbTree는 LinkedList와 Node 관계처럼 dbTree와 AVLTreeNode 관계를 구성하였다.
*   dbTree 또한 Key와 Value를 input으로 받으며, LinkedList를 Node의 item으로 가져 Key가 중복되는 경우가 가능한 Tree라는 점에서 일반 BinaryTree와 다르며,
*   중복된 Key를 처리하는 방식이 LinkedList이기 때문에 노드의 Item은 LinkedList로 제한된다.
*		dbTree는 AVLTreeNode root와 numItem을 갖고, dbTree에서 받은 명령을 AVLTreeNode 메서드를 이용하여 root를 업데이트한다.
*/
public class dbTree<K extends Comparable<K>, V>{
  private UgAVLTreeNode<K, UgLinkedList> root;
  int numItem;

  public dbTree(){
    root = new UgAVLTreeNode<K, UgLinkedList>();
		this.numItem = 0;
  }
  public dbTree(K key, V item){
    UgAVLTreeNode<K, UgLinkedList> newRoot = new UgAVLTreeNode<K, UgLinkedList>();
    UgLinkedList<V> newLL = new UgLinkedList(item);
    root = newRoot;
    root.setItem(newLL);
    root.setKey(key);
    this.numItem = 1;
  }
  public dbTree(K key, UgLinkedList item){
		UgAVLTreeNode<K, UgLinkedList> newRoot = new UgAVLTreeNode<K, UgLinkedList>();
    root = newRoot;
    root.setItem(item);
    root.setKey(key);
		this.numItem = 1;
  }


	public dbTree(dbTree tree){
    root = tree.root;
		this.numItem = tree.numItem;
  }

  public boolean isEmpty(){
    return this.numItem == 0;
  }



  public void add(K key, V item){
    UgAVLTreeNode<K, UgLinkedList> addNode = root;
		boolean dupFlag = false;
    while(addNode!=null){
      int cmpInt = addNode.compareTo(key);
      if(cmpInt<0){
				if(addNode.getRightC() != null){
					addNode = addNode.getRightC();
				}
				else{
					this.numItem += 1;
          UgLinkedList<V> llitem = new UgLinkedList<V>(item);
					addNode.setRightChild(new UgAVLTreeNode(key, llitem));
					break;
				}
      }
      else if(cmpInt>0){
				if(addNode.getLeftC() != null){
					addNode = addNode.getLeftC();
				}
				else{
					this.numItem += 1;
          UgLinkedList<V> llitem = new UgLinkedList<V>(item);
					addNode.setLeftChild(new UgAVLTreeNode(key, llitem));
					break;
				}
      }
			else{
				// Tree이므로 기본적으로 중복된 키는 허용하지 않음
				// 문제 조건 상 item을 LinkedList로 구현하여 Key가 같은 경우 뒤에 붙여주는 방식이기 때문에0
				// 아래의 add는 dbTree에서는 사용한다.
				addNode.getItem().add(item);
				dupFlag = true;
				break;
			}
    }
		if(!dupFlag){
			root.setNode(addNode.setTree(null));
		}
  }


	public UgLinkedList search(K key){
		UgAVLTreeNode<K, UgLinkedList> searchNode = root;
    while(searchNode!=null){
      int cmpInt = searchNode.compareTo(key);
      if(cmpInt<0){
        searchNode = searchNode.getRightC();
      }
      else if(cmpInt>0){
        searchNode = searchNode.getLeftC();
      }
			else{
				return searchNode.getItem();
			}
    }
		return null;
	}

	public UgAVLTreeNode<K, UgLinkedList> searchNode(K key){
		UgAVLTreeNode<K, UgLinkedList> searchNode = root;
    while(searchNode!=null){
      int cmpInt = searchNode.compareTo(key);
      if(cmpInt<0){
        searchNode = searchNode.getRightC();
      }
      else if(cmpInt>0){
        searchNode = searchNode.getLeftC();
      }
			else{
				return searchNode;
			}
    }
		return null;
	}


  public void remove(K key){
		UgAVLTreeNode<K, UgLinkedList> deleteNode = this.searchNode(key);
		deleteNode.remove();
    root.setNode(deleteNode.setTree(null));
		this.numItem -= 1;
  }

  public Object get(){
    return this.root;
  }

  public void removeAll(){
    root = null;
		this.numItem = 0;
  }

	public void print(){
		if(isEmpty()){
			System.out.println("EMPTY");
		}
		else{
			printTree(root, numItem);
			System.out.println();
		}
	}

	public int printTree(UgAVLTreeNode<K, UgLinkedList> printNode, int num){
	   if(printNode != null){
			if(printNode.getKey() != null){
				System.out.print(printNode.getKey());
				num--;
				if(num>0){
					System.out.print(" ");
				}
				num = printTree(printNode.getLeftC(), num);
				num = printTree(printNode.getRightC(), num);
			}
		}
		return num;
	}
}
