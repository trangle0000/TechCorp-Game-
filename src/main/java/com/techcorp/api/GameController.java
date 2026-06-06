package com.techcorp.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    
    @GetMapping("/game/state")
    public String gameState() {
        return "Turn: 1, Cash: 50000, Status: IN_PROGRESS";
    }
}
