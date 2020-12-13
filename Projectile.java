import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Projectile {
	BufferedImage image;
	int speed = 5;
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

	public Projectile() {
		active = true;
		try {
			image = ImageIO.read(NewView.class.getResource("/Effects/shot.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		damage = 1;
		xScale = 25;
		yScale = 25;
		int width = image.getWidth(null);
		int height = image.getWidth(null);

	}

	public void FullChargeShot() {
		try {

			image = ImageIO.read(NewView.class.getResource("/Effects/chargeshot.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}
		damage = 5;
		xScale = 50;
		yScale = 50;
		int width = image.getWidth(null);
		int height = image.getWidth(null);
	}

	public void FullChargeShotFlip() {
		try {

			image = ImageIO.read(NewView.class.getResource("/Effects/chargeshotFLIPPED.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}
		damage = 5;
		xScale = 50;
		yScale = 50;
		int width = image.getWidth(null);
		int height = image.getWidth(null);
	}

	public void SemiChargeShot() {
		try {

			image = ImageIO.read(NewView.class.getResource("/Effects/semichargeshot.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}
		damage = 3;
		xScale = 50;
		yScale = 25;
		int width = image.getWidth(null);
		int height = image.getWidth(null);
	}

	public void SemiChargeShotFlip() {
		try {

			image = ImageIO.read(NewView.class.getResource("/Effects/semichargeshotFLIPPED.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}
		damage = 3;
		xScale = 50;
		yScale = 25;
		int width = image.getWidth(null);
		int height = image.getWidth(null);

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

		int width = image.getWidth(null);
		int height = image.getWidth(null);

	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, xScale, yScale);
	}

}
