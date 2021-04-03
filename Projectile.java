import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Projectile {
	BufferedImage image;

	int speed;
	int damage;
	double time = 0;
	State direction;
	boolean active;
	float x;
	float y;
	int width;
	int height;
	int xScale;
	int yScale;
	boolean reflected = false;
	String id;

	public Projectile() {
		active = true;
		try {
			image = ImageIO.read(NewView.class.getResource("/Effects/shot.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		speed = 15;
		damage = 1;
		xScale = 25;
		yScale = 25;
		int width = image.getWidth(null);
		int height = image.getWidth(null);
		id = "Shot";

	}

	public void FullChargeShot() {
		try {

			image = ImageIO.read(NewView.class.getResource("/Effects/chargeshot.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}
		speed = 20;
		damage = 5;
		xScale = 50;
		yScale = 50;
		int width = image.getWidth(null);
		int height = image.getWidth(null);
		id = "Chargeshot";
	}

	public void FullChargeShotFlip() {
		try {

			image = ImageIO.read(NewView.class.getResource("/Effects/chargeshotFLIPPED.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}
		speed = 20;
		damage = 5;
		xScale = 50;
		yScale = 50;
		int width = image.getWidth(null);
		int height = image.getWidth(null);
		id = "Chargeshot";
	}

	public void SemiChargeShot() {
		try {

			image = ImageIO.read(NewView.class.getResource("/Effects/semichargeshot.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}
		speed = 20;
		damage = 3;
		xScale = 50;
		yScale = 25;
		int width = image.getWidth(null);
		int height = image.getWidth(null);
		id = "Semichargeshot";
	}

	public void SemiChargeShotFlip() {
		try {

			image = ImageIO.read(NewView.class.getResource("/Effects/semichargeshotFLIPPED.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}
		speed = 20;
		damage = 3;
		xScale = 50;
		yScale = 25;
		int width = image.getWidth(null);
		int height = image.getWidth(null);
		id = "Semichargeshot";

	}

	public boolean isActive() {
		return active;
	}

	public void BasicShot() {
		try {

			image = ImageIO.read(NewView.class.getResource("/Effects/shot.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}
		speed = 15;
		int width = image.getWidth(null);
		int height = image.getWidth(null);

	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, xScale, yScale);
	}

}
