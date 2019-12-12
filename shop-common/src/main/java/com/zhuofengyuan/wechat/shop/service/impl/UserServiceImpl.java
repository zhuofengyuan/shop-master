package com.zhuofengyuan.wechat.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper;
import com.zhuofengyuan.wechat.shop.entity.User;
import com.zhuofengyuan.wechat.shop.exception.FengtoosException;
import com.zhuofengyuan.wechat.shop.mapper.UserMapper;
import com.zhuofengyuan.wechat.shop.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuofengyuan.wechat.shop.util.FengtoosUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fengtoos
 * @since 2019-06-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        if(StringUtils.isEmpty(username)){
            return null;
        }
        return this.userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    /**
     * 根据openid获取用户id内容（若无用户则创建）
     * @param user
     * @return
     */
    @Override
    public Serializable findWxUser(User user) {
        String openid = user.getOpenid();
        Long uid = 0L;
        if(StringUtils.isEmpty(openid)){
            throw new FengtoosException(500, "openid is empty");
        }

        var entity = this.userMapper.selectOne(new QueryWrapper<User>().eq("openid", openid));
        if(entity == null){
            this.saveOrUpdate(user);
            uid = user.getId();
        } else {
            uid = entity.getId();
        }

        return uid;
    }

    @Override
    public boolean action(Serializable id, Integer status) {
        var entity = this.getById(id);
        FengtoosUtil.null2Entity(entity);

        var orgStatus = entity.getStatus();
        if(1 == status){
            if(2 != orgStatus){
                throw new FengtoosException("只有禁用状态的用户才允许启用");
            }
        } else if(2 == status){
            if(1 != orgStatus){
                throw new FengtoosException("只有启用状态的用户才允许禁用");
            }
        }

        var u = new User();
        u.setStatus(status);
        return this.update(u, new QueryWrapper<User>().eq("id", id));
    }

    @Override
    public boolean removeById(Serializable id) {
        var entity = this.getById(id);
        FengtoosUtil.null2Entity(entity);

        if(entity.getStatus() == 1){
            throw new FengtoosException(500, "只有禁用状态的用户才允许删除");
        }
        return super.removeById(id);
    }

}
