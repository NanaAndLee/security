package com.lee.web.controller;

import com.lee.dto.FileInfo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileController {

    /**
     *文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {

        System.out.println("name : "+file.getName()+"originalName : "+file.getOriginalFilename()
                + "size : " + file.getSize());

        String folder = "D:\\code\\java\\lee\\springsecurity\\lee-security-demo\\src\\main\\java\\com\\lee\\web\\controller";

        File localFile = new File( folder, new Date(  ).getTime() + ".txt" );
        file.transferTo( localFile );
        return new FileInfo( localFile.getAbsolutePath() );
    }

    /**
     * 文件下载   localhost:8060/file/1559466068443
     * @param id
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/{id}")
    public FileInfo download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String folder = "D:\\code\\java\\lee\\springsecurity\\lee-security-demo\\src\\main\\java\\com\\lee\\web\\controller";

        try(
                //jdk7之后，流可以写在这里，执行完trycatch之后jdk会自动帮你关掉，不用finally了
                InputStream inputStream = new FileInputStream( new File( folder, id + ".txt") );
                OutputStream outputStream = response.getOutputStream();
                ){

            response.setContentType( "application/x-download" );
            response.addHeader( "Content-Disposition", "attachment;filename=test.txt" );

//            Apache的IO工具包
            IOUtils.copy( inputStream, outputStream );
            outputStream.flush();
        }
        return null;
    }



}
