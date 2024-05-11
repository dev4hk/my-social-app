package org.example.mysocialapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Reel;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.service.ReelService;
import org.example.mysocialapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/reels")
@RequiredArgsConstructor
@RestController
public class ReelController {

    private final ReelService reelService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Reel> createReel(@RequestBody Reel reel, @RequestHeader("Authorization") String token) {
        User user = userService.findUserByJwt(token);
        return new ResponseEntity<>(reelService.createReel(reel, user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Reel>> findAllReels() {
        return new ResponseEntity<>(reelService.findAllReels(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reel>> findUserReels(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(reelService.findUserReels(userId), HttpStatus.OK);
    }
}
