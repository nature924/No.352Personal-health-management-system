package com.jyx.healthsys.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("sport_info")
@NoArgsConstructor
@AllArgsConstructor
public class SportInfo implements Serializable {
    @TableId(type = IdType.AUTO)
    @TableField(value = "id")
    private Integer id;

    @TableField(value = "sport_type")
    private String sportType;

    @TableField(value = "suitable_time")
    private String suitableTime;

    @TableField(value = "suitable_heart_rate")
    private String suitableHeartRate;

    @TableField(value = "suitable_frequency")
    private String suitableFrequency;

    @TableField(value = "recommended_speed")
    private String recommendedSpeed;

}

