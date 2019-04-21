package com.zljx.controller;

import com.zljx.vo.HttpClientService;
import com.zljx.vo.PicUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
       PicUploadResult result = new PicUploadResult();
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
        /*   System.out.println("H"+height);
           System.out.println("w"+width);*/
               //4.以当前时间创建文件夹，并且判断文件夹是否存在
               String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
               File dateFile = new File(dirPath+dateFormat);
               if(!dateFile.exists()){
                   dateFile.mkdirs();
               }
               //5.使用UUID使得图片文件名不一致，把UUID当做前缀，截取文件后缀就是.jps
               String prefix = UUID.randomUUID().toString().replace("-","");
               String suffix = filename.substring(filename.lastIndexOf("."));
               String newPath = prefix+suffix;
               //6.实现文件上传
               file.transferTo(new File(dirPath+dateFormat+"/"+newPath));
               System.out.println("上传成功");
               //7.返回数据
               result.setData(urlPath+dateFormat+"/"+newPath);
               return result;
           }catch (IOException e){
               result.setCode(1);
               e.printStackTrace();
               return result;
           }


    }
}
