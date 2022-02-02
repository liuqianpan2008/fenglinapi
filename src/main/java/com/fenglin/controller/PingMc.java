package com.fenglin.controller;

import com.fenglin.service.pingmcimage;
import lombok.SneakyThrows;
import me.dilley.MineStat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/mc")
public class PingMc {
    @RequestMapping("/json")
    public MineStat ping(@RequestBody Map<String, String> data) {
        MineStat ms = new MineStat(data.get("ip"),Integer.parseInt(data.get("port")),5000,MineStat.Request.BETA);
        System.out.println(ms);
        return ms;
    }
}
