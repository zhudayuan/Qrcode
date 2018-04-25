package com.maywidehb.qrpush.entity;


@SuppressWarnings("serial")
public class QrCodeTv  implements java.io.Serializable{
	//code 返回类型 0 换台 1 推送二维码 2 取消某个二维码 3 正在展示的码消失, aftertime -1 不再显示, 其他多长时间后重新请求
	private int code;
	private Long startTime;
	private int serviceid;
	private long qrid;

	private String qrurl;//二维码链接
	private int qrwp; //二维码距离背景图左边距
	private int qrhp; //二维码距离背景图上边距
	private int qrsize; //二维码尺寸
	
	private String backurl;//背景图链接
	private int backwp; //背景图左边距
	private int backhp; //背景图上边距
	private int backsize; //背景图尺寸
	
	private boolean countdown ; //是否需要倒计时	
	private Long aftertime;//多长时间后显示|倒计时时间 (毫秒  -1二维码不再显示)
	private Long workhours;//显示多长时间(毫秒) (-1一直显示)

	

	public long getQrid() {
		return qrid;
	}
	public void setQrid(long qrid) {
		this.qrid = qrid;
	}
	public int getServiceid() {
		return serviceid;
	}
	public void setServiceid(int serviceid) {
		this.serviceid = serviceid;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public String getQrurl() {
		return qrurl;
	}
	public void setQrurl(String qrurl) {
		this.qrurl = qrurl;
	}
	public int getQrwp() {
		return qrwp;
	}
	public void setQrwp(int qrwp) {
		this.qrwp = qrwp;
	}
	public int getQrhp() {
		return qrhp;
	}
	public void setQrhp(int qrhp) {
		this.qrhp = qrhp;
	}
	public int getQrsize() {
		return qrsize;
	}
	public void setQrsize(int qrsize) {
		this.qrsize = qrsize;
	}
	public String getBackurl() {
		return backurl;
	}
	public void setBackurl(String backurl) {
		this.backurl = backurl;
	}
	public int getBackwp() {
		return backwp;
	}
	public void setBackwp(int backwp) {
		this.backwp = backwp;
	}
	public int getBackhp() {
		return backhp;
	}
	public void setBackhp(int backhp) {
		this.backhp = backhp;
	}
	public int getBacksize() {
		return backsize;
	}
	public void setBacksize(int backsize) {
		this.backsize = backsize;
	}
	public boolean isCountdown() {
		return countdown;
	}
	public void setCountdown(boolean countdown) {
		this.countdown = countdown;
	}
	public Long getAftertime() {
		return aftertime;
	}
	public void setAftertime(Long aftertime) {
		this.aftertime = aftertime;
	}
	public Long getWorkhours() {
		return workhours;
	}
	public void setWorkhours(Long workhours) {
		this.workhours = workhours;
	}
	


}
