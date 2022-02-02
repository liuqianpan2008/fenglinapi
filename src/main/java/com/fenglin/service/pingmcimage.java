package com.fenglin.service;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class pingmcimage {
    @SneakyThrows
    public byte[] pingmcimage(HttpServletRequest request){
        //创建背景和画笔

        BufferedImage bgimage =  ImageIO.read(new ClassPathResource("static/bj.jpg").getInputStream());

        Graphics2D graphics = bgimage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //圆形logo

        BufferedImage bufferedImage =  ImageIO.read(new ClassPathResource("static/buffered.png").getInputStream());
        BufferedImage convertImage = convertCircular(bufferedImage);
        Image scledbufferedImage = convertImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        graphics.drawImage(scledbufferedImage, 398-10, 10,  null);
        //解析数据
        String agent=request.getHeader("User-Agent");
        //解析agent字符串
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);
        //获取浏览器对象
        Browser browser = userAgent.getBrowser();
        //获取操作系统对象
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        //写字
        Font font = getSIMSUN(Font.PLAIN, 16);
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒");
        graphics.setColor(Color.BLACK);
        graphics.setFont(font);
        graphics.drawString("你家的ip地址是:"+request.getRemoteAddr(),10,160-16*2);
        graphics.drawString("你当前使用的浏览器是:"+browser.getName(),10,160-16);
        graphics.drawString("这个浏览器类型是:"+browser.getBrowserType(),10,150+10+16*0);
        graphics.drawString("这个浏览器属于的家族:"+browser.getGroup(),10,150+10+16*1);
        graphics.drawString("这个浏览器生产厂商是:"+browser.getManufacturer(),10,150+10+16*2);
        graphics.drawString("这个浏览器使用的渲染引擎:"+browser.getRenderingEngine(),10,150+10+16*3);
        graphics.drawString("这个浏览器浏览器版本:"+userAgent.getBrowserVersion(),10,150+10+16*4);
        graphics.drawString("你的操作系统是:"+operatingSystem.getGroup().getName(),10,150+10+16*5);
        graphics.drawString("这个操作系统生产厂商:"+operatingSystem.getManufacturer(),10,150+10+16*6);
        graphics.drawString("当前时间:"+dateFormat.format(date),10,150+10+16*7);

        graphics.setColor(new Color(1,1,1, 116));
        graphics.drawString("By：枫叶秋林",395,150+10+16*7);

        //返回图片
        graphics.dispose();
        ByteArrayOutputStream outStream =new ByteArrayOutputStream();
        ImageIO.write(bgimage, "JPEG",outStream);
        byte[] bata = outStream.toByteArray();
        outStream.close();
        return bata;
    }

    public static BufferedImage convertCircular(BufferedImage bi1) throws IOException {
        BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1.getHeight());
        Graphics2D g2 = bi2.createGraphics();
        g2.setClip(shape);
        // 使用 setRenderingHint 设置抗锯齿
        g2.drawImage(bi1, 0, 0, null);
        // 设置颜色
        g2.setBackground(Color.green);
        g2.dispose();
        return bi2;
    }
    public static Font getSIMSUN(int style, float size) {
        Font font = null;
        //获取字体流
        InputStream simsunFontFile = pingmcimage.class.getResourceAsStream("/fonts/微软雅黑.ttf");
        try {
            font = Font.createFont(Font.PLAIN, simsunFontFile).deriveFont(style, size);
        } catch (FontFormatException e) {

        } catch (IOException e) {
            font = new Font("微软雅黑", Font.PLAIN, 16);
        }
        return font;
    }

}
