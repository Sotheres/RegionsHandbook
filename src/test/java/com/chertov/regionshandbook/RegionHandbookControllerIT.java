package com.chertov.regionshandbook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.chertov.regionshandbook.dto.RegionDto;
import com.chertov.regionshandbook.entity.Region;
import com.chertov.regionshandbook.mapper.DtoMapper;
import com.chertov.regionshandbook.mapper.RegionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class RegionHandbookControllerIT {

    private static final String ALL_REGIONS = "/region/all";
    private static final String BASE = "/region/";
    private static final String UPDATE_REGION = "/region/update/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private DtoMapper dtoMapper;

    @Test
    void getAllRegions() throws Exception {
        MvcResult result = mockMvc.perform(get(ALL_REGIONS))
                                  .andExpect(status().isOk())
                                  .andReturn();
        RegionDto[] regionDtos =
            jsonMapper.readValue(result.getResponse().getContentAsString(), RegionDto[].class);

        assertThat(regionDtos).hasSize(5);
    }

    @Test
    void getRegion_existingId_regionReceived() throws Exception {
        long regionId = 1L;
        RegionDto expected = new RegionDto(regionId, "EU", "Europe");

        MvcResult result = mockMvc.perform(get(BASE + regionId))
                                  .andExpect(status().isOk())
                                  .andReturn();
        Region receivedRegion =
            jsonMapper.readValue(result.getResponse().getContentAsString(), Region.class);

        assertThat(receivedRegion).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void getRegion_notExistingId_badRequest() throws Exception {
        long regionId = 6L;
        mockMvc.perform(get(BASE + regionId))
               .andExpect(status().isBadRequest());
    }

    @Test
    void createRegion() throws Exception {
        RegionDto regionToCreate = new RegionDto(null, "SA", "South America");

        MvcResult result = mockMvc.perform(post(BASE)
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .content(jsonMapper.writeValueAsString(
                                                   regionToCreate)))
                                  .andExpect(status().isOk())
                                  .andReturn();

        List<Region> allRegions = regionMapper.getAllRegions();
        RegionDto persistedRegionDto =
            jsonMapper.readValue(result.getResponse().getContentAsString(), RegionDto.class);

        assertThat(allRegions).hasSize(6);
        assertThat(regionToCreate).usingRecursiveComparison()
                                  .ignoringFields("id")
                                  .isEqualTo(persistedRegionDto);
    }

    @Test
    void updateRegion_existingId_regionUpdated() throws Exception {
        long regionId = 2L;
        RegionDto updateDto = new RegionDto(regionId, "AU", "Australia");

        mockMvc.perform(patch(UPDATE_REGION + regionId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonMapper.writeValueAsString(updateDto)))
               .andExpect(status().isOk());
        Region updatedRegion = regionMapper.getRegion(regionId);

        assertThat(updateDto).usingRecursiveComparison().isEqualTo(updatedRegion);
    }

    @Test
    void updateRegion_notExistingId_badRequest() throws Exception {
        long regionId = 6L;
        RegionDto updateDto = new RegionDto(regionId, "AU", "Australia");

        mockMvc.perform(patch(UPDATE_REGION + regionId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonMapper.writeValueAsString(updateDto)))
               .andExpect(status().isBadRequest());
    }

}