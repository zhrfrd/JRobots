package com.jrobots.bots;

import com.jrobots.engine.RobotActions;
import com.jrobots.engine.RobotController;
import com.jrobots.engine.RobotView;

public class ScannerBot implements RobotController {
    public void onTick(RobotView view, RobotActions actions) {
        double dist = view.scan(view.getBodyAngleDeg());

        if (dist > 0) {
            actions.fire();
        }

        actions.turn(5);
    }
}
