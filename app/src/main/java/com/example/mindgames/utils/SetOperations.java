package com.example.mindgames.utils;

import android.util.ArraySet;

public class SetOperations {
    public enum Operation{
        UNION, INTERSECTION, DIFFERENCE;
    }

    public static ArraySet<Integer> get(ArraySet<Integer> set1,
                                        ArraySet<Integer> set2, Operation op){
        switch (op){
            case UNION: return getUnion(set1, set2);
            case INTERSECTION: return getIntersection(set1, set2);
            case DIFFERENCE: return getDifference(set1, set2);
        }
        return null;
    }

    //the union of two sets
    private static ArraySet<Integer> getUnion(ArraySet<Integer> set1,
                                              ArraySet<Integer> set2){
        ArraySet<Integer> res = new ArraySet<>(set1);

        for(Integer element : set2){
            res.add(element);
        }

        return res;
    }

    //the intersection of two sets
    private static ArraySet<Integer> getIntersection(ArraySet<Integer> set1,
                                                     ArraySet<Integer> set2){
        ArraySet<Integer> res = new ArraySet<>();

        for(Integer element : set1){
            //if element exists in both sets
            if (set2.contains(element)){
                res.add(element);
            }
        }

        return res;
    }

    //returns the elements that not common
    private static ArraySet<Integer> getDifference(ArraySet<Integer> set1,
                                                   ArraySet<Integer> set2){
        ArraySet<Integer> res = new ArraySet<>();

        for(Integer element : set1){
            if (!set2.contains(element)){
                res.add(element);
            }
        }

        for(Integer element : set2){
            if (!set1.contains(element)){
                res.add(element);
            }
        }

        return res;
    }
}
