package com.crud.dula.platform.support;

import com.crud.dula.common.upload.Uploader;
import com.crud.dula.platform.entity.SysStorage;
import com.crud.dula.platform.service.SysStorageService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author chang
 * @date 2024/6/9
 */
@AllArgsConstructor
@Component
public class SysStorageSupport {

    private final SysStorageService sysStorageService;

    private final Uploader uploader;

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 文件ID
     */
    public String upload(MultipartFile file) {
        if (Objects.isNull(file)) {
            return "";
        }
        String filename = file.getOriginalFilename();
        String filepath = uploader.upload(file);
        SysStorage storage = new SysStorage(filename, filepath, uploader.storageType());
        sysStorageService.save(storage);
        return storage.getId().toString();
    }

    /**
     * 文件上传
     *
     * @param files 文件
     * @return 文件ID
     */
    public String upload(MultipartFile[] files) {
        if (Objects.isNull(files) || files.length == 0) {
            return "";
        }
        List<String> fileIds = new ArrayList<>(files.length);
        for (MultipartFile file : files) {
            fileIds.add(upload(file));
        }
        return String.join(",", fileIds);
    }

    /**
     * 获取文件URL
     *
     * @param id 文件ID
     * @return 文件URL
     */
    public String getUrl(String id) {
        if (StringUtils.isBlank(id)) {
            return "";
        }
        SysStorage storage = sysStorageService.getById(id);
        return Optional.ofNullable(storage).map(SysStorage::getFilepath).orElse("");
    }

    /**
     * 获取文件URL
     *
     * @param ids 文件ID
     * @return 文件URL
     */
    public Map<String, String> getUrlMap(List<Long> ids) {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            return new HashMap<>();
        }
        return sysStorageService.listByIds(ids).stream().collect(HashMap::new, (m, v) -> m.put(v.getId().toString(), v.getFilepath()), HashMap::putAll);
    }
}
