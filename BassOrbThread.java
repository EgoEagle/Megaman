import java.util.Iterator;
import java.util.LinkedList;

public class BassOrbThread {
	LinkedList<BassProjectile> Activebullets = new LinkedList<>();

	public void update() {

		Iterator<BassProjectile> iterator = Activebullets.iterator();
		if (iterator.hasNext()) {

			BassProjectile projectile = iterator.next();
			projectile.current = projectile.Orb[projectile.orbframe];

			float currentTime = System.nanoTime();
			float deltaTime = (currentTime - projectile.StartTime) / 1000000000;

			if (deltaTime > .05) {
				projectile.orbframe++;

			}
			if (projectile.orbframe > 7) {
				projectile.orbframe = 0;
				iterator.remove();

			}
		}

	}
}
