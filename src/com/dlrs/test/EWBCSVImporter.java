/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlrs.test;

import com.dlrs.contrib.GHashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

/**
 *
 * @author Frank
 */
public class EWBCSVImporter {

    //Given a directory containing phrases list, generate mapdb
    public static void main(String ar[]) {

        try {
            File csvFile = new File("/Users/phantom/Documents/EWB/input/Test.csv");

            //DB db = DBMaker.newFileDB(new File("/Users/phantom/Documents/EWB/EWB_DATA/EWB_Phrases.dat")).make();
            //HTreeMap phrasesMap = db.getHashMap("phrasesMap");
            GHashMap phrasesMap = new GHashMap();

            //DB db0 = DBMaker.newFileDB(new File("/Users/phantom/Documents/EWB/EWB_DATA/EWB_PhrasesRev.dat")).make();
            //HTreeMap phrasesMapRev = db0.getHashMap("phrasesMapRev");
            GHashMap phrasesMapRev = new GHashMap();

            //DB db2 = DBMaker.newFileDB(new File("/Users/phantom/Documents/EWB/EWB_DATA/EWB_LongPhrases.dat")).make();
            //HTreeMap longPhrasesMap = db2.getHashMap("longPhrasesMap");
            GHashMap longPhrasesMap = new GHashMap();

            //DB db20 = DBMaker.newFileDB(new File("/Users/phantom/Documents/EWB/EWB_DATA/EWB_LongPhrasesRev.dat")).make();
            //HTreeMap longPhrasesMapRev = db20.getHashMap("longPhrasesMapRev");
            GHashMap longPhrasesMapRev = new GHashMap();

            //DB db3 = DBMaker.newFileDB(new File("/Users/phantom/Documents/EWB/EWB_DATA/EWB_LongPhrasesRel.dat")).make();
            //HTreeMap longPhrasesRelMap = db3.getHashMap("longPhrasesRelMap");
            GHashMap longPhrasesRelMap = new GHashMap();

            //DB db4 = DBMaker.newFileDB(new File("/Users/phantom/Documents/EWB/EWB_DATA/EWB_PhrasesRel.dat")).make();
            //HTreeMap phrasesRelMap = db4.getHashMap("phrasesRelMap");
            GHashMap phrasesRelMap = new GHashMap();

            FileInputStream fis = new FileInputStream(csvFile);
            //Construct BufferedReader from InputStreamReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;

            int wCount = 1;
            int patternsID = longPhrasesMap.size() + 1;
            System.out.println("INIT: "+patternsID);
            int lastWordID = phrasesMap.size() + 1;

            //PASS 1: BUILD LONGPHRASES
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase().trim();
                System.out.println(line);

                StringTokenizer stok = new StringTokenizer(line, ",");

                while (stok.hasMoreTokens()) {
                    String pattern = stok.nextToken().toLowerCase().trim();
                    pattern = pattern.trim();

                    if (pattern.length() < 1) {
                        continue;
                    }
                    if (pattern.startsWith("\"")) {
                        pattern = pattern.substring(1, pattern.length());
                    }
                    if (pattern.endsWith("\"")) {
                        pattern = pattern.substring(0, pattern.length() - 1);
                    }

                    System.out.println("  Pat:" + pattern);
                    //ADD to patetrnsMap
                    //System.out.println("   LP:" + pattern);
                    Integer PID = new Integer(patternsID);
                    if (!longPhrasesMap.containsKey(pattern)) {
                        System.out.println(" Adding New Phrase: " + pattern + " ID:" + PID);
                        longPhrasesMap.put(pattern, PID);
                        longPhrasesMapRev.put(PID, pattern);
                        patternsID++;
                    } else {
                        PID = (Integer) longPhrasesMap.get(pattern);
                        System.out.println(" Existing Phrase: " + pattern + " ID:" + PID);

                    }

                }

            }
            
            

            br.close();
            fis.close();
            
            System.out.println("SECOND PASS");
            
            //PASS2 Build Phrases Map
            //Construct BufferedReader from InputStreamReader
            File csvFile2 = new File("/Users/phantom/Documents/EWB/input/Test.csv");
            FileInputStream fis2 = new FileInputStream(csvFile2);
            BufferedReader br2 = new BufferedReader(new InputStreamReader(fis2));
            line = null;

            wCount = 1;
            //patternsID = longPhrasesMap.size() + 1;
            //lastWordID = phrasesMap.size() + 1;

            //PASS 2: BUILD LONGPHRASES
            ArrayList lineList = null;
            
            while ((line = br2.readLine()) != null) {
                line = line.toLowerCase().trim();
                System.out.println(line);
                lineList  = new ArrayList();
                
                StringTokenizer stok = new StringTokenizer(line, ",");

                while (stok.hasMoreTokens()) {
                    String pattern = stok.nextToken().toLowerCase().trim();
                    pattern = pattern.trim();

                    if (pattern.length() < 1) {
                        continue;
                    }
                    if (pattern.startsWith("\"")) {
                        pattern = pattern.substring(1, pattern.length());
                    }
                    if (pattern.endsWith("\"")) {
                        pattern = pattern.substring(0, pattern.length() - 1);
                    }

                    
                    Integer PID2 = (Integer) longPhrasesMap.get(pattern);
                    System.out.println("  Pat:" + pattern+" ID:"+PID2);
                    
                    lineList.add(PID2);
                    //ADD to patetrnsMap
                    //System.out.println("   LP:" + pattern);
                    //GET PID
                    //Integer PID = (Integer) longPhrasesMap.get(pattern);

                }
                System.out.println(" LINE LIST SIZE:"+lineList.size());
                //ADD REL 
                for(int i=0;i<lineList.size();i++){
                    
                    Integer PID2 = (Integer) lineList.get(i);
                    
                    ArrayList tArray = new ArrayList();
                    
                    if(longPhrasesRelMap.containsKey(PID2)){
                        
                        ArrayList tList = (ArrayList)longPhrasesRelMap.get(PID2);
                        for(int j=0;j<tList.size();j++){
                            Integer TID = (Integer)tList.get(j);
                            System.out.println(""+TID);
                            tArray.add(TID);
                            //lineList.add(TID);
                            
                        }
                        tList.addAll(lineList);
                        longPhrasesRelMap.remove(PID2);
                        
                        //lineList.addAll(tArray);
                        System.out.println("---PID:"+PID2+" "+lineList.size());
                        
                        longPhrasesRelMap.put(PID2, tList);
                       
                    }
                    else{
                        System.out.println("--+PID:"+PID2+" "+lineList.size());
                        longPhrasesRelMap.put(PID2, lineList);
                        
                    }
                    
                }
                
            }
            lineList.clear();
            System.out.println("Closing streams...");
            br.close();
            fis.close();
            
            //SANITY CHECK
            if (longPhrasesMap.containsKey("involve all")) {
                System.out.println("SANITY CHECK: FOUND");
                Integer LPID = (Integer) longPhrasesMap.get("involve all");
                System.out.println("Phrase ID "+LPID);
                if (longPhrasesRelMap.containsKey(LPID)) {
                    ArrayList RMAP = (ArrayList)longPhrasesRelMap.get(LPID);
                    System.out.println("Related Phrases "+RMAP.size());
                    for(int i=0;i<RMAP.size();i++){
                        Integer QID = (Integer)RMAP.get(i);
                        String longPhrase = (String) longPhrasesMapRev.get(QID);
                        //System.out.println(""+longPhrase);
                        System.out.println(""+QID+" - "+longPhrase);
                    }
                    //obj.put("Related PIDs", RMAP);
                }
            }
            
            

            System.out.println("Commiting DB...");
            //db.commit();
            //db0.commit();
            //db2.commit();
            //db20.commit();
            //db3.commit();
            //db4.commit();

            //db.close();
            //db0.close();
            //db2.close();
            //db20.close();
            //db3.close();
            //db4.close();

            System.out.println("Saved: " + wCount);
        } catch (IOException ex) {
            Logger.getLogger(EWBCSVImporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
