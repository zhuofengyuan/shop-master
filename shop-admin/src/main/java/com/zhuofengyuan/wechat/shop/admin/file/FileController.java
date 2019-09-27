package com.zhuofengyuan.wechat.shop.admin.file;


import com.zhuofengyuan.wechat.shop.resp.RestResponseBo;
import com.zhuofengyuan.wechat.shop.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * <p>
 *  文件 上传/下载 接口
 * </p>
 *
 * @author fengtoos
 * @since 2019-08-27
 */
@RestController
@RequestMapping("/admin/file")
public class FileController {

    @Autowired
    IFileService fileService;

    /**
     * 上传文件
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping
    public Mono<Object> upload(@RequestParam("file") MultipartFile file) throws IOException {
        var entity = this.fileService.upload(file);
        return Mono.just(RestResponseBo.ok(entity.getId(), 200));
    }

    /**
     * 下载文件
     * @param id
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public void download(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        var entity = this.fileService.getById(id);
        var file = new File(Paths.get(entity.getPath()).toUri());
        var fis = new FileInputStream(file);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setCharacterEncoding("utf-8");

        int i = 0;
        byte[] buff = new byte[(int)file.length()];
        fis.read(buff);
        var os = response.getOutputStream();
        os.write(buff);
        os.flush();
    }
}
