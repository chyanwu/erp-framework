package com.chenyanwu.erp.erpframework.controller.upload;

import com.chenyanwu.erp.erpframework.common.ResultBean;
import com.chenyanwu.erp.erpframework.service.impl.upload.WaterMarkImpl;
import com.chenyanwu.erp.erpframework.service.upload.WaterMarkTemplate;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: chenyanwu
 * @Date: 2019/5/9 10:35
 * @Description:
 * @Version 1.0
 */
@Controller
@RequestMapping("/fileupload")
public class UploadFileController {

    @Value("${file.temppath}")
    private String TEMP_PATH;

    @Value("${file.path}")
    private String REAL_PATH;

    @Value("${file.repath}")
    private String RE_DRAWING_PATH;

    /**
     * 每个块的命名衔接符，便于根据块名找到块序号
     */
    private static final String CHUNK_NAME_SPLIT = "_";
    /**
     * 块文件后缀
     */
    private static final String SUFFIX = ".part";
    /**
     * 分片大小
     */
    private static final Integer CHUNK_SIZE = 5 * 1024 * 1024;
    /**
     * 图片支持格式
     */
    private static final String PHOTO_TYPE = "jpg,pne";
    /**
     * 视频支持格式
     */
    private static final String VIDEO_TYPE = "rm,rmvb,mpeg1-4,mov,mtv,dat,wmv,avi,3gp,amv,dmv,flv,exe";

    @RequestMapping(value = "/videolist", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("/upload/uploadvideo");
        modelAndView.addObject("typeList", VIDEO_TYPE);
        modelAndView.addObject("button", "多视频上传");
        return modelAndView;
    }

    @RequestMapping(value = "/photolist", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView photoList(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("/upload/uploadphoto");
        modelAndView.addObject("typeList", PHOTO_TYPE);
        modelAndView.addObject("button", "多图片上传");
        return modelAndView;
    }

    @RequestMapping(value = "/upload", produces = "application/json; charset=utf-8")
    @ResponseBody
    public double upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //文件名
            String name = request.getParameter("name");
            //文件块数
            String chunks = request.getParameter("chunks");
            //文件MD5值
            String fileMd5 = request.getParameter("fileMd5");
            //块号
            String chunk = request.getParameter("chunk");
            if (null != file) {
                //开始时间
                long startTime = System.currentTimeMillis();
                //判断上传的文件是否被分片（小于5M的不会分片）
                if (null == chunks && null == chunk) {
                    //没有被切片
                    File realFile = new File(REAL_PATH, name);
                    //如果下载路径不存在则新建,否则删除原有的文件
                    if (!realFile.getParentFile().exists()) {
                        realFile.getParentFile().mkdirs();
                    }
                    if (realFile.exists()) {
                        realFile.delete();
                    }
                    //上传
                    file.transferTo(realFile);
                    //创建文件
                    realFile.createNewFile();
                    //如果是图片加水印
                    reDrawingPhoto(realFile, "ERP", null, RE_DRAWING_PATH);
                    //实际上传文件大小(单位KB)
                    long size1 = realFile.length() / 1024;
                    //结束时间
                    long endTime = System.currentTimeMillis();
                    //速度(KB/s)
                    double speed = size1 * 1000 / ((endTime - startTime));

                    System.out.println("大小:" + size1 + "KB   开始时间:" + new Date(startTime) + "     结束时间:" + new Date(endTime) + "    平均速度:" + speed + "KB/s");
                    return speed;
                }
                //存储分片临时文件夹
                String tempFileDir = TEMP_PATH + File.separator + fileMd5;
                File parentFileDir = new File(tempFileDir);
                if (!parentFileDir.exists()) {
                    parentFileDir.mkdirs();
                }
                //碎片文件(按分片序号命名(fileMd5_chunk_.part)便于排序)
                File partF = new File(tempFileDir + File.separator + fileMd5 + CHUNK_NAME_SPLIT + chunk + CHUNK_NAME_SPLIT + SUFFIX);
                //mvc文件模块上传填充碎片
                //如果已存在,则删除重新写
                if (partF.exists()) {
                    partF.delete();
                }
                file.transferTo(partF);
                //创建碎片
                partF.createNewFile();
                //碎片大小
                long sizePart = partF.length() / 1024;
                long endTime = System.currentTimeMillis();
                //碎片传输速度
                double speed = sizePart * 1000 / ((endTime - startTime));
                System.out.println("碎片大小:" + sizePart + "KB   开始时间:" + new Date(startTime) + "     结束时间:" + new Date(endTime) + "    平均速度:" + speed + "KB/s");
                return speed;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("上传出错!");
        }
        return 0;
    }

    /**
     * @param realFile 文件
     * @param text     水印文字
     * @param integer  旋转
     * @param strPath  存放路径
     */
    private void reDrawingPhoto(File realFile, String text, Integer integer, String strPath) {
        WaterMarkTemplate wm = new WaterMarkImpl();
        wm.watermark(realFile, realFile.getName(), text, integer, strPath);
    }


