package com.loha.flippedclassroom.util;

import java.util.Iterator;
import java.util.Map;

public class SortMap {

    public static Long getMapMaxKey(Map<Long,Integer> map){
        Long maxKey=new Long(0);
        Integer maxValue=0;
        Iterator keys=map.keySet().iterator();
        while (keys.hasNext()){
            Object key=keys.next();
            Integer value=Integer.parseInt(map.get(key).toString());
            if(value>maxValue){
                maxValue=value;
                maxKey=Long.parseLong(key.toString());
            }
        }
        return maxKey;
    }
}
