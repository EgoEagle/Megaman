import java.awt.Rectangle;

public class EnemyCharacter {

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
	float PLAYER_LOCATION_X;
	float PLAYER_LOCATION_Y;
	float Destination;

	public EnemyCharacter(int x, int y, int width, int height) {
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

	public void DeathAnimation() {

	}

	public void update() {
		// TODO Auto-generated method stub

	}

	public void trackPlayer(PlayerCharacter player) {
		PLAYER_LOCATION_X = player.x;
		PLAYER_LOCATION_Y = player.y;
	}

	public float CalculateDistance() {
		float distance = (float) Math
				.sqrt(Math.pow((this.x - PLAYER_LOCATION_X), 2) + Math.pow((this.y - PLAYER_LOCATION_Y), 2));

		return distance;
	}
}
