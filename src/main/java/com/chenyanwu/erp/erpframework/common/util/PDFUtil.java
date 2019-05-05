package com.chenyanwu.erp.erpframework.common.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * @Auther: chenyanwu
 * @Date: 2019/5/5 17:03
 * @Description:
 * @Version 1.0
 */
public class PDFUtil {
    private static final String FONT = "simhei.ttf";

    private static Configuration freemarkerCfg = null;

    static {
        freemarkerCfg =new Configuration();
        //freemarker的模板目录
//        try {
//            freemarkerCfg.setDirectoryForTemplateLoading(new File(PathUtil.getCurrentPath()));
        freemarkerCfg.setClassForTemplateLoading(PDFUtil.class, "/");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    /**
     * 将freemark渲染为html，转换成pdf
     * @param t 动态数据
     * @param
     * @param <T>
     */
    public static <T> void htmlToPdf(T t, String fileName, String template, HttpServletResponse resp) {
        // 渲染html内容
        String content = PDFUtil.freeMarkerRender(t, template);

        Document document = new Document();
        try {
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("content-Type", "application/pdf");
            resp.setHeader("Content-Disposition",
                    "inline;filename=" + URLEncoder.encode(fileName + ".pdf", "UTF-8"));
            // 建立书写器
            PdfWriter writer = PdfWriter.getInstance(document, resp.getOutputStream());
            document.open();
            XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            fontImp.register(FONT);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                    new ByteArrayInputStream(content.getBytes("UTF-8")), null, Charset.forName("UTF-8"), fontImp);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    /**
     * freemarker渲染html
     */
    public static <T> String freeMarkerRender(T data, String htmlTmp) {
        Writer out = new StringWriter();
        try {
            // 获取模板,并设置编码方式
            Template template = freemarkerCfg.getTemplate(htmlTmp);
            template.setEncoding("UTF-8");
            // 合并数据模型与模板，将合并后的数据和模板写入到流中，这里使用的字符流
            template.process(data, out);
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
