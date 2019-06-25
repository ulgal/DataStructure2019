import java.lang.*;
/*
*   UgAVLTreeNode는 LinkedList와 Node관계처럼 Tree와 TreeNode 관계를 구성하였다.
*   핵심은 setTree로 add/remove 등의 메서드를 실행한 뒤 setTree를 통해 tree의 좌우 높이차가 1이 넘지 않도록 보정해준다.
*   만약 Tree의 root에서 recursive하게 내려가는 방식으로 구현한다면(mergesort와 비슷한 방식)
*   매 삽입/삭제마다 Tree의 높이를 보정하는데 모든 노드를 방문하므로 O(n)의 시간복잡도를 갖게 되어 비효율적이다.
*   따라서 TreeNode가 자식 노드 외에 부모 노드의 정보도 갖게 하여
*   setTree의 경우 추가/삭제가 일어난 노드에서 위로 올라가는 방식으로 구현하여 setTree에 걸리는 시간을 단축하였다.
*   이 방식에서의 시간복잡도는 O(logn)이 보장되며
*   만약 회전이 일어난 경우 회전이 일어난 주변 노드들의 부모노드 정보를 업데이트해야 하기 때문에
*   arrangeParrent method를 통해 조절해준다.
*/
public class UgAVLTreeNode<K extends Comparable<K>, T>{
  private T item;
  private K key;
  private UgAVLTreeNode<K, T> parentNode;
  private UgAVLTreeNode<K, T> leftChild;
  private UgAVLTreeNode<K, T> rightChild;
  private int leftHeight;
  private int rightHeight;

  public UgAVLTreeNode(){
    this.item = null;
    this.key = null;
    this.parentNode = null;
    this.leftChild = null;
    this.rightChild = null;
    this.leftHeight = 0;
    this.rightHeight = 0;
  }
  public UgAVLTreeNode(K k){
    this.item = null;
    this.key = k;
    this.parentNode = null;
    this.leftChild = null;
    this.rightChild = null;
    this.leftHeight = 0;
    this.rightHeight = 0;
  }
  public UgAVLTreeNode(T obj){
    this.item = obj;
    this.key = null;
    this.parentNode = null;
    this.leftChild = null;
    this.rightChild = null;
    this.leftHeight = 0;
    this.rightHeight = 0;
  }
  public UgAVLTreeNode(K k, T obj){
    this.item = obj;
    this.key = k;
    this.parentNode = null;
    this.leftChild = null;
    this.rightChild = null;
    this.leftHeight = 0;
    this.rightHeight = 0;
  }
  public UgAVLTreeNode(UgAVLTreeNode<K, T> obj){
    this.item = obj.getItem();
    this.key = obj.getKey();
    this.parentNode = obj.getParent();
    this.leftChild =  obj.getLeftC();
    this.rightChild = obj.getRightC();
    this.leftHeight = obj.getLeftH();
    this.rightHeight = obj.getRightH();
  }



  public void setItem(T item){
    this.item = item;
  }
  public void setKey(K key){
    this.key = key;
  }
  public void setParent(UgAVLTreeNode<K, T> parent){
    if(parent!=null){
      this.parentNode = parent;
    }
    else{
      this.parentNode = null;
    }
  }
  public void setNode(UgAVLTreeNode<K, T> oneNode){
    this.item = oneNode.item;
    this.key = oneNode.key;
    this.parentNode = oneNode.parentNode;
    this.leftChild = oneNode.leftChild;
    this.rightChild = oneNode.rightChild;
    this.leftHeight = oneNode.leftHeight;
    this.rightHeight = oneNode.rightHeight;
  }
  public void setLeftChild(UgAVLTreeNode<K, T> left){
    if(left!=null){
      left.setParent(this);
    }
    this.leftChild = left;
    if(this.getLeftC()!=null){
      this.leftHeight = this.getLeftC().getHeight() + 1;
    }
    else{
      this.leftHeight = 0;
    }
  }
  public void setRightChild(UgAVLTreeNode<K, T> right){
    if(right!=null){
      right.setParent(this);
    }
    this.rightChild = right;
    if(this.getRightC()!=null){
      this.rightHeight = this.getRightC().getHeight() + 1;
    }
    else{
      this.rightHeight = 0;
    }
  }


  public T getItem() {
    return this.item;
  }
  public K getKey() {
    return this.key;
  }
  public UgAVLTreeNode<K, T> getParent(){
    return this.parentNode;
  }
  public UgAVLTreeNode<K, T> getThis(){
    return this;
  }
  public UgAVLTreeNode<K, T> getLeftC(){
    return this.leftChild;
  }
  public UgAVLTreeNode<K, T> getRightC(){
    return this.rightChild;
  }
  public int getLeftH(){
    return this.leftHeight;
  }
  public int getRightH(){
    return this.rightHeight;
  }
  public int getHeight(){
    return Math.max(this.getLeftH(), this.getRightH());
  }

  public boolean hasLeftC(){
    return this.getLeftC() != null;
  }
  public boolean hasRightC(){
    return this.getRightC() != null;
  }
  public boolean hasParent(){
    return this.getParent() != null;
  }
  public boolean hasChild(){
    return (this.getLeftC()!=null)||(this.getRightC()!=null);
  }


    //set, parent지정
  public void remove() {
    if(!this.hasChild()) {
      this.setNode(null);
    }
    else if((this.hasLeftC())&&(!this.hasRightC())){
      this.setNode(this.getLeftC());
    }
    else if((!this.hasLeftC())&&(this.hasRightC())){
      this.setNode(this.getRightC());
    }
    else{
      UgAVLTreeNode<K, T> rightChildLeftMost = this.getRightC();
      while(rightChildLeftMost.hasLeftC()){
        rightChildLeftMost = rightChildLeftMost.getLeftC();
      }
      this.setItem(rightChildLeftMost.getItem());
      rightChildLeftMost.remove();
    }
  }

