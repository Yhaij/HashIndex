import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by yhj on 2017/9/30.
 */
public class Test{
    private static List<Integer> keyList;
    private static List<Info<Integer, Integer>>[] keyLists;
    private static int maxNum = Integer.MIN_VALUE;
    private static int minMum = Integer.MAX_VALUE;
    private static long startTime, endTime;
    private static int threadNum = 0;
    private static Integer cursor = 0;
    private static MyHashMap<Integer, Integer>[] hashMaps;


    public static void SimpleSerialObject(Object hashMap,String filePath){
        try {
            File file = new File(filePath);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(hashMap);
            out.close();
            System.out.println("序列化成功！");
        }catch (Exception e){
            System.out.println("序列化MyHashmap失败！");
            System.out.println(e);
        }
    }
    public static void singal(int hashNum){

        HashDispatch hashMap = new HashDispatch(hashNum);
//      String filePath = "F:\\TPC\\data\\s2z0\\lineitem.tbl";

        System.out.println("create index start....");
        startTime = System.currentTimeMillis();
        hashMap.insertArray(keyList);
        endTime = System.currentTimeMillis();
        System.out.println("create index success,consume " + (endTime - startTime) + "ms");

        SimpleSerialObject(hashMap, "MyHashMap");

        System.out.println("start find a key");
        startTime = System.currentTimeMillis();
        for(int i = 0;i < 1000;i ++){
            List<Integer> list = hashMap.get(new Random().nextInt(maxNum+1));
            if(list == null){
                i--;
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("select a key consume" + (endTime - startTime) + "ms");
    }

    public static void multiThreading(int hashNum){
        hashMaps = new MyHashMap[hashNum];
        for(int i = 0;i<hashNum;i++){
            hashMaps[i] = new MyHashMap<>();
        }
        List<Thread> threadList = new ArrayList<>();
        startTime = System.currentTimeMillis();
        for(int i = 0;i<threadNum;i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        int place;
                        synchronized (cursor){
                            if(cursor < hashNum){
                                place = cursor;
                                cursor ++;
                            }else {
                                place = -1;
                            }
                        }
                        if(place == -1){
                            break;
                        }
//                        for(int i = place;i<keyList.size(); i = i + hashNum){
//                            hashMaps[place].put(keyList.get(i), i);
//                        }
                        for(int i = 0;i<keyLists[place].size();i++){
                            Info<Integer, Integer> info = keyLists[place].get(i);
                            hashMaps[place].put(info.key, info.value);
                        }
                    }
                }
            });
            t.start();
            threadList.add(t);
        }
        for(int i=0;i<threadList.size();i++){
            try {
                threadList.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("create index success,consume " + (endTime - startTime) + "ms");

        for(int i = 0;i<hashMaps.length; i++){
            SimpleSerialObject(hashMaps[i], "/hashmaps/hashmaps"+ i);
        }

        System.out.println("start find a key");
        startTime = System.currentTimeMillis();
        for(int i = 0;i < 1000;i ++){
            int key = new Random().nextInt(maxNum+1);
            List<Integer> list = hashMaps[key%hashNum].get(key);
            if(list == null){
                i--;
            }
//            else {
//                System.out.print(key + ": " );
//                for(int a:list){
//                    System.out.print(a + "  ");
//                }
//                System.out.println();
//            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("select a key consume" + (endTime - startTime) + "ms");
    }


    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        boolean isThread = false;
        while(true){
            System.out.print("是否多线程建立索引(yes or no)：");
            String in = sc.nextLine();
            if(in.toLowerCase().equals("yes")){
                isThread = true;
                break;
            }
            if(in.toLowerCase().equals("no")){
                isThread = false;
                break;
            }
        }
        System.out.print("输入文件的目录：");
        String filePath = sc.nextLine();
        System.out.print("输入Hash的数量：");
        int hashNum = sc.nextInt();
        if(isThread){
            System.out.print("输入Thread的数量：");
            threadNum = sc.nextInt();
        }
        try{
            System.out.println("read file start");
            startTime = System.currentTimeMillis();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = null;
            if(isThread){
                keyLists = new ArrayList[hashNum];
                for(int i = 0;i<keyLists.length;i++){
                    keyLists[i] = new ArrayList<>();
                }
                int n = 0;
                while ((line = br.readLine()) != null){
                    Integer key = Integer.parseInt(line.split("\\|")[0]);
//                System.out.println(line.split("\\|")[0]);
                    if(key > maxNum){
                        maxNum = key;
                    }
                    if(key < minMum){
                        minMum = key;
                    }
                    keyLists[key%hashNum].add(new Info<>(key, ++n));
                }
            }else {
                keyList = new ArrayList<>();
                while ((line = br.readLine()) != null){
                    Integer key = Integer.parseInt(line.split("\\|")[0]);
//                  System.out.println(line.split("\\|")[0]);
                    if(key > maxNum){
                        maxNum = key;
                    }
                    if(key < minMum){
                        minMum = key;
                    }
                    keyList.add(key);
                }
            }
            endTime = System.currentTimeMillis();
            System.out.println("max:" + maxNum + "  min:" + minMum);
            System.out.println("read file success, consume " + (endTime - startTime) + "ms");
        }catch (Exception e){
            System.out.println("Read file fail");
        }
        if(!isThread){
            singal(hashNum);
        }else {
            multiThreading(hashNum);
        }


    }
}