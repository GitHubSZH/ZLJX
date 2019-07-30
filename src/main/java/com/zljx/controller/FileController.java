package com.zljx.controller;

import com.zljx.vo.PicUploadResult;
import com.zljx.vo.QiniuCloudUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileController {



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
                   //把返回图片连接加个 "/" 使得可以访问图片路径
                   StringBuffer stringBuffer = new StringBuffer(url);
                   int cn = "image.zljx1027.cn".length();
                   StringBuffer newUrl = stringBuffer.insert(cn, "/");

                   result.setCode(0);
                   result.setMsg("文件上传成功");
                   System.out.println(newUrl);
                   result.setData("http://"+newUrl.substring(0, newUrl.lastIndexOf("?")));
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
        }   catch (Exception e){
            e.printStackTrace();
            result.setCode(1);
            result.setMsg("移除图片失败");
            return result;
        }
    }
}
