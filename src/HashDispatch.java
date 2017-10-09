import org.omg.CORBA.INTERNAL;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yhj on 2017/10/1.
 */
public class HashDispatch implements Serializable{
    public MyHashMap<Integer, Integer>[] hashMaps;
    public int hashNum = 0;
    private int cursor = 0;
    public HashDispatch(int hashNum){
        this.hashNum = hashNum;
        hashMaps = new MyHashMap[hashNum];
        for(int i = 0;i < hashNum;i ++){
            hashMaps[i] = new MyHashMap();
        }
    }
//    public Integer putThread(Integer key, Integer value){
//        if(hashNum <= 0){
//            throw new IndexOutOfBoundsException("hash num must > 0");
//        }
//        synchronized (hashMaps[key%hashNum]){
//            hashMaps[key%hashNum].put(key, value);
//        }
//        return value;
//    }

    public List<Integer> get(Integer key){
        if(hashNum <= 0){
            throw new IndexOutOfBoundsException("hash num must > 0");
        }
        return hashMaps[key%hashNum].get(key);
    }

//    public void insertArrayThread(List<Integer> keyList) {
//        if(keyList == null){
//            return;
//        }
//        while(true){
//            Integer key, value;
//            synchronized (keyList){
//                if(cursor < keyList.size()){
//                    key = keyList.get(cursor);
//                    value = cursor;
//                    cursor++;
//                }else {
//                    break;
//                }
//            }
//            if(key != null){
//                putThread(keyList.get(key),value);
//            }
//        }
//    }

    public Integer put(Integer key, Integer value){
        if(hashNum <= 0){
            throw new IndexOutOfBoundsException("hash num must > 0");
        }
        hashMaps[key%hashNum].put(key, value);
        return value;
    }

    public void insertArray(List<Integer> keyList) {
        if(keyList == null){
            return ;
        }
        for(int i = 0;i < keyList.size(); i ++){
            put(keyList.get(i),i);
        }
    }

}
