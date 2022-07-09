import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Elecman extends EnemyCharacter {

	BufferedImage current;
	BufferedImage healthbar;
	BufferedImage healthtick;
	BufferedImage healthtick2;
	BufferedImage idle;
	BufferedImage idleBack;
	BufferedImage elec;
	BufferedImage resize;
	BufferedImage[] ZapAnimation;
	BufferedImage[] ElecFormAnimation;
	int ZapAnimationFrame = 0;
	int ElecFormAnimationFrame = 0;
	State state;
	State direction;
	State action;
	long start;
	boolean In_Animation_Flag = false;
	boolean LockedOn = false;
	Explode ExplodeThread;
	ZapThread ZapThread;
	BassProjectile ExplosiveList[];
	BassProjectile ExplodeProjectile;
	float originalheight;
	long wait;
	long elapsed;
	Iterator itr;
	AffineTransform at = AffineTransform.getTranslateInstance(0, 250);
	float deltaTime;
	float prevTime = 0;
	float currentTime;

	public Elecman(int x, int y, int width, int height) {

		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		at.scale(3, 0.8);
		health = 40;
		health2 = 40;
		start = System.currentTimeMillis();
		state = State.STANDING;
		direction = State.LEFT;
		action = null;
		deltaX = 0;
		deltaY = 410;
		ZapThread = new ZapThread();

		ZapAnimation = new BufferedImage[5];
		ElecFormAnimation = new BufferedImage[4];
		ExplosiveList = new BassProjectile[10];
		for (int i = 0; i < 9; i++) {
			ExplodeProjectile = new BassProjectile();
			ExplodeProjectile.Explosion();
			ExplosiveList[i] = ExplodeProjectile;

		}

		try {
			elec = ImageIO.read(NewView.class.getResource("/Effects/CrossHorizontal1.png"));
			// resize = resize(elec, 500, 1);
			idle = ImageIO.read(NewView.class.getResource("/ElecmanSprite/idleBack0.png"));
			idleBack = ImageIO.read(NewView.class.getResource("/ElecmanSprite/idle0.png"));
			healthbar = ImageIO.read(NewView.class.getResource("/Effects/h.png"));
			healthtick = ImageIO.read(NewView.class.getResource("/Effects/bar.png"));
			healthtick2 = ImageIO.read(NewView.class.getResource("/Effects/bar2.png"));
			ZapAnimation[0] = ImageIO.read(NewView.class.getResource("/ElecmanSprite/ElecmanZap0.png"));
			ZapAnimation[1] = ImageIO.read(NewView.class.getResource("/ElecmanSprite/ElecmanZap0.png"));
			ZapAnimation[2] = ImageIO.read(NewView.class.getResource("/ElecmanSprite/ElecmanZap2.png"));
			ZapAnimation[3] = ImageIO.read(NewView.class.getResource("/ElecmanSprite/ElecmanZap2.png"));
			ZapAnimation[4] = ImageIO.read(NewView.class.getResource("/ElecmanSprite/ElecmanZap3.png"));

			ElecFormAnimation[0] = ImageIO.read(NewView.class.getResource("/ElecmanSprite/ElecForm1.png"));
			ElecFormAnimation[1] = ImageIO.read(NewView.class.getResource("/ElecmanSprite/ElecForm2.png"));
			ElecFormAnimation[2] = ImageIO.read(NewView.class.getResource("/ElecmanSprite/ElecForm3.png"));
			ElecFormAnimation[3] = ImageIO.read(NewView.class.getResource("/ElecmanSprite/ElecForm4.png"));
			current = idleBack;

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void spawnAnimation() {
		if (this.direction == State.LEFT) {
			current = idleBack;

		} else if (this.direction == State.RIGHT) {
			current = idleBack;

		}

		currentTime = System.nanoTime();
		deltaTime = (currentTime - prevTime) / 1000000000;
		if (prevTime == 0)
			prevTime = currentTime;
		else if (deltaTime > 1.5f) {
			prevTime = currentTime;
			state = State.ZAP;
		}

	}

	public void update() {

		if (isLive) {

			long targetTime = 1000 / 25;

			elapsed = System.currentTimeMillis();
			wait = targetTime / 1000000;

			if (health <= 0) {
				state = State.DEFEATED;
			}

			//
			//
			//
			/// Action Randomizer

			switch (state) {

			case DEFEATED:
				// defeatedAnimation();

				break;

			case STANDING:
				spawnAnimation();

				break;

			case ZAP:
				ZapAnimation();

				break;

			case ELEC_FORM:
				ElecFormAnimation();
				break;
			default:
				break;
			}

			if (ZapThread.Activebullets.size() > 0)
				ZapThread.update();
		}

		else {
			ExplodeThread.update();
			if (ExplodeThread.Activebullets.size() == 0)
				current = null;
		}

	}

	public void ElecFormAnimation() {
		if (ElecFormAnimationFrame > 3)
			ElecFormAnimationFrame = 0;
		this.width = current.getWidth();
		this.height = current.getHeight();
		current = ElecFormAnimation[ElecFormAnimationFrame];
		if (this.direction == State.LEFT) {
			x -= 6;

			currentTime = System.nanoTime();
			deltaTime = (currentTime - prevTime) / 1000000000;

			if (deltaTime > 0.03f) {
				y = (float) (400 * Math.sin(x / 45));
				if (y < 325)
					y = 325;
				if (y > 400)
					y = 400;
				prevTime = currentTime;
				ElecFormAnimationFrame++;
			}

			if (x < 40) {
				x = 10;
				y = 363;
				this.state = State.STANDING;
				this.direction = State.RIGHT;

				ElecFormAnimationFrame = 0;
				height = 100;
				width = 100;
			}
		} else if (this.direction == State.RIGHT) {
			x += 6;
			currentTime = System.nanoTime();
			deltaTime = (currentTime - prevTime) / 1000000000;

			if (deltaTime > 0.03f) {
				y = (float) (400 * Math.sin(x / 45));
				if (y < 325)
					y = 325;
				if (y > 400)
					y = 400;
				prevTime = currentTime;
				ElecFormAnimationFrame++;

			}
			if (x > 870) {
				y = 363;
				this.state = State.STANDING;
				this.direction = State.LEFT;
				ElecFormAnimationFrame = 0;
				height = 100;
				width = 100;
			}
		}

	}

	public void ZapAnimation() {
		current = ZapAnimation[ZapAnimationFrame];
		currentTime = System.nanoTime();
		deltaTime = (currentTime - prevTime) / 1000000000;
		if (deltaTime > 0.25f) {
			ZapAnimationFrame++;
			prevTime = currentTime;
		}
		if (ZapAnimationFrame == 3 && ZapThread.Activebullets.isEmpty()) {
			for (int i = 0; i < 1; i++) {
				ElecmanProjectile projectile = new ElecmanProjectile();
				projectile.direction = this.direction;
				projectile.x = 10;
				projectile.y = y + 15;
				projectile.StartTime = System.nanoTime();
				projectile.Zap();
				ZapThread.Activebullets.add(projectile);
				playSound("/music/zap.wav");

			}

		}
		if (ZapAnimationFrame == 5) {
			In_Animation_Flag = false;
			this.state = State.ELEC_FORM;
			ZapAnimationFrame = 0;
			start = elapsed;
		}

	}

	public void paint(Graphics g, Mega mega) {
		Graphics2D g2d = (Graphics2D) g.create();

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
		if (this.direction == State.RIGHT)
			g2d.drawImage(current, (int) x, (int) y, current.getWidth() + 30, current.getHeight() + 30, null);

		else if (this.direction == State.LEFT)
			g2d.drawImage(current, (int) x + width, (int) y, -current.getWidth() - 30, current.getHeight() + 30, null);

		// WHEEL INTERACTIONS
		//
		//

		if (ZapThread.Activebullets != null) {
			Rectangle MegaHitBox = mega.getBounds();

			itr = ZapThread.Activebullets.iterator();
			while (itr.hasNext()) {
				ElecmanProjectile projectile = (ElecmanProjectile) itr.next();
				Rectangle ShotHitBox = projectile.getBounds();
				System.out.println(ShotHitBox);
				// System.out.println(ShotHitBox.getY());

				if (projectile.direction == State.RIGHT)
					g2d.drawImage(projectile.current, (int) x + 60, (int) y + 31, projectile.xScale, projectile.yScale,
							null);

				else if (projectile.direction == State.LEFT)
					g2d.drawImage(projectile.current, (int) x + projectile.current.getWidth() - 135, (int) y + 31,
							-projectile.xScale, projectile.yScale, null);

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

		// at.rotate(-Math.toRadians(2), elec.getWidth() / 2, elec.getHeight() / 2);

		// g2d.drawImage(elec, at, null);

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
}
