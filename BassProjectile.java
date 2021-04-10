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
	int pillarframe = 0;
	int explosionframe = 0;
	int orbframe = 0;
	int width;
	int height;
	int xScale;
	int yScale;
	String id;
	BufferedImage[] ExplodeAnimation = new BufferedImage[8];
	BufferedImage[] Orb = new BufferedImage[8];

	public BassProjectile() {
		active = true;
		wheels = new BufferedImage[5];
		pillars = new BufferedImage[30];
	}

	public void OrbAttack() {
		try {
			Orb[0] = ImageIO.read(NewView.class.getResource("/Effects/Orb0.png"));
			Orb[1] = ImageIO.read(NewView.class.getResource("/Effects/Orb1.png"));
			Orb[2] = ImageIO.read(NewView.class.getResource("/Effects/Orb2.png"));
			Orb[3] = ImageIO.read(NewView.class.getResource("/Effects/Orb3.png"));
			Orb[4] = ImageIO.read(NewView.class.getResource("/Effects/Orb4.png"));
			Orb[5] = ImageIO.read(NewView.class.getResource("/Effects/Orb5.png"));
			Orb[6] = ImageIO.read(NewView.class.getResource("/Effects/Orb6.png"));
			Orb[7] = ImageIO.read(NewView.class.getResource("/Effects/Orb6.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		id = "Orb";
		damage = 5;
		xScale = 300;
		yScale = 300;
	}

	public void Explosion() {
		try {
			ExplodeAnimation[0] = ImageIO.read(NewView.class.getResource("/Effects/Explode0.png"));
			ExplodeAnimation[1] = ImageIO.read(NewView.class.getResource("/Effects/Explode0.png"));
			ExplodeAnimation[2] = ImageIO.read(NewView.class.getResource("/Effects/Explode1.png"));
			ExplodeAnimation[3] = ImageIO.read(NewView.class.getResource("/Effects/Explode1.png"));
			ExplodeAnimation[4] = ImageIO.read(NewView.class.getResource("/Effects/Explode2.png"));
			ExplodeAnimation[5] = ImageIO.read(NewView.class.getResource("/Effects/Explode2.png"));
			ExplodeAnimation[6] = ImageIO.read(NewView.class.getResource("/Effects/Explode3.png"));
			ExplodeAnimation[7] = ImageIO.read(NewView.class.getResource("/Effects/Explode4.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		id = "Explosion";
		xScale = 70;
		yScale = 70;
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
		id = "Wheel";
		speed = 15;
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
		id = "Wheel";
		speed = 15;
		damage = 3;
		xScale = 70;
		yScale = 70;

	}

	public void PillarAttack() {
		try {
			pillars[0] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[1] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[2] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[3] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[4] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[5] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[6] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[7] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[8] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[9] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[10] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[11] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[12] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[13] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[14] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[15] = ImageIO.read(NewView.class.getResource("/Effects/Pillar1.png"));
			pillars[16] = ImageIO.read(NewView.class.getResource("/Effects/Pillar2.png"));
			pillars[17] = ImageIO.read(NewView.class.getResource("/Effects/Pillar2.png"));
			pillars[18] = ImageIO.read(NewView.class.getResource("/Effects/Pillar2.png"));
			pillars[19] = ImageIO.read(NewView.class.getResource("/Effects/Pillar2.png"));
			pillars[20] = ImageIO.read(NewView.class.getResource("/Effects/Pillar2.png"));
			pillars[21] = ImageIO.read(NewView.class.getResource("/Effects/Pillar3.png"));
			pillars[22] = ImageIO.read(NewView.class.getResource("/Effects/Pillar3.png"));
			pillars[23] = ImageIO.read(NewView.class.getResource("/Effects/Pillar3.png"));
			pillars[24] = ImageIO.read(NewView.class.getResource("/Effects/Pillar4.png"));
			pillars[25] = ImageIO.read(NewView.class.getResource("/Effects/Pillar4.png"));
			pillars[26] = ImageIO.read(NewView.class.getResource("/Effects/Pillar4.png"));
			pillars[27] = ImageIO.read(NewView.class.getResource("/Effects/Pillar5.png"));
			pillars[28] = ImageIO.read(NewView.class.getResource("/Effects/Pillar5.png"));
			pillars[29] = ImageIO.read(NewView.class.getResource("/Effects/Pillar5.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// this.direction = State.LEFT;
		// this.orientation = State.CLOCKWISE;
		id = "Pillar";
		speed = 5;
		damage = 1;
		xScale = 100;
		yScale = 200;

	}

	public boolean isActive() {
		return active;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, xScale - 30, yScale - 30);
	}

}
