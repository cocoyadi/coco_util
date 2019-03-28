package com.coco.util;


import com.coco.bean.Course;
import com.coco.bean.User;
import com.coco.constants.Constants;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * 使用freemark生成word文件
 *
 */
public class WordUtil {
    private Logger logger = Logger.getLogger(WordUtil.class);

    /**
     * freemark模板配置
     */
    private static Configuration configuration;





    public static void configure(String templatePath){
        configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File(templatePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 传入输出流，执行word渲染及文件输出
     * @param map
     * @param outputStream
     */
    public static void generateWordFile( Map<String,Object> map, OutputStream outputStream){
        configure(Constants.TemplatePath);
        Template template = null;
        try {
            //读取freemarker的导出模板
            template = configuration.getTemplate(Constants.TemplateName);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //写入字符流的抽象类
        Writer out = null;
        try {
            //创建一个使用默认大小输出缓冲区的缓冲字符输出流
            //OutputStreamWriter 字节-字符转换流
            //FileOutputStream 文件输出流
            out = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        try {
            //执行模板,使用提供的数据模型
            template.process(map, out);
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws FileNotFoundException {
        String filePath = "E:\\coco_util\\excel_util\\src\\main\\resources\\file\\";
        String fileName = "template_test.doc";

        Map<String, Object> map = new HashMap<String, Object>();
        User user = new User();
        user.setUserId(1111l);
        user.setAge(25);
        user.setUsername("coco");
        map.put("user",user);

        Course course1 = new Course();
        course1.setName("语文");
        course1.setNumber(15);
        course1.setPosition("教1");
        course1.setTeacher("大白");
        course1.setTime("2019-03-27 10:00:00");

        Course course2 = new Course();
        course2.setName("数学");
        course2.setNumber(15);
        course2.setPosition("教2");
        course2.setTeacher("大白");
        course2.setTime("2019-03-27 14:00:00");

        Course course3 = new Course();
        course1.setName("英语");
        course1.setNumber(15);
        course1.setPosition("教2");
        course1.setTeacher("小白");
        course1.setTime("2019-03-27 10:00:00");
        List<Course> list = new ArrayList<>();
        list.add(course1);
        list.add(course2);
        list.add(course3);

        map.put("user",user);
        map.put("courseList",list);

        //创建一个file对象
        File outFile = new File(filePath + fileName);
        OutputStream outputStream = new FileOutputStream(outFile);

        new WordUtil().generateWordFile( map, outputStream);
    }
}