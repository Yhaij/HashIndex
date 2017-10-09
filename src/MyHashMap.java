import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yhj on 2017/9/30.
 */
public class MyHashMap<K, V> implements Serializable{
    private HashMap<K, MyNode<V>> hashMap;
    public MyHashMap(){
        hashMap = new HashMap<>();
    }
    private static class MyNode<V> implements Serializable{
        public V value;
        public MyNode next;
        public MyNode(V value){
            this.value = value;
            this.next = null;
        }
        public MyNode(V value, MyNode next){
            this.value = value;
            this.next = next;
        }
        public V getValue(){
            return this.value;
        }
        public void setValue(V value){
            this.value = value;
        }
        public MyNode getNext(){
            return this.next;
        }
        public void setNext(MyNode next){
            this.next = next;
        }
    }
    public V put(K key, V value){
        MyNode newNode = new MyNode(value);
        if(hashMap.containsKey(key)){
            MyNode node = hashMap.get(key);
            newNode.next = node.next;
            node.next = newNode;
        }else {
            hashMap.put(key, newNode);
        }
        return value;
    }
    public List<V> get(K key){
        if(hashMap.containsKey(key)){
            MyNode<V> p = hashMap.get(key);
            List<V> list = new ArrayList<V>();
            while(p != null){
                list.add(p.value);
                p = p.next;
            }
            return list;
        }else {
            return null;
        }
    }
}
