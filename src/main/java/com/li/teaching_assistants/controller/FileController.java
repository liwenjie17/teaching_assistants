package com.li.teaching_assistants.controller;


import com.alibaba.fastjson.JSONObject;
import com.li.teaching_assistants.dao.UserDao;
import com.li.teaching_assistants.service.TokenService;
import com.li.teaching_assistants.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/file")
@Slf4j
@CrossOrigin
public class FileController {
    private String UPLOAD_FOLDER = "C:/teaching_assistants/images/";

    @Value(value = "${image.address}")
    private String Address;


    @Resource
    private UserService userService;

    @Resource
    private TokenService tokenService;

    @RequestMapping("/upload")
    public JSONObject upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<Object, Object> meta = new HashMap<>();
        Map<String, Object> verify = tokenService.verifyToken(request, response);
        if((boolean)verify.get("isOk")){
            if (Objects.isNull(file) || file.isEmpty()) {
                log.error("文件为空");
                meta.put("msg","文件为空");
                meta.put("isOk",false);
                jsonObject.put("data",data);
                jsonObject.put("meta",meta);
            }else {
                try {
                    String email = verify.get("email").toString();
                    String preEmail = email.substring(0,email.indexOf("."));//取邮箱.com前缀，以防出现.com.png为后缀导致无法访问
                    //获取文件二进制
                    byte[] bytes = file.getBytes();
                    //取file文件的扩展名
                    String extension =  file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
                    //将文件名改为邮箱.com前缀+.png
                    Path path = Paths.get(UPLOAD_FOLDER+preEmail+extension);
                    //如果没有files文件夹，则创建
                    if (!Files.isWritable(path)) {
                        Files.createDirectories(Paths.get(UPLOAD_FOLDER));
                    }else {
                        Files.deleteIfExists(path);
                    }
                    //文件写入指定路径
                    Files.write(path, bytes);
                    String image = "http://"+Address+":8080/images/"+preEmail+extension;
                    log.info("image==="+image);
                    userService.updateImage(image,email);

                    log.debug("文件写入成功...");
                    meta.put("msg","文件上传成功");
                    meta.put("isOk",true);
                    jsonObject.put("data",data);
                    jsonObject.put("meta",meta);
                }catch (IOException ioe){
                    ioe.printStackTrace();
                    meta.put("msg","上传失败");
                    meta.put("isOk",false);
                    jsonObject.put("data",data);
                    jsonObject.put("meta",meta);
                }
            }
        }else {
            log.info("登陆信息已过期，请重新登录");
            meta.put("msg", "登陆信息已过期，请重新登录");
            meta.put("isOk", false);
            jsonObject.put("data", data);
            jsonObject.put("meta", meta);
        }

        return jsonObject;
    }
}
