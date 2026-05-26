package com.mid.base.sys.asset;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public class SoundAsset {
    final String finalPath;

    byte[] audioData;
    AudioFormat format;
    int bufferSize;

    public SoundAsset(String path) throws Exception {
        this.finalPath = "/" + path;
        loadAudioData();
    }

    public void play() {
        SoundManager.playInstanceSound(this);
    }

    private void loadAudioData() throws Exception {
        URL url = SoundManager.class.getResource(finalPath);
        if (url == null) {
            throw new IOException("Resource sound not found: " + finalPath);
        }

        try (AudioInputStream ais = AudioSystem.getAudioInputStream(url)) {
            this.format = ais.getFormat();
            this.bufferSize = (int) (ais.getFrameLength() * format.getFrameSize());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = ais.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            this.audioData = baos.toByteArray();
        }
    }
}