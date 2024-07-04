package com.crud.dula.common.upload;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author crud
 * @date 2024/5/16
 */
public interface Uploader {

    String UPLOADER_SUFFIX = "_UPLOADER";

    /**
     * 获取存储方式
     *
     * @return 存储方式
     */
    String storageType();

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件地址
     */
    String upload(MultipartFile file);
}
