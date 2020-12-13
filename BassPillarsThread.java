import java.util.Iterator;
import java.util.LinkedList;

public class BassPillarsThread implements Runnable {

	LinkedList<BassProjectile> Activebullets;
	LinkedList<BassProjectile> Waitingbullets;

	@Override
	public void run() {

		Activebullets = new LinkedList<>();
		Waitingbullets = new LinkedList<>();
		long start;
		long fps = 25;
		long targetTime = 1000 / fps;
		long elapsed;
		long wait;

		// TODO Auto-generated method stub
		while (true) {

			start = System.nanoTime();
			// elapsed = System.nanoTime() - start;
			// if(Activebullets.size()>0) {
			while (Waitingbullets.size() > 0) {
				BassProjectile projectile = Waitingbullets.removeLast();
				Activebullets.add(projectile);
			}

			Iterator<BassProjectile> iterator = Activebullets.iterator();
			if (iterator.hasNext()) {
				BassProjectile projectile = iterator.next();

				projectile.pillarframe++;

				projectile.current = projectile.pillars[projectile.pillarframe];
				if (projectile.pillarframe == 0)
					wait = 700;
				// System.out.println(projectile.pillarframe);
				if (projectile.pillarframe > 3) {

					iterator.remove();
				}

			}

			try {
				Thread.sleep(100);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}

		}
	}

}
