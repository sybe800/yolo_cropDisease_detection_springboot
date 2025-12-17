package com.example.Ece.service;

import com.example.Ece.entity.ChinaCity;
import com.example.Ece.mapper.ChinaCityMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource; // 或 import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class ChinaCityService {
    // 添加 @Resource 或 @Autowired 注解，完成依赖注入
    @Resource
    private ChinaCityMapper cityMapper;

    public List<ChinaCity> getCitiesByProvinceId(Integer provinceId) {
        return cityMapper.selectByProvinceId(provinceId);
    }
}
