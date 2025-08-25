package com.kumar.wipro.api.service;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DesOrder{
     public static void main (String [] args)
     {
         String[] names = {"A", "B", "C", "D"};
         Integer[] scores = {10, 13, 12, 11};

         // Pair, sort, and collect back to arrays
         List<Map.Entry<String, Integer>> sorted = 
             IntStream.range(0, names.length)
             .mapToObj(i -> new AbstractMap.SimpleEntry<>(names[i], scores[i]))
             .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
             .collect(Collectors.toList());

         // Extract sorted names and scores
         String[] sortedNames = sorted.stream().map(Map.Entry::getKey).toArray(String[]::new);
         Integer[] sortedScores = sorted.stream().map(Map.Entry::getValue).toArray(Integer[]::new);

         // Print as arrays
         System.out.println("Array 1: " + Arrays.toString(sortedNames));
         System.out.println("Array 2: " + Arrays.toString(sortedScores));
     }
}