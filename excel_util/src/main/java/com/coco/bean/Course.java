package com.coco.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangxiaoxun
 * @date 2019/3/27  16:21
 **/
@Setter
@Getter
public class Course {
    String name;  //课程名称
    Integer number;  //人数
    String time;  //时间
    String teacher; //教师
    String position; //地点
}
