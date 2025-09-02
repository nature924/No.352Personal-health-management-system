package com.jyx.healthsys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("mental_health")
public class MentalHealth {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer totalScore;
    private String depressionLevel;
    private String recommendation;
    private Date testDate;
    private String answers; // 存储用户的答案，JSON格式
} 