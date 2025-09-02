package com.jyx.healthsys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("j_body")
public class Body {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    private String name;

    private Integer age;

    private String gender;

    private Double height;

    private Double weight;
    @TableField(value = "bloodSugar")
    private Double bloodSugar;
    @TableField(value = "bloodPressure")
    private String bloodPressure;
    @TableField(value = "bloodLipid")
    private String bloodLipid;

    @TableField("heart_rate")
    private double heartRate;

    @TableField("vision")
    private Integer vision;

    @TableField("sleep_duration")
    private double sleepDuration;

    @TableField("sleep_quality")
    private String sleepQuality;

    @TableField("smoking")
    private boolean smoking;

    @TableField("drinking")
    private boolean drinking;

    @TableField("exercise")
    private boolean exercise;

    @TableField("food_types")
    private String foodTypes;

    @TableField("water_consumption")
    private double waterConsumption;


}

