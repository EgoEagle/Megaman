import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ElecmanProjectile {
	BufferedImage[] zap;
	BufferedImage current;
	int speed;
	int damage;
	double time = 0;
	State direction;
	State orientation;
	boolean active;
	float x;
	float y;
	int zapframe = 0;
	int explosionframe = 0;
	int orbframe = 0;
	int width;
	int height;
	int xScale;
	int yScale;
	String id;
	BufferedImage[] ExplodeAnimation = new BufferedImage[8];
	BufferedImage[] Orb = new BufferedImage[8];
	float StartTime;

	public ElecmanProjectile() {
		active = true;
		zap = new BufferedImage[5];

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

	public void Zap() {
		try {
			zap[0] = ImageIO.read(NewView.class.getResource("/ElecmanSprite/Zap0.png"));
			zap[1] = ImageIO.read(NewView.class.getResource("/ElecmanSprite/Zap1.png"));
			zap[2] = ImageIO.read(NewView.class.getResource("/ElecmanSprite/Zap2.png"));
			zap[3] = ImageIO.read(NewView.class.getResource("/ElecmanSprite/Zap3.png"));
			zap[4] = ImageIO.read(NewView.class.getResource("/ElecmanSprite/Zap3.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// this.direction = State.LEFT;
		// this.orientation = State.CLOCKWISE;
		id = "Zap";
		speed = 15;
		damage = 3;
		xScale = 1000;
		yScale = 40;

	}

	public boolean isActive() {
		return active;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, xScale, yScale);
	}

}
