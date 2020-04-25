//package com.epam.lab.utils;
//
//import com.epam.lab.model.entities.Author;
//
//import java.util.*;
//
//public class CollectionTools {
//
//    public static void main(String[] args) {
//        // Get the ArrayList with duplicate values
//        Author a = new Author();
//        a.setName("ziad");
//        a.setSurname("sarrih");
//        Author a1 = new Author();
//        a1.setName("ziad");
//        a1.setSurname("sarrih");
//        ArrayList<Author>
//                list = new ArrayList<>(
//                Arrays
//                        .asList(a,a1));
//
//        // Print the Arraylist
//        System.out.println("ArrayList with duplicates: "
//                + list);
//
//        // Remove duplicates
//        List<Author>
//                newList = removeDuplicates(list);
//
//    }
//    private CollectionTools() {
//    }
//
//    public static <T> List<T> toList(Iterable<T> iterable) {
//        List<T> result = new ArrayList<>();
//        iterable.forEach(result::add);
//        return result;
//    }
//
//    public static <T> List<T> removeDuplicates(List<T> list)
//    {
//        Set<T> set = new LinkedHashSet<>(list);
//        list.clear();
//        list.addAll(set);
//        return list;
//    }
//
//}
