package com.byd.emg.resultData;

//历史查询传入参数的封装
public class HistoryParameter {

    private String ca_number;

    private String table_name;

    private String type;

    public HistoryParameter() {}

    public String getCa_number() {
        return ca_number;
    }

    public void setCa_number(String ca_number) {
        this.ca_number = ca_number;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
