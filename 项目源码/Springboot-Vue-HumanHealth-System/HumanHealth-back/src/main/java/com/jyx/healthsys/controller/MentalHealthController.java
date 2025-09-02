package com.jyx.healthsys.controller;

import com.jyx.Data_unification.Unification;
import com.jyx.healthsys.entity.MentalHealth;
import com.jyx.healthsys.service.MentalHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mentalHealth")
public class MentalHealthController {

    @Autowired
    private MentalHealthService mentalHealthService;
    
    /**
     * 保存心理健康测试结果
     */
    @PostMapping("/save")
    public Unification saveMentalHealthTest(@RequestBody MentalHealth mentalHealth) {
        return mentalHealthService.saveMentalHealthTest(mentalHealth);
    }
    
    /**
     * 获取最近的心理健康测试结果
     */
    @GetMapping("/latest/{userId}")
    public Unification getLatestTestByUserId(@PathVariable Integer userId) {
        return mentalHealthService.getLatestTestByUserId(userId);
    }
    
    /**
     * 获取心理健康测试历史
     */
    @GetMapping("/history/{userId}")
    public Unification getTestHistoryByUserId(@PathVariable Integer userId) {
        return mentalHealthService.getTestHistoryByUserId(userId);
    }
} 