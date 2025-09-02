package com.jyx.healthsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyx.healthsys.entity.SportInfo;
import com.jyx.healthsys.entity.User;
import com.jyx.healthsys.entity.UserRole;
import com.jyx.healthsys.mapper.SportInfoMapper;
import com.jyx.healthsys.service.ISportInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class SportInfoServiceImpl extends ServiceImpl<SportInfoMapper, SportInfo> implements ISportInfoService { // 实现 SportInfoService 接口

    @Resource
    private SportInfoMapper sportInfoMapper;


    @Override
    public List<SportInfo> getAllSportInfos() {
        return sportInfoMapper.selectList(null);
    }


//    @Override
//    public List<SportInfo> getSportInfosBySuitableHeartRate(int heartRate) {
//        QueryWrapper<SportInfo> wrapper = new QueryWrapper<>();
//        wrapper.le("suitable_heart_rate", heartRate);
//        return sportInfoMapper.selectList(wrapper);
//    }
//
//
//    @Override
//    public List<SportInfo> getSportInfosBySuitableTimeAndFrequency(int time, int frequency) {
//        QueryWrapper<SportInfo> wrapper = new QueryWrapper<>(); // 创建 QueryWrapper 对象
//        wrapper.le("suitable_time", time) // 设置查询条件，适宜时间小于等于指定时间
//                .ge("suitable_frequency", frequency); // 设置查询条件，适宜频率大于等于指定频率
//        return sportInfoMapper.selectList(wrapper); // 执行查询并返回结果
//    }
//
//
//    @Override // 重写 SportInfoService 接口的方法，根据推荐速度范围获取运动信息
//    public List<SportInfo> getSportInfosByRecommendedSpeedRange(int minSpeed, int maxSpeed) {
//        QueryWrapper<SportInfo> wrapper = new QueryWrapper<>(); // 创建 QueryWrapper 对象
//        wrapper.between("recommended_speed", minSpeed, maxSpeed); // 设置查询条件，推荐速度在指定范围内
//        return sportInfoMapper.selectList(wrapper); // 执行查询并返回结果
//    }



    @Transactional
    @Override
    public boolean addSport(SportInfo sport) {
        QueryWrapper<SportInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sport_type", sport.getSportType());
        List<SportInfo> list = this.baseMapper.selectList(queryWrapper);

        if (list.isEmpty()) {
            this.baseMapper.insert(sport);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void updateSport(SportInfo sport) {
        this.baseMapper.updateById(sport);
    }

    @Override
    public SportInfo getSportById(Integer id) {
        SportInfo sportInfo = this.baseMapper.selectById(id);
        return sportInfo;
    }

    @Override
    public void deletUserById(Integer id) {
        this.baseMapper.deleteById(id);
    }












}

