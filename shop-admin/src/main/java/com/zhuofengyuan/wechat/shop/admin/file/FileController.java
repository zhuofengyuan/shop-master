package com.zhuofengyuan.wechat.shop.admin.file;


import com.zhuofengyuan.wechat.shop.resp.RestResponseBo;
import com.zhuofengyuan.wechat.shop.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;

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
        return Mono.just(RestResponseBo.ok(entity.getId()));
    }

    /**
     * 下载文件
     * @param id
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/{id}")
    public Mono<Void> download(@PathParam("id") String id, ServerHttpResponse response) throws IOException {
        var entity = this.fileService.getById(id);
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + entity.getFilename());
        response.getHeaders().setContentType(MediaType.IMAGE_PNG);

        Resource resource = new ClassPathResource(entity.getPath());
        File file = resource.getFile();
        return zeroCopyResponse.writeWith(file, 0, file.length());
    }
}
