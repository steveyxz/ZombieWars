package com.games.zombieWars;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/*
 * Copying this class in any way is an infraction of the law, and will be punished.
 * All content of this class is protected by copyright
 * © 2020 Steven Zhu - All Rights Reserved
 */

public class Sound {

    private SoundEffect se = new SoundEffect();
    private Music m = new Music();

    public Sound(String fileName, String fileName2) {
        se.setFile(fileName);
        m.setFile(fileName2);
    }

    public void playSound() {
        se.play();
    }

    public void pauseSound() {
        se.pause();
    }

    public void playMusic() {
        m.play();
        m.loop();
    }

    public void stopMusic() {
        m.pause();
    }

    public class SoundEffect {
        Clip clip;

        public void setFile(String file) {
            try {
                File file1 = new File(file);
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(file1);
                clip = AudioSystem.getClip();
                clip.open(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void play() {
            clip.setFramePosition(0);
            clip.start();
        }

        public void pause() {
            clip.setFramePosition(0);
            clip.stop();
        }
    }

    public class Music {
        Clip clip;

        public void setFile(String file) {
            try {
                File file1 = new File(file);
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(file1);
                clip = AudioSystem.getClip();
                clip.open(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void play() {

            clip.setFramePosition(0);
            clip.start();

        }

        public void pause() {
            clip.setFramePosition(0);
            clip.stop();
            clip.close();
        }

        public void loop() {
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        }

    }
}
