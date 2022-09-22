import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
//
public class Bass extends EnemyCharacter {
	private BufferedImage image;
	BufferedImage current;
	BufferedImage healthbar;
	BufferedImage healthtick;
	BufferedImage healthtick2;
	BufferedImage idle;
	BufferedImage idleBack;
	BufferedImage defeated;
	BufferedImage[] SmashAnimation;
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

	Iterator itr;

	State state;
	State direction;
	State action;
	int TeleportCount = 0;
	int WheelCount = 0;
	int walkframe = 0;
	int smashanimation = 0;
	int pillarsummonframe = 0;

	int wheelframe = 0;
	int barrierframe = 0;
	int tpStartFrame = 0;
	int tpEndFrame = 8;
	int defeatCounter = 0;
	long start;
	boolean In_Animation_Flag = false;
	boolean PlayedOnce = false;
	float currentTime = 0;
	float deltaTime = 0;
	float prevTime = 0;

	LinkedList<BassProjectile> Activebullets;

	BassProjectile PillarProjectile;
	BassProjectile PillarProjectileList[];
	BassProjectile ExplosiveList[];
	BassProjectile ExplodeProjectile;

	BassPillarsThread BassPillarsThread;
	BassOrbThread BassOrbThread;
	BassWheelsThread BassWheelsThread;
	Explode ExplodeThread;

	float originalheight;
	long wait;
	long elapsed;

