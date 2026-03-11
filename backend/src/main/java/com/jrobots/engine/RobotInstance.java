package com.jrobots.engine;

public class RobotInstance {
    protected final RobotState state;
    protected final RobotController controller;
    protected final RobotActions actions;
    protected final RobotView view;

    public RobotInstance(RobotState state, RobotController controller) {
        this.state = state;
        this.controller = controller;
        this.actions = new RobotActions();
        this.view = new RobotView(state);
    }
}