package com.jyx.healthsys.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jyx.healthsys.entity.Body;
import com.jyx.healthsys.entity.BodyNotes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BodyNotesMapper extends BaseMapper<BodyNotes> {


}

