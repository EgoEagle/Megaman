import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BassProjectile {
	BufferedImage[] wheels;
	BufferedImage[] pillars;
	BufferedImage current;
	int speed;
	int damage;
	double time = 0;
	State direction;
	State orientation;
	boolean active;
	float x;
	float y;
	int pillarframe = -1;
	int width;
	int height;
	int xScale;
	int yScale;

	public BassProjectile() {
		active = true;
		wheels = new BufferedImage[5];
		pillars = new BufferedImage[5];
	}

	public void WheelAttack() {
		try {
			wheels[0] = ImageIO.read(NewView.class.getResource("/BassSprite/BassWheel1.png"));
			wheels[1] = ImageIO.read(NewView.class.getResource("/BassSprite/BassWheel2.png"));
			wheels[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassWheel3.png"));
			wheels[3] = ImageIO.read(NewView.class.getResource("/BassSprite/BassWheel4.png"));
			wheels[4] = ImageIO.read(NewView.class.getResource("/BassSprite/BassWheel5.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.direction = State.LEFT;
		// this.orientation = State.CLOCKWISE;
		speed = 5;
		damage = 3;
		xScale = 70;
		yScale = 70;

	}

	public void FlippedWheelAttack() {
		try {
			wheels[0] = ImageIO.read(NewView.class.getResource("/BassSprite/FlippedBassWheel1.png"));
			wheels[1] = ImageIO.read(NewView.class.getResource("/BassSprite/FlippedBassWheel2.png"));
			wheels[2] = ImageIO.read(NewView.class.getResource("/BassSprite/FlippedBassWheel3.png"));
			wheels[3] = ImageIO.read(NewView.class.getResource("/BassSprite/FlippedBassWheel4.png"));
			wheels[4] = ImageIO.read(NewView.class.getResource("/BassSprite/FlippedBassWheel5.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.direction = State.RIGHT;
		// this.orientation = State.COUNTERCLOCKWISE;

		speed = 5;
		damage = 3;
		xScale = 70;
		yScale = 70;

	}

	public void PillarAttack() {
		try {
			pillars[0] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[1] = ImageIO.read(NewView.class.getResource("/Effects/Pillar2.png"));
			pillars[2] = ImageIO.read(NewView.class.getResource("/Effects/Pillar3.png"));
			pillars[3] = ImageIO.read(NewView.class.getResource("/Effects/Pillar4.png"));
			pillars[4] = ImageIO.read(NewView.class.getResource("/Effects/Pillar5.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// this.direction = State.LEFT;
		// this.orientation = State.CLOCKWISE;
		speed = 5;
		damage = 1;
		xScale = 60;
		yScale = 200;

	}

	public boolean isActive() {
		return active;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, xScale - 30, yScale - 30);
	}

}
