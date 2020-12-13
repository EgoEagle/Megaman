import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Bass extends Character implements Runnable {

	BufferedImage current;
	BufferedImage effects;
	BufferedImage healthbar;
	BufferedImage healthtick;
	BufferedImage idle;
	BufferedImage idleBack;
	BufferedImage defeated;
	BufferedImage[] ThrowAnimation;
	BufferedImage[] ThrowBackAnimation;
	BufferedImage[] PillarSummonAnimation;
	BufferedImage[] TP_LEFT_Animation;
	BufferedImage[] TP_RIGHT_Animation;
	BufferedImage[] Spawn;
	BufferedImage[] SpawnLeft;
	BufferedImage[] BarrierAnimation;
	boolean leftwalk = false;
	boolean rightwalk = false;
	boolean shoot = false;
	boolean air = false;
	boolean charge = false;
	boolean barrierActive = true;
	State state;
	State direction;
	State action;
	int barrierhealth = 100;
	int walkframe = 0;
	int pillarsummonframe = 0;
	int throwframe = 0;
	int wheelframe = 0;
	int barrierframe = 0;
	int tpStartFrame = 0;
	int tpEndFrame = 2;
	long start;

	Thread BassWheels;
	BassWheelsThread BassWheelsThread;
	Thread BassPillars;
	BassPillarsThread BassPillarsThread;

	float originalheight;
	long wait;
	long elapsed;

	public Bass(int x, int y, int width, int height) {
		super(x, y, width, height);
		health = 80;

		BassPillarsThread = new BassPillarsThread();
		BassPillars = new Thread(BassPillarsThread);

		BassWheelsThread = new BassWheelsThread();
		BassWheels = new Thread(BassWheelsThread);

		BassWheels.start();
		BassPillars.start();
		start = System.currentTimeMillis();
		state = State.STANDING;
		direction = State.LEFT;
		action = null;
		deltaX = 0;
		deltaY = 410;
		ThrowAnimation = new BufferedImage[3];
		ThrowBackAnimation = new BufferedImage[3];
		Spawn = new BufferedImage[6];
		SpawnLeft = new BufferedImage[6];
		BarrierAnimation = new BufferedImage[3];
		TP_LEFT_Animation = new BufferedImage[3];
		TP_RIGHT_Animation = new BufferedImage[3];

		PillarSummonAnimation = new BufferedImage[4];

		// TODO Auto-generated constructor stub
		try {
			/*
			 * healthbar = ImageIO.read(new
			 * File("C:/Users/user/eclipse-workspace/Game/Effects/h.png")); healthtick =
			 * ImageIO.read(new
			 * File("C:/Users/user/eclipse-workspace/Game/Effects/bar.png"));
			 */

			defeated = ImageIO.read(NewView.class.getResource("/BassSprite/BassDefeated.png"));
			healthbar = ImageIO.read(NewView.class.getResource("/Effects/h.png"));
			healthtick = ImageIO.read(NewView.class.getResource("/Effects/bar.png"));
			Spawn[0] = ImageIO.read(NewView.class.getResource("/BassSprite/idle0.png"));
			Spawn[1] = ImageIO.read(NewView.class.getResource("/BassSprite/idle1.png"));
			Spawn[2] = ImageIO.read(NewView.class.getResource("/BassSprite/idle2.png"));
			Spawn[3] = ImageIO.read(NewView.class.getResource("/BassSprite/idle3.png"));
			Spawn[4] = ImageIO.read(NewView.class.getResource("/BassSprite/idle4.png"));
			Spawn[5] = ImageIO.read(NewView.class.getResource("/BassSprite/idle5.png"));

			SpawnLeft[0] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack0.png"));
			SpawnLeft[1] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack1.png"));
			SpawnLeft[2] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack2.png"));
			SpawnLeft[3] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack3.png"));

			BarrierAnimation[0] = ImageIO.read(NewView.class.getResource("/Effects/Barrier1.png"));
			BarrierAnimation[1] = ImageIO.read(NewView.class.getResource("/Effects/Barrier2.png"));
			BarrierAnimation[2] = ImageIO.read(NewView.class.getResource("/Effects/Barrier3.png"));

			ThrowAnimation[0] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow1.png"));
			ThrowAnimation[1] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow2.png"));
			ThrowAnimation[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow3.png"));

			ThrowBackAnimation[0] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack1.png"));
			ThrowBackAnimation[1] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack2.png"));
			ThrowBackAnimation[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack3.png"));

			PillarSummonAnimation[0] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar1.png"));
			PillarSummonAnimation[1] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar2.png"));
			PillarSummonAnimation[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar3.png"));
			PillarSummonAnimation[3] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar4.png"));

			TP_LEFT_Animation[0] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTP1.png"));
			TP_LEFT_Animation[1] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTP2.png"));
			TP_LEFT_Animation[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTP3.png"));

			TP_RIGHT_Animation[0] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTPBack1.png"));
			TP_RIGHT_Animation[1] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTPBack2.png"));
			TP_RIGHT_Animation[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTPBack3.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void spawnAnimation() {
		if (this.direction == State.LEFT) {

			current = Spawn[walkframe];

			walkframe++;

			if (walkframe == 4) {
				walkframe = 0;

			}
		}

		else if (this.direction == State.RIGHT) {

			current = SpawnLeft[walkframe];

			walkframe++;

			if (walkframe == 4) {
				walkframe = 0;

			}

		}
	}

	@Override
	public void run() {
		while (getIsLive()) {

			long targetTime = 1000 / 25;

			elapsed = System.currentTimeMillis();
			wait = targetTime / 1000000;

			if (wait < 0)
				wait = 15;
			/*
			 * else if (state == State.STANDING) wait = 140; else if (state == State.WHEELS)
			 * wait = 140;
			 */
			wait = 100;

			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}

			if (barrierActive) {

				effects = BarrierAnimation[barrierframe];
				barrierframe++;

				if (barrierframe > 2)
					barrierframe = 0;

			}

			if ((elapsed - start) > 3000) {
				Random rand = new Random();
				int int_random = rand.nextInt(4 + 1);
				if (int_random == 0 && this.direction == State.RIGHT) {
					this.state = State.TPRIGHT;
				} else if (int_random == 1 && this.direction == State.LEFT) {
					this.state = State.TPLEFT;
				}

				else if (int_random == 2 && y != 250 && this.direction == State.RIGHT) {
					this.state = State.WHEELS_RIGHT;
				}

				else if (int_random == 3 && y != 250 && this.direction == State.LEFT) {
					this.state = State.WHEELS_LEFT;
				}

				else if (int_random == 4) {
					x = 800;
					y = 250;
					this.direction = State.LEFT;
					this.state = State.PILLAR;
				}

			}

			switch (state) {

			case STANDING:
				spawnAnimation();

				break;

			case PILLAR:
				pillarAnimation();

				break;

			case WHEELS_RIGHT:
				wheelsFlippedAnimation();

				break;

			case WHEELS_LEFT:
				wheelsAnimation();

				break;

			case TPLEFT:
				TeleportAnimationLeft();

				break;

			case TPRIGHT:
				TeleportAnimationRight();

				break;

			/*
			 * case WHEELS_FLIPPED: x = 250; y = 360; wheelsFlippedAnimation(); this.state =
			 * State.STANDING; break;
			 */

			case ORBS:
				break;

			default:
				break;
			}

		}

	}

	private void wheelsAnimation() {
		// TODO Auto-generated method stub

		// this.direction = State.LEFT;
		current = ThrowAnimation[throwframe];
		throwframe++;

		if (throwframe > 2) {

			this.state = State.STANDING;
			for (int i = 0; i < 1; i++) {
				BassProjectile projectile = new BassProjectile();
				// projectile.orientation = State.CLOCKWISE;
				projectile.WheelAttack();
				projectile.x = 900;
				projectile.y = 400;
				BassWheelsThread.Waitingbullets.add(projectile);

			}

			throwframe = 0;
			start = elapsed;
		}

	}

	private void wheelsFlippedAnimation() { // TODO Auto-generated method stub

		current = ThrowBackAnimation[throwframe];
		throwframe++;

		if (throwframe > 2) {

			this.state = State.STANDING;
			for (int i = 0; i < 1; i++) {
				BassProjectile projectile = new BassProjectile();
				// projectile.orientation = State.COUNTERCLOCKWISE;
				projectile.FlippedWheelAttack();
				projectile.x = 50;
				projectile.y = 400;
				BassWheelsThread.Waitingbullets.add(projectile);

			}

			throwframe = 0;
			start = elapsed;
		}

	}

	private void TeleportAnimationLeft() {
		// TODO Auto-generated method stub
		if (tpStartFrame < 3) {
			current = TP_RIGHT_Animation[tpStartFrame];
			tpStartFrame++;

		}
		if (tpStartFrame > 2) {
			x = 100;
			y = 360;
			current = TP_LEFT_Animation[tpEndFrame];
			tpEndFrame--;
			if (tpEndFrame < 0) {
				tpStartFrame = 0;
				tpEndFrame = 2;
				wheelsFlippedAnimation();
				this.direction = State.RIGHT;
				start = elapsed;
			}

		}

	}

	private void TeleportAnimationRight() {
		// TODO Auto-generated method stub
		if (tpStartFrame < 3) {
			current = TP_LEFT_Animation[tpStartFrame];
			tpStartFrame++;

		}
		if (tpStartFrame > 2) {
			x = 800;
			y = 360;
			current = TP_RIGHT_Animation[tpEndFrame];
			tpEndFrame--;
			if (tpEndFrame < 0) {
				tpStartFrame = 0;
				tpEndFrame = 2;
				wheelsAnimation();
				this.direction = State.LEFT;
				start = elapsed;

			}

		}

	}

	private void pillarAnimation() {
		// TODO Auto-generated method stub
		current = PillarSummonAnimation[pillarsummonframe];
		pillarsummonframe++;

		if (pillarsummonframe > 2) {
			for (int i = 0; i < 5; i++) {
				BassProjectile projectile = new BassProjectile();
				// projectile.orientation = State.COUNTERCLOCKWISE;
				projectile.PillarAttack();
				Random rand = new Random();
				int int_random = rand.nextInt(200 + 500);
				projectile.x = int_random;
				projectile.y = 260;
				BassPillarsThread.Activebullets.add(projectile);

			}
			this.state = State.STANDING;
			pillarsummonframe = 0;
			start = elapsed;
		}
	}

}
