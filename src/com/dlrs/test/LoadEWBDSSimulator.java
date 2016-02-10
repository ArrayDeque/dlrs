/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlrs.test;

import com.dlrs.contrib.GHashMap;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author phantom
 */
public class LoadEWBDSSimulator {

//100 Million
    private static int loadLimit = 10000000;
    private static GHashMap phrasesMap = new GHashMap();

    private static GHashMap prevMap = new GHashMap();
    private static GHashMap nextMap = new GHashMap();
    private static GHashMap popularMap = new GHashMap();

    private static int maxRelCount = 50;

    //private static LongMap phrasesMap = new LongHashMap();
    public static void main(String ar[]) {

        generateLargeHashMap();
    }

    public static ArrayList generateRandomList() {

        ArrayList aList = new ArrayList();
        for (int i = 0; i < maxRelCount; i++) {
            aList.add(new Integer(1000000));
        }

        return aList;
    }

    public static ArrayList generateRandomListOfList() {

        ArrayList bList = new ArrayList();
        for (int j = 0; j < maxRelCount; j++) {
            
            ArrayList aList = new ArrayList();
            for (int i = 0; i < maxRelCount; i++) {
                aList.add(new Integer(1000000));
            }

            
                bList.add(aList);
            
        }

        return bList;
    }

    public static void generateLargeHashMap() {

        int announceAt = 0;
        int multiplier = 1;

        Random random = new Random();
        for (int i = 0; i < loadLimit; i++) {
            char[] word = new char[random.nextInt(8) + 9]; // words of length 3 through 10. (1 and 2 letter words are boring.)
            for (int j = 0; j < word.length; j++) {
                word[j] = (char) ('a' + random.nextInt(26));
            }
            //System.out.println(new String(word));

            phrasesMap.put(new String(word), new Integer(i));
            ArrayList bList = generateRandomList();
            ArrayList cList = generateRandomListOfList();

            prevMap.put(new Integer(i), bList);
            nextMap.put(new Integer(i), bList);
            popularMap.put(new Integer(i), cList);

            announceAt++;
            if (announceAt >= 1000000) {

                System.out.println("Loaded: " + (announceAt * multiplier));
                multiplier++;
                announceAt = 0;
            }
        }
    }

}
