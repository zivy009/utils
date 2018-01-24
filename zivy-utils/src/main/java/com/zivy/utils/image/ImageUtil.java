package com.zivy.utils.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.zivy.utils.StringUtilZivy;

public class ImageUtil {

    private Logger log = LoggerFactory.getLogger(getClass());

    private static String DEFAULT_PREVFIX = "thumb_";
    private static Boolean DEFAULT_FORCE = false;

    /**
     * <p>
     * Title: thumbnailImage
     * </p>
     * <p>
     * Description: 根据图片路径生成缩略图
     * </p>
     * 
     * @param imagePath
     *            原图片路径
     * @param w
     *            缩略图宽
     * @param h
     *            缩略图高
     * @param prevfix
     *            生成缩略图的前缀
     * @param force
     *            是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
     */
    public String thumbnailImage(File imgFile, int w, int h, String prevfix, boolean force) {
        if (imgFile.exists()) {
            try {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG,
                // JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames());
                String suffix = null;
                // 获取图片后缀
                if (imgFile.getName().indexOf(".") > -1) {
                    suffix = imgFile.getName().substring(imgFile.getName().lastIndexOf(".") + 1);
                } // 类型和图片后缀全部小写，然后判断后缀是否合法
                if (suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()) < 0) {
                    log.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                    return null;
                }
                log.debug("target image's size, width:{}, height:{}.", w, h);
                Image img = ImageIO.read(imgFile);
                if (!force) {
                    // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if ((width * 1.0) / w < (height * 1.0) / h) {
                        if (width > w) {
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w / (width * 1.0)));
                            log.debug("change image's height, width:{}, height:{}.", w, h);
                        }
                    } else {
                        if (height > h) {
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h / (height * 1.0)));
                            log.debug("change image's width, width:{}, height:{}.", w, h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                String p = imgFile.getPath();
                // 将图片保存在原目录并加上前缀
                File file = new File(p.substring(0, p.lastIndexOf(File.separator)) + File.separator + prevfix + imgFile.getName());
                ImageIO.write(bi, suffix, file);
                return file.getAbsolutePath();
            } catch (IOException e) {
                log.error("generate thumbnail image failed.", e);
            }
        } else {
            log.warn("the image is not exist.");
        }
        return null;
    }

    /**
     * 裁剪 图片
     * 
     * @author zhc
     * @param bytes
     * @param x
     * @param y
     * @param w
     * @param h
     * @param suffix
     * @return
     * @throws IOException
     *             2016年6月12日
     */
    public static ByteArrayOutputStream scafCropper(byte[] bytes, int x, int y, int w, int h, String suffix) throws IOException {

        // 提供一个 BufferedImage，将其用作解码像素数据的目标。

        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        Image img = ImageIO.read(input);
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();

        g.drawImage(img, x, y, w, h, Color.LIGHT_GRAY, null);
        g.dispose();

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        // 将图片保存在原目录并加上前缀

        // File file = new File(filePath);
        ImageIO.write(bi, suffix.replaceAll("\\.", ""), bs);

        return bs;
        // return img.getSource().
    }

    //
    public static ByteArrayOutputStream cropper(byte[] bytes, int x, int y, int w, int h, String suffix) throws IOException {
        if (x < 0 || y < 0) {
            throw new RuntimeException("请在图片有效范围内裁剪！");
        }

        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        ImageInputStream iis = null;
        suffix = suffix.replaceAll("\\.", "");
        try {
            /*******************************************************************
             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
             * 参数：formatName - 包含非正式格式名称 . （例如 "jpeg" 或 "gif"）等 。
             */
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(suffix);
            ImageReader reader = it.next();
            // 获取图片流
            iis = ImageIO.createImageInputStream(is);

            /*******************************************************************
             * <p>
             * iis:读取源.true:只向前搜索
             * </p>
             * .将它标记为 ‘只向前搜索’。 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader
             * 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
             */
            reader.setInput(iis, true);

            /**
             * <p>
             * 描述如何对流进行解码的类
             * <p>
             * .用于指定如何在输入时从 Java Image I/O 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其
             * ImageReader 实现的 getDefaultReadParam 方法中返回 ImageReadParam 的实例。
             */
            ImageReadParam param = reader.getDefaultReadParam();

            /*******************************************************************
             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
             * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
             */
            Rectangle rect = new Rectangle(x, y, w, h);

            // 提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);

            /*******************************************************************
             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
             * BufferedImage 返回。
             */
            BufferedImage bi = reader.read(0, param);

            // 保存新图片
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            // 将图片保存在原目录并加上前缀

            // File file = new File(filePath);
            ImageIO.write(bi, suffix, bs);

            return bs;
        }

        finally {
            if (is != null)
                is.close();
            if (iis != null)
                iis.close();
        }

    }

    public String thumbnailImage(File imgFile, int w, int h, boolean force, String suffix) {
        if (imgFile.exists()) {
            try {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG,
                // JPEG, WBMP, GIF, gif]
                log.debug("target image's size, width:{}, height:{}.", w, h);
                Image img = ImageIO.read(imgFile);
                if (!force) {
                    // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if ((width * 1.0) / w < (height * 1.0) / h) {
                        if (width > w) {
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w / (width * 1.0)));
                            log.debug("change image's height, width:{}, height:{}.", w, h);
                        }
                    } else {
                        if (height > h) {
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h / (height * 1.0)));
                            log.debug("change image's width, width:{}, height:{}.", w, h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                String p = imgFile.getPath();
                // 将图片保存在原目录并加上前缀
                File file = new File(p.substring(0, p.lastIndexOf(File.separator)) + File.separator + DEFAULT_PREVFIX + imgFile.getName());
                ImageIO.write(bi, suffix, file);
                return file.getAbsolutePath();
            } catch (IOException e) {
                log.error("generate thumbnail image failed.", e);
            }
        } else {
            log.warn("the image is not exist.");
        }
        return null;
    }

    public String thumbnailImage(String imagePath, int w, int h, String prevfix, boolean force) {
        File imgFile = new File(imagePath);
        return thumbnailImage(imgFile, w, h, prevfix, force);
    }

    public String thumbnailImage(String imagePath, int w, int h, boolean force) {
        return thumbnailImage(imagePath, w, h, DEFAULT_PREVFIX, force);
    }

    public String thumbnailImage(String imagePath, int w, int h) {
        return thumbnailImage(imagePath, w, h, DEFAULT_FORCE);
    }

    public static void main(String[] args) {
        new ImageUtil().thumbnailImage("F:/other_prj/博瑞高清壁纸/6..jpg", 100, 150);
    }

    /**
     * 获得图片
     * 
     * @param is
     * @return
     * @throws IOException
     *             zhc 2016年3月16日
     */
    public BufferedImage obtainImage(InputStream is) throws IOException {
        BufferedImage image = ImageIO.read(is);
        return image;
    }

    /**
     * 获得缩略图片
     * 
     * @author zhc
     * @param pic
     * @return 2016年4月26日
     */
    public String obtainThumImg(String pic) {
        pic = pic == null ? "" : pic;
        pic = pic.replaceFirst("ys_", "");

        return pic + "?imageView2/1/w/200/h/200";
    }

    public static void makePic(String s, String filePath) throws Exception {
        int width = 400;
        int height = 400;
        File file = new File(filePath);

        Font font = new Font("Serif", Font.BOLD, 10);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        // g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);
        g2.setPaint(Color.RED);

        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(s, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = -bounds.getY();
        double baseY = y + ascent;

        g2.drawString(s, (int) x, (int) baseY);

        ImageIO.write(bi, "jpg", file);
    }

    // 给jpg添加文字
    public static ByteArrayOutputStream createStringMarkWithName(String filePath, String markContent, String name) throws Exception {
        ImageIcon imgIcon = new ImageIcon(filePath);
        String suffix = "jpg";
        Color markContentColor = Color.black;
        float qualNum = 100;
        Image theImg = imgIcon.getImage();
        int width = 325;// theImg.getWidth(null) == -1 ? 200 :
                        // theImg.getWidth(null);
        int height = 228; // =theImg.getHeight(null) == -1 ? 200 :
                          // theImg.getHeight(null);
        // System.out.println(width);
        // System.out.println(height);
        // System.out.println(theImg);
        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();
        g.setColor(markContentColor);
        g.setBackground(Color.red);
        g.drawImage(theImg, 0, 0, null);

        g.setFont(new Font("SimSun", Font.PLAIN, 28)); // 字体、字型、字号
        if (name != null) {
            g.drawString(name, 58, 42); // 画文字
        }

        g.setFont(new Font("SimSun", Font.PLAIN, 18)); // 字体、字型、字号
        StringBuffer sb = new StringBuffer();
        int s = 0, e = 0;
        int topy = 86;
        int leftx = 58;
        markContent.replaceFirst("&nbsp", "");
        int strLen = markContent.getBytes("GBK").length;

        int distant = 26;
        String wStr = "";
        s = 0;
        // 如果有第一行数据，写入第一行；
        if (strLen > s) {
            wStr = StringUtilZivy.subString(markContent, 18);// markContent.substring(s,e);

            g.drawString(wStr, leftx, topy); // 画文字
        }
        // 如果有第二行数据，写入第二行；
        s = 18;
        if (strLen > s) {

            markContent = markContent.replace(wStr, "");
            wStr = StringUtilZivy.subString(markContent, 18);

            g.drawString(wStr, leftx, distant + topy); // 画文字
        }
        s = 36;
        if (strLen > s) {
            markContent = markContent.replace(wStr, "");
            wStr = StringUtilZivy.subString(markContent, 18);
            g.drawString(wStr, leftx, distant * 2 + topy); // 画文字
        }

        s = 54;
        if (strLen > s) {

            markContent = markContent.replace(wStr, "");
            wStr = StringUtilZivy.subString(markContent, 16);
            g.drawString(wStr, leftx, distant * 3 + topy); // 画文字
        }
        s = 70;
        if (strLen > s) { // 结束
            // 临时。 处理掉非字符
            // wStr=new String(wStr.getBytes(),"utf-8");
            // markContent=new String(markContent.getBytes(),"utf-8");
            //
            markContent = markContent.replace(wStr, "");
            e = strLen > 78 ? 78 : strLen;
            if (strLen > s) {
                wStr = StringUtilZivy.subString(markContent, 8, true);
            } else {
                wStr = StringUtilZivy.subString(markContent, 8);
            }

            g.drawString(wStr, leftx, distant * 4 + topy); // 画文字
        }

        // g.drawString(markContent, 10, height / 2+20); // 画文字
        g.dispose();
        // 保存新图片
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        // 将图片保存在原目录并加上前缀
        // File file = new File(filePath);
        ImageIO.write(bimage, suffix, bs);
        return bs;
    }

    // 给jpg添加文字
    public static boolean createStringMark(String filePath, String markContent, String outPath) {
        ImageIcon imgIcon = new ImageIcon(filePath);
        Color markContentColor = Color.white;
        float qualNum = 100;
        Image theImg = imgIcon.getImage();
        int width = theImg.getWidth(null) == -1 ? 200 : theImg.getWidth(null);
        int height = theImg.getHeight(null) == -1 ? 200 : theImg.getHeight(null);
        System.out.println(width);
        System.out.println(height);
        System.out.println(theImg);
        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();
        g.setColor(markContentColor);
        g.setBackground(Color.red);
        g.drawImage(theImg, 0, 0, null);
        g.setFont(new Font(null, Font.BOLD, 15)); // 字体、字型、字号
        g.drawString(markContent, 10, height / 2); // 画文字
        g.drawString(markContent, 10, height / 2 + 20); // 画文字
        g.dispose();
        try {
            FileOutputStream out = new FileOutputStream(outPath); // 先用一个特定的输出文件名
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
            param.setQuality(qualNum, true);
            encoder.encode(bimage, param);
            out.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获得图片宽高
     * 
     * @author zivy
     * @throws IOException 
     * @date 2017年9月10日
     * @describe
     *
     */
    public Map<String,Integer> obtainWidthAndHeight(String imgUR) throws IOException {
        Map<String,Integer> map =new HashMap<String,Integer>();
        URL url = new URL(imgUR);
        URLConnection con = url.openConnection();
        HttpURLConnection con2 = (HttpURLConnection) url.openConnection();
        BufferedImage srcImage = null;
        srcImage = ImageIO.read(con2.getInputStream());
        int srcImageHeight = srcImage.getHeight();
        int srcImageWidth = srcImage.getWidth();
//        System.out.println("ok......srcImageHeight=" + srcImageHeight);
//        System.out.println("ok......srcImageWidth=" + srcImageWidth);
        map.put("width", srcImageWidth);
        map.put("height", srcImageHeight);
        return map;
    }
}