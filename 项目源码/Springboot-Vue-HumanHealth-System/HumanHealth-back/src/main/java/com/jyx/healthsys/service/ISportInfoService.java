package com.jyx.healthsys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jyx.healthsys.entity.SportInfo;

import java.util.List;

public interface ISportInfoService extends IService<SportInfo> {
    //获取所有运动种类信息
    List<SportInfo> getAllSportInfos();

//    //根据适宜心率获取运动种类信息
//    List<SportInfo> getSportInfosBySuitableHeartRate(int heartRate);
//
//    //根据适宜时间和适宜频率获取运动种类信息
//    List<SportInfo> getSportInfosBySuitableTimeAndFrequency(int time, int frequency);
//
//    //根据推荐速度范围获取运动种类信息
//    List<SportInfo> getSportInfosByRecommendedSpeedRange(int minSpeed, int maxSpeed);

    boolean addSport(SportInfo sport);

    void updateSport(SportInfo sport);

    SportInfo getSportById(Integer id);

    void deletUserById(Integer id);
}
