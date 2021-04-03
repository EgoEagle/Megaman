import java.util.Iterator;
import java.util.LinkedList;

public class BassWheelsThread {

	LinkedList<BassProjectile> Activebullets = new LinkedList<>();

	public void update() {

		Iterator<BassProjectile> iterator = Activebullets.iterator();
		while (iterator.hasNext()) {

			BassProjectile projectile = iterator.next();
			if (projectile.direction == State.RIGHT)
				projectile.x += projectile.speed;
			else if (projectile.direction == State.LEFT)
				projectile.x -= projectile.speed;
			else if (projectile.direction == State.UP)
				projectile.y -= projectile.speed;
			else if (projectile.direction == State.DOWN)
				projectile.y += projectile.speed;

			State direction = projectile.direction;
			switch (direction) {
			case LEFT:
				if (projectile.x < 1 && projectile.y > 395)
					projectile.direction = State.UP;
				else if (projectile.x < 1 && projectile.y < 1)
					projectile.direction = State.DOWN;
				break;

			case RIGHT:
				if (projectile.x > 920 && projectile.y < 1) {

					projectile.direction = State.DOWN;
				} else if (projectile.x > 920 && projectile.y > 395)
					projectile.direction = State.UP;

				break;

			case DOWN:
				if (projectile.x > 920 && projectile.y > 395) {
					projectile.direction = State.LEFT;

				}

				else if (projectile.x < 1 && projectile.y > 395)
					projectile.direction = State.RIGHT;
				break;
			case UP:
				if (projectile.y < 1 && projectile.x < 1)
					projectile.direction = State.RIGHT;

				else if (projectile.x > 920 && projectile.y < 1)
					projectile.direction = State.LEFT;

				break;

			}
		}

	}

}