    @RequestMapping(value = "/combine", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultBean combine(String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileMd5 = request.getParameter("fileMd5");
        String size = request.getParameter("size");
        //没有分片的文件
        if (size != null && Integer.parseInt(size) < CHUNK_SIZE) {
            return new ResultBean(name + "上传成功!");
        }
        // 所有分片文件都上传完成
        // 将所有分片文件合并到一个文件中
        File tempFileDir = new File(TEMP_PATH + File.separator + fileMd5);
        //获取文件夹里所有碎片文件
        File[] fileArray = tempFileDir.listFiles(new FileFilter() {
            //排除目录只要文件
            @Override
            public boolean accept(File pathname) {
                // TODO Auto-generated method stub
                if (pathname.isDirectory()) {
                    return false;
                }
                return true;
            }
        });

        if (fileArray == null || fileArray.length == 0) {
            return new ResultBean();
        }
        //转成集合，便于排序
        List<File> fileList = new ArrayList<>(Arrays.asList(fileArray));
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                // TODO Auto-generated method stub
                if (Integer.parseInt(o1.getName().split(CHUNK_NAME_SPLIT)[1]) < Integer.parseInt(o2.getName().split(CHUNK_NAME_SPLIT)[1])) {
                    return -1;
                }
                return 1;
            }
        });
        //synchronized (this) {
        File destTempFile = new File(REAL_PATH, name);
        if (!destTempFile.getParentFile().exists()) {
            destTempFile.getParentFile().mkdirs();
        }
        FileOutputStream destTempfos = new FileOutputStream(destTempFile, true);
        //输出流通道NIO
        FileChannel outChnnel = destTempfos.getChannel();
        //输入流通道NIO
        FileChannel inChannel;
        //合并
        for (File file : fileList) {
            inChannel = new FileInputStream(file).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChnnel);
            inChannel.close();
            //删除分片
            file.delete();
        }
        outChnnel.close();
        //关闭流
        destTempfos.close();
        //删除文件夹
        if (tempFileDir.isDirectory() && tempFileDir.exists()) {
            FileUtils.deleteDirectory(tempFileDir);
        }
        return new ResultBean(name + "上传成功!");
    }

    /**
     * 检验分片是否未上传或者未完成
     * result.header.status=0是已上传完整，否则为1
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkChunk", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultBean checkChunk(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //检查当前分块是否上传成功
        String fileMd5 = request.getParameter("fileMd5");
        String chunk = request.getParameter("chunk");
        String chunkSize = request.getParameter("chunkSize");
        String dir = TEMP_PATH + File.separator + fileMd5;
        String partName = fileMd5 + CHUNK_NAME_SPLIT + chunk + CHUNK_NAME_SPLIT;
        String suffix = ".part";
        File checkFile = new File(dir + File.separator + partName + suffix);
        //检查文件是否存在，且大小是否一致
        if (checkFile.exists() && checkFile.length() == Integer.parseInt(chunkSize)) {
            //上传过
            return new ResultBean("上传过");
        } else {
            //没有上传过
            return new ResultBean();
        }
    }

    /**
     * 文件上传前根据MD5检查
     * 上传过实现秒传
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkFile", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultBean checkFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //检查当前分块是否上传成功
        String fileMd5 = request.getParameter("fileMd5");
        String fileName = request.getParameter("fileName");
        String path = REAL_PATH + File.separator + fileName;
        FileInputStream inputStream = new FileInputStream(new File(path));
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] buffer = new byte[1024];
        int length = -1;
        while ((length = inputStream.read(buffer, 0, 1024)) != -1) {
            md.update(buffer, 0, length);
        }
        BigInteger bigInt = new BigInteger(1, md.digest());
        String realFileMD5 = bigInt.toString(16);
        if (realFileMD5.equals(fileMd5)) {
            return new ResultBean("文件已存在！");
        } else {
            return new ResultBean("文件未存在！");
        }
    }

    @RequestMapping("/uploadImg")
    @ResponseBody
    public ResultBean uploadImg(Long classesId, HttpServletRequest request) {
        List<Map<String, Object>> resultPath = new ArrayList<Map<String, Object>>();
        try {
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // Set factory constraints
            // 设置缓冲区大小，这里是4kb
            factory.setSizeThreshold(4096);
            // 设置缓冲区目录
            factory.setRepository(null);

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            // Set overall request size constraint
            // 设置最大文件尺寸，这里是4MB
            upload.setSizeMax(4194304);
            // 得到所有的文件
            List<FileItem> items = upload.parseRequest(request);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            // 文件夹
            String uploadDir = REAL_PATH + File.separator + sdf.format(new Date());
            //虚拟路径
            String uploadVirPath = TEMP_PATH + File.separator + sdf.format(new Date());

            Iterator<FileItem> i = items.iterator();
            while (i.hasNext()) {
                FileItem fi = i.next();
                String fileName = fi.getName();
                if (fileName != null) {
                    // 解决文件名乱码问题
                    File fullFile = new File(new String(fi.getName().getBytes(), "utf-8"));
                    File savedFile = new File(uploadDir, fullFile.getName());
                    if (!savedFile.getParentFile().exists()) {
                        savedFile.getParentFile().mkdir();
                    }
                    if (!savedFile.exists()) {
                        savedFile.createNewFile();
                    }
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("url", uploadVirPath + "/" + fullFile.getName());
                    map.put("fileSize", (fi.getSize()));
                    fi.write(savedFile);
                    resultPath.add(map);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultBean(resultPath);
    }

}
