import java.util.Iterator;
import java.util.LinkedList;

public class ZapThread {
	LinkedList<ElecmanProjectile> Activebullets = new LinkedList<>();

	public void update() {

		Iterator<ElecmanProjectile> iterator = Activebullets.iterator();
		if (iterator.hasNext()) {

			ElecmanProjectile projectile = iterator.next();
			projectile.current = projectile.zap[projectile.zapframe];

			float currentTime = System.nanoTime();
			float deltaTime = (currentTime - projectile.StartTime) / 1000000000;

			if (deltaTime > .02) {
				projectile.zapframe++;

			}
			if (projectile.zapframe > 4) {
				projectile.zapframe = 0;
				iterator.remove();

			}

		}

	}
}
