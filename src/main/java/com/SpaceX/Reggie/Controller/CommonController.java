package com.SpaceX.Reggie.Controller;

import com.SpaceX.Reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        log.info("上传文件：{}", file.getOriginalFilename());
        String originalName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalName.substring(originalName.lastIndexOf("."));
        try {
            file.transferTo(new File("/Users/Zheng/Downloads/" + fileName));
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        File file = new File("/Users/Zheng/Downloads/" + name);
        try {
            FileInputStream fis = new FileInputStream(file);
            ServletOutputStream sos = response.getOutputStream();
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while((len = fis.read(bytes)) != -1){
                sos.write(bytes, 0, len);
                sos.flush();
            }

            sos.close();
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
