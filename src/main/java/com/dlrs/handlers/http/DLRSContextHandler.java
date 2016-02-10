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

package com.dlrs.handlers.http;

import com.dlrs.actor.Actor;
import com.dlrs.conf.DefaultConf;
import com.dlrs.object.LObject;
import com.dlrs.pr.PRManager;
import com.dlrs.statement.Statement;
import com.dlrs.verb.Verb;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.UUID;
import org.json.JSONObject;
import org.mapdb.DB;
import org.mapdb.HTreeMap;

/**
 *
 * @author Frank
 */
public class DLRSContextHandler implements HttpHandler {

    private DefaultConf defaultConf = null;
    private HTreeMap actorMap = null;
    private HTreeMap verbMap = null;
    private HTreeMap objectMap = null;
    private HTreeMap statementMap = null;
    private HTreeMap actorStatementMap = null;

    private DB db;

    public DLRSContextHandler(DB db, DefaultConf defaultConf, HTreeMap actorMap, HTreeMap verbMap, HTreeMap objectMap, HTreeMap statementMap, HTreeMap actorStatementMap) {
        this.db = db;
        this.defaultConf = defaultConf;

        this.actorMap = actorMap;
        this.verbMap = verbMap;
        this.objectMap = objectMap;
        this.statementMap = statementMap;
        this.objectMap = objectMap;
        this.actorStatementMap = actorStatementMap;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(t.getRequestBody()));
        String line = "";
        StringBuffer buff = new StringBuffer();
        while ((line = in.readLine()) != null) {
            buff.append(line);
        }
        in.close();

        //Print request body
        //System.out.println(buff);
        //System.out.println(defaultConf.getEWBLogStrategy());
        String requestURI = t.getRequestURI().toString().trim();
        String requestMethod = t.getRequestMethod().trim();

        System.out.println("REQUESTURI:" + requestURI + " METHOD:" + t.getRequestMethod() + " BODY:" + buff);
        if (requestURI.contains("/statement") && requestMethod.contains("GET")) {
            //Send the Statement Info
            int index = requestURI.lastIndexOf("/");
            String SID = requestURI.substring(index + 1, requestURI.length());
            System.out.println(SID);
            if (statementMap.containsKey(SID)) {
                Statement stmt = (Statement) statementMap.get(SID);
                ArrayList stmtArray = new ArrayList();
                stmtArray.add(stmt.getStatementUUID());
                stmtArray.add(stmt.getActorJSON());
                stmtArray.add(stmt.getVerbJSON());
                stmtArray.add(stmt.getObjectJSON());
                stmtArray.add(stmt.getTimestamp());
                //Send response
                org.json.JSONObject obj = new org.json.JSONObject();
                obj.put("Statement", stmtArray);
                String response = obj.toString(4);
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                ///NO Records
                String hello = "No statement records found for the actor!";
                t.sendResponseHeaders(200, hello.length());
                OutputStream os = t.getResponseBody();
                os.write(hello.getBytes());
                os.close();
            }
        } else if (requestURI.contains("/findstatementsforactor") && requestMethod.contains("POST")) {
            //GET STATEMENTS FOR ACTORS
            JSONObject jsonObject = new JSONObject(buff.toString().trim());

            JSONObject actorJSON = jsonObject.getJSONObject("actor");
            String actorJSONStr = actorJSON.toString().trim();

            String actorID = null;
            if (actorMap.containsKey(actorJSONStr)) {
                //Existing Actor
                actorID = (String) actorMap.get(actorJSONStr);
                //Populate Actor Statament Map
                if (actorStatementMap.containsKey(actorID)) {
                    ArrayList list = (ArrayList) actorStatementMap.get(actorID);
                    //Send response
                    org.json.JSONObject obj = new org.json.JSONObject();
                    obj.put("Statements", list);
                    String response = obj.toString(4);
                    t.sendResponseHeaders(200, response.length());
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } else {
                    //NO Records
                    String hello = "No statement records found for the actor!";
                    t.sendResponseHeaders(200, hello.length());
                    OutputStream os = t.getResponseBody();
                    os.write(hello.getBytes());
                    os.close();
                }
            } else {
                //NO Actor records
                String hello = "No actor record found";
                t.sendResponseHeaders(200, hello.length());
                OutputStream os = t.getResponseBody();
                os.write(hello.getBytes());
                os.close();
            }

        } else if (requestURI.contains("/statement") && requestMethod.contains("POST")) {
            //POST STATEMENT
            System.out.println("POST STATEMENTS");
            JSONObject jsonObject = new JSONObject(buff.toString().trim());

            JSONObject actorJSON = jsonObject.getJSONObject("actor");
            JSONObject verbJSON = jsonObject.getJSONObject("verb");
            JSONObject objectJSON = jsonObject.getJSONObject("object");

            //Set Actor
            Actor actor = new Actor();
            String actorJSONStr = actorJSON.toString().trim();
            actor.setActorJSON(actorJSONStr);
            //Set Verb
            Verb verb = new Verb();
            String verbJSONStr = verbJSON.toString().trim();
            verb.setVerbJSON(verbJSONStr);
            //Set Object
            LObject obj = new LObject();
            String objectJSONStr = objectJSON.toString().trim();
            obj.setObjectJSON(objectJSONStr);
            //Set Statement
            Statement stmt = new Statement();
            String statementID = UUID.randomUUID().toString();
            stmt.setStatementUUID(statementID);
            stmt.setActorJSON(actorJSONStr);
            stmt.setVerbJSON(verbJSONStr);
            stmt.setObjectJSON(objectJSONStr);
            stmt.setTimestamp(System.currentTimeMillis());
            //Check Actor Map
            String actorID = null;
            if (actorMap.containsKey(actorJSONStr)) {
                //Existing Actor
                actorID = (String) actorMap.get(actorJSONStr);
            } else {
                //New Actor
                actorID = UUID.randomUUID().toString();
                actorMap.put(actorJSONStr, actorID);
            }
            //Populate Actor Statament Map
            if (actorStatementMap.containsKey(actorID)) {
                ArrayList list = (ArrayList) actorStatementMap.get(actorID);
                list.add(statementID);
                actorStatementMap.remove(actorID);
                actorStatementMap.put(actorID, list);
            } else {
                ArrayList list = new ArrayList();
                list.add(statementID);
                actorStatementMap.put(actorID, list);
            }
            //Populate Statement Map
            statementMap.put(statementID, stmt);

            t.sendResponseHeaders(200, statementID.length());
            OutputStream os = t.getResponseBody();
            os.write(statementID.getBytes());
            os.close();
        } else {
            //Default response
            String hello = "Hello from DLRS!";
            t.sendResponseHeaders(200, hello.length());
            OutputStream os = t.getResponseBody();
            os.write(hello.getBytes());
            os.close();

        }

    }
}
