import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Bass extends EnemyCharacter {

	BufferedImage current;
	BufferedImage effects;
	BufferedImage healthbar;
	BufferedImage healthtick;
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
	boolean barrierActive = true;

	State state;
	State direction;
	State action;
	int barrierhealth = 100;
	int TeleportCount = 0;
	int WheelCount = 0;
	int walkframe = 0;
	int smashanimation = -1;
	int pillarsummonframe = -1;
	int throwframe = 0;
	int wheelframe = 0;
	int barrierframe = 0;
	int tpStartFrame = 0;
	int tpEndFrame = 8;
	int defeatCounter = 0;
	long start;
	boolean In_Animation_Flag = false;
	boolean LockedOn = false;

	LinkedList<BassProjectile> Activebullets;

	BassProjectile PillarProjectile;
	BassProjectile PillarProjectileList[];
	BassProjectile ExplosiveList[];
	BassProjectile ExplodeProjectile;

	BassPillarsThread BassPillarsThread;
	BassOrbThread BassOrbThread;
	Explode ExplodeThread;

	float originalheight;
	long wait;
	long elapsed;

	public Bass(int x, int y, int width, int height) {

		super(x, y, width, height);

		health = 80;
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
		start = System.currentTimeMillis();
		state = State.STANDING;
		direction = State.FACING_LEFT;
		action = null;
		deltaX = 0;
		deltaY = 410;
		ThrowAnimation = new BufferedImage[12];
		ThrowBackAnimation = new BufferedImage[12];
		Spawn = new BufferedImage[18];
		SpawnLeft = new BufferedImage[18];
		BarrierAnimation = new BufferedImage[3];
		TP_LEFT_Animation = new BufferedImage[9];
		TP_RIGHT_Animation = new BufferedImage[9];
		PillarSummonAnimation = new BufferedImage[22];
		SmashAnimation = new BufferedImage[25];

		// TODO Auto-generated constructor stub
		try {

			defeated = ImageIO.read(NewView.class.getResource("/BassSprite/BassDefeated.png"));
			healthbar = ImageIO.read(NewView.class.getResource("/Effects/h.png"));
			healthtick = ImageIO.read(NewView.class.getResource("/Effects/bar.png"));
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
			ThrowAnimation[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow1.png"));
			ThrowAnimation[3] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow1.png"));
			ThrowAnimation[4] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow2.png"));
			ThrowAnimation[5] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow2.png"));
			ThrowAnimation[6] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow2.png"));
			ThrowAnimation[7] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow2.png"));
			ThrowAnimation[8] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow3.png"));
			ThrowAnimation[9] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow3.png"));
			ThrowAnimation[10] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow3.png"));
			ThrowAnimation[11] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrow3.png"));

			ThrowBackAnimation[0] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack1.png"));
			ThrowBackAnimation[1] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack1.png"));
			ThrowBackAnimation[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack1.png"));
			ThrowBackAnimation[3] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack1.png"));
			ThrowBackAnimation[4] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack2.png"));
			ThrowBackAnimation[5] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack2.png"));
			ThrowBackAnimation[6] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack2.png"));
			ThrowBackAnimation[7] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack2.png"));
			ThrowBackAnimation[8] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack3.png"));
			ThrowBackAnimation[9] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack3.png"));
			ThrowBackAnimation[10] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack3.png"));
			ThrowBackAnimation[11] = ImageIO.read(NewView.class.getResource("/BassSprite/BassThrowBack3.png"));

			PillarSummonAnimation[0] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar1.png"));
			PillarSummonAnimation[1] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar1.png"));
			PillarSummonAnimation[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar1.png"));
			PillarSummonAnimation[3] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar1.png"));
			PillarSummonAnimation[4] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar1.png"));
			PillarSummonAnimation[5] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar1.png"));
			PillarSummonAnimation[3] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar2.png"));
			PillarSummonAnimation[4] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar2.png"));
			PillarSummonAnimation[5] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar2.png"));
			PillarSummonAnimation[6] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar2.png"));
			PillarSummonAnimation[7] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar2.png"));
			PillarSummonAnimation[8] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar2.png"));
			PillarSummonAnimation[9] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar3.png"));
			PillarSummonAnimation[10] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar3.png"));
			PillarSummonAnimation[11] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar3.png"));
			PillarSummonAnimation[12] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar3.png"));
			PillarSummonAnimation[13] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar3.png"));
			PillarSummonAnimation[14] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar3.png"));
			PillarSummonAnimation[15] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar4.png"));
			PillarSummonAnimation[16] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar4.png"));
			PillarSummonAnimation[17] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar4.png"));
			PillarSummonAnimation[18] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar4.png"));
			PillarSummonAnimation[19] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar4.png"));
			PillarSummonAnimation[20] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar4.png"));
			PillarSummonAnimation[21] = ImageIO.read(NewView.class.getResource("/BassSprite/BassPillar4.png"));

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
			SmashAnimation[2] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[3] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[4] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[5] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[6] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[7] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[8] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[9] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[10] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[11] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[12] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[13] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[14] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[15] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[16] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash0.png"));
			SmashAnimation[17] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash1.png"));
			SmashAnimation[18] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash1.png"));
			SmashAnimation[19] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash1.png"));
			SmashAnimation[20] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash1.png"));
			SmashAnimation[21] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash2.png"));
			SmashAnimation[22] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash2.png"));
			SmashAnimation[23] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash2.png"));
			SmashAnimation[24] = ImageIO.read(NewView.class.getResource("/BassSprite/BassSmash2.png"));

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

				else if (int_random == 2 && !barrierActive) {
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

		}

		else {
			ExplodeThread.update();
			if (ExplodeThread.Activebullets.size() == 0)
				current = null;
		}
	}

	private void Smash() {
		// TODO Auto-generated method stub
		smashanimation++;
		current = SmashAnimation[smashanimation];

		if (smashanimation == 5) {
			if (!LockedOn) {
				LockedOn = true;
			}

		}

		else if (smashanimation == 15) {

			this.x = this.PLAYER_LOCATION_X - 30;
			this.y = this.PLAYER_LOCATION_Y - 35;
		}

		else if (smashanimation == 24) {
			BassProjectile projectile = new BassProjectile();
			projectile.OrbAttack();
			projectile.x = x;
			projectile.y = y;
			BassOrbThread.Activebullets.add(projectile);
			// In_Animation_Flag = false;
			this.state = State.TP_TO_RIGHT_SIDE;
			smashanimation = 0;
			start = elapsed;
			LockedOn = false;

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
		throwframe++;

		if (throwframe > 11) {

			this.state = State.STANDING;
			for (int i = 0; i < 1; i++) {
				BassProjectile projectile = new BassProjectile();
				// projectile.orientation = State.CLOCKWISE;
				projectile.WheelAttack();
				projectile.x = 900;
				projectile.y = 400;
				Activebullets.add(projectile);

			}
			In_Animation_Flag = false;
			throwframe = 0;
			start = elapsed;
		}

	}

	private void wheelsFlippedAnimation() { // TODO Auto-generated method stub

		current = ThrowBackAnimation[throwframe];
		throwframe++;

		if (throwframe > 11) {

			this.state = State.STANDING;
			for (int i = 0; i < 1; i++) {
				BassProjectile projectile = new BassProjectile();

				projectile.FlippedWheelAttack();
				projectile.x = 50;
				projectile.y = 400;
				Activebullets.add(projectile);

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
		pillarsummonframe++;
		current = PillarSummonAnimation[pillarsummonframe];

		if (pillarsummonframe == 21) {
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

}
