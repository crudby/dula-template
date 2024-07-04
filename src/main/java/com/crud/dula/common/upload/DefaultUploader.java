package com.crud.dula.common.upload;

import com.crud.dula.common.id.GlobalUtil;
import com.crud.dula.common.result.BizException;
import com.crud.dula.common.utils.TimeUtil;
import com.crud.dula.platform.constant.StorageConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;

/**
 * @author crud
 * @date 2024/5/16
 */
@Slf4j
@Component(StorageConstants.DEFAULT_STORAGE_TYPE + Uploader.UPLOADER_SUFFIX)
public class DefaultUploader implements Uploader{

    @Value("${dula.uploader.default.urlPrefix}")
    private String defaultUrlPrefix;

    @Value("${dula.uploader.default.storagePath}")
    private String defaultStoragePath;

    @Override
    public String storageType() {
        return StorageConstants.DEFAULT_STORAGE_TYPE;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件路径
     */
    @Override
    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String suffix = "";
        if (originalFilename != null) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String fileName = GlobalUtil.getNextId() + suffix;
        String monthDirectory = TimeUtil.format(LocalDate.now(), "yyyyMM");
        try {
            File newFile = new File(getDir(monthDirectory), fileName);
            file.transferTo(newFile);
            return defaultUrlPrefix + monthDirectory + "/" + fileName;
        } catch (Exception e) {
            throw new BizException("文件上传失败", e);
        }
    }

    /**
     * 获取保存路径
     *
     * @param monthDirectory 次级目录
     * @return 保存路径
     */
    private File getDir(String monthDirectory) {
        // 完整的保存路径
        String savePath = defaultStoragePath + monthDirectory;
        // 创建目录，如果不存在
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 获取运行路径
     *
     * @return 运行路径
     */
    public static String getRunPath() {
        try {
            String baseDirectory = ResourceUtils.getURL("classpath:").getPath();
            if (baseDirectory.startsWith("file:")) {
                baseDirectory = baseDirectory.substring(5);
            }
            // 顶级目录
            return new File(baseDirectory).getParent();
        }catch (Exception e) {
            throw new BizException("获取运行路径失败", e);
        }
    }
}
