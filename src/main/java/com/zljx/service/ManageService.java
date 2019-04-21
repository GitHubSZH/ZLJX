package com.zljx.service;

import com.zljx.pojo.Admin;

public interface ManageService {
    String findCartAll();

    Admin doLogin(Admin admin);
}
