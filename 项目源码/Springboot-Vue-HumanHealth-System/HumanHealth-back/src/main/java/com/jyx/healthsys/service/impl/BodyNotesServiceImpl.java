package com.jyx.healthsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyx.healthsys.entity.Body;
import com.jyx.healthsys.entity.BodyNotes;
import com.jyx.healthsys.mapper.BodyNotesMapper;
import com.jyx.healthsys.service.IBodyNotesService;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
public class BodyNotesServiceImpl extends ServiceImpl<BodyNotesMapper, BodyNotes> implements IBodyNotesService {

    private IBodyNotesService bodyNotesMapper;

    @Override
    public boolean insert(BodyNotes bodyNotes) {
        this.baseMapper.insert(bodyNotes);
        return true;
    }

    @Override
    public List<BodyNotes> getBodyNotes(Integer id) {
        LambdaQueryWrapper<BodyNotes> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BodyNotes::getId, id);
        List<BodyNotes> bodyNotesList = baseMapper.selectList(queryWrapper);
        return bodyNotesList;
    }

    @Override
    public void delete(Integer id) {
        QueryWrapper<BodyNotes> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        baseMapper.delete(wrapper);
    }



    @Override
    public BodyNotes getUserBodyById(Integer notesid) {
        BodyNotes bodyNotes = this.baseMapper.selectById(notesid);
        return bodyNotes;
    }

    @Override
    public void updateUserBody(BodyNotes bodyNotes) {
        bodyNotes.setDate(null);
        this.baseMapper.updateById(bodyNotes);
    }

    @Override
    public void deleteUserBodyById(Integer notesid) {
        this.baseMapper.deleteById(notesid);
    }


}

