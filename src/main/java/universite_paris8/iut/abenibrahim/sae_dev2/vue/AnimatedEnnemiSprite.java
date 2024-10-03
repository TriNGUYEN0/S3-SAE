package universite_paris8.iut.abenibrahim.sae_dev2.vue;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.AnimationTimer;
public class AnimatedEnnemiSprite extends AnimationTimer {
    private ImageView imageView;
    private String[] frames;
    private int currentFrame;
    private Timeline animationTimeline;
    private long lastUpdate;

    public AnimatedEnnemiSprite(String[] frames, ImageView imageView) {
        this.frames = frames;
        this.imageView = imageView;
        this.currentFrame = 0;
        this.animationTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> nextFrame()));
        this.animationTimeline.setCycleCount(Timeline.INDEFINITE);
        this.lastUpdate = 0;
    }

    public void start() {
        super.start();
        animationTimeline.play();
    }

    public void stop() {
        animationTimeline.stop();
    }
    public void restart() {
        stop();
        start();
    }
    private void nextFrame() {
        currentFrame = (currentFrame + 1) % frames.length;
        imageView.setImage(new Image(frames[currentFrame]));
    }
    @Override
    public void handle(long now) {
        if (now - lastUpdate >= 500_000_000) { // Mettre à jour toutes les 100 ms
            nextFrame();
            lastUpdate = now;
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setFrames(String[] frames) {
        this.frames = frames;
        this.currentFrame = 0;
    }
    public void restartWithNewFrames(String[] newFrames) {
        setFrames(newFrames);
        restart();
    }
    public void updateFrames(String[] newFrames) {
        if (this.frames != newFrames) {
            this.frames = newFrames;
            this.currentFrame = 0;
        }
    }
}
