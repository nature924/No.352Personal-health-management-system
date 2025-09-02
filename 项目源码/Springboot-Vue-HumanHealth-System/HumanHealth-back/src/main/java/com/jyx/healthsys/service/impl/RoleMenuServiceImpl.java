package com.jyx.healthsys.service.impl;

import com.jyx.healthsys.entity.Role;
import com.jyx.healthsys.entity.RoleMenu;
import com.jyx.healthsys.mapper.RoleMenuMapper;
import com.jyx.healthsys.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 金义雄
 * @since 2023-02-23
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {
}
