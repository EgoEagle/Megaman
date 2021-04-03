import java.util.Iterator;
import java.util.LinkedList;

public class BassPillarsThread {

	LinkedList<BassProjectile> Activebullets = new LinkedList<>();
	LinkedList<BassProjectile> Waitingbullets = new LinkedList<>();

	public void update() {

		if (Waitingbullets.size() > 0 && Activebullets.size() == 0) {
			BassProjectile projectile = Waitingbullets.removeLast();
			Activebullets.add(projectile);
		}

		Iterator<BassProjectile> iterator = Activebullets.iterator();
		if (iterator.hasNext()) {

			BassProjectile projectile = iterator.next();
			projectile.current = projectile.pillars[projectile.pillarframe];
			projectile.pillarframe++;

			if (projectile.pillarframe > 28) {
				projectile.pillarframe = 0;
				iterator.remove();

			}

		}

	}

}
