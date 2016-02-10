/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlrs.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

/**
 *
 * @author Frank
 */
public class PhrasesTxtImporter {

    //Given a directory containing phrases list, generate mapdb
    public static void main(String ar[]) {

        File path = new File("/Users/phantom/Documents/EWB/input1/");
        DB db = DBMaker.newFileDB(new File("/Users/phantom/Documents/EWB/EWB_DATA/EWB_Phrases.dat")).make();
        HTreeMap phrasesMap = db.getHashMap("phrasesMap");
        
        DB db0 = DBMaker.newFileDB(new File("/Users/phantom/Documents/EWB/EWB_DATA/EWB_PhrasesRev.dat")).make();
        HTreeMap phrasesMapRev = db0.getHashMap("phrasesMapRev");

        File files[] = path.listFiles();

        int wCount = 1;

        for (int i = 0; i < files.length; i++) {
            FileInputStream fis = null;
            try {
                File file = files[i];
                fis = new FileInputStream(file);
                //Construct BufferedReader from InputStreamReader
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line = null;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!phrasesMap.containsKey(line)) {
                        phrasesMap.put(line, new Integer(wCount));
                        phrasesMapRev.put(new Integer(wCount),line);
                        wCount++;
                    }

                }

                br.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        db.commit();
        db0.commit();
        db.close();
        db0.close();

        System.out.println("Saved: " + wCount);
    }

}
