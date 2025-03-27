package com.haohaodayouxi.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haohaodayouxi.common.core.constants.CurrentUserContextHolder;
import com.haohaodayouxi.common.core.exception.BusinessException;
import com.haohaodayouxi.common.core.interfaces.InitService;
import com.haohaodayouxi.common.redis.service.impl.CommonRedisServiceImpl;
import com.haohaodayouxi.common.util.constants.StringConstant;
import com.haohaodayouxi.manage.constants.RedisConstants;
import com.haohaodayouxi.manage.constants.SysConstants;
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
        if (ObjectUtils.isEmpty(req) || ObjectUtils.allNull(req.getParamCode(), req.getParamCodes(), req.getParamParentCode())) {
            res = stringRedisServiceImpl.batchGetByPattern(RedisConstants.PARAM_KEY + StringConstant.COLON + StringConstant.MATCHES_PATTERN, SParamBO.class);
        } else {
            List<String> ids = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(req.getParamCode())) {
                ids.add(req.getParamCode().toString());
            }
            if (ObjectUtils.isNotEmpty(req.getParamCodes())) {
                ids.addAll(Arrays.stream(req.getParamCodes().split(StringConstant.EN_COMMA)).toList());
            }
            if (ObjectUtils.isNotEmpty(req.getParamParentCode())) {
                res = stringRedisServiceImpl.batchGetByPattern(RedisConstants.getParamKey(req.getParamParentCode()) + StringConstant.MATCHES_PATTERN, SParamBO.class);
                if (ObjectUtils.isNotEmpty(ids)) {
                    res = res.stream().filter(f -> ids.contains(f.getParamCode().toString())).toList();
                }
            } else {
                res = stringRedisServiceImpl.batchGetByKeys(ids.stream().map(f -> RedisConstants.getParamKey(Long.parseLong(f))).toList(), SParamBO.class, true);
            }
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
                .paramParentCode(req.getParamParentCode())
                .paramName(req.getParamName())
                .paramValue(req.getParamValue())
                .paramRemark(req.getParamRemark())
                .paramSortCode(req.getParamSortCode())
                .build();
        boolean add = ObjectUtils.isEmpty(param.getParamCode());
        if (!add && param.getParamCode().equals(param.getParamParentCode())) {
            throw new BusinessException("父级不能是自身");
        }
        if (!param.getParamParentCode().equals(SysConstants.TOP_LEVEL_ID)) {
            SParam parent = baseMapper.selectById(param.getParamParentCode());
            if (ObjectUtils.isEmpty(parent)) {
                throw new BusinessException("父级数据错误，请重试");
            } else {
                // 判断父级路径不包含自身ID
                // 1 1001 1001001
                // 11 11001 11001001
                int length = parent.getParamParentCode().toString().length();
                int size = length % 3 > 0 ? length / 3 + 1 : length / 3;
                for (int i = size; i > 0; i--) {
                    int start = (i - 1) * 3 - 1;
                    if (start < 0) {
                        start = 0;
                    }
                    int end = i * 3 - 1;
                    if (end > length) {
                        end = length;
                    }
                    String substring = parent.getParamParentCode().toString().substring(start, end);
                    if (substring.equals(param.getParamCode().toString())) {
                        throw new BusinessException("父级不能是自身的子集");
                    }
                }
            }
        }
        if (baseMapper.sameCheck(param.getParamCode(), param.getParamParentCode(), param.getParamName())) {
            throw new BusinessException("当前父级下 { " + param.getParamName() + " } 已存在");
        }
        LoginCacheBO bo = (LoginCacheBO) CurrentUserContextHolder.get();
        param.setUpdateUid(bo.getUserLoginCacheBO().getUserId());
        param.setUpdateTime(new Date());
        if (add) {
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
    }
}
