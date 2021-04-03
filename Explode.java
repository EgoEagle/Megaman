import java.util.Iterator;
import java.util.LinkedList;

public class Explode {
	LinkedList<BassProjectile> Activebullets = new LinkedList<>();

	public void update() {

		Iterator<BassProjectile> iterator = Activebullets.iterator();
		if (iterator.hasNext()) {

			BassProjectile projectile = iterator.next();
			projectile.current = projectile.ExplodeAnimation[projectile.explosionframe];
			projectile.explosionframe++;

			if (projectile.explosionframe > 7) {
				projectile.explosionframe = 0;
				iterator.remove();

			}

		}

	}
}
