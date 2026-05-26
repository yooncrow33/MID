package com.mid.base.sys.asset;

import com.mid.base.sys.asset.image.ImageAsset;

import java.util.HashMap;
import java.util.Map;

public class AssetManager {

    private static Map<String, SoundAsset> soundAssetMap = new HashMap<>();
    private static Map<String, ImageAsset> imageAssetMap = new HashMap<>();
    private static void add(String str, SoundAsset sa) {soundAssetMap.put(str,sa);}
    private static void add(String str, ImageAsset ia) {imageAssetMap.put(str,ia);}

    static {
        loadSound("click", "click.wav");
    }

    private static void loadSound(String str, String path) {
        try {
            soundAssetMap.put(str, new SoundAsset(path));
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

    public static SoundAsset getImage(String str) {
        SoundAsset asset = soundAssetMap.get(str);

        if (asset == null) {
            System.err.println("\n[Asset Error] Oh! sound asset key '" + str + "' is wrong!");
            System.err.println("현재 로드된 에셋 키 목록: " + soundAssetMap.keySet() + "\n");
            System.exit(0);
        }

        return asset;
    }
}
