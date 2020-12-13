import java.util.Iterator;
import java.util.LinkedList;

public class BassWheelsThread implements Runnable {

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
				/*
				 * if (projectile.x > 999) { // Remove the current element from the iterator and
				 * the list. iterator.remove(); }
				 */
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
