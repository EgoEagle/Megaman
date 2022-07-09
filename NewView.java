
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
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
	String shootString;
	String rightString;
	String leftString;
	String chargeString;

	JFrame frame;
	TitlePanel titlePanel;
	CharSelectPanel charSelectPanel;
	JPanel ctrlPanel;
	GamePanel game;

	Thread gameThread;
	Thread titleThread;
	Thread characterSelectThread;
	Thread characterThread;
	Thread MusicThread;

	BufferedImage title;
	BufferedImage Controls;
	int i = 0;
	boolean titleflag = true;
	boolean charflag = true;
	boolean musicflag = true;
	private Clip clip;
	Sound music;
	boolean LOADED = false;
	private Graphics2D g;

	boolean ElecMan = false;
	boolean Bass = false;
	String BossKey = "";

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
					frame.setBounds(150, 150, 530, 366);
					// frame.pack();
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
		boolean CONTROL_PRESSED = false;
		boolean ENTER_PRESSED = false;
		boolean START_GAME_HOVERED = true;

		public TitlePanel() {

			music = new Sound("/music/MegaTitle.wav");
			MusicThread = new Thread(music);
			MusicThread.start();

			frame.setFocusable(true);
			frame.requestFocus();
			frame.addKeyListener(this);

			this.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.setLayout(null);

			try {
				Controls = ImageIO.read(NewView.class.getResource("/Background/Controls.png"));
				title = ImageIO.read(NewView.class.getResource("/Background/Title2.jpg"));

			} catch (IOException e) {
				e.printStackTrace();

			}

		}

		public void paint(Graphics g) {

			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;

			g2d.drawImage(title, 0, 0, 530, 366, null);

			if (ENTER_PRESSED) {
				Font myFont = new Font("Serif", Font.BOLD, 20);
				g2d.setFont(myFont);

				if (START_GAME_HOVERED && !CONTROL_PRESSED) {
					g2d.setColor(Color.yellow);
					g2d.drawString("START GAME", 200, 250);
					g2d.setColor(Color.gray);
					g2d.drawString("CONTROLS", 200, 270);

				} else {
					g2d.setColor(Color.gray);
					g2d.drawString("START GAME", 200, 250);
					g2d.setColor(Color.yellow);
					g2d.drawString("CONTROLS", 200, 270);
				}

				if (CONTROL_PRESSED) {
					g2d.setComposite(AlphaComposite.SrcOver.derive(0.8f));
					g2d.drawImage(Controls, 5, 0, 500, 300, null);
					g2d.setComposite(AlphaComposite.SrcOver.derive(1f));
				}

			}

			else if (!ENTER_PRESSED) {
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
		}

		public void run() {
			while (titleflag) {
				int wait = 200;
				if (ENTER_PRESSED)
					wait = 20;
				try {

					Thread.sleep(wait);

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

				if (ENTER_PRESSED && START_GAME_HOVERED && !CONTROL_PRESSED) {
					playSound("/music/select.wav");
					titlePanel.setVisible(false);
					charSelectPanel = new CharSelectPanel();
					characterSelectThread = new Thread(charSelectPanel);
					characterSelectThread.start();
					charSelectPanel.go();
					frame.add(charSelectPanel);
					frame.removeKeyListener(this);
					// titleflag = false;

				}

				else if (ENTER_PRESSED && !START_GAME_HOVERED && !CONTROL_PRESSED) {
					playSound("/music/select.wav");
					CONTROL_PRESSED = true;
				}

				else if (!CONTROL_PRESSED) {
					playSound("/music/select.wav");
					ENTER_PRESSED = true;
				}

			}

			if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
				if (CONTROL_PRESSED)
					CONTROL_PRESSED = false;
				else if (ENTER_PRESSED)
					ENTER_PRESSED = false;
			}

			if (arg0.getKeyCode() == KeyEvent.VK_UP) {
				if (ENTER_PRESSED && !CONTROL_PRESSED)
					playSound("/music/move.wav");
				START_GAME_HOVERED = true;

			}

			if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
				if (ENTER_PRESSED && !CONTROL_PRESSED)
					playSound("/music/move.wav");
				START_GAME_HOVERED = false;

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
		BufferedImage Selector_Frame;
		BufferedImage Boss_Selection_Frame;
		BufferedImage Bass_Icon;
		BufferedImage Elecman_Icon;
		boolean CHARACTER_SELECTED = false;
		int SelectorXpos = 50;
		int SelectorYpos = 0;
		int Selector_Index_X = 1;
		int Selector_Index_Y = 1;
		int Selector_Index = 11;

		public void go() {
			try {
				CharSelect = ImageIO.read(NewView.class.getResource("/Background/CharSelect.png"));
				ZeroSelect = ImageIO.read(NewView.class.getResource("/Effects/ZeroSelect.png"));
				XSelect = ImageIO.read(NewView.class.getResource("/Effects/XSelect.png"));
				SelectorLeft = ImageIO.read(NewView.class.getResource("/Effects/SelectorLeft.png"));
				SelectorRight = ImageIO.read(NewView.class.getResource("/Effects/SelectorRight.png"));
				Selector_Frame = ImageIO.read(NewView.class.getResource("/Effects/Selector_Frame.png"));
				Boss_Selection_Frame = ImageIO.read(NewView.class.getResource("/Effects/Boss_Selection_Frame.png"));
				Bass_Icon = ImageIO.read(NewView.class.getResource("/Effects/Bass.png"));
				Elecman_Icon = ImageIO.read(NewView.class.getResource("/Effects/Elecman.png"));
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

			if (!CHARACTER_SELECTED) {
				g2d.drawImage(XSelect, 50, 0, 500, 470, null);
				g2d.drawImage(ZeroSelect, 440, 0, 500, 470, null);
				g2d.drawImage(currentSelector, SelectorXpos, SelectorYpos, 500, 470, null);
			}

			else if (CHARACTER_SELECTED) {
				// g2d.drawImage(Selector_Frame, 259, 240, 100, 95, null);
				// g2d.drawImage(Selector_Frame, 259, 137, 100, 95, null);

				g2d.drawImage(Elecman_Icon, 250, 26, 118, 108, null);
				g2d.drawImage(Bass_Icon, 589, 335, 118, 113, null);
				g2d.drawImage(Boss_Selection_Frame, 230, 0, 500, 470, null);

				switch (Selector_Index) {
				case 11:
					g2d.drawImage(Selector_Frame, 259, 34, 100, 95, null);
					break;

				case 21:
					g2d.drawImage(Selector_Frame, 259, 137, 100, 95, null);
					break;

				case 31:
					g2d.drawImage(Selector_Frame, 259, 240, 100, 95, null);
					break;

				case 41:
					g2d.drawImage(Selector_Frame, 259, 343, 100, 95, null);
					break;

				case 12:
					g2d.drawImage(Selector_Frame, 372, 34, 100, 95, null);
					break;
				case 42:
					g2d.drawImage(Selector_Frame, 372, 343, 100, 95, null);

					break;
				case 13:
					g2d.drawImage(Selector_Frame, 485, 34, 100, 95, null);
					break;
				case 43:
					g2d.drawImage(Selector_Frame, 485, 343, 100, 95, null);
					break;

				case 14:
					g2d.drawImage(Selector_Frame, 598, 34, 100, 95, null);
					break;
				case 24:
					g2d.drawImage(Selector_Frame, 598, 137, 100, 95, null);
					break;
				case 34:
					g2d.drawImage(Selector_Frame, 598, 240, 100, 95, null);
					break;
				case 44:
					g2d.drawImage(Selector_Frame, 598, 343, 100, 95, null);
					break;

				}
			}
		}

		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub

			if (!CHARACTER_SELECTED) {

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
					CHARACTER_SELECTED = true;

				}

			}

			else if (CHARACTER_SELECTED) {

				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
					CHARACTER_SELECTED = false;
				}

				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					switch (Selector_Index) {
					case 11:
						ElecMan = true;
						playSound("/music/select.wav");
						frame.removeKeyListener(this);
						charSelectPanel.setVisible(false);
						game = new GamePanel();
						gameThread = new Thread(game);
						// charflag = false;
						gameThread.start();
						frame.add(game);
						break;

					case 21:

						break;

					case 31:

						break;

					case 41:

						break;

					case 12:

						break;
					case 42:

						break;
					case 13:

						break;
					case 43:

						break;

					case 14:

						break;
					case 24:

						break;
					case 34:

						break;
					case 44:
						Bass = true;
						playSound("/music/select.wav");
						frame.removeKeyListener(this);
						charSelectPanel.setVisible(false);
						game = new GamePanel();
						gameThread = new Thread(game);
						// charflag = false;
						gameThread.start();
						frame.add(game);
						break;

					}

				}

				if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					playSound("/music/move.wav");
					if (Selector_Index == 12 || Selector_Index == 13) {
						Selector_Index += 30;
					}

					else if (Selector_Index != 41 || Selector_Index != 44)
						if (Selector_Index < 40)
							Selector_Index += 10;
				}

				if (arg0.getKeyCode() == KeyEvent.VK_UP) {
					playSound("/music/move.wav");
					if (Selector_Index == 42 || Selector_Index == 43) {
						Selector_Index -= 30;
					} else if (Selector_Index != 11 || Selector_Index != 14)
						if (Selector_Index > 15)
							Selector_Index -= 10;
				}
				if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
					playSound("/music/move.wav");
					if (Selector_Index == 34 || Selector_Index == 24) {
						Selector_Index -= 3;
					} else if (Selector_Index == 12 || Selector_Index == 13 || Selector_Index == 14
							|| Selector_Index == 42 || Selector_Index == 43 || Selector_Index == 44)
						Selector_Index--;
				}
				if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
					playSound("/music/move.wav");
					if (Selector_Index == 21 || Selector_Index == 31) {
						Selector_Index += 3;
					} else if (Selector_Index == 12 || Selector_Index == 13 || Selector_Index == 11
							|| Selector_Index == 42 || Selector_Index == 43 || Selector_Index == 41)
						Selector_Index++;

				}

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
		// JPanel load= new JPanel();

		Color yellow = new Color(225, 225, 0);
		Mega mega;
		EnemyCharacter ec;
		ArrayList al = new ArrayList();
		Background bg;
		Background border;
		InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getActionMap();
		private Map<String, Point> pressedKeys = new HashMap<String, Point>();
		public HashMap<String, EnemyCharacter> BossMap = new HashMap<String, EnemyCharacter>();
		int StageWidth;
		int StageHeight;

		Timer timer;
		Iterator itr;
		boolean PAUSE = false;
		boolean running = false;

		public GamePanel() {
			// al.add(bass);
			// al.add(elecman);
			/*
			 * if (BossMap.containsKey(BossKey)) BossMap.get(BossKey) = new
			 * BossMap.get(BossKey);
			 */

			if (Bass) {
				BossKey = "Bass";
				ec = new Bass(800, 360, 70, 100);
				BossMap.put("Bass", ec);
				bg = new Background("/Background/cave.jpg");
				StageWidth = 1000;
				StageHeight = 500;

			}
			if (ElecMan) {
				BossKey = "Elecman";
				ec = new Elecman(870, 360, 100, 100);
				BossMap.put("Elecman", ec);
				bg = new Background("/Background/Stage6.png");
				StageWidth = 1000;
				StageHeight = 500;
			}
			// frame.add(load);
			// load.setVisible(true);
			running = true;
			music.stop();
			music = new Sound("/music/SurgeOfPower.wav");

			timer = new Timer(24, this);
			frame.setResizable(false);
			timer.setInitialDelay(0);

			mega = new Mega(0, 0, 30, 30);
			border = new Background("/Background/Border1.png");

			this.addAction("LEFT", 0, 0);
			this.addAction("RIGHT", 0, 0);
			this.addAction("UP", 0, 0);
			this.addAction("DOWN", 0, 5);
			this.addAction("X", 0, 0);
			this.addAction("Q", 0, 0);
			this.addAction("Z", 0, 0);
			this.addAction("SPACE", 0, 0);
			this.addAction("ESCAPE", 0, 0);

			frame.setSize(600, 600);
			frame.setBounds(100, 100, StageWidth, StageHeight);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}

		public void paint(Graphics g) {

			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setColor(yellow);

			if (!LOADED) {
				g2d.drawImage(title, 0, 0, 1000, 500, null);
			}

			else if (LOADED) {
				// background
				g2d.drawImage(bg.image, 0, 0, 1000, 500, null);
				g2d.drawImage(border.image, -10, -5, 1000, 500, null);

				// Debugger
				g2d.drawString("Shoot: " + shootString, 100, 100);
				g2d.drawString("Left: " + leftString, 100, 110);
				g2d.drawString("Right: " + rightString, 100, 120);
				g2d.drawString("Charge: " + chargeString, 100, 130);

				g2d.drawImage(mega.healthbar, 10, 150, 25, 130, null);
				for (int healthstack = 0; healthstack < mega.health; healthstack++) {

					int ypos = 240 - (healthstack * 6);
					int xpos = 14;
					g2d.drawImage(mega.healthtick, xpos, ypos, 15, 7, null);

				}

				try {
					//
					//
					//
					// Megaman Projectile Collision Check
					if (mega.isLive) {
						mega.paint(g, BossMap, BossKey);
					}

					ec.paint(g, mega);

					if (!mega.invincible) {
						g2d.drawImage(mega.current, (int) mega.deltaX, (int) mega.deltaY, 50, 50, null);
					} else {
						g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
						g2d.drawImage(mega.current, (int) mega.deltaX, (int) mega.deltaY, 50, 50, null);
						g2d.setComposite(AlphaComposite.SrcOver.derive(1f));

					}
					g2d.drawImage(mega.effects, (int) mega.x, (int) mega.y, 50, 50, null);
				} catch (Exception e) {
					e.printStackTrace();
				}

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
						if (mega.chargeTimer == 0) {
							mega.chargeTimer = System.nanoTime();

						}
						break;
					}

					/*
					 * else if (mega.CHARGEABLE == false && !mega.shoot) { mega.shootframe = 0;
					 * mega.shoot = true; mega.CHARGEABLE = true;
					 * 
					 * // Projectile Generation Projectile projectile = new Projectile();
					 * projectile.time = System.nanoTime(); projectile.x = mega.x; projectile.y =
					 * mega.y; if (mega.direction == State.LEFT) projectile.direction = State.LEFT;
					 * else if (mega.direction == State.RIGHT) projectile.direction = State.RIGHT;
					 * mega.playSound("/music/0- Buster.wav");
					 * mega.ProjectileThread.Activebullets.add(projectile); break; }
					 */

				case "Q":
					break;

				case "SPACE":
					if (mega.state == State.STANDING) {
						mega.state = State.SLIDE;
					}
					break;

				case "ESCAPE":
					if (PAUSE)
						PAUSE = false;
					else
						PAUSE = true;

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
			if (!PAUSE) {
				if ((pressedKeys.size() >= 1) && mega.air == false) {

					if (mega.state != State.SPAWNING) {
						if (mega.WallHop == false) {
							mega.state = State.JUMPING;
							mega.originalheight = mega.y;
							mega.airtime = System.nanoTime();
							mega.air = true;
							mega.JumpRelease = false;
						}
						// timer.start();
					}
				}

				// Stop the Timer when all keys have been released

			}
		}

		private void ReleasehandleKeyEvent(String keyStroke, Point moveDelta) {
			// Keep track of which keys are pressed

			switch (keyStroke) {
			case "LEFT":
				mega.leftwalk = false;
				mega.walkframe = 0;
				if (mega.state == State.STANDING) {
					mega.current = mega.idleBack;
				}
				break;
			case "RIGHT":
				mega.rightwalk = false;
				mega.walkframe = 0;

				if (mega.state == State.STANDING) {
					mega.current = mega.idle;
				}
				break;

			case "X":

				mega.chargeCount = 0;
				mega.shootframe = 0;
				mega.playedOnce = false;
				if (mega.CHARGEABLE && mega.minChargeReached) {

					mega.shoot = true;

					Projectile projectile = new Projectile();
					projectile.time = System.nanoTime();
					projectile.x = mega.x;
					projectile.y = mega.y - 10;
					double currentTime = System.nanoTime();
					double time = (currentTime - mega.chargeTimer) / 1000000000;

					if (time < 1.2 && time > 0.8) {
						projectile.SemiChargeShot();
						projectile.y = mega.y;
					} else if (time > 1.2) {

						projectile.FullChargeShot();

					}
					if (mega.direction == State.LEFT) {
						projectile.direction = State.LEFT;

						if (time < 1.2 && time > 0.8)
							projectile.SemiChargeShotFlip();
						else if (time > 1.2) {

							projectile.FullChargeShotFlip();
						}
					} else if (mega.direction == State.RIGHT) {
						projectile.direction = State.RIGHT;
					}
					mega.playSound("/music/0- Buster.wav");
					mega.ProjectileThread.Activebullets.add(projectile);

				}

				else if (!mega.minChargeReached && !mega.shoot) {

					mega.shoot = true;
					Projectile projectile = new Projectile();

					projectile.time = System.nanoTime();
					projectile.x = mega.x;
					projectile.y = mega.y;
					if (mega.direction == State.LEFT)
						projectile.direction = State.LEFT;
					else if (mega.direction == State.RIGHT)
						projectile.direction = State.RIGHT;
					mega.playSound("/music/0- Buster.wav");
					mega.ProjectileThread.Activebullets.add(projectile);

				}
				mega.effects = null;
				mega.chargeTimer = 0;
				mega.minChargeReached = false;
				mega.CHARGEABLE = true;
				break;

			case "Z":
				mega.JumpRelease = true;
				mega.WallHop = false;
				break;
			case "Q":
				mega.deltaX = 50;
				mega.deltaY = 200;
				mega.health--;
				break;

			case "SPACE":
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
			long lastTime = System.nanoTime();
			long now;
			double delta = 0;
			long fps = 30;
			long targetTime = 1000 / fps;
			long elapsed;
			double ns = 1000000000 / fps;
			long Frametimer = System.currentTimeMillis();
			int frames = 0;

			long wait;

			while (running) {

				if (!mega.isLive) {

					running = false;
					game.setVisible(false);
					music.stop();
					Bass = false;
					ElecMan = false;
					titlePanel = new TitlePanel();
					titleThread = new Thread(titlePanel);
					titleThread.start();
					titleflag = true;
					frame.setBounds(150, 150, 530, 366);
					frame.setLocationRelativeTo(null);
					frame.setResizable(false);
					frame.add(titlePanel);
					titlePanel.setVisible(true);
					frame.setVisible(true);
				}

				if (mega.health < 0)
					mega.isLive = false;
				start = System.nanoTime();
				elapsed = System.nanoTime() - start;
				wait = targetTime - elapsed / 1000000;
				if (wait < 0)
					wait = 15;

				try {
					if (!LOADED) {

						LOADED = true;
					} else {
						Thread.sleep(wait);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block

					e.printStackTrace();
				}

				if (LOADED) {
					if (!PAUSE) {
						if (mega.air == false)
							mega.setLocation(mega.deltaX, mega.deltaY);

						if (mega.state != State.SPAWNING) {
							mega.setLocation(mega.deltaX, mega.deltaY);
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

						if (mega.CHARGEABLE)
							chargeString = "true";
						else
							chargeString = "false";

						// Update Player
						mega.ProjectileThread.update();
						// if (mega.isLive) {

						now = System.nanoTime();
						delta += (now - lastTime) / ns;
						lastTime = now;
						while (delta >= 1) {

							mega.update();

							if (mega.chargeCount == 13 && !mega.playedOnce) {
								mega.playSound("/music/5- Charge Complete.wav");
								mega.playedOnce = true;
							}
							// }

							// Update Enemy
							if (Bass) {
								if (!ec.LockedOn) {
									ec.trackPlayer(mega);
								}
								ec.update();

							}

							if (ElecMan) {
								ec.update();
							}

							delta--;
						}

						this.repaint();
						frames++;
					}
				}

				if (System.currentTimeMillis() - Frametimer > 1000) {
					Frametimer += 1000;
					System.out.println("FPS " + frames);
					frames = 0;
				}
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