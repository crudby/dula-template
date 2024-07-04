package com.crud.dula.platform.support;

import com.crud.dula.common.utils.BeanUtil;
import com.crud.dula.platform.entity.SysUser;
import com.crud.dula.platform.service.SysUserService;
import com.crud.dula.platform.vo.SysUserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author crud
 * @date 2024/6/17
 */
@Slf4j
@Component
@AllArgsConstructor
public class SysUserSupport {

    private final SysUserService sysUserService;

    private final SysStorageSupport sysStorageSupport;

    /**
     * 根据id获取用户信息
     *
     * @param id id
     * @return 用户信息
     */
    public SysUserVO getUserById(Long id) {
        SysUser user = sysUserService.getById(id);
        if (Objects.isNull(user)) {
            return null;
        }
        return BeanUtil.toBean(user, SysUserVO.class, (r, t) -> {
            if (StringUtils.isNotBlank(r.getAvatar())) {
                t.setAvatarUrl(sysStorageSupport.getUrl(r.getAvatar()));
            }
        });
    }

    /**
     * 根据id集合获取用户信息
     *
     * @param ids id集合
     * @return 用户信息
     */
    public Map<Long, SysUserVO> getUserMapByIds(List<Long> ids) {
        List<SysUser> sysUserList = sysUserService.listByIds(ids);
        Map<String, String> urlMap = sysStorageSupport.getUrlMap(sysUserList.stream().map(SysUser::getAvatar).filter(Objects::nonNull).distinct().map(Long::parseLong).collect(Collectors.toList()));
        return BeanUtil.toList(sysUserList, SysUserVO.class, (r, t) -> {
            if (StringUtils.isNotBlank(r.getAvatar())) {
                t.setAvatarUrl(urlMap.get(r.getAvatar()));
            }
        }).stream().collect(Collectors.toMap(SysUserVO::getId, Function.identity()));
    }

}
