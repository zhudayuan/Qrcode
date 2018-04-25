package com.maywidehb.qrpush.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public interface CF {
    Config cfg = load();

    static Config load() {

        Config config = ConfigFactory.load();//扫描加载所有可用的配置文件

//        File file = new File("D:\\WorkSpace\\qrcode\\qrpush\\src\\test\\resources\\qr.conf");
//        if (file.exists()) {
//            Config custom = ConfigFactory.parseFile(file);
//            config = custom.withFallback(config);
//        }

        //加载自定义配置, 值来自jvm启动参数指定-Dqr.conf
        if (config.hasPath("qr.conf")) {
            File file = new File(config.getString("qr.conf"));
            if (file.exists()) {
                Config custom = ConfigFactory.parseFile(file);
                config = custom.withFallback(config);
            }
        }
        String home = System.getProperty("user.dir");
        if(config.hasPath("qr.home")){
            home = config.getString("qr.home");//加载自定义配置, 值来自jvm启动参数指定-Dqr.home
            System.setProperty("qr.home", home);
            System.setProperty("mp.home", home);
        }

        if(config.hasPath("server.port")){
            System.setProperty("server.port", config.getString("server.port"));
        }
        if(config.hasPath("mp_conf_path")){
            //设置mpush配置文件路径
            System.setProperty("mp.conf", home+"/"+config.getString("mp_conf_path"));
        }

        return config;
    }

    interface qr {
        String home = CF.cfg.getString("qr.home");
        interface log {
            String log_root_level = CF.cfg.getString("qr.log_root_level");
            String log_conf = home+"/"+CF.cfg.getString("qr.log_conf");
        }
    }


    interface redis {
        Config redis = CF.cfg.getObject("qr.redis").toConfig();
        String host = redis.getString("host");
        int port = redis.getInt("port");
        String password = redis.getString("password");
        int maxTotal = redis.getInt("pool.max-total");
        int maxWait = redis.getInt("pool.max-wait");
        int maxIdle = redis.getInt("pool.max-idle");
        int minIdle = redis.getInt("pool.min-idle");
        int timeout = redis.getInt("timeout");
    }





}