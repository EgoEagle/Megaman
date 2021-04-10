import java.util.Iterator;
import java.util.LinkedList;

public class BassOrbThread {
	LinkedList<BassProjectile> Activebullets = new LinkedList<>();

	public void update() {

		Iterator<BassProjectile> iterator = Activebullets.iterator();
		if (iterator.hasNext()) {

			BassProjectile projectile = iterator.next();
			projectile.current = projectile.Orb[projectile.orbframe];

			if (projectile.orbframe != 7) {
				projectile.orbframe++;

			} else {
				projectile.pillarframe = 0;
				iterator.remove();

			}
		}

	}
}
