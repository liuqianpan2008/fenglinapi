package com.fenglin.controller;

import com.fenglin.service.pingmcimage;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/info")
public class info {
    @SneakyThrows
    @GetMapping(value = "/image",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(HttpServletRequest request) {
        return new pingmcimage().pingmcimage(request);
    }
}
