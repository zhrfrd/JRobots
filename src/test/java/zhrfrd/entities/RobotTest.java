package zhrfrd.entities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Container;
import java.awt.Dimension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RobotTest {
    private Robot robot;
    private Container parentContainer;

    @BeforeEach
    void BeforeEach() {
	this.robot = Mockito.mock(Robot.class, Mockito.CALLS_REAL_METHODS);// new Robot(); 
    }

    private void setParentContainer() {
	this.parentContainer = new Container();
	this.parentContainer.setSize(new Dimension(100, 100));
//	this.parentContainer.add(this.robot);
	Mockito.when(this.robot.getParent()).thenReturn(this.parentContainer);
    }

    @Test
    void isNotAliveAtCreation() {
	assertFalse(this.robot.isAlive(), "The robot should be alive at start, dead instead");
    }

//    @Test
//    t

}
