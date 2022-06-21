/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

/**
 *
 * @author Ahmed Hammad
 */
public class Shaker {
    private TranslateTransition translateTransition;
    public Shaker(Node node) {
        translateTransition = new TranslateTransition(Duration.millis(50),node);
        translateTransition.setFromX(0f);
        translateTransition.setByX(10f);
        translateTransition.setCycleCount(2);
        translateTransition.setAutoReverse(true);
    }
    public void shake() {
        translateTransition.playFromStart();
    }
}
