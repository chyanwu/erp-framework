package com.chenyanwu.erp.erpdao.codegenerator.core;

/**
 * @ClassName Configure
 * @Description 生成代码所要放的路径设置
 * @Author chenyanwu
 * @Date 2019/1/14 16:38
 * @Version 1.0
 */
public class Configure {
    private String targetDir;
    private String entityPackage;
    private String xmlPackage;
    private String mapperPackage;
    private String servicePackage;
    private String serviceImplPackage;
    private String controllerPackage;

    public String getTargetDir() {
        return targetDir;
    }

    public Configure setTargetDir(String targetDir) {
        this.targetDir = targetDir;
        return this;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public Configure setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
        return this;
    }

    public String getXmlPackage() {
        return xmlPackage;
    }

    public Configure setXmlPackage(String xmlPackage) {
        this.xmlPackage = xmlPackage;
        return this;
    }

    public String getMapperPackage() {
        return mapperPackage;
    }

    public Configure setMapperPackage(String mapperPackage) {
        this.mapperPackage = mapperPackage;
        return this;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public Configure setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
        return this;
    }

    public String getServiceImplPackage() {
        return serviceImplPackage;
    }

    public Configure setServiceImplPackage(String serviceImplPackage) {
        this.serviceImplPackage = serviceImplPackage;
        return this;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public Configure setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
        return this;
    }
}
