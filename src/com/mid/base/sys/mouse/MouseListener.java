package com.mid.base.sys.mouse;

import com.mid.base.sys.asset.AssetManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseListener extends MouseAdapter implements MouseWheelListener {
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches > 0) {
            //Main.scrollUp();
        } else if (notches < 0) {
            //Main.scrollDown();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        AssetManager.getSound("click").play();
    }

}
