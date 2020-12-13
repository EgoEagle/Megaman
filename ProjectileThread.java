import java.util.Iterator;
import java.util.LinkedList;

public class ProjectileThread implements Runnable {

	LinkedList<Projectile> Activebullets;
	LinkedList<Projectile> Waitingbullets;

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
				Projectile projectile = Waitingbullets.removeLast();
				Activebullets.add(projectile);
			}
			Iterator<Projectile> itr = Activebullets.iterator();
			while (itr.hasNext()) {
				Projectile projectile = itr.next();
				if (projectile.direction == State.RIGHT)
					projectile.x += projectile.speed;
				else if (projectile.direction == State.LEFT)
					projectile.x -= projectile.speed;

				if (projectile.x > 999) {
					itr.remove();
				}

			}

			try {
				Thread.sleep(10);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}

		}
	}

}
