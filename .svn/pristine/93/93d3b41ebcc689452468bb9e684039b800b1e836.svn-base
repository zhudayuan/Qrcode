package com.maywidehb.qrpush.service.Impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.maywidehb.qrpush.config.Logs;
import com.maywidehb.qrpush.config.QrConstant;
import com.maywidehb.qrpush.entity.Result;
import com.maywidehb.qrpush.service.QrManager;
import com.maywidehb.qrpush.service.push.PushManager;
import com.maywidehb.qrpush.utils.RedisUtil;
import com.mpush.api.push.PushCallback;
import com.mpush.api.push.PushResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QrCodeImpl  implements QrManager {

    @Resource
    private PushManager pushManager;


    /**
     *
     */
    @Override
    public Result pushCodeToClient(String cardid,String code0,String qrid,String serviceid) throws Exception {
        if(StringUtils.isBlank(cardid)||StringUtils.isBlank(code0)){
            throw new Exception("卡号和code不能为空");
        }
        boolean flag = true;
        int code = Integer.valueOf(code0);
//        //检查
//        if((code == 2||code == 3)&&StringUtils.isNotBlank(qrid)){
//            flag=true;
//        }else if (code==6&&StringUtils.isNotBlank(serviceid) ){
//            flag=true;
//        }else if(code==7){
//            flag=true;
//        }
        if((code == 2||code == 3)&&StringUtils.isBlank(qrid)){
            flag = false;
        }else if (code == 6 && StringUtils.isBlank(serviceid) ){
            flag = false;
        }

        if(!flag){
            throw new Exception("参数不正确,请检查");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("qrid",qrid);
        jsonObject.put("serviceid",serviceid);

        return push(cardid, jsonObject.toString());

    }

    @Override
    public Result pushQrList(List<String> jsonList) throws Exception {
        Result ret = new Result();
        PushResult pushResult;
        String userId;
//        String errorMsg = "";
//        String serialno = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
//        serialno += ((int)(Math.random()*900)+100);
        int timeout = 20000;
        int num = 0;
        long sTime=System.currentTimeMillis();
        Statistics statistics = new Statistics();
        for(String ss:jsonList){
            JSONObject qrs = JSON.parseObject(ss);
            String channelIds = qrs.getString("CHANNELID");
            for(int i=0; channelIds!=null && i<channelIds.split(",").length;i++){
                JSONObject qr = JSON.parseObject(ss);
                userId = qr.getString("LOGICDEVNO");
                qr.put("CHANNELID",channelIds.split(",")[i]);

                num++;
                try {
                    qr = dealQrCode(qr);

                    //少量推送采用同步,数量大推送采用异步推送
                    if(jsonList.size() < 2){
                        pushResult = pushManager.send(userId,qr.toString(),null);
                        statistics.add(pushResult.resultCode);
                    }else{
                        pushManager.futurePush(userId,qr.toString(),timeout,new PushCallback() {
                            @Override
                            public void onResult(PushResult result) {
                                statistics.add(result.resultCode);
                                Logs.QRPR.info("send msg {},userId={}",result.getResultDesc(), result.userId);
                            }
                        });
                    }
                }catch (Exception e){
                    statistics.add(2);
                    Logs.QRPR.info("send msg failure {},userId={}",e.getMessage(), userId);
//                    errorMsg += ",userId="+userId+"~"+e.getMessage();
                }
            }
        }
        int j = timeout/10;
        while(statistics.totalNum.get() < num && j>0){
            j--;
            Thread.sleep(10);
        }

        statistics.totalNum.set(num);
        JSONObject rt =  new JSONObject();
//        if(errorMsg!="")
//            rt.put("errorMsg",errorMsg);
        rt.put("costTime",System.currentTimeMillis()-sTime);
        rt.put("result",statistics.toString());
//        rt.put("serialno",serialno);

        ret.setCode(0);
        ret.setMsg("success");
        ret.setData(rt);
        return ret;
    }

    private JSONObject dealQrCode(JSONObject qr) throws Exception {
        JSONObject qrscene = new JSONObject();
        long startTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(qr.getString("QRTIME")).getTime();
        if(startTime < System.currentTimeMillis()){
            throw new Exception("QRTIME="+qr.getString("QRTIME")+" Less than the current time");
        }
        String [] scene =qr.getString("QRURL").split("[?]");
        if(scene!=null && scene.length>1){
            String [] p =scene[1].split("&");
            for(int i=0;i < p.length ;i++){
                if(StringUtils.isNotBlank(p[i])&&p[i].contains("=")){
//                    qr.put(p[i].split("=")[0], p[i].split("=")[1]);
                    qrscene.put(p[i].split("=")[0], p[i].split("=")[1]);
                }
            }
        }
        qrscene.put("cardid", qr.get("LOGICDEVNO"));
        qrscene.put("serviceid", qr.getLongValue("CHANNELID"));

        Long qrid = RedisUtil.incr("QRPUT_QRID",2);
        qr.put("qrid", qrid);
        qr.put("QRURL", qr.getString("QRURL").split("[?]")[0]+"?qrid="+qrid);
        qr.put("cardid", qr.get("LOGICDEVNO"));
        qr.put("serviceid", qr.getLongValue("CHANNELID"));
        qr.put("workhours", qr.get("WORKTIMES"));
        qr.put("aftertime", qr.get("AFTERTIMES"));
        qr.put("COUNTDOWN", (0==qr.getInteger("COUNTDOWN")));

        qr.remove("WORKTIMES");
        qr.remove("LOGICDEVNO");
        qr.remove("CHANNELID");
        qr.remove("AFTERTIMES");
        qr.put( "startTime",startTime);
        qr.put("timestamp", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        qr.put("code", 1);
        if(!qr.containsKey("qrtype")){
            qrscene.put("qrtype", QrConstant.TCODE.COUPON_AD);
            qr.put("qrtype",QrConstant.TCODE.COUPON_AD) ;
        }
        JSONObject jsonObject = getQrByAID(qr.getString("AID"));
        //转换二维码边距
        qr.put("QRHP", jsonObject.getIntValue("backhp")+qr.getIntValue("QRHP"));
        qr.put("QRWP", jsonObject.getIntValue("backwp")+qr.getIntValue("QRWP"));

        jsonObject  = mergeSameKeys(jsonObject,qr);

        int time = (int)((startTime-System.currentTimeMillis()+qr.getLongValue("aftertime")+jsonObject.getLong("workhours"))/1000+600);
        RedisUtil.setex("QRID_"+qrid,time, qrscene.toString(), 5);
        //记录二维码内容qrid
        Logs.QRCODE.info(jsonObject.toString());
        return jsonObject;
    }

    private  JSONObject getQrByAID(String AID) throws Exception {

        String rule = "";
        rule = RedisUtil.getKeyByDbidx("R_"+AID, 1);
        if(StringUtils.isBlank(rule)){
            if(QrConstant.PARAM.BI_AID_M.equals(AID)){
                rule = QrConstant.PARAM.QR_RULE_M;
            }else if(QrConstant.PARAM.BI_AID_R.equals(AID)){
                rule = QrConstant.PARAM.QR_RULE_R;
            }else{
                throw new Exception("广告位不存在,请检查");
            }
        }

        return JSON.parseObject(rule);
    }
    /**
     * json key相同值覆盖(忽略大小写)
     * @param json1 原
     * @param json2 取
     * @return json1
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static JSONObject mergeSameKeys(JSONObject json1,JSONObject json2) throws JSONException {
        if(json2 == null || json2.entrySet().isEmpty()){
            return json1;
        }
        for (Map.Entry<String, Object> entry : json2.entrySet()) {
            json1.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        return json1;
    }

    public Result pushReturn(String cardId,String serviceID,String tvTime,String qrId) throws Exception {
        if(StringUtils.isBlank(tvTime)||StringUtils.isBlank(serviceID)||StringUtils.isBlank(cardId)){
            throw new Exception("参数不能为空");
        }
        if(Long.parseLong(tvTime) > 0){
            String key = "V_"+cardId+"_"+serviceID;
            long time = RedisUtil.ttl(key,3);
            if(time == -2){
                RedisUtil.setex(key, (int)(Long.parseLong(tvTime)/1000), tvTime, 3);
            }else{
                RedisUtil.expire(key, (int)(time+(Long.parseLong(tvTime)/1000)),  3);
            }
        }else if(-1 == Long.parseLong(tvTime)){
            //订购频道,默认设置10分钟
            String key = "V_"+cardId+"_"+serviceID;
            RedisUtil.setex(key, 600, tvTime, 3);

        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aftertime", Long.parseLong(tvTime));
        jsonObject.put("qrid", qrId);
        jsonObject.put("code", 3);
        return push(cardId, jsonObject.toString());

	}

	
	
	public Result push(String userId, String message) throws Exception {
		if(StringUtils.isBlank(userId)){
			throw new Exception("userId不能为空");
		}

        PushResult retResult = pushManager.send(userId,message,null);

        Result ret = new Result();
        if(null != retResult && 1 == retResult.resultCode){
            ret.setCode(0);
            ret.setMsg(retResult.getResultDesc());
//            ret.setData(retResult.retData);
        }else if(null != retResult){
            ret.setCode(1);
            ret.setMsg(retResult.getResultDesc());
            ret.setData(retResult);
        }
		return ret;
	}



    private static class Statistics {
        final AtomicInteger successNum = new AtomicInteger();
        final AtomicInteger failureNum = new AtomicInteger();
        final AtomicInteger offlineNum = new AtomicInteger();
        final AtomicInteger timeoutNum = new AtomicInteger();
        final AtomicInteger totalNum = new AtomicInteger();
        AtomicInteger[] counters = new AtomicInteger[]{successNum, failureNum, offlineNum, timeoutNum};

        private void add(int code) {
            totalNum.incrementAndGet();
            counters[code - 1].incrementAndGet();
        }

        @Override
        public String toString() {
            return "{" +
                    "totalNum=" + totalNum +
                    ", successNum=" + successNum +
                    ", offlineNum=" + offlineNum +
                    ", timeoutNum=" + timeoutNum +
                    ", failureNum=" + failureNum +
                    '}';
        }
    }
}
