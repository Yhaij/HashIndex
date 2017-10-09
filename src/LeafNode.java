import java.io.Serializable;

/**
 * Created by yhj on 2017/10/4.
 */
public class LeafNode<K ,V> implements Serializable{
    public K key;
    public V value;
    public LeafNode<K, V> next;

    public LeafNode(K key, V value, LeafNode<K, V> next){
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public LeafNode(K key, V value){
        this.key = key;
        this.value = value;
        this.next = null;
    }


    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public LeafNode<K, V> getNext() {
        return next;
    }

    public void setNext(LeafNode<K, V> next) {
        this.next = next;
    }
}
