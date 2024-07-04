package com.crud.dula.platform.web;

import com.crud.dula.common.result.BizException;
import com.crud.dula.common.result.HttpResult;
import com.crud.dula.common.result.ResultCode;
import com.crud.dula.common.utils.BeanUtil;
import com.crud.dula.platform.entity.SysGroup;
import com.crud.dula.platform.entity.SysRole;
import com.crud.dula.platform.entity.SysUser;
import com.crud.dula.platform.entity.table.SysUserTable;
import com.crud.dula.platform.service.*;
import com.crud.dula.platform.vo.SysUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 系统管理员API
 *
 * @author crud
 * @date 2024/4/22
 */
@Api(value = "/sys", tags = {"系统管理员API"})
@AllArgsConstructor
@RestController
@RequestMapping("/sys")
public class SysAdminController {

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    private final SysUserRoleService sysUserRoleService;

    private final SysGroupService sysGroupService;

    private final SysUserGroupService sysUserGroupService;

    /**
     * 用户列表
     *
     * @return list
     */
    @ApiOperation(value = "用户列表", notes = "用户列表", httpMethod = "GET")
    @GetMapping("/user/list")
    public HttpResult<List<SysUserVO>> list() {
        List<SysUser> list = sysUserService.queryChain().orderBy(SysUserTable.SYS_USER.CREATE_TIME.desc()).list();
        return HttpResult.success(BeanUtil.toList(list, SysUserVO.class));
    }

    /**
     * 用户禁用/启用
     *
     * @param id id
     * @return bool
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "id", required = true)
    })
    @ApiOperation(value = "用户禁用/启用", notes = "用户禁用/启用", httpMethod = "POST")
    @PostMapping("/user/disable/{id}")
    public HttpResult<Boolean> disable(@PathVariable("id") Long id) {
        SysUser sysUser = sysUserService.getById(id);
        if (Objects.isNull(sysUser)) {
            throw new BizException(ResultCode.DATA_NOT_EXIST);
        }
        sysUser.setEnabled(!sysUser.getEnabled());
        boolean updated = sysUserService.updateById(sysUser);
        return HttpResult.success(updated);
    }

    /**
     * 用户删除
     *
     * @param id id
     * @return bool
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "id", required = true)
    })
    @ApiOperation(value = "用户删除", notes = "用户删除", httpMethod = "POST")
    @PostMapping("/user/delete/{id}")
    public HttpResult<Boolean> delete(@PathVariable("id") Long id) {
        boolean removed = sysUserService.removeById(id);
        return HttpResult.success(removed);
    }


    /**
     * 用户角色配置
     *
     * @param userId userId
     * @param roleIds roleIds
     * @return bool
     */
    @ApiOperation(value = "用户角色配置", notes = "用户角色配置", httpMethod = "POST")
    @PostMapping("/user/role-config")
    public HttpResult<Boolean> userRoleConfig(@RequestParam("userId") Long userId, List<Long> roleIds) {
        if (Objects.nonNull(roleIds) &&!roleIds.isEmpty()) {
            List<SysRole> sysRoles = sysRoleService.listByIds(roleIds);
            if (sysRoles.size()< roleIds.size()) {
                return HttpResult.fail(ResultCode.DATA_NOT_EXIST.getCode(), "配置角色不存在");
            }
        }
        sysUserRoleService.config(userId, roleIds);
        return HttpResult.success(Boolean.TRUE);
    }

    /**
     * 用户组织配置
     *
     * @param userId userId
     * @param groupIds roleIds
     * @return bool
     */
    @ApiOperation(value = "用户组织配置", notes = "用户组织配置", httpMethod = "POST")
    @PostMapping("/user/group-config")
    public HttpResult<Boolean> userGroupConfig(@RequestParam("userId") Long userId, List<Long> groupIds) {
        if (Objects.nonNull(groupIds) &&!groupIds.isEmpty()) {
            List<SysGroup> sysGroups = sysGroupService.listByIds(groupIds);
            if (sysGroups.size()< groupIds.size()) {
                return HttpResult.fail(ResultCode.DATA_NOT_EXIST.getCode(), "配置组织不存在");
            }
        }
        sysUserGroupService.config(userId, groupIds);
        return HttpResult.success(Boolean.TRUE);
    }
}
