package com.crud.dula.platform.web;

import com.crud.dula.common.jwt.JwtUtil;
import com.crud.dula.common.result.HttpResult;
import com.crud.dula.common.result.ResultCode;
import com.crud.dula.common.utils.BeanUtil;
import com.crud.dula.platform.dto.PasswordInitDTO;
import com.crud.dula.platform.dto.PasswordResetByCodeDTO;
import com.crud.dula.platform.dto.PasswordResetDTO;
import com.crud.dula.platform.dto.SysUserDTO;
import com.crud.dula.platform.email.EmailDTO;
import com.crud.dula.platform.email.EmailSender;
import com.crud.dula.platform.email.EmailTemplate;
import com.crud.dula.platform.entity.SysMenu;
import com.crud.dula.platform.entity.SysRoleMenu;
import com.crud.dula.platform.entity.SysUser;
import com.crud.dula.platform.entity.table.SysRoleMenuTable;
import com.crud.dula.platform.entity.table.SysUserTable;
import com.crud.dula.platform.service.SysMenuService;
import com.crud.dula.platform.service.SysRoleMenuService;
import com.crud.dula.platform.service.SysUserService;
import com.crud.dula.platform.support.SysMenuSupport;
import com.crud.dula.platform.support.SysStorageSupport;
import com.crud.dula.platform.vo.SysMenuTreeVO;
import com.crud.dula.platform.vo.SysUserVO;
import com.crud.dula.security.LoginUser;
import com.crud.dula.security.SecurityConstants;
import com.crud.dula.security.session.LoginSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 系统用户API
 *
 * @author crud
 * @date 2023/9/15
 */
@Api(value = "/sys", tags = {"系统用户API"})
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/sys")
public class SysUserController {

    private final LoginSession loginSession;

    private final SysUserService sysUserService;

    private final PasswordEncoder passwordEncoder;

    private final SysRoleMenuService sysRoleMenuService;

    private final SysMenuService sysMenuService;

    private final SysMenuSupport sysMenuSupport;

    private final SysStorageSupport sysStorageSupport;

    private final EmailSender emailSender;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 用户信息
     *
     * @return user
     */
    @ApiOperation(value = "用户信息", notes = "用户信息", httpMethod = "GET")
    @GetMapping("/user/profile")
    public HttpResult<SysUserVO> userProfile() {
        LoginUser loginUser = loginSession.getLoginUser();
        SysUser user = sysUserService.queryChain()
                .select(SysUserTable.SYS_USER.ALL_COLUMNS)
                .where(SysUserTable.SYS_USER.PHONE.eq(loginUser.getSysUser().getPhone()))
                .one();
        return HttpResult.success(BeanUtil.toBean(user, SysUserVO.class, (r, t) -> {
            if (StringUtils.isNoneBlank(r.getAvatar())) {
                t.setAvatarUrl(sysStorageSupport.getUrl(r.getAvatar()));
            }
        }));
    }

    /**
     * 用户菜单
     *
     * @return menuVO
     */
    @ApiOperation(value = "用户菜单", notes = "用户菜单", httpMethod = "GET")
    @GetMapping("/user/menu")
    public HttpResult<List<SysMenuTreeVO>> menuTree() {
        LoginUser loginUser = loginSession.getLoginUser();
        List<String> permissions = loginUser.getPermissions();
        if (Objects.isNull(permissions) || permissions.isEmpty()) {
            return HttpResult.success(List.of());
        }
        List<SysRoleMenu> roleMenuList = sysRoleMenuService.queryChain().where(SysRoleMenuTable.SYS_ROLE_MENU.ROLE_ID.in(permissions)).list();
        List<Long> menuIds = roleMenuList.stream().map(SysRoleMenu::getMenuId).distinct().collect(Collectors.toList());
        List<Long> operateIds = roleMenuList.stream().map(SysRoleMenu::getMenuOperateIds).filter(StringUtils::isNoneBlank).flatMap(ids -> Stream.of(ids.split(",")).map(Long::parseLong)).distinct().collect(Collectors.toList());
        List<SysMenu> sysMenus = sysMenuService.listByIds(menuIds);
        return HttpResult.success(sysMenuSupport.applyTree(sysMenus, operateIds));
    }

