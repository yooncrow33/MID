package com.mid.base.sys.asset.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;


public class SoundAsset {
    final String finalPath;
    Clip clip;
    public SoundAsset(String path) throws Exception {
        finalPath = "/" + path;
        clip = loadClip();
    }

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

