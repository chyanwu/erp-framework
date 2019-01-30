package com.chenyanwu.erp.erpdao.codegenerator;

import com.chenyanwu.erp.erpdao.ErpDaoApplication;
import com.chenyanwu.erp.erpdao.codegenerator.core.Configure;
import com.chenyanwu.erp.erpdao.codegenerator.core.DataProcessor;
import com.chenyanwu.erp.erpdao.codegenerator.model.Table;

import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName AutoGeneratorUtil
 * @Description 代码生成器
 * @Author chenyanwu
 * @Date 2019/1/14 15:40
 * @Version 1.0
 */
public class AutoGeneratorUtil {
    // 数据库信息
    private static String url = "jdbc:mysql://127.0.0.1:3306/erp-framework?serverTimezone=GMT";
    private static String driver = "com.mysql.jdbc.Driver";
//    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String user = "root";
    private static String password = "mysql";

    /**
     * 获取数据库连接.
     *
     * @return connection
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection;
        Class.forName(driver);
        connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    /**
     * 获取项目的路径
     *
     * @return
     * @throws Exception
     */
    public static String getProjectPath() throws Exception {
        URL url = ErpDaoApplication.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = URLDecoder.decode(url.getPath(), "utf-8");
        filePath = filePath.substring(1, filePath.length() - ("/target/classes").length());

        // 处理操作系统，mac系统需要添加一个/
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            filePath = "/" + filePath;
        }

        return filePath;
    }

    public static void main(String[] args) throws Exception {

        //注意：一定要指定下划线，目的是为了下面截取出模块名;在此处修改对应的表名来生成代码
        String tableNamePattern = "erp_user";
        String childPackage = "";
        //设置模块名
        int index = tableNamePattern.indexOf("_");
        if (index > 0) {
            childPackage = "." + tableNamePattern.substring(0, index);
        }

        // 设置生成代码路径
        Configure configure = new Configure();
        configure.setTargetDir(getProjectPath() + "/src/main/java/")
                .setEntityPackage("com.chenyanwu.erp.erpdao.entity" + childPackage)
                // 此处是生成mapper的xml文件，在resources目录下
                .setXmlPackage("mapper" + childPackage)
                .setMapperPackage("com.chenyanwu.erp.erpdao.dao" + childPackage)
                .setServicePackage("com.chenyanwu.erp.erpservice.service" + childPackage)
                .setServiceImplPackage("com.chenyanwu.erp.erpservice.service.impl" + childPackage)
                .setControllerPackage("com.chenyanwu.erp.erpweb.controller" + childPackage);

        Connection connection = getConnection();
        DataProcessor dataProcessor = new DataProcessor(connection);
        List<Table> tableInfos = dataProcessor.getTableInfos(tableNamePattern);
        // 生成代码
        Generator generator = new Generator(configure);
        try {
            for (Table table : tableInfos) {

                // 生成Dao层的代码
                generator.generateModel(configure.getTargetDir(), table);
                String xmlDir = configure.getTargetDir().replace("main/java", "main/resources");
                generator.generateXml(xmlDir, table);
                generator.generateMapper(configure.getTargetDir(), table);

                // 生成Service层的代码 targetDir: D:/projects/erp-framework/erp-dao//src/main/java/
                String serviceDir = configure.getTargetDir().replace("/erp-dao//src", "/erp-service//src");
                generator.generateService(serviceDir, table);
                generator.generateServiceImpl(serviceDir, table);

                // 生成Web层的代码
                String controllerDir = configure.getTargetDir().replace("/erp-dao//src", "/erp-web///src");
                generator.generateWebController(controllerDir, table);

                // 生成界面的代码
//                String pagetemplateDir = configure.getTargetDir().replace("/core/src/main/java", "/webapp/src/main/resources/templates");
//                generator.generatePages(pagetemplateDir, table);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
