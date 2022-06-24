package com.example.biz.controller;

import com.example.common.utils.R;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/storage")
public class StorgaeController {

    /**
     * MultipartFile  这个类一般是用来接受前台传过来的文件
     * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile
     * 上传
     */
    @RequestMapping("/anon/upload")
    public R upload(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
        //MultipartFile转File
        //Spring提供了一个ResourceUtils工具类，它支持“classpath:”和“file:”的地址前缀，它能够从指定的地址加载文件资源
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        File filePath = new File(path.getAbsolutePath(), "static/cache/");
        if (!filePath.exists() && !filePath.isDirectory()) {
            filePath.mkdir();
        }

        //获取原始文件名称(包含格式)
        String originalFileName = file.getOriginalFilename();

        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

        //设置文件新名称: 当前时间+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + name + "." + type;

        //在指定路径下创建一个文件
        File targetFile = new File(filePath, fileName);

        //将文件保存到服务器指定位置
        try {
            file.transferTo(targetFile); //转存文件,保存到一个目标文件中
            Map<String, Object> map = new HashMap<>();
            map.put("name", fileName);
            map.put("type", type);
            map.put("url", "/cache/" + fileName);
            return new R<>(map);  //返回给前端进行回显
        } catch (IOException e) {
            e.printStackTrace();
            return new R<>(e);
        }
    }
}
