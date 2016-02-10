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
