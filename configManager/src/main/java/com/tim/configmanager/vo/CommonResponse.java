package com.tim.configmanager.vo;

/**
 * @description: 返回报文
 * @author: li si
 * @create: 2020-09-20 14:48
 */
public class CommonResponse {
    private Integer respCode;
    private String respMsg;
    private Object data;

    public static CommonResponse success(Object data){
        CommonResponse commonResponse = new CommonResponse(1000, null, data);
        return commonResponse;
    }

    public static CommonResponse success(){
        CommonResponse commonResponse = new CommonResponse(1000, null, null);
        return commonResponse;
    }

    public static CommonResponse fail(String msg){
        CommonResponse commonResponse = new CommonResponse(3001, msg, null);
        return commonResponse;
    }

    public static CommonResponse fail(){
        CommonResponse commonResponse = new CommonResponse(3001, "执行失败", null);
        return commonResponse;
    }

    public CommonResponse(Integer respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    public CommonResponse() {
    }

    public CommonResponse(Integer respCode, String respMsg, Object data) {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.data = data;
    }

    public Integer getRespCode() {
        return respCode;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
