package objects;

import com.sun.javafx.scene.traversal.Direction;

public class ClientGameObject {
	
	private double xPosition;
	private double yPosition;
	private double width;
	private double height;
	private Direction direction;
	private String imageFileName;


	public ClientGameObject(double xPosition, double yPosition, double width, double height, Direction direction, String imageFileName) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
		this.imageFileName = imageFileName;
		this.direction = direction;
	}

	public void setDirection(Direction direction){
		this.direction = direction;
	}
	public Direction getDirection(){
		return direction;
	}

	public double getxPosition() {
		return xPosition;
	}

	public void setxPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getyPosition() {
		return yPosition;
	}

	public void setyPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
}