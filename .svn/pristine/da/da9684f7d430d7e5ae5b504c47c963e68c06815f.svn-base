package com.maywidehb.qrpush.controller;

import com.maywidehb.qrpush.service.QueryManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/query")
public class QueryAction {

    @Resource
    private QueryManager queryManager;


    @RequestMapping(value="/getOnlineUserNum")
    public long getOnlineUserNum(HttpServletRequest request) throws Exception{
        String ip = request.getParameter("ip");
        return queryManager.getOnlineUserNum(ip);
    }

    @RequestMapping(value="/getOnlineUserList")
    public List<String> getOnlineUserList(HttpServletRequest request) throws Exception{
        String ip = request.getParameter("ip");
        return queryManager.getOnlineUserList(ip);
    }


}


/*
    redis
    取在线用户列表  zrange  mp:oul:123.56.7.110 0 -1
    在线用户数  zcard mp:oul:123.56.7.110
    判断用户是否在线(返回集合索引值)  zrank mp:oul:123.56.7.110 user-9
    用户是否在线,返回用户路由  hgetall mp:ur:user-1
*/
