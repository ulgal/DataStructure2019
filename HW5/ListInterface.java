/*
*   ListInterface는 HW2의 ListInterface를 활용하였다.
*/
public interface ListInterface<T>{
	public boolean isEmpty();

	public int size();

	public void add(T item);

	public void insert(int index, T item);

  public void remove(int index);

  public Object get(int index);

	public void removeAll();


}
