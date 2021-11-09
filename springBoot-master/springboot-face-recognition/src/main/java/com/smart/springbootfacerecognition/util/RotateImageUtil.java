package com.smart.springbootfacerecognition.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectoryBase;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class RotateImageUtil {


    /**
     *
     * @param stringList 图片绝对路径
     */
    public static void rotateImage(List<String> stringList) {
        stringList.forEach(s -> {
            File file = new File(s);
            try {
                Metadata metadata = ImageMetadataReader.readMetadata(file);
                StringBuilder description = new StringBuilder();
                metadata.getDirectories().forEach(directory -> {
                    directory.getTags().forEach(tag -> {
                        if (tag.getTagType() == ExifDirectoryBase.TAG_ORIENTATION) {
                            description.append(tag.getDescription().replaceAll(" ", ""));
                        }
                    });
                });

                if (description.length() > 0) {
                    int rotateIndex = description.indexOf("Rotate");
                    int cwIndex = description.indexOf("CW");
                    if (rotateIndex >= 0 && cwIndex > 0 && rotateIndex < cwIndex) {
                        int angel = Integer.valueOf(description.substring(rotateIndex + 6, cwIndex));
                        log.info("============图片方向纠正，顺时针旋转{}°,图片路径：{}===========", angel, s);
                        BufferedImage oldImage = ImageIO.read(file);
                        BufferedImage newImage = RotateImageUtil.Rotate(oldImage, angel);
                        ImageIO.write(newImage, "jpg", file);
                        newImage.getGraphics().dispose();
                        oldImage.getGraphics().dispose();
                    }
                }

            } catch (ImageProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }



    /**

     * 对图片进行旋转
     *
     * @param src   被旋转图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    public static BufferedImage Rotate(Image src, int angel) {
        int srcWidth = src.getWidth(null);
        int srcHeight = src.getHeight(null);
        // 计算旋转后图片的尺寸
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
                srcWidth, srcHeight)), angel);
        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();


        //解决png透明图片会变黑的问题 8.31add
       /* res = g2.getDeviceConfiguration().createCompatibleImage(src.getWidth(null), src.getHeight(null), Transparency.TRANSLUCENT);
        g2 = res.createGraphics();*/

        // 进行转换
        g2.translate((rect_des.width - srcWidth) / 2,
                (rect_des.height - srcHeight) / 2);
        g2.rotate(Math.toRadians(angel), srcWidth / 2, srcHeight / 2);
        g2.drawImage(src, null, null);
        return res;
    }

    /**
     * 计算旋转后的图片
     *
     * @param src   被旋转的图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        // 如果旋转的角度大于90度做相应的转换
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }
        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angelAlpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angelDeltaWidth = Math.atan((double) src.height / src.width);
        double angelDeltaHeight = Math.atan((double) src.width / src.height);
        int lenDeltaWidth = (int) (len * Math.cos(Math.PI - angelAlpha
                - angelDeltaWidth));
        int lenDeltaHeight = (int) (len * Math.cos(Math.PI - angelAlpha
                - angelDeltaHeight));
        int desWidth = src.width + lenDeltaWidth * 2;
        int desHeight = src.height + lenDeltaHeight * 2;
        return new Rectangle(new Dimension(desWidth, desHeight));
    }



}
