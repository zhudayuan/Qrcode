package com.maywidehb.qrpush.service.push.impl;

import com.google.common.collect.Sets;
import com.maywidehb.qrpush.config.Logs;
import com.maywidehb.qrpush.service.push.PushManager;
import com.mpush.api.Constants;
import com.mpush.api.push.AckModel;
import com.mpush.api.push.PushCallback;
import com.mpush.api.push.PushContext;
import com.mpush.api.push.PushResult;
import com.mpush.api.push.PushSender;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.FutureTask;




@Service
public class PushServiceImpl implements PushManager {

    @Resource
    private PushSender mpusher;
    public PushServiceImpl(){
        if(null == mpusher){
            mpusher = PushSender.create();
            mpusher.start().join();
        }
    }

    @Override
    public PushResult sendBroadcast(List<String> tags, String condition, String message) throws Exception{
        return send(null,null, AckModel.NO_ACK, message,true,tags,
                condition,30000 ,null);
    }


    @Override
    public PushResult send(String userId, String message, PushCallback callback) throws Exception{
        if(StringUtils.isEmpty(userId)){
            throw new Exception("根据userId推送消息,userId不能为空");
        }
        return send(userId,null,AckModel.AUTO_ACK, message,false,null,
                null,30000 ,callback);
    }


    @Override
    public PushResult send(List<String> userIds ,String message) throws Exception{
        return send(null,userIds,AckModel.AUTO_ACK, message,false,null,
                null,60000 ,null);
    }

    
    /**
     *推送优先级 userId > userIds > Broadcast(true)
     * @param userId  单用户
     * @param userIds  多用户
     * @param ackModel 客户端应答模式
     * @param message  推送内容
     * @param Broadcast 是否广播推送
     * @param tags  标签
     * @param condition "tags&&tags.indexOf('test')!=-1"
     * @param timeout 6000
     * @param callback 回调函数
     * @return boolean
     */
    private PushResult send(String userId, List<String> userIds, AckModel ackModel,
                        String message, boolean Broadcast, List<String> tags,
                        String condition, int timeout, PushCallback callback) throws Exception{

        byte[] content = message.getBytes(Constants.UTF_8);

        PushContext context = new PushContext(content)
                .setAckModel(ackModel)
                .setUserId(userId)
                .setUserIds(userIds)
                .setBroadcast(Broadcast)
                .setTags(tags==null?null:Sets.newHashSet(tags))
                .setCondition(condition)
                .setTimeout(timeout)
                .setCallback(callback);

        return send2(context, 1);
    }


    //同步推送,等待返回结果
    private PushResult send2(PushContext context, int num ) throws Exception{


        FutureTask<PushResult> future = mpusher.send(context);
        //不是单用户推送,不返回推送结果
        if(context.getUserId() == null){
            return future.get();
        }
        PushResult pushResult = new PushResult(2);
        boolean flag = true;

        while(flag){
            if(future.isDone()){
                pushResult = future.get();
                if(PushResult.CODE_TIMEOUT == pushResult.resultCode && num > 0){
                    Logs.QRPR.info("send msg {},userId={},timeLine={}",
                            pushResult.getResultDesc(), context.getUserId(), Arrays.toString(pushResult.timeLine));
                    num--;
                    return send2(context, num);
                }
                flag = false;
            }else{
                Thread.sleep(2);
            }
        }
        Logs.QRPR.info("send msg {},userId={}",pushResult.getResultDesc(), context.getUserId());

        return pushResult;
    }





    @Override
    public FutureTask futurePush(String userId, String message, int timeout, PushCallback callBack) {

        if(null == mpusher){
            mpusher = PushSender.create();
            mpusher.start().join();
        }
        byte[] content = message.getBytes(Constants.UTF_8);

        PushContext context = new PushContext(content)
                .setAckModel(AckModel.NO_ACK)
                .setUserId(userId)
                .setTimeout(timeout)
                .setCallback(callBack);

        return mpusher.send(context);
    }

}
