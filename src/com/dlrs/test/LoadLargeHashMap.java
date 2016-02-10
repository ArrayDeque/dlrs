/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlrs.test;

import com.dlrs.contrib.GHashMap;
import com.dlrs.contrib.StringMap;
import java.io.File;
import java.io.IOException;
//import gnu.trove.map.hash.THashMap;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import org.mapdb.*;

/**
 *
 * @author phantom
 */
public class LoadLargeHashMap {

    //Result: THashMap 42 Million at 5.5 GB - Hangs (Trove)
    //Result: GHashMap 47 Million at 5.5 GB - Hangs (Google)
    //Result: HashMap 36 Million at 5.8 GB - Hangs
    //Result: TreeMap 35 Million at 4.56 GB - Hangs (No Compressed Memory) (sortedmap)
    //Result: ConcurrentSkipListMap 36 Million at 4.3 GB - Hangs (sortedmap)
    //100 Million
    private static int loadLimit = 10000000;
    //private static GHashMap phrasesMap = new GHashMap();
    private static HTreeMap phrasesMap = null;

    //private static LongMap phrasesMap = new LongHashMap();
    public static void main(String ar[]) {

        DB db = DBMaker.newFileDB(new File("/Users/Frank/Desktop/EWB.dat")).make();

        phrasesMap = db.getHashMap("phrasesMap");

        /*
         if (phrasesMap.containsKey("Test")) {
         System.out.println("YES");
         } else {
         System.out.println("No");
         }
         */
        
        generateLargeHashMap();
        
        phrasesMap.put("Love", new Integer(0));
        phrasesMap.put("Jennings", new Integer(0));
        
        db.commit();
        db.close();

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
            announceAt++;
            if (announceAt >= 1000000) {

                System.out.println("Loaded: " + (announceAt * multiplier));
                multiplier++;
                announceAt = 0;
            }
        }
    }

}
