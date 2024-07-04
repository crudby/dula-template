package com.crud.dula.security.service.impl;

import com.crud.dula.common.result.ResultCode;
import com.crud.dula.platform.entity.SysUser;
import com.crud.dula.platform.entity.SysUserRole;
import com.crud.dula.platform.entity.table.SysUserRoleTable;
import com.crud.dula.platform.entity.table.SysUserTable;
import com.crud.dula.platform.service.SysUserRoleService;
import com.crud.dula.platform.service.SysUserService;
import com.crud.dula.security.LoginUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author crud
 * @date 2023/9/15
 */
@Service("sysUserDetailService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.queryChain()
                .select(SysUserTable.SYS_USER.ALL_COLUMNS)
                .where(SysUserTable.SYS_USER.USERNAME.eq(username).or(SysUserTable.SYS_USER.PHONE.eq(username)).or(SysUserTable.SYS_USER.EMAIL.eq(username)))
                .one();
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.message);
        }
        List<String> roleIds = sysUserRoleService.queryChain().where(SysUserRoleTable.SYS_USER_ROLE.USER_ID.eq(user.getId())).list()
                .stream().map(SysUserRole::getRoleId).map(String::valueOf).collect(Collectors.toList());
        LoginUser loginUser = new LoginUser();
        loginUser.setSysUser(user);
        loginUser.setPermissions(roleIds);
        return loginUser;
    }
}
