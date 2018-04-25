package com.maywidehb.qrpush.entity;

public class RetResult {
    public String userId;
    public int retCode;
    public String retMsg;
    public long costTime;
    public Object retData;
    public Object sedMsg;



    public static final int CODE_SUCCESS = 1;
    public static final int CODE_FAILURE = 2;
    public static final int CODE_OFFLINE = 3;
    public static final int CODE_TIMEOUT = 4;




    public RetResult setSedMsg(Object sedMsg) {
        this.sedMsg = sedMsg;
        return this;
    }

    public RetResult setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public RetResult setRetCode(int retCode) {
        this.retCode = retCode;
        return this;
    }

    public RetResult setRetMsg(String retMsg) {
        this.retMsg = retMsg;
        return this;

    }
    public RetResult setRetData(Object retData) {
        this.retData = retData;
        return this;
    }
    public RetResult setCostTime(long costTime) {
        this.costTime = costTime;
        return this;
    }
    public Object getRetData() {
        return retData;
    }
    public int getRetCode() {
        return retCode;
    }
    public long getCostTime() {
        return costTime;
    }
    public String getRetMsg() {
        return retMsg;
    }
    public String getUserId() {
        return userId;
    }
    public Object getSedMsg() {
        return sedMsg;
    }

    @Override
    public String toString() {
        return "RetResult{" +
                "userId='" + userId + '\'' +
                ", retCode=" + retCode +
                ", retMsg='" + retMsg + '\'' +
                ", costTime=" + costTime +
                ", retData=" + retData +
                ", sedMsg=" + sedMsg +
                '}';
    }
}
