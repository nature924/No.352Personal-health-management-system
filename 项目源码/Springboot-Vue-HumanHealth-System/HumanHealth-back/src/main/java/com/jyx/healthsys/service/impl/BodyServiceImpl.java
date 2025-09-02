package com.jyx.healthsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyx.healthsys.entity.Body;
import com.jyx.healthsys.entity.BodyNotes;
import com.jyx.healthsys.entity.SportInfo;
import com.jyx.healthsys.mapper.BodyMapper;
import com.jyx.healthsys.mapper.BodyNotesMapper;
import com.jyx.healthsys.service.IBodyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BodyServiceImpl extends ServiceImpl<BodyMapper, Body> implements IBodyService {


    @Resource
    private BodyMapper bodyMapper;
    private BodyNotesMapper bodyNotesMapper;


    @Override
    public boolean insert(Body body) {
        LambdaQueryWrapper<Body> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Body::getId, body.getId());
        int count = this.baseMapper.selectCount(wrapper).intValue();
        if (count > 0) {
            // 存在相同记录，执行更新
            this.baseMapper.update(body, wrapper);
            return false;
        } else {
            // 不存在相同记录，插入数据库
            this.baseMapper.insert(body);
            return true;
        }
    }









    @Override
    public void update(Body body) {
        this.baseMapper.updateById(body);
    }


    @Override
    public List<Body> getBodyListByUserId(Integer pid) {
        return bodyMapper.getBodyListByUserId(pid);
    }


    @Override
    public Body getBodyById(Integer id) {
        Body body = this.baseMapper.selectById(id);
        return body;
    }

    @Override
    public void updateBody(Body body) {
        this.baseMapper.updateById(body);
    }

    @Override
    public void deletBodyById(Integer id) {
        // 直接删除指定主键的记录
        this.baseMapper.deleteById(id);
        //删除BodyNotes表中与
    }











}

