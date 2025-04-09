package com.haohaodayouxi.manage.utils;

import com.haohaodayouxi.common.core.exception.BusinessException;
import com.haohaodayouxi.manage.constants.enums.file.FrameLengthTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Ffmpeg处理工具
 */
@Slf4j
public class FfmpegUtil {

    /**
     * 获取视频封面图
     *
     * @param bytes          视频数据流
     * @param coverImgFormat 图片格式
     * @param videoCoverWith 图片宽度
     * @return 图片数据
     */
    private byte[] getVideoCoverImg(byte[] bytes, String coverImgFormat, int videoCoverWith) {
        ByteArrayOutputStream bos = null;
        try {
            // 视频对应的封面图
            bos = FfmpegUtil.getVideoFrameImage(new ByteArrayInputStream(bytes), coverImgFormat, videoCoverWith);
            if (ObjectUtils.isNotEmpty(bos)) {
                return bos.toByteArray();
            }
        } catch (Exception e) {
            log.error("视频封面图生成异常", e);
        } finally {
            IOUtils.closeQuietly(bos);
        }
        return null;
    }

    /**
     * 获取视频帧图片
     * 优先提取关键帧，若不存在就按照总帧数进行等份取第一份之后的
     *
     * @param bis            视频源
     * @param formatName     图片格式
     * @param videoCoverWith 图片宽度
     * @return 帧数据
     */
    public static ByteArrayOutputStream getVideoFrameImage(ByteArrayInputStream bis, String formatName, int videoCoverWith) {
        if (ObjectUtils.isEmpty(bis)) {
            throw new BusinessException("视频数据为空");
        }
        ByteArrayOutputStream bos = null;
        try {
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(bis);
            long start = System.currentTimeMillis();
            ff.start();
            int length = ff.getLengthInFrames();
            log.debug("视频总帧数length={}", length);
            if (ObjectUtils.isEmpty(length) || length <= 0) {
                log.error("获取视频数据失败，视频帧数=[{}]", length);
            }
            int i = 0;
            FrameLengthTypeEnum lengthTypeEnum = FrameLengthTypeEnum.getEnumByLength(length);
            int number = length / lengthTypeEnum.getLevelType();
            Frame frame;
            Frame last = null;
            while (i < length) {
                frame = ff.grabFrame();
                if (frame != null && frame.image != null) {
                    if (frame.keyFrame || i > number) {
                        log.debug("获取到帧 keyFrame: {}, i: {}", frame.keyFrame, i);
                        last = frame.clone();
                        break;
                    }
                }
                i++;
            }
            ff.stop();
            if (last != null && last.image != null) {
                BufferedImage bufferedImage = doExecuteFrame(last, videoCoverWith);
                bos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, formatName, bos);
            }
            long end = System.currentTimeMillis();
            log.debug("截取图片共耗时{}", end - start);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bos;
    }


    /**
     * 截取缩略图返回流
     *
     * @param frame frame
     */
    public static BufferedImage doExecuteFrame(Frame frame, int videoCoverWith) {
        //截取的图片
        if (null == frame || null == frame.image) {
            return null;
        }
        try (Java2DFrameConverter converter = new Java2DFrameConverter()) {
            BufferedImage srcImage = converter.getBufferedImage(frame);
            int srcImageWidth = srcImage.getWidth();
            int srcImageHeight = srcImage.getHeight();
            //对帧图片进行等比例缩放（缩略图）
            int width = srcImageWidth;
            int height = srcImageHeight;
            //没有配置宽和高 就使用自带的 否则使用配置的图片宽高
            if (ObjectUtils.isNotEmpty(videoCoverWith)) {
                width = videoCoverWith;
                height = (int) (((double) width / srcImageWidth) * srcImageHeight);
            }
            BufferedImage thumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            thumbnailImage.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            return thumbnailImage;
        }
    }

}
