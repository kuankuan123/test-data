package com.msbjy.makedata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class T {
    public static void main(String[] args) {
        System.out.println(1111);
        Set<String> strings = new HashSet<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        strings.add("e");

        ArrayList<String> list = new ArrayList<>(strings);
        System.out.println(list.toString());
        int toIndex=2;
        for(int i=0;i < list.size();i+=2){
         if(i+2 > list.size()){
             toIndex=list.size()-i;
         }
            List<String> strings1 = list.subList(i, i + toIndex);
            System.out.println(strings1);
        }
    }
}
