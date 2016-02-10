/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlrs.conf;

/**
 *
 * @author phantom
 */
public class DefaultConf {
    //ewb/health
    public String EWBDefaultContext = "/hello";
    public int EWBPort = 8678;
    //EWB Strategy - AUTO/POOL/NULL
    public String EWBStrategy = "AUTO";
    //EWB Strategy - ALL/SILENT/DEBUG
    public String EWBLogStrategy = "ALL";
    //MEMORY/DISK
    public String EWBStoreStrategy = "MEMORY";
    
    public String EWBStorePath = "/Users/phantom/Desktop";

    public DefaultConf() {
        
    }

    public String getEWBDefaultContext() {
        return EWBDefaultContext;
    }

    public void setEWBDefaultContext(String EWBDefaultContext) {
        this.EWBDefaultContext = EWBDefaultContext;
    }

    public int getEWBPort() {
        return EWBPort;
    }

    public void setEWBPort(int EWBPort) {
        this.EWBPort = EWBPort;
    }

    public String getEWBStrategy() {
        return EWBStrategy;
    }

    public void setEWBStrategy(String EWBStrategy) {
        this.EWBStrategy = EWBStrategy;
    }

    public String getEWBLogStrategy() {
        return EWBLogStrategy;
    }

    public void setEWBLogStrategy(String EWBLogStrategy) {
        this.EWBLogStrategy = EWBLogStrategy;
    }

    public String getEWBStoreStrategy() {
        return EWBStoreStrategy;
    }

    public void setEWBStoreStrategy(String EWBStoreStrategy) {
        this.EWBStoreStrategy = EWBStoreStrategy;
    }

    public String getEWBStorePath() {
        return EWBStorePath;
    }

    public void setEWBStorePath(String EWBStorePath) {
        this.EWBStorePath = EWBStorePath;
    }


    
    
    
    
}
