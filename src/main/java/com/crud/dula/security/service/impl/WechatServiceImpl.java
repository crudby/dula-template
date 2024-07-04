package com.crud.dula.security.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.crud.dula.common.dict.NameConstant;
import com.crud.dula.common.id.GlobalUtil;
import com.crud.dula.common.result.BizException;
import com.crud.dula.common.result.ResultCode;
import com.crud.dula.security.service.WechatService;
import com.crud.dula.platform.entity.SysUser;
import com.crud.dula.platform.entity.table.SysUserTable;
import com.crud.dula.platform.service.SysUserService;
import com.crud.dula.security.LoginUser;
import com.mybatisflex.core.tenant.TenantManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

/**
 * @author crud
 * @date 2023/9/15
 */
@Slf4j
@Service
public class WechatServiceImpl implements WechatService {

    @Resource
    private WxMaService wxMaService;

    @Resource
    private SysUserService sysUserService;

    /**
     * 微信登录
     * @param code code
     * @return LoginUser
     */
    @Override
    public LoginUser loginByCode(String code) {
        try {
            WxMaJscode2SessionResult sessionInfo = wxMaService.jsCode2SessionInfo(code);
            String openid = sessionInfo.getOpenid();
            SysUser user = TenantManager.withoutTenantCondition(() -> sysUserService.queryChain().where(SysUserTable.SYS_USER.OPEN_ID.eq(openid)).one());
            SysUser sysUser = Optional.ofNullable(user).orElseGet(() -> defaultUser(openid));
            return new LoginUser(sysUser, new ArrayList<>());
        } catch (Exception e) {
            log.error("微信登录失败", e);
            throw new BizException(ResultCode.LOGIN_TIMEOUT);
        }
    }

    @Transactional
    public SysUser defaultUser(String openId) {
        LocalDateTime now = LocalDateTime.now();
        Long id = GlobalUtil.getNextId();
        Random random = new Random();
        int randomKey = random.nextInt(NameConstant.ADJECTIVE_ARRAY.length-1);
        SysUser sysUser = SysUser.builder().openId(openId)
                .nickname(NameConstant.ADJECTIVE_ARRAY[randomKey]+NameConstant.DEFAULT_NAME)
                .username("微信用户")
                .build();
        sysUser.setId(id);
        sysUser.setTenantId(String.valueOf(id));
        sysUser.setEnabled(Boolean.TRUE);
        sysUser.setRegistered(Boolean.FALSE);
        sysUser.setCreateTime(now);
        sysUser.setReviseTime(now);
        sysUser.setCreator(NameConstant.SYSTEM_ID);
        sysUser.setReviser(NameConstant.SYSTEM_ID);
        TenantManager.withoutTenantCondition(() -> this.sysUserService.save(sysUser));
        return sysUser;
    }
}
