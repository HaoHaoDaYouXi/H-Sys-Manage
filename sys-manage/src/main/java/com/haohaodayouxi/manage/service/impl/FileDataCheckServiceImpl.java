package com.haohaodayouxi.manage.service.impl;

import com.haohaodayouxi.common.core.interfaces.InitService;
import com.haohaodayouxi.common.util.constants.OSNameConstants;
import com.haohaodayouxi.manage.service.FileDataCheckService;
import com.haohaodayouxi.manage.utils.file.TensorFlowUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Service;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * FileDataCheckServiceImpl
 *
 * @author TONE
 * @date 2025/4/28
 */
@Slf4j
@Service
public class FileDataCheckServiceImpl implements FileDataCheckService, InitService {

    private static byte[] NSFW_DATA;

    @Override
    public void init() {
        log.info(" ============加载模型开始 ============ ");
        try {
            log.info(" ============ TensorFlow.version : {} ============ ", TensorFlow.version());
            if (System.getProperty("os.name").toLowerCase().contains(OSNameConstants.MAC)) {
                log.info(" TensorFlow 暂时不支持MAC系统 ");
            } else {
                InputStream is = Files.newInputStream(Paths.get("model/nsfw.pb"));
                NSFW_DATA = IOUtils.toByteArray(is);
                log.info(" ============ nsfw.pb加载成功 ============ ");
            }
        } catch (Exception e) {
            log.error("加载模型文件异常", e);
            throw new NoSuchBeanDefinitionException("加载模型文件异常");
        }
        log.info(" ============加载模型结束 ============ ");
    }

    @Override
    public boolean checkNsfw(byte[] bytes) {
        if (ObjectUtils.isEmpty(NSFW_DATA)) {
            log.info("无NSFW模型数据，不做鉴定");
            return false;
        }
        float prediction;
        long l = System.currentTimeMillis();
        try (Tensor<Float> image = TensorFlowUtil.constructAndExecuteGraphToNormalizeImage(bytes)) {
            float[] predictions = TensorFlowUtil.executeInceptionGraph(NSFW_DATA, image);
            log.debug("NSFW Prediction time-consuming : {} ms", System.currentTimeMillis() - l);
            log.debug("NSFW predictions {}", predictions);
            prediction = predictions[1];
        }
        return prediction >= 0.8;
    }
}
