import java.util.Iterator;
import java.util.LinkedList;

public class ProjectileThread {

	LinkedList<Projectile> Activebullets = new LinkedList<>();

	public void update() {

		Iterator<Projectile> itr = Activebullets.iterator();
		while (itr.hasNext()) {
			Projectile projectile = itr.next();
			if (projectile.direction == State.RIGHT)
				projectile.x += projectile.speed;
			else if (projectile.direction == State.LEFT)
				projectile.x -= projectile.speed;

			if (projectile.x > 999 || projectile.x < 0) {
				itr.remove();
			}

		}

	}

}
