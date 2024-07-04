package com.crud.dula.platform.web;

import com.crud.dula.common.result.HttpResult;
import com.crud.dula.common.utils.BeanUtil;
import com.crud.dula.platform.entity.SysStorage;
import com.crud.dula.platform.service.SysStorageService;
import com.crud.dula.common.upload.Uploader;
import com.crud.dula.platform.vo.SysStorageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统存储API
 *
 * @author crud
 * @date 2024/5/16
 */
@Api(value = "/sys", tags = {"系统存储API"})
@Slf4j
@RequestMapping("/sys")
@AllArgsConstructor
@RestController
public class SysStorageController {

    private final SysStorageService sysStorageService;

    private final Uploader uploader;

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 文件信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "file", name = "file", value = "文件", required = true)
    })
    @ApiOperation(value = "文件上传", notes = "文件上传", httpMethod = "POST")
    @PostMapping("/storage-upload")
    public HttpResult<SysStorageVO> upload(@RequestParam("file") MultipartFile file) {
        String filename = file.getOriginalFilename();
        String filepath = uploader.upload(file);
        SysStorage storage = new SysStorage(filename, filepath, uploader.storageType());
        sysStorageService.save(storage);
        return HttpResult.success(BeanUtil.toBean(storage, SysStorageVO.class));
    }

    /**
     * 文件信息
     *
     * @param id 文件ID
     * @return 文件信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "long", name = "id", value = "文件ID", required = true)
    })
    @ApiOperation(value = "文件信息", notes = "文件信息", httpMethod = "GET")
    @GetMapping("/storage-get")
    public HttpResult<SysStorageVO> get(@RequestParam("id") Long id) {
        SysStorage storage = sysStorageService.getById(id);
        return HttpResult.success(BeanUtil.toBean(storage, SysStorageVO.class));
    }

}
