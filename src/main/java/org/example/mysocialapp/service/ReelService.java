package org.example.mysocialapp.service;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Reel;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.repository.ReelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReelService {

    private final ReelRepository reelRepository;
    private final UserService userService;

    public Reel createReel(Reel reel, User user) {
        Reel newReel = Reel.builder()
                .title(reel.getTitle())
                .user(user)
                .video(reel.getVideo())
                .build();

        return reelRepository.save(newReel);
    }


    public List<Reel> findAllReels(){
        return reelRepository.findAll();
    }

    public List<Reel> findUserReels(Integer userId) {
        userService.findUserById(userId);
        return reelRepository.findByUserId(userId);
    }

}
