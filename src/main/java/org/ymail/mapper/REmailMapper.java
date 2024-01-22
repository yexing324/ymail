package org.ymail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.ymail.entity.Email;
@Mapper
public interface REmailMapper extends BaseMapper<Email> {
}