  public void removeLeft() {
    if(!this.getLeftC().hasChild()) {
      this.setLeftChild(null);
    }
    else if((this.getLeftC().hasLeftC())&&(!this.getLeftC().hasRightC())){
      this.setLeftChild(this.getLeftC().getLeftC());
    }
    else if((!this.getLeftC().hasLeftC())&&(this.getLeftC().hasRightC())){
      this.setLeftChild(this.getLeftC().getRightC());
    }
    else{
      UgAVLTreeNode<K, T> rightChildLeftMost = this.getLeftC().getRightC();
      while(rightChildLeftMost.hasLeftC()){
        rightChildLeftMost = rightChildLeftMost.getLeftC();
      }
      this.getLeftC().setItem(rightChildLeftMost.getItem());
      rightChildLeftMost.remove();
    }
  }

  public void removeRight() {
    if(!this.getRightC().hasChild()) {
      this.setRightChild(null);
    }
    else if((this.getRightC().hasLeftC())&&(!this.getRightC().hasRightC())){
      this.setRightChild(this.getRightC().getLeftC());
    }
    else if((!this.getRightC().hasLeftC())&&(this.getRightC().hasRightC())){
      this.setRightChild(this.getRightC().getRightC());
    }
    else{
      UgAVLTreeNode<K, T> leftChildRightMost = this.getRightC().getRightC();
      while(leftChildRightMost.hasLeftC()){
        leftChildRightMost = leftChildRightMost.getLeftC();
      }
      this.getRightC().setItem(leftChildRightMost.getItem());
      leftChildRightMost.remove();
    }
  }


  // 얘가 tree의 높이 보정해주는 메서드, 아래서 위로 올라간다.
  public UgAVLTreeNode<K, T> setTree(UgAVLTreeNode<K, T> fromBottom){
    if(fromBottom!=null){
      int cmpInt = this.compareTo(fromBottom);
      if(cmpInt>0){
        this.setLeftChild(fromBottom);
      }
      else if(cmpInt<0){
        this.setRightChild(fromBottom);
      }
    }
    if(this.getLeftC()!=null){ // leftHeight 다시 정해주기
      this.leftHeight = this.getLeftC().getHeight() + 1;
    }
    else{
      this.leftHeight = 0;
    }
    if(this.getRightC()!=null){ // RightHeight 다시 정해주기
      this.rightHeight = this.getRightC().getHeight() + 1;
    }
    else{
        this.rightHeight = 0;
    }
    if(Math.max(this.getLeftH(), this.getRightH())-Math.min(this.getLeftH(), this.getRightH())>1){ // 1이상 차이날 경우 rotation
      if(this.getLeftH()>this.getRightH()){ // 왼쪽이 더 큰 경우  LeftChild에 대해 rotL한 뒤 this Node에서 rotR함
        if(this.getLeftC().getRightH()>this.getLeftC().getLeftH()){
          this.setLeftChild(this.getLeftC().rotL());
        }
        this.setNode(this.rotR());
      }
      else{ // 오른쪽이 더 큰 경우  RightChild에 대해 rotR한 뒤 this Node에서 rotL함.
        if(this.getRightC().getLeftH()>this.getRightC().getRightH()){
          this.setRightChild(this.getRightC().rotR());
        }
        this.setNode(this.rotL());
      }
    }
    return (this.getParent()!=null) ? this.getParent().setTree(this) : this;
  }

  /*
  * 처음에 3이 들어가는 이유
  * 회전이 들어가면 subtree의 새 root노드,  기존 root노드, 새root노드의 오른쪽에 달려있다 root 노드에 달아지는 노드
  * 세 노드에 대해서 새로 인스턴스를 생성하는 방식으로 rotation을 하기 때문에 parent 정보를 업데이트해줘야 한다.
  */
  public void arrangeParent(UgAVLTreeNode<K, T> child, int num){
    if(child!=null&&num>0){
      child.setParent(this);
      child.arrangeParent(child.getRightC(), num-1);
      child.arrangeParent(child.getLeftC(), num-1);
    }
  }

  public UgAVLTreeNode<K, T> rotR(){
    UgAVLTreeNode<K, T> retNode = new UgAVLTreeNode();
    UgAVLTreeNode<K, T> retRNode = new UgAVLTreeNode();
    retNode.setNode(this.getLeftC());
    retNode.setParent(this.getParent());
    retRNode.setNode(this);
    retRNode.setLeftChild((retNode.getRightC()!=null) ? retNode.getRightC() : null);
    retNode.setRightChild(retRNode);
    retNode.arrangeParent(retNode.getLeftC(), 3);
    retNode.arrangeParent(retNode.getRightC(), 3);
    return retNode;
  }

  public UgAVLTreeNode<K, T> rotL(){
    UgAVLTreeNode<K, T> retNode = new UgAVLTreeNode();
    UgAVLTreeNode<K, T> retLNode = new UgAVLTreeNode();
    retNode.setNode(this.getRightC());
    retNode.setParent(this.getParent());
    retLNode.setNode(this);
    retLNode.setRightChild((retNode.getLeftC()!=null) ? retNode.getLeftC() : null);
    retNode.setLeftChild(retLNode);
    retNode.arrangeParent(retNode.getLeftC(), 3);
    retNode.arrangeParent(retNode.getRightC(), 3);
    return retNode;
  }


  public int compareTo(UgAVLTreeNode<K, T> other){
    return this.getKey().compareTo(other.getKey());
  }


  public int compareTo(K key){
    return this.getKey().compareTo(key);
  }


}
