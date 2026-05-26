package com.mid.base.sys.asset;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public class LoopSoundAsset {
    final String finalPath;
    final Clip clip;

    public LoopSoundAsset(String path) throws Exception {
        this.finalPath = "/" + path;
        clip = loadClip();
    }

    public void stop() {SoundManager.startLoopSound(finalPath);}
    public void start() {SoundManager.stopLoopSound(finalPath);}

    private Clip loadClip()
            throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream ais;
        URL url = SoundManager.class.getResource(finalPath);
        if (url == null) {
            throw new IOException("Resource sound not found: " + finalPath);
        }
        ais = AudioSystem.getAudioInputStream(url);

        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        return clip;
    }
}
