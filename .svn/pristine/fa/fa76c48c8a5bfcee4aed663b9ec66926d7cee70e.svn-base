package com.maywidehb.qrpush.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maywidehb.qrpush.entity.Result;
import com.maywidehb.qrpush.service.QrManager;
import com.maywidehb.qrpush.service.push.PushManager;
import com.maywidehb.qrpush.utils.Des3;
import com.mpush.api.push.PushResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.net.URLDecoder.decode;

/**
 *
 */
@RestController
@RequestMapping("/push")
public class PushAction {

    @Resource
    private PushManager pushManager;

    private Result result = new Result();

    @Resource
    private QrManager manager;
    /**
     */
    @RequestMapping("/pushReturn")
    public Result pushReturn(@RequestBody Map<String,String> reqMap)throws Exception{
        String cardid =  reqMap.get("cardid");
        String serviceid = reqMap.get("serviceid");
        String tvtime = reqMap.get("tvtime");
        String qrid =  reqMap.get("qrid");

        cardid = Des3.hexdecrypt(cardid);
        return manager.pushReturn(cardid,serviceid,tvtime,qrid);
    }

    @RequestMapping("/pushCodeToClient")
    public Result pushCodeToClient(HttpServletRequest request)throws Exception{
//        code 1 推送要展示的二维码 2 参数(qrid) 取消等待展示的二维码 3 参数(qrid) 取消正在显示的二维码, 告知下次请求时间
//         4  客户端告诉服务端某个码可以展示  5 告诉服务端某个二维码停止展示
//        6  参数(serviceid)清除该卡号某个频道所有的二维码, 包括在展示和未展示的
//        7  清除该卡号所有的二维码, 包括在展示和未展示的
        String cardid = request.getParameter("cardid");
        String code = request.getParameter("code");//json字符串
        String qrid = request.getParameter("qrid");//json字符串
        String serviceid = request.getParameter("serviceid");//json字符串

        cardid = Des3.hexdecrypt(cardid);
        return manager.pushCodeToClient(cardid,code,qrid,serviceid);
    }

    @RequestMapping("/pushQrcode")
    public Result pushQrList(@RequestBody Map<String,Object> reqMap)throws Exception{

        if(reqMap.get("QRLIST") == null ){
            throw new Exception("参数QRLIST为空");
        }
        List<String> jsonList = new ArrayList<>();
        Iterator iterator = ((List) reqMap.get("QRLIST")).iterator();
        while(iterator.hasNext()){
            String qr = JSON.toJSONString(iterator.next());
            jsonList.add(qr);
        }

        return manager.pushQrList(jsonList);
    }

    @RequestMapping()
    public String test() {
        return "/push";
    }

    @RequestMapping(value="/sendByBroadcast")
    public PushResult sendByBroadcast(HttpServletRequest request) throws Exception{

        String tags = request.getParameter("tags");
        String condition = request.getParameter("condition");
        String message = request.getParameter("message");
        if(StringUtils.isBlank(message)){
            throw  new Exception("message不能为空");
        }
//        Des3.hexencrypt(message);

        return pushManager.sendBroadcast(tags==null?null:Arrays.asList(tags), condition, message);
    }


    @RequestMapping(value="/sendByUserIds")
    public PushResult sendByUserIds(HttpServletRequest request)  throws Exception{
        String userIds = request.getParameter("userIds");
        String message = request.getParameter("message");
        message = decode(message, "UTF-8");
        return pushManager.send(userIds!=null?Arrays.asList(userIds.split(",")):null,message);
    }

    @RequestMapping(value="/testPushQrcode")
    public Result testPushQrcode(HttpServletRequest request,@RequestBody Map<String,Object> reqMap)throws Exception{

        String is = request.getParameter("userId_i");
        String js = request.getParameter("userId_j");

        if(reqMap.get("QRLIST") == null ){
            throw new Exception("参数QRLIST为空");
        }
        List<String> jsonList = new ArrayList<>();
        Iterator iterator = ((List) reqMap.get("QRLIST")).iterator();

        while(iterator.hasNext()){
            String qr = JSON.toJSONString(iterator.next());
//            jsonList.add(qr);
            if(StringUtils.isNotBlank(is)&&StringUtils.isNotBlank(js)){
                JSONObject json = JSON.parseObject(qr);
                String [] user_is = is.split(",");
                for(int i=0;i<user_is.length;i++){
                    for(int j=Integer.parseInt(js);j>0;j--){
                        json.put("LOGICDEVNO","userid-"+user_is[i]+"_"+j);
                        jsonList.add(json.toJSONString());
                    }
                }
            }
        }



        if(jsonList.size()<1){
            throw new Exception("参数为空");
        }
        return manager.pushQrList(jsonList);
    }


}
