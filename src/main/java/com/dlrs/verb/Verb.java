package com.dlrs.verb;

import java.io.Serializable;

/**
 *
 * @author Frank
 */
public class Verb implements Serializable{
    
    private int verbID = -1;
    
    private String verbJSON = null;

    public int getVerbID() {
        return verbID;
    }

    public void setVerbID(int verbID) {
        this.verbID = verbID;
    }

    public String getVerbJSON() {
        return verbJSON;
    }

    public void setVerbJSON(String verbJSON) {
        this.verbJSON = verbJSON;
    }
    
    
}
