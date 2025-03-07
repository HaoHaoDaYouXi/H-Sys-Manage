package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.common.core.interfaces.InitService;
import com.haohaodayouxi.common.redis.service.impl.CommonRedisServiceImpl;
import com.haohaodayouxi.common.util.constants.StringConstant;
import com.haohaodayouxi.manage.constants.RedisConstants;
import com.haohaodayouxi.manage.mapper.SParamMapper;
import com.haohaodayouxi.manage.model.bo.param.SParamBO;
import com.haohaodayouxi.manage.model.db.SParam;
import com.haohaodayouxi.manage.model.req.param.SParamReq;
import com.haohaodayouxi.manage.service.SParamService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SParamServiceImpl
 *
 * @author TONE
 * @date 2024/12/8
 */
@Slf4j
@Service
public class SParamServiceImpl extends ServiceImpl<SParamMapper, SParam> implements SParamService, InitService {

    @Resource
    private CommonRedisServiceImpl<SParamBO> stringRedisServiceImpl;


    @Override
    public void init() {
        // 查询并放入缓存
        setCache(baseMapper.getByReq(null));
    }

    private void setCache(List<SParamBO> bos) {
        if (ObjectUtils.isNotEmpty(bos)) {
            stringRedisServiceImpl.batchSet(bos.stream().collect(Collectors.toMap(k -> RedisConstants.getParamKey(k.getParamCode()), v -> v, (v1, v2) -> v2)));
        }
    }

    @Override
    public int updateBatch(List<SParam> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SParam> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SParam> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public List<SParamBO> getByReq(SParamReq req) {
        if (ObjectUtils.isEmpty(req) || ObjectUtils.allNull(req.getParamCode(), req.getParamCodes(), req.getParamParentCode())) {
            return null;
        }
        if (ObjectUtils.allNotNull(req.getParamCode(), req.getParamCodes())) {
            req.setParamCodes(req.getParamCodes() + StringConstant.EN_COMMA + req.getParamCode());
        }
        List<SParamBO> res = baseMapper.getByReq(req);
        setCache(res);
        return res;
    }

    public List<SParamBO> getByCache(SParamReq req) {
        if (ObjectUtils.isEmpty(req) || ObjectUtils.allNull(req.getParamCode(), req.getParamCodes(), req.getParamParentCode())) {
            return null;
        }
        List<String> ids = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(req.getParamCode())) {
            ids.add(req.getParamCode().toString());
        }
        if (ObjectUtils.isNotEmpty(req.getParamCodes())) {
            ids.addAll(Arrays.stream(req.getParamCodes().split(StringConstant.EN_COMMA)).toList());
        }
        List<SParamBO> res;
        if (ObjectUtils.isNotEmpty(req.getParamParentCode())) {
            res = stringRedisServiceImpl.batchGetByPattern(RedisConstants.getParamKey(req.getParamParentCode()) + StringConstant.MATCHES_PATTERN, SParamBO.class);
            if (ObjectUtils.isNotEmpty(ids)) {
                res = res.stream().filter(f -> ids.contains(f.getParamCode().toString())).toList();
            }
        } else {
            res = stringRedisServiceImpl.batchGetByKeys(ids.stream().map(f -> RedisConstants.getParamKey(Long.parseLong(f))).toList(), SParamBO.class, true);
        }
        if (ObjectUtils.isEmpty(res)) {
            res = getByReq(req);
        } else {
            res = res.stream().sorted(Comparator.comparing(SParamBO::getParamSortCode).thenComparing(SParamBO::getParamCode)).collect(Collectors.toList());
        }
        return res;
    }
}
