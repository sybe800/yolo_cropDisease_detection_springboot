package com.example.Ece.mapper;

import com.example.Ece.entity.ChinaCity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChinaCityMapper extends BaseMapper<ChinaCity> {
    // 直接用注解编写 SQL，无需 XML
    @Select("SELECT city_id, province_id, city_name FROM china_cities WHERE province_id = #{provinceId}")
    List<ChinaCity> selectByProvinceId(@Param("provinceId") Integer provinceId);
}