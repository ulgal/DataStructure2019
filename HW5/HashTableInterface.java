public interface HashTableInterface<K, V>{

	public boolean isEmpty(int index);

	public int hashingKey(K key);

  public Object get(int index);

	public void add(K key, V value);

	public Object search(K key);

  public void removeAll();
}
