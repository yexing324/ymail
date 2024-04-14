package org.ymail.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 带有Object的分页
 * 因为page只能在record中放入list
 * 但是目前需求是在record中放入object,object中有几个list
 * @param <T>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MPage<T> extends Page<T> {
    T data;
}
