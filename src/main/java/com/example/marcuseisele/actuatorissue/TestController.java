package com.example.marcuseisele.actuatorissue;

import com.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class TestController {

    @GetMapping(path = "/{param1}/path/{param2}/entity")
    public ResponseEntity<byte[]> getImage1(@RequestHeader("User-Agent") String userAgent,
                                            @PathVariable(value = "param1") String param1,
                                            @PathVariable(value = "param2") String param2) throws IOException {
        log.info("GET UserAgent: {}, param1: {}, param2: {}", userAgent, param1, param2);

        byte[] bytes;
        InputStream inputStream;

        try {
            inputStream = getClass().getResourceAsStream("/assets/test.png");
            bytes = ByteStreams.toByteArray(inputStream);
        } catch (IOException | NullPointerException e) {
            log.error(e.getMessage());
            throw e;
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(600, TimeUnit.SECONDS))
                .contentType(MediaType.IMAGE_PNG)
                .body(bytes);

    }
}
