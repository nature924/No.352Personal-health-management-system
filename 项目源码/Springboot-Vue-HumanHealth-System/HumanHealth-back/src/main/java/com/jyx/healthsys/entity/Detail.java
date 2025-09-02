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
@AllArgsConstructor
@NoArgsConstructor
@TableName("detail")
public class Detail implements Serializable {


    @TableId(type = IdType.AUTO)
    @TableField(value = "id")
    private Integer id;


    @TableField("sport_type")
    private String sportType;

    private String disease;

    private String method;

    private String notes;




//    @TableField(exist = false)
//    private String suitable_time;
}
