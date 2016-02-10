/*
 * Copyright 2016 Frank Jennings
 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 
 * http://www.apache.org/licenses/LICENSE-2.0
 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dlrs;

/**
 *
 * @author phantom
 */
import com.dlrs.conf.DefaultConf;
import com.dlrs.executor.EWBExecutorStrategy;
import com.dlrs.handlers.http.DLRSContextHandler;
import com.dlrs.pr.PRManager;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

public class DLRSServer {

    private static DefaultConf defaultConf = new DefaultConf();
    private static HTreeMap actorMap = null;
    private static HTreeMap verbMap = null;
    private static HTreeMap objectMap = null;
    private static HTreeMap statementMap = null;
    private static HTreeMap actorStatementMap = null;
    
    private static DB db;
    private static HttpServer server = null;

    public static void attachShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    PRManager.print(defaultConf, "Shutting down...", 1);
                    db.commit();
                    db.close();
                    server.stop(0);
                    PRManager.print(defaultConf, "Good Bye!", 1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    public static void loadConfigFile(File configFile) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(configFile);
            //Construct BufferedReader from InputStreamReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("#")) {
                    continue;
                }
                if (line.indexOf("=") != -1) {
                    StringTokenizer stok = new StringTokenizer(line, "=");
                    while (stok.hasMoreTokens()) {
                        String configKey = stok.nextToken().trim();
                        String configValue = stok.nextToken().trim();

                        if (configKey.equals("dlrsdefaultcontext")) {
                            defaultConf.setEWBDefaultContext(configValue);
                        }
                        if (configKey.equals("dlrsport")) {
                            defaultConf.setEWBPort(Integer.parseInt(configValue));
                        }
                        if (configKey.equals("dlrsstrategy")) {
                            defaultConf.setEWBStrategy(configValue);
                        }
                        if (configKey.equals("dlrslogstrategy")) {
                            defaultConf.setEWBLogStrategy(configValue);
                        }
                        if (configKey.equals("dlrsstorepath")) {
                            defaultConf.setEWBStorePath(configValue);
                        }
                    }
                }
            }

            br.close();
        } catch (Exception ex) {
            System.out.println("FATAL - DLRS 1.0 configuration file error: " + ex.getMessage());
            System.exit(0);
        }

    }

    public static void main(String[] args) throws Exception {
        try {
            attachShutDownHook();
            //First load the configuration file
            File base = new File(DLRSServer.class
                    .getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
            File configFile = new File(base, "dlrsconfig.properties");

            if (!configFile.exists()) {
                System.out.println("FATAL - DLRS 1.0 configuration file not found! Shutting down!");
                System.exit(0);
            } else {
                //Load the configuration file
                loadConfigFile(configFile);

            }

            PRManager.print(defaultConf, "DLRS 1.0", 1);
            PRManager.print(defaultConf, "Loading config file..." + configFile.getName(), 2);
            PRManager.print(defaultConf, "Starting default server...", 2);
            PRManager.print(defaultConf, "Listening on port..." + defaultConf.getEWBPort(), 2);
            server = HttpServer.create(new InetSocketAddress(defaultConf.getEWBPort()), 0);

            //LOAD DATA //THIS PATH SHOULD COME FROM CONF
            PRManager.print(defaultConf, "Loading EWB data...", 2);

            //db = DBMaker.newFileDB(new File("EWB_PasteBin.dat")).make();

            db = DBMaker
                    .newFileDB(new File("DLRS_DATA.dat"))
                    .closeOnJvmShutdown()
                    .transactionDisable()
                    .make();
            
            actorMap = db.getHashMap("actorMap");
            verbMap = db.getHashMap("verbMap");
            objectMap = db.getHashMap("objectMap");
            statementMap = db.getHashMap("statementMap");
            actorStatementMap = db.getHashMap("actorStatementMap");
            
            
            PRManager.print(defaultConf, "Loading DLRS data...DONE", 2);

            PRManager.print(defaultConf, "Enabling default context...", 2);
            //server.createContext(defaultConf.getEWBDefaultContext(), new DefaultContextHandler(defaultConf, phrasesMap, phrasesMapRev,longPhrasesMap, longPhrasesMapRev, longPhrasesRelMap, phrasesRelMap));
            server.createContext(defaultConf.getEWBDefaultContext(), new DLRSContextHandler(db, defaultConf, actorMap,verbMap,objectMap,statementMap, actorStatementMap));

//server.setExecutor(null); // creates a default executor
            //server.setExecutor(Executors.newCachedThreadPool());
            PRManager.print(defaultConf, "Enabling default executor strategy..." + defaultConf.getEWBStrategy(), 2);
            server.setExecutor(EWBExecutorStrategy.getStrategy(defaultConf.getEWBStrategy()));
            server.start();

            PRManager.print(defaultConf, "DLRS Ready.", 2);
        } catch (Exception ex) {
            PRManager.print(defaultConf, "DLRS Error: " + ex.getMessage(), 1);
            ex.printStackTrace();
        }

    }
}
