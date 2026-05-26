package com.mid.base.core;

import com.mid.base.sys.asset.AssetManager;

import java.awt.*;

public class Game {

    public void update(double dt) {

    }

    public void render(Graphics g) {
        g.drawImage(AssetManager.getImage("backGround").get(),0,0,null);
    }
}
