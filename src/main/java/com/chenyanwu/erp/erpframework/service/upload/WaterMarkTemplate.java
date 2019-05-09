package com.chenyanwu.erp.erpframework.service.upload;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @ClassName WaterMarkTemplate
 * @Description TODO
 * @Author chenyanwu
 * @Date 2018/12/17 11:38
 * @Version 1.0
 */
public abstract class WaterMarkTemplate {
    // 定义水印的文字
    public static final String WATER_MARK = "复用无效";
    // 定义水印字体参数
    public static final int FONT_STYLE = Font.BOLD;
    public static final String FONT_NAME = "楷体";
    public static final int FONT_SIZE = 30;
    public static final Color FONT_COLOR = Color.RED;

    // 透明度
    public static float ALPHA = 0.3F;
    // 切斜度
    public static Integer DEGREE = 30;

    public static final int X = 10;
    public static final int Y = 10;

    /** 由子类实现将文字水印画入画板
     * @param text
     * @param width
     * @param height
     * @param graphics2D
     */
    public abstract void drawString(String text, int width, int height, Graphics2D graphics2D);

    /**
     * 实现图片上添加水印.
     * @param file  图片文件
     * @param filename 文件名
     * @param text 水印文字
     * @param deg 旋转度数
     * @param storePath 存储路径
     * @return 返回添加水印的图片的路径
     */
    public String watermark(File file, String filename, String text, Integer deg, String storePath) {
        String logoFileName = "logo_" + filename;
        OutputStream os = null;
        try {
            // 读取图片
            Image image = ImageIO.read(file);
            // 获取图片的高度和宽度
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            // 图片缓存
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // 创建绘图工具
            Graphics2D graphics2D = bufferedImage.createGraphics();
            // 通过绘图工具对象将原图绘制到画板
            graphics2D.drawImage(image, 0,0, width, height, null);
            // 设置画板的文字类型
            graphics2D.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
            graphics2D.setColor(FONT_COLOR);

            // 设置画板中水印的透明度
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
            // 设置画板旋转
            if(deg != null) {
                graphics2D.rotate(Math.toRadians(deg),bufferedImage.getWidth()/2,bufferedImage.getHeight()/2);
            }

            this.drawString(text, width, height, graphics2D);

            graphics2D.dispose();
            File imgFile = new File(storePath,logoFileName);
            if(!imgFile.getParentFile().exists()){
                imgFile.getParentFile().mkdirs();
            }
            os = new FileOutputStream(imgFile);

            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
            en.encode(bufferedImage);

        } catch (Exception e) {
            if(os != null){
                try {
                    os.close();
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取水印文字的宽
     * @param text 输入的水印文字
     * @return 返回水印文字的宽
     */
    public int getTextLength(String text){
        int length = text.length();

        for (int i=0; i<text.length(); i++){
            String s = String.valueOf(text.charAt(i));
            // 长度大于1，说明是汉字
            if (s.getBytes().length > 1){
                length++;
            }
        }

        // 中英文字符的转换
        length = length%2==0 ? length/2 : length/2 + 1;
        return length;
    }
}
