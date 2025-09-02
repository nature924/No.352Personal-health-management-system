package com.jyx.healthsys.mapper;

import com.jyx.healthsys.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 金义雄
 * @since 2023-02-23
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     *
     *@Param注解用于给参数取别名，使SQL语句中的占位符可以正确映射到方法参数；
     * getMenuListByUserId是方法名，根据Java命名规范，应该以动词开头，表示该方法的功能；
     * (Integer userId, Integer pid)是方法参数，分别表示用户ID和菜单父ID。
     */
    public List<Menu> getMenuListByUserId(@Param("userId") Integer userId, @Param("pid") Integer pid);
}
