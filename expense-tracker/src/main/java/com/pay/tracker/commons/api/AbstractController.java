package com.pay.tracker.commons.api;

import com.pay.tracker.commons.model.User;
import com.pay.tracker.commons.service.UserSecurityContext;

public class AbstractController {
    protected User getUser(){
        return UserSecurityContext.getUser();
    }
}
