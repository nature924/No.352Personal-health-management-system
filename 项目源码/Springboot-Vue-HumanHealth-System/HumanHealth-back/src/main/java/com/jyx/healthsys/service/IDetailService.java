package com.jyx.healthsys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jyx.healthsys.entity.Detail;
import com.jyx.healthsys.entity.SportInfo;

import java.util.List;
import java.util.Map;


public interface IDetailService extends IService<Detail> {
    List<Detail> getDetailInfo(String sportName);

    void updateDetail(Detail detail);

    Detail getDetailById(Integer id);

    void deletDetailById(Integer id);

    boolean addDetail(Detail detail);
}



