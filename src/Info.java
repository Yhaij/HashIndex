/**
 * Created by yhj on 2017/10/2.
 */
public class Info<K, V> {
    public K key;
    public V value;
    public Info(K key, V value){
        this.key = key;
        this.value = value;
    }
    public K getKey(){
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
