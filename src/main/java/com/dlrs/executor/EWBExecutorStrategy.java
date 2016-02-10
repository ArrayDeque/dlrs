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
