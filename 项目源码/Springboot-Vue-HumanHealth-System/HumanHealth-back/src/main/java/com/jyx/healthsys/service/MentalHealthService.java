package com.jyx.healthsys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jyx.Data_unification.Unification;
import com.jyx.healthsys.entity.MentalHealth;

public interface MentalHealthService extends IService<MentalHealth> {
    
    /**
     * 保存心理健康测试结果
     */
    Unification saveMentalHealthTest(MentalHealth mentalHealth);
    
    /**
     * 根据用户ID获取最近的心理健康测试结果
     */
    Unification getLatestTestByUserId(Integer userId);
    
    /**
     * 获取用户所有心理健康测试历史
     */
    Unification getTestHistoryByUserId(Integer userId);
} 