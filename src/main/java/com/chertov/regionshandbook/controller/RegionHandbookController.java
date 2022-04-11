package com.chertov.regionshandbook.controller;

import com.chertov.regionshandbook.dto.RegionDto;
import com.chertov.regionshandbook.service.RegionHandbookService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Manage regions handbook")
@RequestMapping("/region")
@RestController
public class RegionHandbookController {

    private final RegionHandbookService regionHandbookService;

    public RegionHandbookController(RegionHandbookService regionHandbookService) {
        this.regionHandbookService = regionHandbookService;
    }

    @Operation(summary = "Get all regions")
    @GetMapping("/all")
    public List<RegionDto> getAllRegions() {
        return regionHandbookService.getAllRegions();
    }

    @Operation(summary = "Get region by id")
    @GetMapping("/{regionId}")
    public RegionDto getRegion(@PathVariable long regionId) {
        return regionHandbookService.getRegion(regionId);
    }

    @Operation(summary = "Create new region")
    @PostMapping("/")
    public RegionDto createRegion(@RequestBody RegionDto regionDto) {
        return regionHandbookService.createRegion(regionDto);
    }

    @Operation(summary = "Update region by id")
    @PatchMapping("/update/{regionId}")
    public RegionDto updateRegion(@RequestBody RegionDto regionDto, @PathVariable long regionId) {
        return regionHandbookService.updateRegion(regionDto, regionId);
    }
}
