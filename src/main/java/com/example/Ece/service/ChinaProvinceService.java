package com.example.Ece.service;

import com.example.Ece.entity.ChinaProvince;
import com.example.Ece.mapper.ChinaProvinceMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class ChinaProvinceService {
    @Resource
    private ChinaProvinceMapper provinceMapper;

    public List<ChinaProvince> getAllProvinces() {
        return provinceMapper.selectList(null);
    }
}