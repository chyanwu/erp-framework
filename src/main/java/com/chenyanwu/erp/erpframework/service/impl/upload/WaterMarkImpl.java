package com.chenyanwu.erp.erpframework.service.impl.upload;

import com.chenyanwu.erp.erpframework.service.upload.WaterMarkTemplate;

import java.awt.*;

/**
 * @ClassName WaterMarkImpl
 * @Description 在图片上添加水印
 * @Author chenyanwu
 * @Date 2018/12/17 12:26
 * @Version 1.0
 */
public class WaterMarkImpl extends WaterMarkTemplate {
    @Override
    public void drawString(String text, int width, int height, Graphics2D graphics2D) {
        String markText = WATER_MARK;
        if(!"".equals(text)) {
            markText = text;
        }
        //计算文字水印的宽高
        int fontW = FONT_SIZE * getTextLength(markText);
        int fontH = FONT_SIZE;

        int widthDiff = width - fontW;
        int heightDiff = height - fontH;

        int x = X;
        int y= Y;

        if(x>widthDiff){
            x = widthDiff;
        }
        if (y>heightDiff){
            y = heightDiff;
        }
        // 将水印文字绘制到画板
        graphics2D.drawString(markText, x, y + FONT_SIZE);
    }
}
