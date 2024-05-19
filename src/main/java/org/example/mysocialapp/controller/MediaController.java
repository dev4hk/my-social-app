package org.example.mysocialapp.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.example.mysocialapp.exception.UserException;
import org.example.mysocialapp.request.MediaRequest;
import org.example.mysocialapp.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.Paths.get;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/load-media")
public class MediaController {
    private final UserService userService;

//    @GetMapping(produces = MediaType.ALL_VALUE)
//    public ResponseEntity<Resource> getMedia(@RequestParam String fileName, @RequestParam String filePath) throws IOException {
//        Path path = get(filePath);
//        if(!Files.exists(path)) {
//            throw new FileNotFoundException(fileName + " was not found");
//        }
//        Resource resource = new UrlResource(path.toUri());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("FILE-NAME",fileName);
//        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + fileName);
//        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(path)))
//                .headers(httpHeaders).body(resource);
//    }

    @PostMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<String> getMedia(@RequestBody MediaRequest request, @RequestHeader("Authorization") String token) throws IOException, UserException {
        userService.findUserByJwt(token);
        Path path = get(request.getFilePath());
        if(!Files.exists(path)) {
            throw new FileNotFoundException(request.getFileName() + " was not found");
        }
        Resource resource = new UrlResource(path.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("FILE-NAME",request.getFileName());
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + request.getFileName());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(path)))
                .headers(httpHeaders).body(Base64.encodeBase64String(resource.getContentAsByteArray()));
    }

}
