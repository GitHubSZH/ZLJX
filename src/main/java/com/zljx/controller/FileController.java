package com.zljx.controller;

import com.qiniu.common.QiniuException;
import com.zljx.vo.HttpClientService;
import com.zljx.vo.PicUploadResult;
import com.zljx.vo.QiniuCloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@PropertySource("classpath:/properties/image.properties")
public class FileController {

    @Autowired
    private HttpClientService httpClientService;

    @Value("${image.dirPath}")
    private String dirPath;
    @Value("${image.urlPath}")
    private String urlPath;


    //判断是否是第一次加载
        /*   String sx = (String) session.getAttribute("temp");
           if(StringUtils.isEmpty(sx)){
               session.setAttribute("temp","123");
              System.out.println("第一次加载");
           }else{
               src.clear();
               System.out.println("加载多次");
           }*/


   @RequestMapping("/upload")
    public PicUploadResult upload(@RequestParam("files") MultipartFile file, HttpSession session){
       System.out.println(file);
       PicUploadResult result = new PicUploadResult();

       if(StringUtils.isEmpty(file)){
           result.setCode(1);
           result.setMsg("请先选择需要上传的图片");
           return result;
       }
       //1.获取图片名称


           String filename = file.getOriginalFilename();
           //1.1把名称全部转换为小写
           filename = filename.toLowerCase();
           //2.1判断是否为.jsp|png|gif结尾的，用正则来判断
           if(!filename.matches("^.+\\.(jpg|png|gif)$")){

               result.setCode(1);
               return result;
           }

           //3.判断是否是恶意程序 BufferedImage
           try {
               BufferedImage image = ImageIO.read(file.getInputStream());
               int height = image.getHeight();
               int width = image.getWidth();
               if(height==0||width==0){
                   result.setCode(1);
                   return result;
               }
               byte[] bytes = file.getBytes();
               String imageName  = UUID.randomUUID().toString().replace("-","");
               QiniuCloudUtil qiniuUtil = new QiniuCloudUtil();
               try {
                   //使用base64方式上传到七牛云
                   String url = qiniuUtil.put64image(bytes, imageName);
                   result.setCode(0);
                   result.setMsg("文件上传成功");
                   //去除后面的"?号" http://psmddsfe1.bkt.clouddn.com/05f706c7d54d4bb18b8bdef8c5de0ccc?imageView2/0/q/75|imageslim
                   int i = url.lastIndexOf("?");
                   String newUrl = url.substring(0, i);
                   result.setData("http://"+newUrl);
                   return result;
               } catch (Exception e) {
                   e.printStackTrace();
                   result.setCode(1);
                   result.setMsg("文件上传失败");
                   return result;
               }
           }catch (IOException e){
               result.setCode(1);
               e.printStackTrace();
               return result;
           }


    }
    @RequestMapping("/manage/delete")
    public PicUploadResult deletePic(@RequestParam("pickey") String pickey){
        PicUploadResult result = new PicUploadResult();
        QiniuCloudUtil qiniuUtil = new QiniuCloudUtil();
        try {
            //七牛云 删除图片
            qiniuUtil.delete(pickey);
            result.setCode(0);
            result.setMsg("移除图片成功");
            return result;
        } catch (QiniuException e) {
            e.printStackTrace();
            result.setCode(1);
            result.setMsg("该图片已经不存在了！");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(1);
            result.setMsg("移除图片失败");
            return result;
        }
    }
}
