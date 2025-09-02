package com.jyx.healthsys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jyx.healthsys.entity.Body;
import com.jyx.healthsys.entity.Detail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DetailMapper extends BaseMapper<Detail> {
    List<Detail> getDetailInfo(String sportName);
}

