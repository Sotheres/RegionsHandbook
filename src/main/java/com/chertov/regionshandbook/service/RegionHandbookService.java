package com.chertov.regionshandbook.service;

import com.chertov.regionshandbook.dto.RegionDto;
import com.chertov.regionshandbook.entity.Region;
import com.chertov.regionshandbook.exception.RegionNotFoundException;
import com.chertov.regionshandbook.mapper.DtoMapper;
import com.chertov.regionshandbook.mapper.RegionMapper;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RegionHandbookService {

    private final RegionMapper regionMapper;
    private final DtoMapper dtoMapper;

    public RegionHandbookService(RegionMapper regionMapper, DtoMapper dtoMapper) {
        this.regionMapper = regionMapper;
        this.dtoMapper = dtoMapper;
    }

    @Cacheable("regions")
    public List<RegionDto> getAllRegions() {
        List<Region> allRegions = regionMapper.getAllRegions();
        return dtoMapper.convert(allRegions);
    }

    @Cacheable("region")
    public RegionDto getRegion(long regionId) {
        Region region = receiveRegion(regionId);
        return dtoMapper.convert(region);
    }

    @CacheEvict(value = "regions", allEntries = true)
    public RegionDto createRegion(RegionDto regionDto) {
        Region region = dtoMapper.convert(regionDto);
        regionMapper.saveOrUpdate(region);
        return dtoMapper.convert(region);
    }

    @CacheEvict(value = {"region", "regions"}, allEntries = true)
    public RegionDto updateRegion(RegionDto regionDto, long regionId) {
        Region region = receiveRegion(regionId);
        dtoMapper.updateRegion(regionDto, region);
        regionMapper.saveOrUpdate(region);

        return dtoMapper.convert(region);
    }

    private Region receiveRegion(long regionId) {
        Region region = regionMapper.getRegion(regionId);
        if (region == null) {
            throw new RegionNotFoundException("Region with id " + regionId + " is not found.");
        }
        return region;
    }
}
