package com.haohaodayouxi.manage.service;

/**
 * FileDataCheckService
 *
 * @author TONE
 * @date 2025/4/28
 */
public interface FileDataCheckService {
    /**
     * Nsfw预测
     *
     * @param bytes 数据
     * @return true 为违规
     */
    boolean checkNsfw(byte[] bytes);
}
