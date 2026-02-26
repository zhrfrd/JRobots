package com.jrobots.bots;

import com.jrobots.engine.RobotActions;
import com.jrobots.engine.RobotController;
import com.jrobots.engine.RobotView;

public class SpinnerBot implements RobotController {
    @Override
    public void onTick(RobotView view, RobotActions actions) {
        actions.turn(5);
        actions.move(2);
        actions.fire();
    }
}
