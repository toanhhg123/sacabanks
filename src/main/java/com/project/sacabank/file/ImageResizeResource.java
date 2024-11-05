package com.project.sacabank.file;

import org.springframework.core.io.ByteArrayResource;

public record ImageResizeResource(ByteArrayResource data, String contentType) {

}
