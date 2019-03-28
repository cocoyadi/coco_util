package com.coco.util;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author zhangxiaoxun
 * @date 2019/3/27  16:43
 **/
public class CommonUtils {
    private final static Logger logger = Logger.getLogger(CommonUtils.class);

    /**
     * Date 转 String 格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     * @author miaozhangzhang
     * @date 2016-4-12下午03:36:35
     */
    public static String formateDateToStrHasHms(Date date) {
        if (date == null) {
            return "";
        }
        // 一种格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }


    /**
     * 方法说明：把日期变成 2009-01-02这样的格式 创建日期：2009-2-23,下午02:24:03,hyc
     *
     * @param date
     * @return
     */
    public static String formateDateToStr(Date date) {
        if (date == null) return "";
        // 一种格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return df.format(date);
    }


    /**
     * 方法说明：任意格式化 创建日期：2010-8-18,下午04:40:31,hyc
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String formateDateToStr(Date date, String formatStr) {
        if (date == null) {
            return "";
        }
        // 一种格式
        SimpleDateFormat df = new SimpleDateFormat(formatStr);
        return df.format(date);
    }

    /**
     * 方法说明：把格式好的字符串生成日期 创建日期：2009-5-4,上午11:31:30,hyc
     *
     * @param source
     * @return
     * @throws ParseException
     */
    public static Date parseDateFormStr(String source) throws ParseException {
        if (source == null || "".equals(source)) {
            return null;
        }
        // 一种格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return df.parse(source);
    }
}
