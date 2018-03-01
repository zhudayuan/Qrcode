/*
 * (C) Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *   ohun@live.cn (夜色)
 */

package com.maywide.test;

import com.mpush.bootstrap.Main;
import com.mpush.bootstrap.ServerLauncher;


/**
 * Created by yxx on 2016/5/16.
 *
 * @author ohun@live.cn
 */
public class ServerTestMain {

    public static void main(String[] args) {
    	start();
    }



    public static void start() {
        System.setProperty("io.netty.leakDetection.level", "PARANOID");
        System.setProperty("io.netty.noKeySetOptimization", "false");
        Main.main(null);
    }
    
    public static void start2(){
//    	com.mpush.bootstrap.ServerLauncher 
    	
    	ServerLauncher launcher=new ServerLauncher();
    	launcher.start();
    }
}