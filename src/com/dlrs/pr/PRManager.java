/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlrs.pr;

import com.dlrs.conf.DefaultConf;

/**
 *
 * @author phantom
 */
public class PRManager {

    

    public static void print(DefaultConf defaultConf, String message, int spaces) {

        if (!defaultConf.getEWBLogStrategy().equals("SILENT")) {

            for (int i = 0; i < spaces; i++) {
                System.out.print(" ");
            }
            System.out.println(message);
        }
    }
}
