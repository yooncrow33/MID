package com.mid.base.sys.asset;

import com.mid.base.sys.asset.image.ImageAsset;
import com.mid.base.sys.asset.sound.SoundAsset;

import java.util.HashMap;
import java.util.Map;

public class AssetManager {

    private static Map<String, SoundAsset> soundAssetMap = new HashMap<>();
    private static Map<String, ImageAsset> imageAssetMap = new HashMap<>();
    private static void add(String str, SoundAsset sa) {soundAssetMap.put(str,sa);}
    private static void add(String str, ImageAsset ia) {imageAssetMap.put(str,ia);}

    static {

    }

    public void loadImageAsset() {

    }

    public static void loadSoundAsset() {

    }
}
