package general.interfaces;

import javafx.scene.Scene;

/**
 * Created by Delia on 11/30/2016.
 */
public interface IGalleryView {
    int GALLERY_WIDTH = 1200;
    int GALLERY_HEIGHT = 600;
    double SCROLL_WINDOW_HEIGHT = 100;
    double SCROLL_WINDOW_WIDTH = .4 * GALLERY_WIDTH;

    Scene getScene();
}
