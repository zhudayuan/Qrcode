package com.maywidehb.qrpush.controller;

import com.alibaba.fastjson.JSONObject;
import com.maywidehb.qrpush.entity.Result;
import com.maywidehb.qrpush.utils.Des3;
import com.maywidehb.qrpush.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/que")
public class QrAction {

    @RequestMapping("/queByQrid")
    public Result queByQrid(HttpServletRequest request)throws Exception{
        String qrid = request.getParameter("qrid");
        if(StringUtils.isBlank(qrid)){
            throw new Exception("qrid不能为空");
        }
        qrid = Des3.hexdecrypt(qrid);
        String qr = RedisUtil.getKeyByDbidx("QRID_"+qrid.trim(),5);
        Result result = new Result();

        if(StringUtils.isBlank(qr)){
            result.setCode(1);
            result.setMsg("qrid已失效或者不存在");
        }else{
            result.setCode(0);
            result.setMsg("success");
            result.setData(qr);
        }

        return result;
    }


    @RequestMapping("/des3")
    public Result des3(HttpServletRequest request)throws Exception{
        String param = request.getParameter("param");
        String type = request.getParameter("type");

        Result result = new Result();

        String param1 = param;
        JSONObject rt =  new JSONObject();
        try{
            rt.put("param1",Des3.hexdecrypt(param));
            param1 = Des3.hexdecrypt(param);
        }catch (Exception e){
            try {
                rt.put("param1",Des3.decode(param));
                param1 = Des3.decode(param);
            }catch (Exception e1){
                result.setMsg("解密失败");
            }
        }finally{
            rt.put("Des3.hexdecrypt加密:"+param1,Des3.hexencrypt(param1));
            rt.put("Des3.encode加密:"+param1,Des3.encode(param1));
        }


        result.setCode(0);
        result.setMsg("success");
        result.setData(rt);


        return result;
    }
}
