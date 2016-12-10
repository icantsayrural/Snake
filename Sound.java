import java.io.InputStream;
import javax.sound.sampled.*;

public class Sound {
    private static final String DEATH_FILE = "sound/death.wav";
    private static final String FOOD_FILE = "sound/food.wav";
    private static final String POWER_UP_FILE = "sound/powerUp.wav";
    private static final String LEVEL_UP_FILE = "sound/levelUp.wav";

    private Clip foodClip;
    private Clip deathClip;
    private Clip powerUpClip;
    private Clip levelUpClip;

    Sound() {
        try {
            InputStream foodFile = getClass().getClassLoader().getResourceAsStream(FOOD_FILE);
            AudioInputStream foodSound = AudioSystem.getAudioInputStream(foodFile);

            InputStream deathFile = getClass().getClassLoader().getResourceAsStream(DEATH_FILE);
            AudioInputStream deathSound = AudioSystem.getAudioInputStream(deathFile);

            InputStream powerUpFile = getClass().getClassLoader().getResourceAsStream(POWER_UP_FILE);
            AudioInputStream powerUpSound = AudioSystem.getAudioInputStream(powerUpFile);

            InputStream levelUpFile = getClass().getClassLoader().getResourceAsStream(LEVEL_UP_FILE);
            AudioInputStream levelUpSound = AudioSystem.getAudioInputStream(levelUpFile);

            foodClip = AudioSystem.getClip();
            foodClip.open(foodSound);

            deathClip = AudioSystem.getClip();
            deathClip.open(deathSound);

            powerUpClip = AudioSystem.getClip();
            powerUpClip.open(powerUpSound);

            levelUpClip = AudioSystem.getClip();
            levelUpClip.open(levelUpSound);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    void playDeathSound() {
        deathClip.stop();

        deathClip.setFramePosition(0);
        deathClip.start();
    }

    void playFoodSound() {
        foodClip.stop();

        foodClip.setFramePosition(0);
        foodClip.start();
    }

    void playPowerUpSound() {
        powerUpClip.stop();

        powerUpClip.setFramePosition(0);
        powerUpClip.start();
    }

    void playLevelUpSound() {
        levelUpClip.stop();

        levelUpClip.setFramePosition(0);
        levelUpClip.start();
    }
}
