package com.crud.dula.platform.query;

import com.crud.dula.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author crud
 * @date 2023/11/22
 */
@ApiModel(description = "用户信息查询")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserQuery extends BaseQuery {

}
