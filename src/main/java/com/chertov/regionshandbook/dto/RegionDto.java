package com.chertov.regionshandbook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegionDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String shortName;

    private String name;

    public RegionDto() {
    }

    public RegionDto(Long id, String shortName, String name) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

}
