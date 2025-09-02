package com.jyx.healthsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyx.Data_unification.Unification;
import com.jyx.healthsys.entity.MentalHealth;
import com.jyx.healthsys.mapper.MentalHealthMapper;
import com.jyx.healthsys.service.MentalHealthService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MentalHealthServiceImpl extends ServiceImpl<MentalHealthMapper, MentalHealth> implements MentalHealthService {

    @Override
    public Unification saveMentalHealthTest(MentalHealth mentalHealth) {
        // 设置测试日期
        mentalHealth.setTestDate(new Date());
        
        // 根据总分设置抑郁程度级别
        int totalScore = mentalHealth.getTotalScore();
        String depressionLevel;
        String recommendation;
        
        if (totalScore < 50) {
            depressionLevel = "无抑郁症状";
            recommendation = "您的心理状态良好，继续保持积极乐观的生活态度。";
        } else if (totalScore < 60) {
            depressionLevel = "轻度抑郁";
            recommendation = "建议适当放松，多进行户外活动，注意休息，保持规律作息。";
        } else if (totalScore < 70) {
            depressionLevel = "中度抑郁";
            recommendation = "建议寻求心理咨询师的专业帮助，学习一些减压和情绪管理技巧。";
        } else {
            depressionLevel = "重度抑郁";
            recommendation = "强烈建议尽快寻求专业的心理医生或精神科医生的帮助。";
        }
        
        mentalHealth.setDepressionLevel(depressionLevel);
        mentalHealth.setRecommendation(recommendation);
        
        // 保存测试结果
        this.save(mentalHealth);
        
        return Unification.success(mentalHealth);
    }

    @Override
    public Unification getLatestTestByUserId(Integer userId) {
        LambdaQueryWrapper<MentalHealth> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MentalHealth::getUserId, userId)
               .orderByDesc(MentalHealth::getTestDate)
               .last("LIMIT 1");
        
        MentalHealth mentalHealth = this.getOne(wrapper);
        
        if (mentalHealth == null) {
            return Unification.fail("未找到心理健康测试记录");
        }
        
        return Unification.success(mentalHealth);
    }

    @Override
    public Unification getTestHistoryByUserId(Integer userId) {
        LambdaQueryWrapper<MentalHealth> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MentalHealth::getUserId, userId)
               .orderByDesc(MentalHealth::getTestDate);
        
        List<MentalHealth> historyList = this.list(wrapper);
        
        return Unification.success(historyList);
    }
} 