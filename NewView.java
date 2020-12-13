
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import org.eclipse.swt.graphics.Point;

public class NewView {
	private final static String PRESSED = "pressed ";
	private final static String RELEASED = "released ";
	private final static Point RELEASED_POINT = new Point(0, 0);
	JFrame frame;
	TitlePanel titlePanel;
	CharSelectPanel charSelectPanel;
	JPanel ctrlPanel;
	GamePanel game;
	String Car = null;
	Thread gameThread;
	Thread titleThread;
	Thread characterSelectThread;
	Thread characterThread;
	Thread enemyThread;
	Thread projectileThread;
	Thread mthread;

	BufferedImage title;
	int i = 0;
	boolean titleflag = true;
	boolean charflag = true;
	boolean musicflag = true;
	String shootString;
	String rightString;
	String leftString;
	String chargeString;
	private Clip clip;
	Sound music;

	public static void main(String[] args) {
		new NewView();

	}

	public NewView() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					// set up frame + add title panel
					frame = new JFrame("Game");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					// frame.setSize(600, 600);
					// frame.setBounds(100, 100, 1000, 500);
					frame.setBounds(150, 150, 530, 366);
					frame.setLocationRelativeTo(null);
					frame.setResizable(false);
					titlePanel = new TitlePanel();
					titleThread = new Thread(titlePanel);
					titleThread.start();
					frame.add(titlePanel);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	class TitlePanel extends JPanel implements Runnable, KeyListener {

		int time = 0;

		public TitlePanel() {

			music = new Sound("/music/MegaTitle.wav");
			mthread = new Thread(music);
			mthread.start();

			frame.setFocusable(true);
			frame.requestFocus();
			frame.addKeyListener(this);

			this.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.setLayout(null);

			try {

				title = ImageIO.read(NewView.class.getResource("/Background/Title2.jpg"));

			} catch (IOException e) {
				e.printStackTrace();

			}

		}

		public void paint(Graphics g) {

			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;

			g2d.drawImage(title, 0, 0, 530, 366, null);

			if (time % 2 == 0) {
				g2d.setColor(Color.yellow);
				Font myFont = new Font("Serif", Font.BOLD, 30);
				g2d.setFont(myFont);
				;
				g2d.drawString("Press Enter", 200, 250);

			} else {
				g2d.drawImage(title, 0, 0, 530, 366, null);
			}

		}

		public void run() {
			while (titleflag) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				time++;
				this.repaint();

			}
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
				playSound("/music/select.wav");
				titlePanel.setVisible(false);
				charSelectPanel = new CharSelectPanel();
				characterSelectThread = new Thread(charSelectPanel);
				characterSelectThread.start();
				charSelectPanel.go();
				frame.add(charSelectPanel);
				frame.removeKeyListener(this);
				// music.stop();
				// musicflag = false;
				titleflag = false;
			}

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	class CharSelectPanel extends JPanel implements KeyListener, Runnable {

		Color darkGreen = new Color(28, 89, 71);
		Color black = new Color(0, 0, 0);
		Color yellow = new Color(225, 225, 0);
		BufferedImage CharSelect;
		BufferedImage ZeroSelect;
		BufferedImage XSelect;
		BufferedImage currentSelector;
		BufferedImage SelectorLeft;
		BufferedImage SelectorRight;
		int SelectorXpos = 50;
		int SelectorYpos = 0;

		public void go() {
			try {
				CharSelect = ImageIO.read(NewView.class.getResource("/Background/CharSelect.png"));
				ZeroSelect = ImageIO.read(NewView.class.getResource("/Effects/ZeroSelect.png"));
				XSelect = ImageIO.read(NewView.class.getResource("/Effects/XSelect.png"));
				SelectorLeft = ImageIO.read(NewView.class.getResource("/Effects/SelectorLeft.png"));
				SelectorRight = ImageIO.read(NewView.class.getResource("/Effects/SelectorRight.png"));
				currentSelector = SelectorLeft;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			music.stop();
			music = new Sound("/music/Selection.wav");

			frame.setFocusable(true);
			frame.requestFocus();
			frame.addKeyListener(this);
			frame.setSize(600, 600);
			frame.setBounds(100, 100, 1000, 500);
			// frame.setBounds(100, 100, 800, 800);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			charSelectPanel.setBounds(100, 100, 555, 555);
			frame.setResizable(false);
			charSelectPanel.setLayout(null);

			frame.add(charSelectPanel);
		}

		public void paint(Graphics g) {

			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(CharSelect, 0, 0, 1000, 500, null);
			g2d.drawImage(XSelect, 50, 0, 500, 470, null);
			g2d.drawImage(ZeroSelect, 440, 0, 500, 470, null);
			g2d.drawImage(currentSelector, SelectorXpos, SelectorYpos, 500, 470, null);
		}

		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
				playSound("/music/move.wav");
				SelectorXpos = 440;
				currentSelector = SelectorRight;

			}

			if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
				playSound("/music/move.wav");
				SelectorXpos = 50;
				currentSelector = SelectorLeft;

			}

