package com.chertov.regionshandbook.mapper;

import com.chertov.regionshandbook.dto.RegionDto;
import com.chertov.regionshandbook.entity.Region;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public RegionDto convert(Region region) {
        if (region == null) {
            return null;
        }
        RegionDto regionDto = new RegionDto();
        regionDto.setId(region.getId());
        regionDto.setName(region.getName());
        regionDto.setShortName(region.getShortName());

        return regionDto;
    }

    public Region convert(RegionDto regionDto) {
        if (regionDto == null) {
            return null;
        }
        Region region = new Region();
        region.setName(regionDto.getName());
        region.setShortName(regionDto.getShortName());

        return region;
    }

    public List<RegionDto> convert(List<Region> regions) {
        if (regions == null) {
            return Collections.emptyList();
        }
        ArrayList<RegionDto> regionDtos = new ArrayList<>(regions.size());
        for (Region region : regions) {
            RegionDto regionDto = convert(region);
            regionDtos.add(regionDto);
        }
        return regionDtos;
    }

    public void updateRegion(RegionDto regionDto, Region region) {
        if (regionDto != null) {
            region.setName(regionDto.getName());
            region.setShortName(regionDto.getShortName());
        }
    }
}
