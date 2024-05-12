package org.ymail.service;

import org.ymail.entity.UserDo;
import org.ymail.entity.Vo.UserVo;
import org.ymail.resp.UserResp;
import org.ymail.util.Result;

public interface UserService {
    Result<Void> register(UserVo reqParam);

    Result<UserResp> login(UserDo reParam);


}