    /**
     * 新增用户
     *
     * @param userDTO userDTO
     * @return bool
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysUserDTO", name = "userDTO", value = "userDTO", required = true)
    })
    @ApiOperation(value = "新增用户", notes = "新增用户", httpMethod = "POST")
    @PostMapping("/user/create")
    public HttpResult<Boolean> create(@Valid @RequestBody SysUserDTO userDTO) {
        long count = sysUserService.queryChain().where(SysUserTable.SYS_USER.USERNAME.eq(userDTO.getUsername())).count();
        if (count > 0) {
            return HttpResult.fail(ResultCode.BUSINESS_FAILED.getCode(), "用户名已存在");
        }
        if (StringUtils.isNotBlank(userDTO.getPhone())) {
            count = sysUserService.queryChain().where(SysUserTable.SYS_USER.PHONE.eq(userDTO.getPhone())).count();
            if (count > 0) {
                return HttpResult.fail(ResultCode.BUSINESS_FAILED.getCode(), "手机号已注册");
            }
        }
        if (StringUtils.isNoneBlank(userDTO.getEmail())) {
            count = sysUserService.queryChain().where(SysUserTable.SYS_USER.EMAIL.eq(userDTO.getEmail())).count();
            if (count > 0) {
                return HttpResult.fail(ResultCode.BUSINESS_FAILED.getCode(), "邮箱已注册");
            }
        }
        SysUser sysUser = BeanUtil.toBean(userDTO, SysUser.class);
        // 生成随机密码
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        log.info("调用 {}", sysUser);
        boolean saved = sysUserService.save(sysUser);
        return HttpResult.success(saved);
    }

    /**
     * 用户信息更新
     *
     * @param userDTO userDTO
     * @return bool
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysUserDTO", name = "userDTO", value = "userDTO", required = true)
    })
    @ApiOperation(value = "用户信息更新", notes = "用户信息更新", httpMethod = "POST")
    @PostMapping("/user/update")
    public HttpResult<SysUserVO> update(@RequestBody SysUserDTO userDTO) {
        SysUser dbUser = sysUserService.getById(userDTO.getId());
        if (Objects.isNull(dbUser)) {
            return HttpResult.fail(ResultCode.DATA_NOT_EXIST.getCode(), "用户不存在");
        }
        SysUser sysUser = BeanUtil.toBean(userDTO, SysUser.class);
        sysUser.setPhone(dbUser.getPhone());
        sysUserService.updateById(sysUser);
        return HttpResult.success(BeanUtil.toBean(sysUser, SysUserVO.class, (r, t) -> {
            if (StringUtils.isNoneBlank(r.getAvatar())) {
                t.setAvatarUrl(sysStorageSupport.getUrl(r.getAvatar()));
            }
        }));
    }

    /**
     * 首次密码重置
     *
     * @param passwordInitDTO passwordDTO
     * @return bool
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "PasswordInitDTO", name = "passwordInitDTO", value = "passwordDTO", required = true)
    })
    @ApiOperation(value = "首次密码重置", notes = "首次密码重置", httpMethod = "POST")
    @PostMapping("/user/password-init")
    public HttpResult<Boolean> passwordInit(@Valid @RequestBody PasswordInitDTO passwordInitDTO) {
        Optional<Object> safeValue = JwtUtil.getSafeValue(passwordInitDTO.getToken(), SecurityConstants.USER_ID);
        if (safeValue.isEmpty()) {
            return HttpResult.fail(ResultCode.BUSINESS_FAILED.getCode(), "重置时间已过期");
        }
        Long userId = (Long) safeValue.get();
        SysUser sysUser = sysUserService.getById(userId);
        if (sysUser.getRegistered()) {
            return HttpResult.fail(ResultCode.BUSINESS_FAILED.getCode(), "无需初始化重置");
        }
        sysUser.setPassword(passwordEncoder.encode(passwordInitDTO.getPassword()));
        sysUser.setRegistered(Boolean.TRUE);
        sysUserService.updateById(sysUser);
        return HttpResult.success();
    }

    /**
     * 发送验证码
     *
     * @param phone phone
     * @return bool
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "phone", value = "phone", required = true)
    })
    @ApiOperation(value = "发送验证码", notes = "发送验证码", httpMethod = "GET")
    @GetMapping("/user/send-verification-code")
    public HttpResult<Boolean> sendVerificationCode(@RequestParam("phone") String phone) {
        SysUser user = sysUserService.queryChain()
                .select(SysUserTable.SYS_USER.ALL_COLUMNS)
                .where(SysUserTable.SYS_USER.PHONE.eq(phone))
                .one();
        if (Objects.isNull(user)) {
            return HttpResult.fail(ResultCode.DATA_NOT_EXIST.getCode(), "用户不存在！");
        }
        if (StringUtils.isBlank(user.getEmail())) {
            return HttpResult.fail(ResultCode.BUSINESS_FAILED.getCode(), "用户未绑定邮箱，当前仅支持邮箱验证");
        }
        Random random = new Random();
        String code = String.format("%06d", random.nextInt(999999));
        stringRedisTemplate.opsForValue().set(String.format("dula:user:reset:%s", user.getPhone()), code, 5, TimeUnit.MINUTES);
        emailSender.htmlEmail(new EmailDTO(new String[]{user.getEmail()}, EmailTemplate.SUBJECT, String.format(EmailTemplate.RESET_PASSWORD, user.getUsername(), code)));
        return HttpResult.success(true);
    }

    /**
     * 密码重置
     *
     * @param dto dto
     * @return bool
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "PasswordResetByCodeDTO", name = "dto", value = "dto", required = true)
    })
    @ApiOperation(value = "密码重置", notes = "密码重置", httpMethod = "POST")
    @PostMapping("/user/password-reset-byCode")
    public HttpResult<Boolean> passwordResetByCode(@Valid @RequestBody PasswordResetByCodeDTO dto) {
        SysUser user = sysUserService.queryChain()
                .select(SysUserTable.SYS_USER.ALL_COLUMNS)
                .where(SysUserTable.SYS_USER.PHONE.eq(dto.getPhone()))
                .one();
        if (Objects.isNull(user)) {
            return HttpResult.fail(ResultCode.DATA_NOT_EXIST.getCode(), "用户不存在！");
        }
        String s = stringRedisTemplate.opsForValue().get(String.format("dula:user:reset:%s", user.getPhone()));
        if (StringUtils.isBlank(s) || !Objects.equals(s, dto.getCode())) {
            return HttpResult.fail(ResultCode.BUSINESS_FAILED.getCode(), "验证码错误");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        boolean updated = sysUserService.updateById(user);
        stringRedisTemplate.delete(String.format("dula:user:reset:%s", user.getPhone()));
        return HttpResult.success(updated);
    }

    /**
     * 用户密码重置
     *
     * @param passwordResetDTO password
     * @return bool
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "PasswordResetDTO", name = "passwordResetDTO", value = "password", required = true)
    })
    @ApiOperation(value = "用户密码重置", notes = "用户密码重置", httpMethod = "POST")
    @PostMapping("/user/password-reset")
    public HttpResult<Boolean> passwordReset(@Valid @RequestBody PasswordResetDTO passwordResetDTO) {
        SysUser sysUser = sysUserService.getById(loginSession.getUserId());
        if (!passwordEncoder.matches(passwordResetDTO.getOldPassword(), sysUser.getPassword())) {
            return HttpResult.fail(ResultCode.BUSINESS_FAILED.getCode(), "当前密码不匹配！");
        }
        sysUser.setPassword(passwordEncoder.encode(passwordResetDTO.getNewPassword()));
        boolean updated = sysUserService.updateById(sysUser);
        return HttpResult.success(updated);
    }
}
