package com.chenyanwu.erp.erpframework.common.codegenerator;

import com.chenyanwu.erp.erpframework.common.codegenerator.core.BeanUtils;
import com.chenyanwu.erp.erpframework.common.codegenerator.core.Configure;
import com.chenyanwu.erp.erpframework.common.codegenerator.model.Table;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName Generator
 * @Description TODO
 * @Author chenyanwu
 * @Date 2019/1/14 17:08
 * @Version 1.0
 */
public class Generator {
    protected Configure configure;

    public Generator() {
        this.configure = new Configure();
    }

    public Generator(Configure configure) {
        this.configure = configure;
    }

    public Writer createWriter(String targetDir, String path) {
        path = targetDir + path;
        File file = new File(path);
        String dir = file.getParent();
        File pd = new File(dir);
        if (!pd.exists()) {
            pd.mkdirs();
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }

    private VelocityEngine createVelocityEngine() {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, configure.getTargetDir() + "template/");
        ve.init();
        return ve;
    }

    private VelocityContext createContext(Table table) {
        Map map = BeanUtils.getValueMap(table);
        Map configMap = BeanUtils.getValueMap(configure);
        configMap.put("author", System.getProperty("user.name"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        configMap.put("dateTime", sdf.format(new Date()));
        map.putAll(configMap);
        VelocityContext context = new VelocityContext(map);

        return context;
    }

    public void generateModel(String targetDir, Table table) {
        VelocityEngine velocityEngine = createVelocityEngine();
        VelocityContext context = createContext(table);
        Writer writer = createWriter(targetDir, configure.getEntityPackage()
                .replace(".", "/") + "/" + table.getBeanName() + ".java");
        Template t = velocityEngine.getTemplate("beanTemplate.vm");

        t.merge(context, writer);
        flushWriter(writer);
    }

    public void generateMapper(String targetDir, Table table) {
        VelocityEngine velocityEngine = createVelocityEngine();
        VelocityContext context = createContext(table);
        Writer writer = createWriter(targetDir, configure.getMapperPackage()
                .replace(".", "/") + "/" + table.getBeanName() + "Mapper.java");
        Template t = velocityEngine.getTemplate("mapperTemplate.vm");

        t.merge(context, writer);
        flushWriter(writer);
    }

    public void generateXml(String targetDir, Table table) {
        VelocityEngine velocityEngine = createVelocityEngine();
        VelocityContext context = createContext(table);
        Writer writer = createWriter(targetDir, configure.getXmlPackage()
                .replace(".", "/") + "/" + table.getBeanName() + "Mapper.xml");
        Template t = velocityEngine.getTemplate("xmlTemplate.vm");

        t.merge(context, writer);

        flushWriter(writer);
    }

    public void generateService(String targetDir, Table table) {
        VelocityEngine velocityEngine = createVelocityEngine();
        VelocityContext context = createContext(table);
        Writer writer = createWriter(targetDir, configure.getServicePackage()
                .replace(".", "/") + "/" + table.getBeanName() + "Service.java");
        Template t = velocityEngine.getTemplate("serviceTemplate.vm");

        t.merge(context, writer);
        flushWriter(writer);
    }

    public void generateServiceImpl(String targetDir, Table table) {
        VelocityEngine velocityEngine = createVelocityEngine();
        VelocityContext context = createContext(table);
        Writer writer = createWriter(targetDir, configure.getServiceImplPackage()
                .replace(".", "/") + "/" + table.getBeanName() + "ServiceImpl.java");
        Template t = velocityEngine.getTemplate("serviceImplTemplate.vm");

        t.merge(context, writer);
        flushWriter(writer);
    }

    public void generateWebController(String targetDir, Table table) {
        VelocityEngine velocityEngine = createVelocityEngine();
        VelocityContext context = createContext(table);
        Writer writer = createWriter(targetDir, configure.getControllerPackage()
                .replace(".", "/") + "/" + table.getBeanName() + "Controller.java");
        Template t = velocityEngine.getTemplate("controllerTemplate.vm");

        t.merge(context, writer);
        flushWriter(writer);
    }

    public void generatePages(String targetDir, Table table) {
//        generateIndexPage(targetDir,table);
//        generateAddPage(targetDir,table);
//        generateEditPage(targetDir,table);
    }

    private void flushWriter(Writer writer) {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
