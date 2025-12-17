// com.example.Ece.controller.AreaController.java
package com.example.Ece.controller;

import com.example.Ece.entity.ChinaProvince;
import com.example.Ece.entity.ChinaCity;
import com.example.Ece.service.ChinaProvinceService;
import com.example.Ece.service.ChinaCityService;
import com.example.Ece.common.Result; // 用你项目里的Result统一响应
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/area")
public class AreaController {

    @Resource
    private ChinaProvinceService provinceService;
    @Resource
    private ChinaCityService cityService;

    // 获取所有省份（返回名称列表）
    @GetMapping("/provinces")
    public Result<List<String>> getAllProvinces() {
        List<String> provinceNames = provinceService.getAllProvinces().stream()
                .map(ChinaProvince::getProvinceName)
                .collect(Collectors.toList());
        return Result.success(provinceNames);
    }

    // 根据省份名称获取城市列表
    @GetMapping("/cities")
    public Result<List<String>> getCitiesByProvinceName(@RequestParam String provinceName) {
        // 先根据省份名称查省份ID
        ChinaProvince province = provinceService.getAllProvinces().stream()
                .filter(p -> p.getProvinceName().equals(provinceName))
                .findFirst()
                .orElse(null);
        if (province == null) {
            return Result.success();
        }
        // 再查该省下的城市
        List<String> cityNames = cityService.getCitiesByProvinceId(province.getProvinceId()).stream()
                .map(ChinaCity::getCityName)
                .collect(Collectors.toList());
        return Result.success(cityNames);
    }
}