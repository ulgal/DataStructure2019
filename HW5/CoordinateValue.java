/*
*   좌표를 담은 클래스
*   continuous를 통해 연속인지 판별
*/
public class CoordinateValue{
  private int column;
  private int row;

  public CoordinateValue(int column, int row){
    this.column = column;
    this.row = row;
  }
  public void setColumn(int column){
    this.column = column;
  }
  public void setRow(int row){
    this.row = row;
  }
  public int getColumn(){
    return this.column;
  }
  public int getRow(){
    return this.row;
  }
  public void printCoordinateValue(){
    System.out.print("("+this.getColumn()+", "+this.getRow()+")");
  }
  public CoordinateValue continuous(UgLinkedList others){
    Node<CoordinateValue> other = others.getHead();
    while(other.hasNext()){
      other= other.getNext();
      if(this.getColumn()==other.getItem().getColumn()&&other.getItem().getRow()-this.getRow()==1){
        return other.getItem();
      }
    }
    return null;
  }
  public boolean continuous(CoordinateValue other){
    if(this.getColumn()==other.getColumn()&&other.getRow()-this.getRow()==1){
      return true;
    }
    else
      return false;
  }
}
