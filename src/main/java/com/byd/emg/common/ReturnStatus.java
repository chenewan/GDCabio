package com.byd.emg.common;

public enum ReturnStatus {

    NUM0(0, "操作成功"), NUM1(1, "文件路径为空"), NUM2(2, "文件错误"), NUM3(3, "文件不存在"), NUM4(4, "读取文件失败"), NUM999(999, "未知错误");

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private ReturnStatus() {
    }

    private ReturnStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
