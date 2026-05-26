package com.mid.base.sys.asset;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {

    private static Map<String, SoundAsset> soundAssetMap = new HashMap<>();
    private static Map<String, ImageAsset> imageAssetMap = new HashMap<>();

    static {
        loadSound("click", "click.wav");

        loadImage("backGround", "baseBackGround.png");
    }

    private static void loadSound(String str, String path) {
        try {
            soundAssetMap.put(str, new SoundAsset(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadImage(String str, String path) {
        try {
            imageAssetMap.put(str, new ImageAsset(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static SoundAsset getSound(String str) {
        SoundAsset asset = soundAssetMap.get(str);

        if (asset == null) {
            System.err.println("\n[Asset Error] Oh! sound asset key '" + str + "' is wrong!");
            System.err.println("현재 로드된 에셋 키 목록: " + soundAssetMap.keySet() + "\n");
            System.exit(0);
        }

        return asset;
    }

    public static ImageAsset getImage(String str) {
        ImageAsset asset = imageAssetMap.get(str);

        if (asset == null) {
            System.err.println("\n[Asset Error] Oh! sound asset key '" + str + "' is wrong!");
            System.err.println("현재 로드된 에셋 키 목록: " + imageAssetMap.keySet() + "\n");
            System.exit(0);
        }

        return asset;
    }
}
