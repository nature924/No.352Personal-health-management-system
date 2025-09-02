package com.jyx.healthsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyx.healthsys.entity.Body;
import com.jyx.healthsys.entity.Detail;
import com.jyx.healthsys.entity.SportInfo;
import com.jyx.healthsys.mapper.DetailMapper;
import com.jyx.healthsys.service.IDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;





@Service
public class DetailServiceImpl extends ServiceImpl<DetailMapper, Detail> implements IDetailService {


    @Resource
    private DetailMapper detailMapper;


    @Override
    public List<Detail> getDetailInfo(String sportName) {

        return detailMapper.getDetailInfo(sportName);
    }




    @Transactional
    @Override
    public boolean addDetail(Detail detail) {
        QueryWrapper<Detail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sport_type", detail.getSportType());
        List<Detail> list = this.baseMapper.selectList(queryWrapper);
        System.out.println(list);
        if (list.isEmpty()) {
            this.baseMapper.insert(detail);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void updateDetail(Detail detail) {
        // 更新用户表中的数据
        this.baseMapper.updateById(detail);
    }

    @Override
    public Detail getDetailById(Integer id) {
        System.out.println(id);
        Detail detail = this.baseMapper.selectById(id);
        return detail;
    }

    @Override
    public void deletDetailById(Integer id) {
        this.baseMapper.deleteById(id);
    }
}


