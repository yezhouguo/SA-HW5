package com.hw5.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.core.util.FileUtil;

@Controller
public class videoController {

    @RequestMapping("/")
    public String goupload()
    {
        return "upload";
    }

    @GetMapping(value="/gouploadagain")
    public String gouploadagain()
    {
        return "upload";
    }

    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file,HttpServletRequest request)
            throws InterruptedException 
    {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        request.getSession().setAttribute("fileOutPath", "D:\\GitHub\\SA-Homework\\SA-HW5\\HW5-output\\");
        //String filePath = request.getSession().getServletContext().getRealPath("");
        String filePath = "D:\\GitHub\\SA-Homework\\SA-HW5\\HW5-input\\";
        System.out.println("fileName-->" + fileName);
        System.out.println("getContentType-->" + contentType);
        try 
        {
            File targetFile = new File(filePath);  
            if(!targetFile.exists())
            {    
                targetFile.mkdirs();   
            }       
            FileOutputStream out = new FileOutputStream(filePath + fileName);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } 
        catch (Exception e) {
            // TODO: handle exception
            }
        //System.out.println(fileName.substring(0,fileName.lastIndexOf(".")));
        Convert convertVideo = new Convert();
        convertVideo.process(fileName);

        return "uploadSuccess";
    }

    @RequestMapping("/list")
    public String list(HttpServletRequest request,Model model)
    {
        System.out.println("bad");
        File dir = new File((String) request.getSession().getAttribute("fileOutPath"));
        File[] subFiles = dir.listFiles();
        ArrayList<File> videos = new ArrayList<>();
        ArrayList<String> videosName = new ArrayList<>();
        for (File subFile : subFiles) 
        {
            videos.add(subFile);
            videosName.add(subFile.getName());
            System.out.println(subFile.getAbsolutePath());
        }
        model.addAttribute("videos", videos);
        model.addAttribute("videosName", videosName);
        return "playlist";
    }

}