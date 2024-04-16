package org.ymail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ymail.entity.Email;

import java.util.List;

@Mapper
public interface EmailMapper extends BaseMapper<Email> {
    @Select("select * from t_email where `master` = #{master} " +
            "and pinned =0 and del_flag=0 and `group` =#{group} order by  update_time " +
            "LIMIT #{index},#{size}")
    List<Email>selectOtherEmail(@Param("master") String master,@Param("group") String group, @Param("index") int index,@Param("size") int size);
}
