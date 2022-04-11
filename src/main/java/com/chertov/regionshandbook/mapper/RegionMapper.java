package com.chertov.regionshandbook.mapper;

import com.chertov.regionshandbook.entity.Region;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RegionMapper {

    @Select("SELECT * FROM Regions")
    List<Region> getAllRegions();

    @Select("SELECT * FROM Regions WHERE id = #{id}")
    Region getRegion(long id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("MERGE INTO Regions KEY(id) VALUES (#{id}, #{shortName}, #{name})")
    void saveOrUpdate(Region region);
}
