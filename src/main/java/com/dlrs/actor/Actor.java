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

package com.dlrs.actor;

/**
 *
 * @author Frank
 */
public class Actor {
    
    private int actorID = -1;
    
    private String actorJSON = null;

    public int getActorID() {
        return actorID;
    }

    public void setActorID(int actorID) {
        this.actorID = actorID;
    }

    public String getActorJSON() {
        return actorJSON;
    }

    public void setActorJSON(String actorJSON) {
        this.actorJSON = actorJSON;
    }
    
    
    
   
    
}
