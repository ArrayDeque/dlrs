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
