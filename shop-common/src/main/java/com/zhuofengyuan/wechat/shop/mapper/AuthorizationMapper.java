package com.zhuofengyuan.wechat.shop.mapper;

import com.zhuofengyuan.wechat.shop.entity.Authorization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fengtoos
 * @since 2019-06-29
 */
//@Mapper
public interface AuthorizationMapper extends BaseMapper<Authorization> {

    List<Authorization> selectByUserId(@Param("userId") Long userId);
}
