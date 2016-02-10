package com.dlrs.object;

import java.io.Serializable;

/**
 *
 * @author Frank
 */
public class LObject implements Serializable{
    
    private int objectID = -1;
    
    private String objectJSON = null;

    public int getObjectID() {
        return objectID;
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    public String getObjectJSON() {
        return objectJSON;
    }

    public void setObjectJSON(String objectJSON) {
        this.objectJSON = objectJSON;
    }
    
    
}
