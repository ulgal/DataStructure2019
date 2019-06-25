/*
*   UgHashTable은 dbTree의 배열을 가진다.
*   key는 string이 들어오기 때문에 hashsingKey를 통해 string으로 변환 후 ascii코드 합을 구하는 방식 사용 사용하였다.
*		HashNum을 input으로 받아 그만큼의 배열을 생성한다.
*   Array에 Value가 그대로 저장되는게 아니라 LinkedList를 갖는 Tree가 저장되고
*   Array size의 확장이 제한된 조건(과도한 충돌에서 효율적인 Tree 구성)이기 때문에
*   loadFactor와 loadFactor에 따른 배열 크기 보정은 구현하지 않았다.
*/
import java.lang.reflect.Array;
public class UgHashTable<K extends Comparable<K>, V> implements HashTableInterface<K, V>{ // K = key, String // V = value, CoordinateValue
  dbTree [] HashBucket;
  int hashNum;


  public UgHashTable(int hashNum){
    HashBucket = new dbTree[hashNum];
    this.hashNum = hashNum;
  }
  @Override
  public boolean isEmpty(int index){
    return HashBucket[index] == null;
  }

  @Override
  public int hashingKey(K keyK){
    int hashedKey = 0;
    String key = keyK.toString();
    for(int i = 0; i < key.length(); i++){
      hashedKey += (key.charAt(i));
    }
    return hashedKey % hashNum ;
  }

  @Override
  public dbTree get(int index){
    return (HashBucket[index]!=null) ? HashBucket[index] : new dbTree<K, V>();
  }

  @Override
  public void add(K keyK, V value){
    int index = hashingKey(keyK);
    if(HashBucket[index] == null){
      //UgLinkedList<V> llValue = new UgLinkedList((CoordinateValue)value);
      HashBucket[index] = new dbTree<K, V>(keyK, value);
    }
    else{
      //UgLinkedList<V> llValue = new UgLinkedList((CoordinateValue)value);
      HashBucket[index].add(keyK, value);
    }
  }

  @Override
  public UgLinkedList search(K keyK){
    int index = hashingKey(keyK);
    return (HashBucket[index]!=null) ? ( HashBucket[index].search(keyK)) : null;
  }

  @Override
  public void removeAll(){
    HashBucket = new dbTree[hashNum];
  }

}
