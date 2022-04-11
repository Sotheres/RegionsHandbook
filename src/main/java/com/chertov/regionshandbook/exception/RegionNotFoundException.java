package com.chertov.regionshandbook.exception;

public class RegionNotFoundException extends RuntimeException {

    public RegionNotFoundException(String message) {
        super(message);
    }
}
