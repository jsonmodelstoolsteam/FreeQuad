package application.stages.components;

import javafx.animation.RotateTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class LabelWithFlag extends Label {

	private boolean isRevert;
	private RotateTransition rotateTransition;

	public LabelWithFlag(String string) {
		super(string);
		this.isRevert = false;
		rotateTransition = new RotateTransition(Duration.ONE, this);
		rotateTransition.setAutoReverse(true);
		rotateTransition.setByAngle(180);
	}

	public boolean isRevert() {
		return isRevert;
	}

	public void revert() {
		this.isRevert = !isRevert;
		rotateTransition.play();
	}
}