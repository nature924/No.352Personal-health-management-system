package com.jyx.healthsys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jyx.healthsys.entity.HealthReminder;

import java.util.List;

public interface IHealthReminderService extends IService<HealthReminder> {
    
    List<HealthReminder> getUserReminders(Integer userId);
    
    boolean addReminder(HealthReminder reminder);
    
    boolean updateReminder(HealthReminder reminder);
    
    boolean markAsRead(Integer reminderId);
    
    boolean deleteReminder(Integer reminderId);
    
    int getUnreadCount(Integer userId);
    
    boolean markAllAsRead(Integer userId);
} 