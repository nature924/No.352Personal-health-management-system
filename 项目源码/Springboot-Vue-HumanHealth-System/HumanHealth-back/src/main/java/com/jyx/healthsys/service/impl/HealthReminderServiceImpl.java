package com.jyx.healthsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyx.healthsys.entity.HealthReminder;
import com.jyx.healthsys.mapper.HealthReminderMapper;
import com.jyx.healthsys.service.IHealthReminderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthReminderServiceImpl extends ServiceImpl<HealthReminderMapper, HealthReminder> implements IHealthReminderService {

    @Override
    public List<HealthReminder> getUserReminders(Integer userId) {
        LambdaQueryWrapper<HealthReminder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HealthReminder::getUserId, userId);
        wrapper.orderByDesc(HealthReminder::getReminderTime);
        return this.list(wrapper);
    }

    @Override
    public boolean addReminder(HealthReminder reminder) {
        return this.save(reminder);
    }

    @Override
    public boolean updateReminder(HealthReminder reminder) {
        return this.updateById(reminder);
    }

    @Override
    public boolean markAsRead(Integer reminderId) {
        HealthReminder reminder = this.getById(reminderId);
        if (reminder != null) {
            reminder.setIsRead(true);
            return this.updateById(reminder);
        }
        return false;
    }

    @Override
    public boolean deleteReminder(Integer reminderId) {
        return this.removeById(reminderId);
    }

    @Override
    public int getUnreadCount(Integer userId) {
        LambdaQueryWrapper<HealthReminder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HealthReminder::getUserId, userId);
        wrapper.eq(HealthReminder::getIsRead, false);
        long count = this.count(wrapper);
        return count > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) count;
    }

    @Override
    public boolean markAllAsRead(Integer userId) {
        LambdaUpdateWrapper<HealthReminder> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(HealthReminder::getUserId, userId);
        updateWrapper.eq(HealthReminder::getIsRead, false);
        updateWrapper.set(HealthReminder::getIsRead, true);
        
        return this.update(updateWrapper);
    }
} 