			if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
				playSound("/music/select.wav");
				charSelectPanel.setVisible(false);
				frame.removeKeyListener(this);
				charflag = false;
				game = new GamePanel();
				gameThread = new Thread(game);
				gameThread.start();
				frame.add(game);

			}

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (charflag) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.repaint();

			}
		}

	}

	class GamePanel extends JPanel implements ActionListener, Runnable {

		Color darkGreen = new Color(28, 89, 71);
		Color black = new Color(0, 0, 0);
		Color yellow = new Color(225, 225, 0);
		Mega mega;
		Bass bass;
		ProjectileThread ProjectileThread = new ProjectileThread();
		Background bg;
		InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getActionMap();
		private Map<String, Point> pressedKeys = new HashMap<String, Point>();
		Timer timer;
		Iterator itr;

		public GamePanel() {

			music.stop();
			music = new Sound("/music/SurgeOfPower.wav");

			timer = new Timer(24, this);
			frame.setResizable(false);
			timer.setInitialDelay(0);

			mega = new Mega(0, 0, 30, 30);
			bass = new Bass(800, 360, 70, 100);

			enemyThread = new Thread(bass);
			enemyThread.start();

			characterThread = new Thread(mega);
			characterThread.start();

			projectileThread = new Thread(ProjectileThread);
			projectileThread.start();

			bg = new Background("/Background/cave.jpg");
			this.addAction("LEFT", 0, 0);
			this.addAction("RIGHT", 0, 0);
			this.addAction("UP", 0, 0);
			this.addAction("DOWN", 0, 5);
			this.addAction("X", 0, 0);
			this.addAction("Q", 0, 0);
			this.addAction("Z", 0, 0);

			frame.setSize(600, 600);
			frame.setBounds(100, 100, 1000, 500);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}

		public void paintComponent(Graphics g) {

			long start = System.nanoTime();
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setColor(yellow);
			g2d.drawImage(bg.image, 0, 0, 1000, 500, null);
			g2d.drawString("Shoot: " + shootString, 100, 100);
			g2d.drawString("Left: " + leftString, 100, 110);
			g2d.drawString("Right: " + rightString, 100, 120);
			g2d.drawString("Charge: " + chargeString, 100, 130);
			g2d.drawImage(mega.healthbar, 10, 150, 25, 130, null);
			g2d.drawImage(bass.healthbar, 925, 0, 30, 480, null);
			for (int healthstack = 0; healthstack < mega.health; healthstack++) {

				int ypos = 240 - (healthstack * 6);
				int xpos = 14;
				g2d.drawImage(mega.healthtick, xpos, ypos, 15, 7, null);

			}
			for (int healthstack = 0; healthstack < bass.health; healthstack++) {

				int ypos = 360 - (healthstack * 4);
				int xpos = 932;
				g2d.drawImage(bass.healthtick, xpos, ypos, 13, 4, null);

			}

			g2d.drawImage(bass.effects, (int) bass.x - 13, (int) bass.y - 25, 120, 120, null);
			g2d.drawImage(bass.current, (int) bass.x, (int) bass.y, 100, 100, null);

			g2d.drawImage(mega.current, (int) mega.deltaX, (int) mega.deltaY, 50, 50, null);
			g2d.drawImage(mega.effects, (int) mega.x, (int) mega.y, 50, 50, null);

			try {

				if (ProjectileThread.Activebullets != null) {
					Rectangle BassHitBox = bass.getBounds();

					itr = ProjectileThread.Activebullets.iterator();
					while (itr.hasNext()) {
						Projectile projectile = (Projectile) itr.next();
						Rectangle ShotHitBox = projectile.getBounds();
						g2d.drawImage(projectile.image, (int) projectile.x + 30, (int) projectile.y + 10,
								projectile.xScale, projectile.yScale, null);

						if (ShotHitBox.intersects(BassHitBox)) {
							if (bass.barrierActive) {
								if (bass.barrierhealth - projectile.damage < 0) {
									bass.barrierhealth = 0;
									bass.barrierActive = false;
									bass.effects = null;

								} else
									bass.barrierhealth -= projectile.damage;
							} else {
								bass.health -= projectile.damage;
								if (bass.health <= 0) {
									bass.current = bass.defeated;
									bass.setDestroyed();
								}
							}
							itr.remove();
						}

					}
				}

				if (bass.BassWheelsThread.Activebullets != null) {
					Rectangle MegaHitBox = mega.getBounds();

					itr = bass.BassWheelsThread.Activebullets.iterator();
					while (itr.hasNext()) {
						BassProjectile projectile = (BassProjectile) itr.next();
						Rectangle ShotHitBox = projectile.getBounds();
///
						g2d.drawImage(projectile.wheels[4], (int) projectile.x, (int) projectile.y, projectile.xScale,
								projectile.yScale, null);

						if (ShotHitBox.intersects(MegaHitBox)) {
							mega.health -= projectile.damage;

							itr.remove();

							if (mega.health <= 0)
								mega.setDestroyed();

						}

					}
				}

				if (bass.BassPillarsThread.Activebullets != null) {
					Rectangle MegaHitBox = mega.getBounds();

					itr = bass.BassPillarsThread.Activebullets.iterator();
					while (itr.hasNext()) {
						BassProjectile projectile = (BassProjectile) itr.next();
						Rectangle ShotHitBox = projectile.getBounds();
///
						g2d.drawImage(projectile.current, (int) projectile.x, (int) projectile.y, projectile.xScale,
								projectile.yScale, null);

						if (ShotHitBox.intersects(MegaHitBox) && projectile.pillarframe > 0
								&& projectile.pillarframe != 4) {
							mega.health -= projectile.damage;

							if (mega.health <= 0)
								mega.setDestroyed();

						}

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			g2d.dispose();

		}

		public void addAction(String keyStroke, int deltaX, int deltaY) {
			// InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

			String pressedKey = PRESSED + keyStroke;
			KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(pressedKey);
			if (keyStroke == "Z") {
				JumpAnimationAction pressedAction = new JumpAnimationAction(keyStroke, new Point(deltaX, deltaY));
				im.put(pressedKeyStroke, pressedKey);
				am.put(pressedKey, pressedAction);
			} else {
				PressAnimationAction pressedAction = new PressAnimationAction(keyStroke, new Point(deltaX, deltaY));
				im.put(pressedKeyStroke, pressedKey);
				am.put(pressedKey, pressedAction);
			}

			String releasedKey = RELEASED + keyStroke;
			KeyStroke releasedKeyStroke = KeyStroke.getKeyStroke(releasedKey);
			ReleaseAnimationAction releasedAction = new ReleaseAnimationAction(keyStroke, RELEASED_POINT);
			im.put(releasedKeyStroke, releasedKey);
			am.put(releasedKey, releasedAction);
		}

		private class PressAnimationAction extends AbstractAction implements ActionListener {
			private Point moveDelta;

			public PressAnimationAction(String keyStroke, Point moveDelta) {
				super(PRESSED + keyStroke);
				putValue(ACTION_COMMAND_KEY, keyStroke);

				this.moveDelta = moveDelta;
			}

			public void actionPerformed(ActionEvent e) {
				PresshandleKeyEvent((String) getValue(ACTION_COMMAND_KEY), moveDelta);
			}
		}

		private class JumpAnimationAction extends AbstractAction implements ActionListener {
			private Point moveDelta;

			public JumpAnimationAction(String keyStroke, Point moveDelta) {
				super(PRESSED + keyStroke);
				putValue(ACTION_COMMAND_KEY, keyStroke);

				this.moveDelta = moveDelta;
			}

			public void actionPerformed(ActionEvent e) {
				JumphandleKeyEvent((String) getValue(ACTION_COMMAND_KEY), moveDelta);
			}
		}

		private class ReleaseAnimationAction extends AbstractAction implements ActionListener {
			private Point moveDelta;

			public ReleaseAnimationAction(String keyStroke, Point moveDelta) {
				super(PRESSED + keyStroke);
				putValue(ACTION_COMMAND_KEY, keyStroke);

				this.moveDelta = moveDelta;
			}

			public void actionPerformed(ActionEvent e) {
				ReleasehandleKeyEvent((String) getValue(ACTION_COMMAND_KEY), moveDelta);
			}
		}

		private void PresshandleKeyEvent(String keyStroke, Point moveDelta) {
			// Keep track of which keys are pressed

			if (mega.state != State.SPAWNING) {
				switch (keyStroke) {
				case "LEFT":
					mega.action = State.LEFTWALK;
					mega.direction = State.LEFT;
					mega.leftwalk = true;
					mega.rightwalk = false;
					break;
				case "RIGHT":
					mega.action = State.RIGHTWALK;
					mega.direction = State.RIGHT;
					mega.leftwalk = false;
					mega.rightwalk = true;
					break;

				case "X":

					if (mega.CHARGEABLE) {
						mega.charge = true;
						if (mega.chargeTimer == 0) {
							mega.chargeTimer = System.nanoTime();
						}
						break;

					}

					else if (mega.shoot == false && mega.charge == false) {
						mega.shoot = true;
						mega.CHARGEABLE = true;
						mega.ChargeStartTime = System.nanoTime();
						Projectile projectile = new Projectile();

						projectile.time = System.nanoTime();
						projectile.x = mega.x;
						projectile.y = mega.y;
						if (mega.direction == State.LEFT)
							projectile.direction = State.LEFT;
						else if (mega.direction == State.RIGHT)
							projectile.direction = State.RIGHT;
						ProjectileThread.Waitingbullets.add(projectile);
						break;
					}
					break;

				case "Q":
					break;
				}

			}

			if (RELEASED_POINT == moveDelta)
				pressedKeys.remove(keyStroke);

			else
				pressedKeys.put(keyStroke, moveDelta);

			// Start the Timer when the first key is pressed

			if ((pressedKeys.size() >= 1)) {

				timer.start();
			}

			// Stop the Timer when all keys have been released

		}

		private void JumphandleKeyEvent(String keyStroke, Point moveDelta) {
			// Keep track of which keys are pressed

			if (RELEASED_POINT == moveDelta)
				pressedKeys.remove(keyStroke);

			else
				pressedKeys.put(keyStroke, moveDelta);

			// Start the Timer when the first key is pressed

			if ((pressedKeys.size() >= 1) && mega.air == false) {
				if (mega.state != State.SPAWNING) {
					mega.state = State.JUMPING;
					mega.originalheight = mega.y;
					mega.airtime = System.nanoTime();
					mega.air = true;
					timer.start();
				}
			}

			// Stop the Timer when all keys have been released

		}

		private void ReleasehandleKeyEvent(String keyStroke, Point moveDelta) {
			// Keep track of which keys are pressed

			switch (keyStroke) {
			case "LEFT":
				mega.leftwalk = false;
				if (mega.state == State.STANDING) {
					mega.current = mega.idleBack;
				}
				break;
			case "RIGHT":
				mega.rightwalk = false;
				if (mega.state == State.STANDING) {
					mega.current = mega.idle;
				} // mega.action=State.STILL; //pressedKeys.remove("LEFT");
				break;

			case "X": /*
						 * if(mega.direction==State.LEFT) { mega.current=mega.idleBack;}
						 * if(mega.direction==State.RIGHT) { mega.current=mega.idle;}
						 */
				// mega.shoot = false;
				mega.effects = null;
				mega.chargeCount = 0;
				mega.shootframe = 0;

				if (!mega.minChargeReached) {
					mega.charge = false;
					mega.chargeTimer = 0;
				}

				else if (mega.charge && mega.minChargeReached) {
					mega.ChargeStartTime = 0;
					mega.minChargeReached = false;
					mega.CHARGEABLE = false;
					mega.shoot = true;
					// mega.shoot = false;
					mega.charge = false;

					Projectile projectile = new Projectile();
					projectile.time = System.nanoTime();
					projectile.x = mega.x;
					projectile.y = mega.y - 10;
					double currentTime = System.nanoTime();
					double time = (currentTime - mega.chargeTimer) / 100000000.0;
					/*
					 * System.out.print(currentTime + "-" + mega.chargeTimer + "=");
					 * 
					 * System.out.println(time);
					 */
					if (time < 6.5 && time > 4) {
						projectile.SemiChargeShot();

					} else if (time > 6.5) {
						projectile.FullChargeShot();

					}
					if (mega.direction == State.LEFT) {
						projectile.direction = State.LEFT;

						if (time < 6.5 && time > 4)
							projectile.SemiChargeShotFlip();
						else if (time > 6.5)
							projectile.FullChargeShotFlip();
					} else if (mega.direction == State.RIGHT)
						projectile.direction = State.RIGHT;

					ProjectileThread.Activebullets.add(projectile);

				}
				/*
				 * Projectile projectile=new Projectile(); projectile.FullChargeShot();
				 * projectile.time=System.nanoTime(); projectile.x = mega.x; projectile.y =
				 * mega.y; if(mega.direction==State.LEFT) projectile.direction=State.LEFT; else
				 * if(mega.direction==State.RIGHT) projectile.direction=State.RIGHT;
				 * ProjectileThread.Activebullets.add(projectile);
				 */
				mega.chargeTimer = 0;
				mega.shoot = false;
				break;

			case "Z":
				break;
			case "Q":
				mega.deltaX = 400;
				mega.deltaY = 410;
				mega.health--;
				break;

			}

			if (RELEASED_POINT == moveDelta)
				pressedKeys.remove(keyStroke);
			else
				pressedKeys.put(keyStroke, moveDelta);

			// Start the Timer when the first key is pressed

			// Stop the Timer when all keys have been released

			if (pressedKeys.size() == 0) {
				// if(keyStroke=="Z") {mega.air=false;}
				// mega.air=false;
				timer.stop();
			}
		}

		// Invoked when the Timer fires

		public void actionPerformed(ActionEvent e) {

		}

		// Move the component to its new location

		public void run() {

			long start;
			long fps = 30;
			long targetTime = 1000 / fps;
			long elapsed;
			long wait;

			while (true) {

				if (mega.air == false)
					mega.setLocation(mega.deltaX, mega.deltaY);

				if (mega.state != State.SPAWNING) {
					mega.setLocation(mega.deltaX, mega.deltaY);
				}

				start = System.nanoTime();
				elapsed = System.nanoTime() - start;
				wait = targetTime - elapsed / 1000000;
				if (wait < 0)
					wait = 15;

				try {
					Thread.sleep(wait);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block

					e.printStackTrace();
				}
				if (mega.shoot)
					shootString = "true";
				else
					shootString = "false";

				if (mega.leftwalk)
					leftString = "true";
				else
					leftString = "false";

				if (mega.rightwalk)
					rightString = "true";
				else
					rightString = "false";

				if (mega.charge)
					chargeString = "true";
				else
					chargeString = "false";
				this.repaint();

			}

		}

	}

	public class Sound implements Runnable {

		public Sound(String fileName) {
			// specify the sound to play
			// (assuming the sound can be played by the audio system)
			// from a wave File

			try {

				AudioInputStream sound = AudioSystem.getAudioInputStream(NewView.class.getResource(fileName));

				// load the sound into memory (a Clip)
				clip = AudioSystem.getClip();
				clip.open(sound);

			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Sound: Malformed URL: " + e);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
				throw new RuntimeException("Sound: Unsupported Audio File: " + e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Sound: Input/Output Error: " + e);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
				throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
			}

			// play, stop, loop the sound clip
		}

		public void play() {
			clip.setFramePosition(0); // Must always rewind!
			clip.start();
		}

		public void loop() {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}

		public void stop() {

			clip.stop();
			clip.close();
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			play();
			while (musicflag) {

				loop();
			}
			stop();
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

}
