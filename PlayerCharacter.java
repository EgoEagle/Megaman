import java.awt.Rectangle;

public class PlayerCharacter {

	float x;
	float y;
	int width;
	int height;
	float deltaX;
	float deltaY;
	int speed;
	int health;
	double airtime = 0;
	boolean isLive = true;

	public PlayerCharacter(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public boolean getIsLive() {
		return isLive;
	}

	public void setIsLive(boolean isLive) {
		this.isLive = isLive;
	}

	public void setDestroyed() {
		this.isLive = false;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public void update() {
		// TODO Auto-generated method stub

	}
}
