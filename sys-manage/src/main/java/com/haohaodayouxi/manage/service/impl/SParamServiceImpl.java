package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.common.core.constants.CurrentUserContextHolder;
import com.haohaodayouxi.common.core.exception.BusinessException;
import com.haohaodayouxi.common.core.interfaces.InitService;
import com.haohaodayouxi.common.redis.service.impl.CommonRedisServiceImpl;
import com.haohaodayouxi.common.util.constants.StringConstant;
import com.haohaodayouxi.common.util.enums.TrueFalseEnum;
import com.haohaodayouxi.manage.constants.RedisConstants;
import com.haohaodayouxi.manage.mapper.SParamMapper;
import com.haohaodayouxi.manage.model.bo.login.LoginCacheBO;
import com.haohaodayouxi.manage.model.bo.param.SParamBO;
import com.haohaodayouxi.manage.model.db.SParam;
import com.haohaodayouxi.manage.model.req.param.SParamAddOrUpdReq;
import com.haohaodayouxi.manage.model.req.param.SParamReq;
import com.haohaodayouxi.manage.service.SParamService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.*;
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
        if (ObjectUtils.isNotEmpty(req) && ObjectUtils.allNotNull(req.getParamCode(), req.getParamCodes())) {
            req.setParamCodes(req.getParamCodes() + StringConstant.EN_COMMA + req.getParamCode());
        }
        List<SParamBO> res = baseMapper.getByReq(req);
        setCache(res);
        return res;
    }

    public List<SParamBO> getByCache(SParamReq req) {
        List<SParamBO> res;
        if (ObjectUtils.isEmpty(req) || ObjectUtils.allNull(req.getParamCode(), req.getParamCodes())) {
            res = stringRedisServiceImpl.batchGetByPattern(RedisConstants.PARAM_KEY + StringConstant.COLON + StringConstant.MATCHES_PATTERN, SParamBO.class);
        } else {
            List<String> ids = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(req.getParamCode())) {
                ids.add(req.getParamCode().toString());
            }
            if (ObjectUtils.isNotEmpty(req.getParamCodes())) {
                ids.addAll(Arrays.stream(req.getParamCodes().split(StringConstant.EN_COMMA)).toList());
            }
            res = stringRedisServiceImpl.batchGetByKeys(ids.stream().map(f -> RedisConstants.getParamKey(Long.parseLong(f))).toList(), SParamBO.class, true);
        }
        if (ObjectUtils.isEmpty(res)) {
            res = getByReq(req);
        } else {
            res = res.stream().sorted(Comparator.comparing(SParamBO::getParamSortCode).thenComparing(SParamBO::getParamCode)).collect(Collectors.toList());
        }
        return res;
    }

    @Override
    public void addOrUpd(SParamAddOrUpdReq req) {
        SParam param = SParam.builder()
                .paramCode(req.getParamCode())
                .paramName(req.getParamName())
                .paramValue(req.getParamValue())
                .paramRemark(req.getParamRemark())
                .paramSortCode(req.getParamSortCode())
                .build();
        if (baseMapper.sameCheck(param.getParamCode(), param.getParamName())) {
            throw new BusinessException("当前 { " + param.getParamName() + " } 参数已存在");
        }
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        param.setUpdateUid(bo.getUserLoginCacheBO().getUserId());
        param.setUpdateTime(new Date());
        if (ObjectUtils.isEmpty(param.getParamCode())) {
            param.setCreateUid(bo.getUserLoginCacheBO().getUserId());
            param.setCreateTime(param.getUpdateTime());
            baseMapper.insert(param);
        } else {
            SParam old = baseMapper.selectById(param.getParamCode());
            if (ObjectUtils.isEmpty(old)) {
                throw new BusinessException("参数编号数据错误，请重试");
            }
            baseMapper.updateById(param);
        }
        setCache(Collections.singletonList(SParamBO.builder()
                .paramCode(param.getParamCode())
                .paramName(param.getParamName())
                .paramValue(param.getParamValue())
                .paramSortCode(param.getParamSortCode())
                .updateTime(param.getUpdateTime())
                .build()));
    }

    @Override
    public void batchDel(List<Long> ids) {
        baseMapper.update(new LambdaUpdateWrapper<SParam>()
                .set(SParam::getDelStatus, TrueFalseEnum.TRUE.getCode())
                .set(SParam::getUpdateTime, new Date())
                .eq(SParam::getDelStatus, TrueFalseEnum.FALSE.getCode())
                .in(SParam::getParamCode, ids)
        );
        stringRedisServiceImpl.del(ids.stream().map(RedisConstants::getParamKey).toList());
    }
}
