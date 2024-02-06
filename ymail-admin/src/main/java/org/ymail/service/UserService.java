package org.ymail.service;

import org.ymail.entity.UserDo;
import org.ymail.resp.UserResp;
import org.ymail.util.Result;

public interface UserService {
    Result<Void> register(UserDo reqParam);

    Result<UserResp> login(UserDo reParam);


}
