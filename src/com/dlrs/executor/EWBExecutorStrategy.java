/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlrs.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author phantom
 */
public class EWBExecutorStrategy {

    
    private static int poolSize = 40;

    public static ExecutorService getStrategy(String type) {

        if (type.equals("AUTO")) {
            return Executors.newCachedThreadPool();
        }
        if (type.equals("POOL")) {
            return Executors.newFixedThreadPool(poolSize);
        }
        if (type.equals("NULL")) {
            return null;
        }
        return null;

    }
}
