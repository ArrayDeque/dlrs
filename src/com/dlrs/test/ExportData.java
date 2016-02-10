/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlrs.test;

import java.io.File;
import java.util.Iterator;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

/**
 *
 * @author phantom
 */
public class ExportData {

    public static void main(String ar[]) {

        DB db = DBMaker.newFileDB(new File("/Users/phantom/Documents/EWB/EWB_DATA/EWB_Phrases.dat")).make();
            HTreeMap phrasesMap = db.getHashMap("phrasesMap");

            DB db0 = DBMaker.newFileDB(new File("/Users/phantom/Documents/EWB/EWB_DATA/EWB_PhrasesRev.dat")).make();
            HTreeMap phrasesMapRev = db0.getHashMap("phrasesMapRev");

            DB db2 = DBMaker.newFileDB(new File("/Users/phantom/Documents/EWB/EWB_DATA/EWB_LongPhrases.dat")).make();
            HTreeMap longPhrasesMap = db2.getHashMap("longPhrasesMap");

            DB db20 = DBMaker.newFileDB(new File("/Users/phantom/Documents/EWB/EWB_DATA/EWB_LongPhrasesRev.dat")).make();
            HTreeMap longPhrasesMapRev = db20.getHashMap("longPhrasesMapRev");

            DB db3 = DBMaker.newFileDB(new File("/Users/phantom/Documents/EWB/EWB_DATA/EWB_LongPhrasesRel.dat")).make();
            HTreeMap longPhrasesRelMap = db3.getHashMap("longPhrasesRelMap");

            DB db4 = DBMaker.newFileDB(new File("/Users/phantom/Documents/EWB/EWB_DATA/EWB_PhrasesRel.dat")).make();
            HTreeMap phrasesRelMap = db4.getHashMap("phrasesRelMap");
        //TEST
        //if (longPhrasesMap.containsKey("shadow")) {
            //System.out.println("YES");
        //}
        
        //PRINT LONG PHRASES
        Iterator longPhrases = longPhrasesMap.keySet().iterator();
        while(longPhrases.hasNext()){
            System.out.println(longPhrases.next());
        }
        //PRINT LONG PHRASES
        Iterator longPhrasesRel = longPhrasesRelMap.keySet().iterator();
        while(longPhrasesRel.hasNext()){
            System.out.println(longPhrasesRel.next());
        }

        db.close();
        db2.close();
        db3.close();
        db4.close();
    }

}
