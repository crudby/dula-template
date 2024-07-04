package com.crud.dula.platform.email;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author crud
 * @date 2023/9/25 20:02
 */
@Data
@AllArgsConstructor
public class EmailDTO {

    /**
     * 邮件接收方，可多人
     */
    private String[] tos;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;
}
