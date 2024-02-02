package org.ymail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.ymail.entity.UserDo;
@Mapper
public interface UserMapper extends BaseMapper<UserDo> {
}
