package com.mid.base.sys.asset;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SoundManager {
    public static float volume = 0.5f;

    private File externalRoot = null;

    private static final ConcurrentLinkedQueue<SoundAsset> soundQueue = new ConcurrentLinkedQueue<>();
    private static Map<String, LoopSoundAsset> loopSoundAssetMap = new HashMap<>();


    public static void startLoopSound(String str) {
        LoopSoundAsset l = loopSoundAssetMap.get(str);
        if (l!=null) {
            l.clip.start();
        } else {
            System.err.println("[ERROR] not found loopSound.");
        }
    }

    public static void stopLoopSound(String str) {
        LoopSoundAsset l = loopSoundAssetMap.get(str);
        if (l!=null) {
            l.clip.stop();
            l.clip.setFramePosition(0);
        } else {
            System.err.println("[ERROR] not found loopSound.");
        }
    }

    @Deprecated
    public void setExternalRoot(String dir) {
        externalRoot = new File(dir);
    }

    public static void update() {
        if (soundQueue.isEmpty()) return;

        SoundAsset s;
        while ((s = soundQueue.poll()) != null) {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(s.format, s.audioData, 0, s.audioData.length);
                setVolume(clip, SoundManager.volume);
                clip.addLineListener(e -> {
                    if (e.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
                clip.start();
            } catch (Exception e) {
                System.err.println("[Sound] Dynamic play failed for: " + s.finalPath);
                e.printStackTrace();
            }
        }
    }

    public static void playInstanceSound(SoundAsset clip) {
        soundQueue.add(clip);
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
    public static void setVolume(Clip clip, float volume) {
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