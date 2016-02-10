
package com.dlrs.statement;

import java.io.Serializable;

/**
 *
 * @author Frank
 */
public class Statement implements Serializable{
    
    private String statementUUID = null;
    
    private String actorJSON = null;
    private String verbJSON = null;
    private String objectJSON = null;
    
    private long timestamp = -1;
    
    public void Statement(){
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    

    public String getStatementUUID() {
        return statementUUID;
    }

    public void setStatementUUID(String statementUUID) {
        this.statementUUID = statementUUID;
    }

    public String getActorJSON() {
        return actorJSON;
    }

    public void setActorJSON(String actorJSON) {
        this.actorJSON = actorJSON;
    }

    public String getVerbJSON() {
        return verbJSON;
    }

    public void setVerbJSON(String verbJSON) {
        this.verbJSON = verbJSON;
    }

    public String getObjectJSON() {
        return objectJSON;
    }

    public void setObjectJSON(String objectJSON) {
        this.objectJSON = objectJSON;
    }
    
    
    
}
