package com.maywidehb.qrpush.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maywidehb.qrpush.entity.QrCodeTv;
import com.maywidehb.qrpush.entity.Result;
import com.maywidehb.qrpush.service.QrManager;
import com.maywidehb.qrpush.utils.Des3;
import com.maywidehb.qrpush.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value="/wappweb/qrtv")
public class SwitchAction {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    public static Logger qrlogger = Logger.getLogger ("Qrput");
    protected final Logger logger = Logger.getLogger(getClass());

    private QrManager manager;

    private Result result=new Result();


    /**
     * 二维码规则  查询换台规则
     * @return
     * @throws Exception
     */
    @RequestMapping("/queSwitchRule")
    public Result queSwitchRule(HttpServletRequest request)throws Exception{
        String cardid = request.getParameter("cardid");
        String serviceID = request.getParameter("serviceID");
        String channelName = request.getParameter("channelName");

        String out="";
        out = "queSwitchRule_"+sdf.format(new Date())+"==>&serviceID="+serviceID+"&channelName="+channelName;

        QrCodeTv retBO = new QrCodeTv();


        try{
            cardid = Des3.decode(cardid);
            out += "&cardid="+cardid;
//            //从redis_0数据库查询RuleID
            String key = "T_"+cardid + "_" + serviceID;
            String ruleID = RedisUtil.getKey(key);
            out=out+"&redis_0,key=" + key + "&value=" + ruleID;

            if(StringUtils.isNotBlank(ruleID) && Long.parseLong(ruleID)>0){
//              2.根据ruleID从redis_2数据库查询Rule
                String key2 = "R_"+ruleID;
                String rule = RedisUtil.getKeyByDbidx(key2,1);
                if(StringUtils.isNotBlank(rule)){
                    JSONObject jsonObject = JSON.parseObject(rule) ;
                    if(StringUtils.isNotBlank(jsonObject.getString("qrurl"))){
                        Long qrid = RedisUtil.incr("QRPUT_QRID",2);
                        jsonObject.put("cardid", cardid);
                        jsonObject.put("serviceid", serviceID);
                        jsonObject.put("channelname", channelName);
                        jsonObject.put("timestamp", sdf.format(new Date()));
                        jsonObject.put("qrid", qrid);
                        jsonObject.put("qrurl", jsonObject.getString("qrurl")+"?qrid="+qrid);

                        RedisUtil.setKey("QRID_"+qrid,
                                cardid+"~"+serviceID+"~"+ruleID+"~"+jsonObject.getLongValue("tvtime"), 5);
                        String oldQrid = RedisUtil.getSet("Q_"+cardid, String.valueOf(qrid), 5);
                        RedisUtil.delKey(oldQrid, 5);

                        qrlogger.info(jsonObject.toString());
                        out += jsonObject.toString();
                        retBO =  (QrCodeTv) JSON.parseObject(jsonObject.toString(), QrCodeTv.class);

                    }
                }
            }



            result.setData(retBO);
            result.setCode(0);
            result.setMsg("调用接口成功");
        }catch (Exception e) {
            e.printStackTrace();
            result.setCode(101); // 服务调用异常
            result.setMsg(e.getMessage());
        }finally{
            System.out.println(out);
            logger.info(out);
        }
        return result;
    }
}
