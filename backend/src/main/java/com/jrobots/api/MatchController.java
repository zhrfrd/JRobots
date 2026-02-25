package com.jrobots.api;

import com.jrobots.bots.SpinnerBot;
import com.jrobots.engine.MatchEngine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {
    @GetMapping("/test-match")
    public Object testMatch() {
        MatchEngine engine = new MatchEngine();

        return engine.runMatch(new SpinnerBot(), new SpinnerBot(), 200);
    }
}
