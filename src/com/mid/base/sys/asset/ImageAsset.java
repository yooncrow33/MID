package com.mid.base.sys.asset;

import java.awt.*;

public class ImageAsset {
    final String finalPath;
    final Image image;

    public ImageAsset(String path) throws Exception {
        this.finalPath = "/" + path;
        this.image = java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource(finalPath));
    }

    public Image get() {
        return image;
    }
}
