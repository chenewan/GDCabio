package com.byd.emg.pojo;

public class RoleAcl {

    private Integer id;

    private Integer roleid;

    private Integer aclid;

    private String operator;

    private String operatetime;

    public RoleAcl() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getAclid() {
        return aclid;
    }

    public void setAclid(Integer aclid) {
        this.aclid = aclid;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(String operatetime) {
        this.operatetime = operatetime;
    }
}
