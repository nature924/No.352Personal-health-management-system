package com.jyx.healthsys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyx.Data_unification.Unification;
import com.jyx.healthsys.entity.HealthReminder;
import com.jyx.healthsys.service.IHealthReminderService;
import com.jyx.healthsys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reminder")
public class HealthReminderController {

    @Autowired
    private IHealthReminderService reminderService;
    
    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public Unification<Map<String, Object>> getReminderList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "priority", required = false) String priority,
            @RequestParam(value = "isRead", required = false) Boolean isRead,
            @RequestParam("pageNo") Long pageNo,
            @RequestParam("pageSize") Long pageSize) {
        
        // 获取当前登录用户ID
        Map<String, Object> userData = userService.getUserId();
        Integer userId = (Integer) userData.get("id");
        
        if (userId == null) {
            return Unification.fail("未获取到用户信息");
        }
        
        LambdaQueryWrapper<HealthReminder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HealthReminder::getUserId, userId);
        wrapper.eq(StringUtils.hasLength(type), HealthReminder::getType, type);
        wrapper.eq(StringUtils.hasLength(priority), HealthReminder::getPriority, priority);
        wrapper.eq(isRead != null, HealthReminder::getIsRead, isRead);
        
        // 添加关键字搜索
        if (StringUtils.hasLength(keyword)) {
            wrapper.and(w -> w.like(HealthReminder::getTitle, keyword)
                    .or()
                    .like(HealthReminder::getContent, keyword));
        }
        
        wrapper.orderByDesc(HealthReminder::getReminderTime);
        
        Page<HealthReminder> page = new Page<>(pageNo, pageSize);
        reminderService.page(page, wrapper);
        
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        
        return Unification.success(data);
    }
    
    @GetMapping("/unreadCount")
    public Unification<Integer> getUnreadCount() {
        Map<String, Object> userData = userService.getUserId();
        Integer userId = (Integer) userData.get("id");
        
        if (userId == null) {
            return Unification.fail("未获取到用户信息");
        }
        
        int count = reminderService.getUnreadCount(userId);
        return Unification.success(count);
    }
    
    @PostMapping("/add")
    public Unification<?> addReminder(@RequestBody HealthReminder reminder) {
        Map<String, Object> userData = userService.getUserId();
        Integer userId = (Integer) userData.get("id");
        
        if (userId == null) {
            return Unification.fail("未获取到用户信息");
        }
        
        reminder.setUserId(userId);
        reminder.setIsRead(false);
        
        boolean result = reminderService.addReminder(reminder);
        if (result) {
            return Unification.success("添加成功");
        } else {
            return Unification.fail("添加失败");
        }
    }
    
    @PutMapping("/update")
    public Unification<?> updateReminder(@RequestBody HealthReminder reminder) {
        boolean result = reminderService.updateReminder(reminder);
        if (result) {
            return Unification.success("更新成功");
        } else {
            return Unification.fail("更新失败");
        }
    }
    
    @PutMapping("/markAsRead/{id}")
    public Unification<?> markAsRead(@PathVariable("id") Integer id) {
        boolean result = reminderService.markAsRead(id);
        if (result) {
            return Unification.success("标记成功");
        } else {
            return Unification.fail("标记失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Unification<?> deleteReminder(@PathVariable("id") Integer id) {
        boolean result = reminderService.deleteReminder(id);
        if (result) {
            return Unification.success("删除成功");
        } else {
            return Unification.fail("删除失败");
        }
    }
    
    @PutMapping("/markAllAsRead")
    public Unification<?> markAllAsRead() {
        Map<String, Object> userData = userService.getUserId();
        Integer userId = (Integer) userData.get("id");
        
        if (userId == null) {
            return Unification.fail("未获取到用户信息");
        }
        
        boolean result = reminderService.markAllAsRead(userId);
        if (result) {
            return Unification.success("标记成功");
        } else {
            return Unification.fail("标记失败");
        }
    }
} 