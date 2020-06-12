package com.byd.emg.service;

import com.byd.emg.pojo.Acl;

public interface AclService {

    public Acl selectAclByUrl(String requestUrl);
}
