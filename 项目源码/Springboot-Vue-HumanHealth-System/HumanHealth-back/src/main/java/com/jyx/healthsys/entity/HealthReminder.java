package com.jyx.healthsys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("j_health_reminder")
public class HealthReminder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "reminder_id", type = IdType.AUTO)
    private Integer reminderId;

    @TableField("user_id")
    private Integer userId;

    private String title;

    private String content;

    @TableField("reminder_time")
    private Date reminderTime;

    @TableField("is_read")
    private Boolean isRead;

    private String type;

    private String priority;

    @TableField("create_time")
    private Date createTime;
} 