	public Bass(int x, int y, int width, int height) {

		super(x, y, width, height);
		barrierhealth = 100;
		health = 40;
		health2 = 40;
		barrierActive = true;
		throwframe = 0;
		Activebullets = new LinkedList<>();
		PillarProjectileList = new BassProjectile[5];
		ExplosiveList = new BassProjectile[10];

		for (int i = 0; i < 5; i++) {
			PillarProjectile = new BassProjectile();
			PillarProjectile.PillarAttack();
			PillarProjectileList[i] = PillarProjectile;

		}

		for (int i = 0; i < 9; i++) {
			ExplodeProjectile = new BassProjectile();
			ExplodeProjectile.Explosion();
			ExplosiveList[i] = ExplodeProjectile;

		}

		BassPillarsThread = new BassPillarsThread();
		BassOrbThread = new BassOrbThread();
		ExplodeThread = new Explode();
		BassWheelsThread = new BassWheelsThread();
		start = System.currentTimeMillis();
		state = State.STANDING;
		direction = State.FACING_LEFT;
		action = null;
		deltaX = 0;
		deltaY = 410;
		ThrowAnimation = new BufferedImage[4];
		ThrowBackAnimation = new BufferedImage[4];
		Spawn = new BufferedImage[18];
		SpawnLeft = new BufferedImage[18];
		BarrierAnimation = new BufferedImage[3];
		TP_LEFT_Animation = new BufferedImage[9];
		TP_RIGHT_Animation = new BufferedImage[9];
		PillarSummonAnimation = new BufferedImage[5];
		SmashAnimation = new BufferedImage[4];

		// TODO Auto-generated constructor stub
		try {

			defeated = ImageIO.read(NewView.class.getResource("/BassSprite/BassDefeated.png"));
			healthbar = ImageIO.read(NewView.class.getResource("/Effects/h.png"));
			healthtick = ImageIO.read(NewView.class.getResource("/Effects/bar.png"));
			healthtick2 = ImageIO.read(NewView.class.getResource("/Effects/bar2.png"));
			Spawn[0] = ImageIO.read(NewView.class.getResource("/BassSprite/idle0.png"));
			Spawn[1] = ImageIO.read(NewView.class.getResource("/BassSprite/idle0.png"));
			Spawn[2] = ImageIO.read(NewView.class.getResource("/BassSprite/idle0.png"));
			Spawn[3] = ImageIO.read(NewView.class.getResource("/BassSprite/idle1.png"));
			Spawn[4] = ImageIO.read(NewView.class.getResource("/BassSprite/idle1.png"));
			Spawn[5] = ImageIO.read(NewView.class.getResource("/BassSprite/idle1.png"));
			Spawn[6] = ImageIO.read(NewView.class.getResource("/BassSprite/idle2.png"));
			Spawn[7] = ImageIO.read(NewView.class.getResource("/BassSprite/idle2.png"));
			Spawn[8] = ImageIO.read(NewView.class.getResource("/BassSprite/idle2.png"));
			Spawn[9] = ImageIO.read(NewView.class.getResource("/BassSprite/idle3.png"));
			Spawn[10] = ImageIO.read(NewView.class.getResource("/BassSprite/idle3.png"));
			Spawn[11] = ImageIO.read(NewView.class.getResource("/BassSprite/idle3.png"));
			Spawn[12] = ImageIO.read(NewView.class.getResource("/BassSprite/idle4.png"));
			Spawn[13] = ImageIO.read(NewView.class.getResource("/BassSprite/idle4.png"));
			Spawn[14] = ImageIO.read(NewView.class.getResource("/BassSprite/idle4.png"));
			Spawn[15] = ImageIO.read(NewView.class.getResource("/BassSprite/idle5.png"));
			Spawn[16] = ImageIO.read(NewView.class.getResource("/BassSprite/idle5.png"));
			Spawn[17] = ImageIO.read(NewView.class.getResource("/BassSprite/idle5.png"));

			SpawnLeft[0] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack0.png"));
			SpawnLeft[1] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack0.png"));
			SpawnLeft[2] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack0.png"));
			SpawnLeft[3] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack1.png"));
			SpawnLeft[4] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack1.png"));
			SpawnLeft[5] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack1.png"));
			SpawnLeft[6] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack2.png"));
			SpawnLeft[7] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack2.png"));
			SpawnLeft[8] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack2.png"));
			SpawnLeft[9] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack3.png"));
			SpawnLeft[10] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack3.png"));
			SpawnLeft[11] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack3.png"));
			SpawnLeft[12] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack4.png"));
			SpawnLeft[13] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack4.png"));
			SpawnLeft[14] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack4.png"));
			SpawnLeft[15] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack5.png"));
			SpawnLeft[16] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack5.png"));
			SpawnLeft[17] = ImageIO.read(NewView.class.getResource("/BassSprite/idleBack5.png"));

			BarrierAnimation[0] = ImageIO.read(NewView.class.getResource("/Effects/Barrier1.png"));
			BarrierAnimation[1] = ImageIO.read(NewView.class.getResource("/Effects/Barrier2.png"));
			BarrierAnimation[2] = ImageIO.read(NewView.class.getResource("/Effects/Barrier3.png"));

			ThrowAnimation[0] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow1.png"));
			ThrowAnimation[1] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow1.png"));
			ThrowAnimation[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow2.png"));
			ThrowAnimation[3] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow3.png"));

			ThrowBackAnimation[0] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack1.png"));
			ThrowBackAnimation[1] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack1.png"));
			ThrowBackAnimation[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack2.png"));
			ThrowBackAnimation[3] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack3.png"));

			PillarSummonAnimation[0] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar1.png"));
			PillarSummonAnimation[1] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar1.png"));
			PillarSummonAnimation[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar2.png"));
			PillarSummonAnimation[3] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar3.png"));
			PillarSummonAnimation[4] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar4.png"));

			TP_LEFT_Animation[0] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTP1.png"));
			TP_LEFT_Animation[1] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTP1.png"));
			TP_LEFT_Animation[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTP1.png"));
			TP_LEFT_Animation[3] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTP2.png"));
			TP_LEFT_Animation[4] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTP2.png"));
			TP_LEFT_Animation[5] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTP2.png"));
			TP_LEFT_Animation[6] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTP3.png"));
			TP_LEFT_Animation[7] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTP3.png"));
			TP_LEFT_Animation[8] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTP3.png"));

			TP_RIGHT_Animation[0] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTPBack1.png"));
			TP_RIGHT_Animation[1] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTPBack1.png"));
			TP_RIGHT_Animation[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTPBack1.png"));
			TP_RIGHT_Animation[3] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTPBack2.png"));
			TP_RIGHT_Animation[4] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTPBack2.png"));
			TP_RIGHT_Animation[5] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTPBack2.png"));
			TP_RIGHT_Animation[6] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTPBack3.png"));
			TP_RIGHT_Animation[7] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTPBack3.png"));
			TP_RIGHT_Animation[8] = ImageIO.read(NewView.class.getResource("/BassSprite/BassTPBack3.png"));

			SmashAnimation[0] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[1] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash1.png"));
			SmashAnimation[3] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash2.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void spawnAnimation() {

		if (this.direction == State.FACING_LEFT)
			current = Spawn[walkframe];

		else if (this.direction == State.FACING_RIGHT)
			current = SpawnLeft[walkframe];

		if (walkframe == 17) {
			walkframe = 0;

		} else
			walkframe++;
	}

	@Override
	public void update() {

		if (isLive) {
			long targetTime = 1000 / 25;

			elapsed = System.currentTimeMillis();
			wait = targetTime / 1000000;

			if (health <= 0) {
				state = State.DEFEATED;
			}

			if (barrierActive) {

				effects = BarrierAnimation[barrierframe];
				barrierframe++;

				if (barrierframe > 2)
					barrierframe = 0;

			}
			//
			//
			//
			/// Action Randomizer
			if ((elapsed - start) > 3000 && !In_Animation_Flag) {
				Random rand = new Random();
				int int_random = rand.nextInt(4 + 1);
				if (int_random == 0 && TeleportCount != 2) {
					In_Animation_Flag = true;
					TeleportCount++;
					if (this.direction == State.FACING_RIGHT)
						this.state = State.TP_TO_RIGHT_SIDE;

					else if (this.direction == State.FACING_LEFT) {
						this.state = State.TP_TO_LEFT_SIDE;
					}

				}

				else if (int_random == 1 && y != 275 && WheelCount != 2) {
					In_Animation_Flag = true;
					WheelCount++;
					TeleportCount = 0;
					if (this.direction == State.FACING_RIGHT)
						this.state = State.WHEELS_RIGHT;
					else if (this.direction == State.FACING_LEFT)
						this.state = State.WHEELS_LEFT;
				}

				else if (int_random == 2 && !barrierActive && WheelCount == 2) {
					In_Animation_Flag = true;
					TeleportCount = 0;
					x = 800;
					y = 275;
					this.direction = State.FACING_LEFT;
					this.state = State.PILLAR;
				}

				else if (int_random == 3 && WheelCount == 2) {
					WheelCount = 0;
					In_Animation_Flag = true;
					// TeleportCount = 0;
					x = 100;
					y = 275;
					this.direction = State.FACING_RIGHT;
					this.state = State.SMASH;
				}

			}

			switch (state) {

			case DEFEATED:
				defeatedAnimation();

				break;

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

			case TP_TO_LEFT_SIDE:

				TeleportAnimationLeft();

				break;

			case TP_TO_RIGHT_SIDE:

				TeleportAnimationRight();

				break;

			case SMASH:
				Smash();
				break;

			default:
				break;
			}
			if (BassPillarsThread.Waitingbullets.size() > 0 || BassPillarsThread.Activebullets.size() > 0)
				BassPillarsThread.update();
			if (BassOrbThread.Activebullets.size() > 0)
				BassOrbThread.update();
			if (BassWheelsThread.Activebullets.size() > 0)
				BassWheelsThread.update();

		}

		else {
			ExplodeThread.update();
			if (ExplodeThread.Activebullets.size() == 0)
				current = null;
		}
	}

	private void Smash() {
		// TODO Auto-generated method stub

		current = SmashAnimation[smashanimation];
		currentTime = System.nanoTime();
		deltaTime = (currentTime - prevTime) / 1000000000;
		if (deltaTime > 0.4f) {
			smashanimation++;
			prevTime = currentTime;
		}

		if (smashanimation == 1) {
			if (!LockedOn) {
				LockedOn = true;
			}

		}

		if (smashanimation == 2) {
			this.x = this.PLAYER_LOCATION_X - 140;
			this.y = this.PLAYER_LOCATION_Y - 150;
		}

		else if (smashanimation == 3 && PlayedOnce == false) {
			PlayedOnce = true;
			BassProjectile projectile = new BassProjectile();
			projectile.OrbAttack();
			projectile.x = x;
			projectile.y = y;
			BassOrbThread.Activebullets.add(projectile);
			playSound("/music/BassOrb.wav");
			// In_Animation_Flag = false

		}

		if (smashanimation > 3) {
			this.state = State.TP_TO_RIGHT_SIDE;
			smashanimation = 0;
			start = elapsed;
			LockedOn = false;
			PlayedOnce = false;
		}

	}

	private void defeatedAnimation() {
		// TODO Auto-generated method stub
		// setDestroyed();
		current = defeated;
		setDestroyed();
		ExplodeThread = new Explode();
		for (int i = 0; i < 9; i++) {

			Random rand = new Random();
			int int_random = (int) (rand.nextInt((int) ((x + 25) - x + 1)) + x);
			int int_random2 = (int) (rand.nextInt((int) ((y + 25) - y + 1)) + y);

			ExplosiveList[i].x = int_random;
			ExplosiveList[i].y = int_random2;
			ExplodeThread.Activebullets.add(ExplosiveList[i]);

		}

	}

	private void wheelsAnimation() {
		// TODO Auto-generated method stub

		// this.direction = State.LEFT;
		current = ThrowAnimation[throwframe];

		currentTime = System.nanoTime();
		deltaTime = (currentTime - prevTime) / 1000000000;
		if (deltaTime > 0.2f) {
			throwframe++;
			prevTime = currentTime;
		}

		if (throwframe > 3) {

			this.state = State.STANDING;
			for (int i = 0; i < 1; i++) {
				BassProjectile projectile = new BassProjectile();
				// projectile.orientation = State.CLOCKWISE;
				projectile.WheelAttack();
				projectile.x = 900;
				projectile.y = 400;
				BassWheelsThread.Activebullets.add(projectile);

			}
			In_Animation_Flag = false;
			throwframe = 0;
			start = elapsed;
		}

	}

	private void wheelsFlippedAnimation() { // TODO Auto-generated method stub

		current = ThrowBackAnimation[throwframe];
		currentTime = System.nanoTime();
		deltaTime = (currentTime - prevTime) / 1000000000;
		if (deltaTime > 0.225f) {
			throwframe++;
			prevTime = currentTime;
		}

		if (throwframe > 3) {

			this.state = State.STANDING;
			for (int i = 0; i < 1; i++) {
				BassProjectile projectile = new BassProjectile();

				projectile.FlippedWheelAttack();
				projectile.x = 50;
				projectile.y = 400;
				BassWheelsThread.Activebullets.add(projectile);

			}
			In_Animation_Flag = false;
			throwframe = 0;
			start = elapsed;
		}

	}

	private void TeleportAnimationLeft() {
		// TODO Auto-generated method stub
		if (tpStartFrame < 9) {
			current = TP_RIGHT_Animation[tpStartFrame];
			tpStartFrame++;

		} else if (tpEndFrame != -1 && tpStartFrame == 9) {
			x = 100;
			y = 360;
			current = TP_LEFT_Animation[tpEndFrame];
			// this.direction = State.FACING_RIGHT;

			tpEndFrame--;

			if (tpEndFrame < 0) {
				tpStartFrame = 0;
				tpEndFrame = 8;
				In_Animation_Flag = false;
				// wheelsFlippedAnimation();
				this.direction = State.FACING_RIGHT;
				this.state = State.STANDING;

				start = elapsed;
			}

		}

	}

	private void TeleportAnimationRight() {
		// TODO Auto-generated method stub
		if (tpStartFrame < 9) {
			current = TP_LEFT_Animation[tpStartFrame];
			tpStartFrame++;

		} else if (tpEndFrame != -1 && tpStartFrame == 9) {
			x = 800;
			y = 360;
			current = TP_RIGHT_Animation[tpEndFrame];
			tpEndFrame--;
			if (tpEndFrame < 0) {
				tpStartFrame = 0;
				tpEndFrame = 8;
				this.direction = State.FACING_LEFT;

				In_Animation_Flag = false;
				// wheelsAnimation();
				this.state = State.STANDING;

				start = elapsed;

			}

		}

	}

	private void pillarAnimation() {
		// TODO Auto-generated method stub

		current = PillarSummonAnimation[pillarsummonframe];
		currentTime = System.nanoTime();
		deltaTime = (currentTime - prevTime) / 1000000000;
		if (deltaTime > 0.3f) {
			pillarsummonframe++;
			prevTime = currentTime;
		}
		if (pillarsummonframe == 5) {
			for (int i = 0; i < 5; i++) {
				Random rand = new Random();
				int int_random = rand.nextInt(200 + 500);
				PillarProjectileList[i].x = int_random;
				PillarProjectileList[i].y = 260;
				BassPillarsThread.Waitingbullets.add(PillarProjectileList[i]);

			}
			In_Animation_Flag = false;
			this.state = State.STANDING;
			pillarsummonframe = 0;
			start = elapsed;

		}
	}

	public void playSound(String soundName) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(NewView.class.getResource(soundName));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}

	public void paint(Graphics g, Mega mega) {
		Graphics2D g2d = (Graphics2D) g.create();
		// g.setColor(java.awt.Color.black);
		// image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);

		// BASS
		g2d.drawImage(healthbar, 925, 100, 40, 240, null);

		for (int healthstack = 0; healthstack < health; healthstack++) {

			int ypos = 275 - (healthstack * 4);
			int xpos = 932;
			g2d.drawImage(healthtick, xpos, ypos, 20, 4, null);

		}

		for (int healthstack = 0; healthstack < health2; healthstack++) {

			int ypos = 275 - (healthstack * 4);
			int xpos = 932;
			g2d.drawImage(healthtick2, xpos, ypos, 20, 4, null);

		}

		g2d.drawImage(effects, (int) x - 13, (int) y - 25, 120, 120, null);
		g2d.drawImage(current, (int) x, (int) y, 100, 100, null);

		if (BassWheelsThread.Activebullets != null) {

			// WHEEL INTERACTIONS
			//
			//

			if (BassWheelsThread.Activebullets != null) {
				Rectangle MegaHitBox = mega.getBounds();

				itr = BassWheelsThread.Activebullets.iterator();
				while (itr.hasNext()) {
					BassProjectile projectile = (BassProjectile) itr.next();
					Rectangle ShotHitBox = projectile.getBounds();

					g2d.drawImage(projectile.wheels[4], (int) projectile.x, (int) projectile.y, projectile.xScale,
							projectile.yScale, null);

					if (ShotHitBox.intersects(MegaHitBox) && mega.invincible == false) {
						mega.health -= projectile.damage;

						itr.remove();
						mega.invincible = true;
						mega.Invincible_Frame_Start_Time = System.nanoTime();

					}
					if (!isLive) {
						itr.remove();
					}

				}

			}

		}
		//
		//
		// Pillar Collision Check
		if (BassPillarsThread.Activebullets != null) {
			Rectangle MegaHitBox = mega.getBounds();

			itr = BassPillarsThread.Activebullets.iterator();
			while (itr.hasNext()) {
				BassProjectile projectile = (BassProjectile) itr.next();
				Rectangle ShotHitBox = projectile.getBounds();

				g2d.drawImage(projectile.current, (int) projectile.x, (int) projectile.y, projectile.xScale,
						projectile.yScale, null);

				if (ShotHitBox.intersects(MegaHitBox) && projectile.pillarframe > 15 && projectile.pillarframe < 27) {
					mega.health -= projectile.damage;

				}
				if (!isLive) {
					itr.remove();
				}

			}

		}
		// Bass Orb
		if (BassOrbThread.Activebullets != null) {
			Rectangle MegaHitBox = mega.getBounds();

			itr = BassOrbThread.Activebullets.iterator();
			while (itr.hasNext()) {
				BassProjectile projectile = (BassProjectile) itr.next();
				Rectangle ShotHitBox = projectile.getBounds();

				g2d.drawImage(projectile.current, (int) projectile.x, (int) projectile.y, projectile.xScale,
						projectile.yScale, null);

				if (ShotHitBox.intersects(MegaHitBox) && mega.invincible == false) {
					mega.health -= projectile.damage;
					mega.invincible = true;
					mega.Invincible_Frame_Start_Time = System.nanoTime();

				}
				if (!isLive) {
					itr.remove();
				}

			}

		}

		//
		//
		// Explosion Animation
		if (ExplodeThread.Activebullets != null) {

			itr = ExplodeThread.Activebullets.iterator();
			while (itr.hasNext()) {
				BassProjectile projectile = (BassProjectile) itr.next();

				g2d.drawImage(projectile.current, (int) projectile.x, (int) projectile.y, projectile.xScale,
						projectile.yScale, null);

			}
		}

	}

}
