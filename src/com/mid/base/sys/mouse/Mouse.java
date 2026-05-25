package com.mid.base.sys.mouse;

import com.mid.base.sys.Main;

public class Mouse {
    private static Main component = null;
    public static void wakeUp(Main component) {
        Mouse.component = component;
    }
    public static int getX() {return component.getMouseX();}
    public static int getY() {return component.getMouseY();}
}
