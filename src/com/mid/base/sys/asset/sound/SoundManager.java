package com.mid.base.sys.asset.sound;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    private final Map<String, Clip> bgmMap = new HashMap<>();
    private Clip currentBgm;

    private float bgmVolume = 0.0f;
    private boolean sfxEnable = true;

    private File externalRoot = null;

    private static ArrayList<SoundAsset> soundAssets = new ArrayList<>();

    public SoundManager() {
        setBgmVolume(0.5f);
    }

    @Deprecated
    public void setExternalRoot(String dir) {
        externalRoot = new File(dir);
    }

    public static void update() {
        if (soundAssets.isEmpty()) {return;}

        for (SoundAsset s : soundAssets) {
            //s.
        }
    }

    @Deprecated
    public void play(String path) {
        if (!sfxEnable) return;
        try {
            Clip clip = loadClip(path);
            setVolume(clip, 0.5F);

            clip.addLineListener(e -> {
                if (e.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

            clip.start();
        } catch (Exception e) {
            System.err.println("[Sound] play failed: " + path);
            e.printStackTrace();
        }
    }

    @Deprecated
    public void loopBgm(String path) {
        stopBgm();

        try {
            Clip clip = bgmMap.computeIfAbsent(path, p -> {
                try {
                    return loadClip(p);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            setVolume(clip, bgmVolume);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

            currentBgm = clip;
        } catch (Exception e) {
            System.err.println("[Sound] BGM failed: " + path);
            e.printStackTrace();
        }
    }

    public void stopBgm() {
        if (currentBgm != null) {
            currentBgm.stop();
            currentBgm.setFramePosition(0);
            currentBgm = null;
        }
    }

    public void setBgmVolume(float db) {
        bgmVolume = db;
        if (currentBgm != null) {
            setVolume(currentBgm, db);
        }
    }

    public void setSfxVolume(boolean b) {
        sfxEnable = b;
    }

    public void dispose() {
        stopBgm();
        for (Clip clip : bgmMap.values()) clip.close();
        bgmMap.clear();
    }

    private Clip loadClip(String path)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        AudioInputStream ais;

        if (externalRoot != null) {
            File file = new File(externalRoot, path);
            if (file.exists()) {
                ais = AudioSystem.getAudioInputStream(file);
            } else {
                throw new IOException("External sound not found: " + file);
            }
        }
        else {
            URL url = SoundManager.class.getResource("/" + path);
            if (url == null) {
                throw new IOException("Resource sound not found: " + path);
            }
            ais = AudioSystem.getAudioInputStream(url);
        }

        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        return clip;
    }

    @Deprecated
    private void setVolume(Clip clip, float volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            float dB = (float) (Math.log(volume <= 0.0 ? 0.0001 : volume) / Math.log(10.0) * 20.0);

            float min = gain.getMinimum();
            float max = gain.getMaximum();
            if (dB < min) dB = min;
            if (dB > max) dB = max;

            gain.setValue(dB);
        }
    }
}