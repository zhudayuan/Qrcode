package com.mpush.test;

import com.mpush.api.Client;
import com.mpush.api.ClientListener;
import com.mpush.client.ClientConfig;
import com.mpush.util.DefaultLogger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCghPCWCobG8nTD24juwSVataW7iViRxcTkey/B792VZEhuHjQvA3cAJgx2Lv8GnX8NIoShZtoCg3Cx6ecs+VEPD2fBcg2L4JK7xldGpOJ3ONEAyVsLOttXZtNXvyDZRijiErQALMTorcgi79M5uVX9/jMv2Ggb2XAeZhlLD28fHwIDAQAB";
    private static  String allocServer = "http://172.31.252.33:9999/";
    public static void main(String[] args) throws Exception {
        int count = 1;
        String serverHost = "127.0.0.1";
//        String serverHost = "120.79.229.253";
        String  client_Ip ="";
        int port=3000;
        int sleep = 1000;
        int j = 0;

        if (args != null && args.length > 0) {
            count = Integer.parseInt(args[0]);

            if (args.length > 1) {
                j  = Integer.parseInt(args[1]);
            }
            if (args.length > 2) {
                client_Ip = args[2];
                System.setProperty("client_Ip",client_Ip);
            }
            if (args.length > 3) {
                serverHost = args[3];
                allocServer = null;
            }
        }
        System.out.println("参数:数量:"+count+",休眠时间:"+sleep+",命名格式:userid-"+j+"_i,客户端ip:"+client_Ip+"服务器ip:"+serverHost);

        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        ClientListener listener = new L(scheduledExecutor);
        Client client = null;
        String cacheDir = System.getProperty("user.dir");
        System.out.println("cacheDir:"+cacheDir);

        Thread.sleep(3000);

        String  userid="8270104243895428";
        for (int i = 0; i < count; i++) {
            userid = "userid-"+j+"_"+i;
            client = ClientConfig
                    .build()
                    .setPublicKey(publicKey)
                    .setAllotServer(allocServer)
                    .setServerHost(serverHost)
                    .setServerPort(port)
                    .setDeviceId("deviceId-test-" +j+"_"+i)
                    .setOsName("android")
                    .setOsVersion("6.0")
                    .setClientVersion("2.0")
                    .setUserId(userid)
                    .setTags(client_Ip)
                    .setSessionStorageDir(cacheDir+"/data/" +j+"_"+i)
                    .setLogger(new DefaultLogger())
                    .setLogEnabled(true)
                    .setEnableHttpProxy(true)
                    .setClientListener(listener)
                    .create();
            client.start();
            Thread.sleep(sleep);
        }
    }

    public static class L implements ClientListener {
        private final ScheduledExecutorService scheduledExecutor;
        boolean flag = true;

        public L(ScheduledExecutorService scheduledExecutor) {
            this.scheduledExecutor = scheduledExecutor;
        }

        @Override
        public void onConnected(Client client) {
            flag = true;
        }

        @Override
        public void onDisConnected(Client client) {
            flag = false;
        }

        @Override
        public void onHandshakeOk(final Client client, final int heartbeat) {
            scheduledExecutor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    client.healthCheck();
                }
            }, 10, 10, TimeUnit.SECONDS);

            //client.push(PushContext.build("test"));

        }

        @Override
        public void onReceivePush(Client client, byte[] content, int messageId) {
            if (messageId > 0) client.ack(messageId);
        }

        @Override
        public void onKickUser(String deviceId, String userId) {

        }

        @Override
        public void onBind(boolean success, String userId) {

        }

        @Override
        public void onUnbind(boolean success, String userId) {

        }
    }
}