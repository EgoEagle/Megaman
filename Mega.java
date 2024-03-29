import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mega extends PlayerCharacter {

	BufferedImage current;
	BufferedImage effects;
	BufferedImage healthbar;
	BufferedImage healthtick;
	BufferedImage idle;
	BufferedImage idleBack;
	BufferedImage[] MegaRun;
	BufferedImage[] MegaBackRun;
	BufferedImage[] MegaShoot;
	BufferedImage[] MegaBackShoot;
	BufferedImage[] JumpAsc;
	BufferedImage[] JumpDes;
	BufferedImage[] JumpBackAsc;
	BufferedImage[] JumpBackDes;
	BufferedImage[] Spawn;
	BufferedImage[] JumpShoot;
	BufferedImage[] FallShoot;
	BufferedImage[] JumpBackShoot;
	BufferedImage[] FallBackShoot;
	BufferedImage[] ChargeAnimation;
	BufferedImage[] MegaSlideLeft;
	BufferedImage[] MegaSlideShootLeft;
	BufferedImage[] MegaSlideRight;
	BufferedImage[] MegaSlideShootRight;
	BufferedImage WallClimbLeft;
	BufferedImage WallShootLeft;
	BufferedImage WallClimbRight;
	BufferedImage WallShootRight;
	boolean WallHop = false;
	boolean actionLock = false;
	boolean invincible = false;
	boolean leftwalk = false;
	boolean rightwalk = false;
	boolean shoot = false;
	boolean air = false;
	boolean charge = false;
	boolean minChargeReached = false;
	boolean playedOnce = false;
	State state;
	State direction;
	State action;
	boolean JumpRelease = true;
	boolean CHARGEABLE;
	boolean Charge_Time_Lock = false;
	// LinkedList<Projectile> Activebullets;

	boolean chargeFlag = false;
	double chargeTimer = 0;
	long ChargeStartTime = 0;
	long Invincible_Frame_Start_Time = 0;
	int slideframe = 0;
	int walkframe = 0;
	int jumpframe = 0;
	int fallframe = 0;
	int shootframe = 0;
	int chargeCount = 0;

	float originalheight;
	long wait;

	public Mega(int x, int y, int width, int height) {
		super(x, y, width, height);
		health = 14;
		state = State.SPAWNING;
		direction = State.RIGHT;
		action = null;
		deltaX = 0;
		deltaY = 0;
		CHARGEABLE = true;
		Spawn = new BufferedImage[9];
		JumpBackAsc = new BufferedImage[9];
		JumpBackDes = new BufferedImage[12];
		JumpAsc = new BufferedImage[9];
		JumpDes = new BufferedImage[12];
		MegaShoot = new BufferedImage[4];
		MegaBackShoot = new BufferedImage[4];
		MegaRun = new BufferedImage[25];
		MegaBackRun = new BufferedImage[25];
		JumpShoot = new BufferedImage[9];
		FallShoot = new BufferedImage[12];
		JumpBackShoot = new BufferedImage[9];
		FallBackShoot = new BufferedImage[12];
		ChargeAnimation = new BufferedImage[14];
		MegaSlideLeft = new BufferedImage[12];
		MegaSlideShootLeft = new BufferedImage[2];
		MegaSlideRight = new BufferedImage[12];
		MegaSlideShootRight = new BufferedImage[2];
		// Activebullets = new LinkedList<>();

		// TODO Auto-generated constructor stub
		try {
			healthbar = ImageIO.read(NewView.class.getResource("/Effects/h.png"));
			healthtick = ImageIO.read(NewView.class.getResource("/Effects/bar.png"));
			Spawn[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/spawn0.png"));
			Spawn[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/spawn1.png"));
			Spawn[2] = ImageIO.read(NewView.class.getResource("/MegamanSprite/spawn2.png"));
			Spawn[3] = ImageIO.read(NewView.class.getResource("/MegamanSprite/spawn3.png"));
			Spawn[4] = ImageIO.read(NewView.class.getResource("/MegamanSprite/spawn4.png"));
			Spawn[5] = ImageIO.read(NewView.class.getResource("/MegamanSprite/spawn5.png"));
			Spawn[6] = ImageIO.read(NewView.class.getResource("/MegamanSprite/spawn6.png"));
			Spawn[7] = ImageIO.read(NewView.class.getResource("/MegamanSprite/spawn7.png"));
			Spawn[8] = ImageIO.read(NewView.class.getResource("/MegamanSprite/spawn8.png"));

			MegaRun[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun0.png"));
			MegaRun[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun0.png"));
			MegaRun[2] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun1.png"));
			MegaRun[3] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun1.png"));
			MegaRun[4] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun2.png"));
			MegaRun[5] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun2.png"));
			MegaRun[6] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun3.png"));
			MegaRun[7] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun3.png"));
			MegaRun[8] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun4.png"));
			MegaRun[9] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun4.png"));
			MegaRun[10] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun5.png"));
			MegaRun[11] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun5.png"));
			MegaRun[12] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun6.png"));
			MegaRun[13] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun6.png"));
			MegaRun[14] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun7.png"));
			MegaRun[15] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun7.png"));
			MegaRun[16] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun8.png"));
			MegaRun[17] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun8.png"));
			MegaRun[18] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun9.png"));
			MegaRun[18] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun9.png"));
			MegaRun[19] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun10.png"));
			MegaRun[20] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun10.png"));
			MegaRun[21] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun11.png"));
			MegaRun[22] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun11.png"));
			MegaRun[23] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun12.png"));
			MegaRun[24] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaRun12.png"));
///
			//
			//
			MegaBackRun[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun0.png"));
			MegaBackRun[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun0.png"));
			MegaBackRun[2] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun1.png"));
			MegaBackRun[3] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun1.png"));
			MegaBackRun[4] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun2.png"));
			MegaBackRun[5] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun2.png"));
			MegaBackRun[6] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun3.png"));
			MegaBackRun[7] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun3.png"));
			MegaBackRun[8] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun4.png"));
			MegaBackRun[9] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun4.png"));
			MegaBackRun[10] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun5.png"));
			MegaBackRun[11] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun5.png"));
			MegaBackRun[12] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun6.png"));
			MegaBackRun[13] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun6.png"));
			MegaBackRun[14] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun7.png"));
			MegaBackRun[15] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun7.png"));
			MegaBackRun[16] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun8.png"));
			MegaBackRun[17] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun8.png"));
			MegaBackRun[18] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun9.png"));
			MegaBackRun[18] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun9.png"));
			MegaBackRun[19] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun10.png"));
			MegaBackRun[20] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun10.png"));
			MegaBackRun[21] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun11.png"));
			MegaBackRun[22] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun11.png"));
			MegaBackRun[23] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun12.png"));
			MegaBackRun[24] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackRun12.png"));

			MegaShoot[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaShoot.png"));
			MegaShoot[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaShoot1.png"));
			MegaShoot[2] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaShoot2.png"));
			MegaShoot[3] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaShoot2.png"));

			MegaBackShoot[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackShoot.png"));
			MegaBackShoot[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackShoot1.png"));
			MegaBackShoot[2] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackShoot2.png"));
			MegaBackShoot[3] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackShoot2.png"));

			JumpAsc[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump0.png"));
			JumpAsc[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump0.png"));
			JumpAsc[2] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump0.png"));
			JumpAsc[3] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump1.png"));
			JumpAsc[4] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump1.png"));
			JumpAsc[5] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump1.png"));
			JumpAsc[6] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump2.png"));
			JumpAsc[7] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump2.png"));
			JumpAsc[8] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump2.png"));

			JumpDes[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump3.png"));
			JumpDes[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump3.png"));
			JumpDes[2] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump3.png"));
			JumpDes[3] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump4.png"));
			JumpDes[4] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump4.png"));
			JumpDes[5] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump4.png"));
			JumpDes[6] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump5.png"));
			JumpDes[7] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump5.png"));
			JumpDes[8] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump5.png"));
			JumpDes[9] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump6.png"));
			JumpDes[10] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump6.png"));
			JumpDes[11] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaJump6.png"));

			JumpBackAsc[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump0.png"));
			JumpBackAsc[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump0.png"));
			JumpBackAsc[2] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump0.png"));
			JumpBackAsc[3] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump1.png"));
			JumpBackAsc[4] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump1.png"));
			JumpBackAsc[5] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump1.png"));
			JumpBackAsc[6] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump2.png"));
			JumpBackAsc[7] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump2.png"));
			JumpBackAsc[8] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump2.png"));

			JumpBackDes[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump3.png"));
			JumpBackDes[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump3.png"));
			JumpBackDes[2] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump3.png"));
			JumpBackDes[3] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump4.png"));
			JumpBackDes[4] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump4.png"));
			JumpBackDes[5] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump4.png"));
			JumpBackDes[6] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump5.png"));
			JumpBackDes[7] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump5.png"));
			JumpBackDes[8] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump5.png"));
			JumpBackDes[9] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump6.png"));
			JumpBackDes[10] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump6.png"));
			JumpBackDes[11] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBackJump6.png"));

			JumpShoot[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot0.png"));
			JumpShoot[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot0.png"));
			JumpShoot[2] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot0.png"));
			JumpShoot[3] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot1.png"));
			JumpShoot[4] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot1.png"));
			JumpShoot[5] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot1.png"));
			JumpShoot[6] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot2.png"));
			JumpShoot[7] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot2.png"));
			JumpShoot[8] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot2.png"));

			FallShoot[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot3.png"));
			FallShoot[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot3.png"));
			FallShoot[2] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot3.png"));
			FallShoot[3] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot4.png"));
			FallShoot[4] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot4.png"));
			FallShoot[5] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot4.png"));
			FallShoot[6] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot5.png"));
			FallShoot[7] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot5.png"));
			FallShoot[8] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot5.png"));
			FallShoot[9] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot6.png"));
			FallShoot[10] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot6.png"));
			FallShoot[11] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirShoot6.png"));

			JumpBackShoot[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot0.png"));
			JumpBackShoot[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot0.png"));
			JumpBackShoot[2] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot0.png"));
			JumpBackShoot[3] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot1.png"));
			JumpBackShoot[4] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot1.png"));
			JumpBackShoot[5] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot1.png"));
			JumpBackShoot[6] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot2.png"));
			JumpBackShoot[7] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot2.png"));
			JumpBackShoot[8] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot2.png"));

			FallBackShoot[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot3.png"));
			FallBackShoot[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot3.png"));
			FallBackShoot[2] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot3.png"));
			FallBackShoot[3] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot4.png"));
			FallBackShoot[4] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot4.png"));
			FallBackShoot[5] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot4.png"));
			FallBackShoot[6] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot5.png"));
			FallBackShoot[7] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot5.png"));
			FallBackShoot[8] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot5.png"));
			FallBackShoot[9] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot6.png"));
			FallBackShoot[10] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot6.png"));
			FallBackShoot[11] = ImageIO.read(NewView.class.getResource("/MegamanSprite/AirBackShoot6.png"));

			ChargeAnimation[0] = ImageIO.read(NewView.class.getResource("/Effects/Charge.png"));
			ChargeAnimation[1] = ImageIO.read(NewView.class.getResource("/Effects/Charge0.png"));
			ChargeAnimation[2] = ImageIO.read(NewView.class.getResource("/Effects/Charge1.png"));
			ChargeAnimation[3] = ImageIO.read(NewView.class.getResource("/Effects/Charge2.png"));
			ChargeAnimation[4] = ImageIO.read(NewView.class.getResource("/Effects/Charge3.png"));
			ChargeAnimation[5] = ImageIO.read(NewView.class.getResource("/Effects/Charge4.png"));
			ChargeAnimation[6] = ImageIO.read(NewView.class.getResource("/Effects/Charge5.png"));
			ChargeAnimation[7] = ImageIO.read(NewView.class.getResource("/Effects/Charge6.png"));
			ChargeAnimation[8] = ImageIO.read(NewView.class.getResource("/Effects/Charge7.png"));
			ChargeAnimation[9] = ImageIO.read(NewView.class.getResource("/Effects/Charge8.png"));
			ChargeAnimation[10] = ImageIO.read(NewView.class.getResource("/Effects/Charge9.png"));
			ChargeAnimation[11] = ImageIO.read(NewView.class.getResource("/Effects/Charge10.png"));
			ChargeAnimation[12] = ImageIO.read(NewView.class.getResource("/Effects/Charge11.png"));
			ChargeAnimation[13] = ImageIO.read(NewView.class.getResource("/Effects/Charge12.png"));

			idle = ImageIO.read(NewView.class.getResource("/MegamanSprite/Mega.png"));
			idleBack = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaBack.png"));
			WallClimbLeft = ImageIO.read(NewView.class.getResource("/MegamanSprite/WallClimbLeft.png"));
			WallShootLeft = ImageIO.read(NewView.class.getResource("/MegamanSprite/WallShootLeft.png"));
			WallClimbRight = ImageIO.read(NewView.class.getResource("/MegamanSprite/WallClimbRight.png"));
			WallShootRight = ImageIO.read(NewView.class.getResource("/MegamanSprite/WallShootRight.png"));
			MegaSlideLeft[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideLeft0.png"));
			MegaSlideLeft[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideLeft0.png"));
			MegaSlideLeft[2] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideLeft0.png"));
			MegaSlideLeft[3] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideLeft1.png"));
			MegaSlideLeft[4] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideLeft1.png"));
			MegaSlideLeft[5] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideLeft1.png"));
			MegaSlideLeft[6] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideLeft1.png"));
			MegaSlideLeft[7] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideLeft1.png"));
			MegaSlideLeft[8] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideLeft1.png"));
			MegaSlideLeft[9] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideLeft1.png"));
			MegaSlideLeft[10] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideLeft1.png"));
			MegaSlideLeft[11] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideLeft1.png"));

			MegaSlideRight[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideRight0.png"));
			MegaSlideRight[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideRight0.png"));
			MegaSlideRight[2] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideRight0.png"));
			MegaSlideRight[3] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideRight1.png"));
			MegaSlideRight[4] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideRight1.png"));
			MegaSlideRight[5] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideRight1.png"));
			MegaSlideRight[6] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideRight1.png"));
			MegaSlideRight[7] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideRight1.png"));
			MegaSlideRight[8] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideRight1.png"));
			MegaSlideRight[9] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideRight1.png"));
			MegaSlideRight[10] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideRight1.png"));
			MegaSlideRight[11] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideRight1.png"));

			MegaSlideShootLeft[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideShootLeft0.png"));
			MegaSlideShootLeft[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideShootLeft1.png"));

			MegaSlideShootRight[0] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideShootRight0.png"));
			MegaSlideShootRight[1] = ImageIO.read(NewView.class.getResource("/MegamanSprite/MegaSlideShootRight1.png"));
			current = idle;

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	private void FallingSequence() {
		// TODO Auto-generated method stub

		double time = (System.nanoTime() - airtime) / 100000000;

		double delta = (float) ((4 * time) - (-1 * 0.5 * time));

		if (shoot && (rightwalk || leftwalk)) {
			if (rightwalk) {

				current = FallShoot[fallframe];
				if (deltaX + 9 > 935) {
					deltaX = 935;
				} else {
					deltaX += 9;
				}
			}

			else if (leftwalk) {

				current = FallBackShoot[fallframe];
				if (deltaX - 9 < -5) {
					deltaX = 0;
				} else {
					deltaX -= 9;
				}
			}

		}

		else if (shoot) {
			if (direction == State.RIGHT)
				current = FallShoot[fallframe];
			else if (direction == State.LEFT)
				current = FallBackShoot[fallframe];
		}

		else if (rightwalk) {
			current = JumpDes[fallframe];
			if (deltaX + 9 > 935) {
				deltaX = 935;
				state = State.WALL_CLIMB_RIGHT;
				air = false;
			} else {
				deltaX += 9;
			}
		}

		else if (leftwalk) {
			current = JumpBackDes[fallframe];
			if (deltaX - 9 < -5) {
				deltaX = 0;
				state = State.WALL_CLIMB_LEFT;
				air = false;
			} else {
				deltaX -= 9;
			}
		}

		else {
			if (direction == State.RIGHT)
				current = JumpDes[fallframe];
			else if (direction == State.LEFT)
				current = JumpBackDes[fallframe];
		}
		fallframe++;
		deltaY += delta;
		shoot = false;
		if (y < 300 && fallframe > 1) {

			fallframe--;

		}

		else if (y >= 410 || fallframe > 11) {

			y = 410;
			fallframe = 0;
			deltaY = 410;
			state = State.STANDING;
			air = false;
			action = State.STILL;

		}

	}

	private void StandingSequence() {
		// TODO Auto-generated method stub

		if (shoot) {
			if (direction == State.RIGHT) {
				current = MegaShoot[shootframe];
			} else if (direction == State.LEFT) {
				current = MegaBackShoot[shootframe];
			}

			shootframe++;

		}

		else if (rightwalk) {

			current = MegaRun[walkframe];
			walkframe++;
			if (deltaX + 8 > 935) {
				deltaX = 935;
			} else {
				deltaX += 8;
			}

		}

		else if (leftwalk) {

			current = MegaBackRun[walkframe];
			walkframe++;
			if (deltaX - 8 < -5) {
				deltaX = 0;
			} else {
				deltaX -= 8;
			}

		}

		if (walkframe > 24) {
			walkframe = 0;
		}

		if (shootframe > 3) {
			shootframe = 0;
			action = State.STILL;
			shoot = false;

		}

	}

	private void JumpingSequence() {
		// TODO Auto-generated method stub

		double time = (System.nanoTime() - airtime) / 100000000;

		double delta = (float) ((8 * time) - (1 * 0.5 * time));

		if (shoot) {
			if (direction == State.RIGHT)
				current = JumpShoot[jumpframe];
			else if (direction == State.LEFT)
				current = JumpBackShoot[jumpframe];

		}

		else if (rightwalk) {
			current = JumpAsc[jumpframe];
			if (deltaX + 12 > 935) {
				deltaX = 935;
				state = State.WALL_CLIMB_RIGHT;
			} else {
				deltaX += 12;
			}
		}

		else if (leftwalk) {
			current = JumpBackAsc[jumpframe];
			if (deltaX - 12 < -5) {
				deltaX = 0;
				state = State.WALL_CLIMB_LEFT;
			} else {
				deltaX -= 12;
			}
		}

		if (shootframe > 8) {
			shootframe = 0;
			shoot = false;

		}

		else {
			if (direction == State.RIGHT)
				current = JumpAsc[jumpframe];
			else if (direction == State.LEFT)
				current = JumpBackAsc[jumpframe];
		}

		jumpframe++;

		if (deltaY - delta > 0)
			deltaY -= delta;
		else
			deltaY = 0;

		if (jumpframe > 8 || JumpRelease) {
			jumpframe = 0;
			state = State.FALLING;
			airtime = System.nanoTime();
		}

	}

	public void spawnAnimation() {
		current = Spawn[walkframe];
		if (deltaY >= 410) {
			deltaY = 410;
			walkframe++;
		}

		else
			deltaY += 20;
		if (walkframe == 9 && deltaY < 410)
			walkframe = 0;
		else if (walkframe == 9) {
			walkframe = 0;
			state = State.STANDING;
			current = idle;
		}
	}

	public void update() {
		// System.out.println(state);
		if (invincible) {

			long invincibleTimer = ((System.nanoTime() - Invincible_Frame_Start_Time) / 1000000000);
			if (invincibleTimer > 1)
				invincible = false;
		}

		if (action == State.STILL) {
			if (direction == State.LEFT)
				current = idleBack;
			else if (direction == State.RIGHT)
				current = idle;
			action = null;
		}

		if (CHARGEABLE && chargeTimer != 0) {
			playSound();
			double currentTime = System.nanoTime();
			double TotalChargeTime = (currentTime - chargeTimer) / 1000000000;

			if (TotalChargeTime > 0.1) {
				effects = ChargeAnimation[chargeCount];
				chargeCount++;

				if (TotalChargeTime > 0.4)
					minChargeReached = true;

				if (chargeCount > 13)
					chargeCount = 0;
			}

		}

		switch (state) {

		case SPAWNING:

			spawnAnimation();
			break;

		case STANDING:
			StandingSequence();
			break;

		case FALLING:
			FallingSequence();
			break;

		case JUMPING:
			JumpingSequence();
			break;

		case WALL_CLIMB_LEFT:
			WallClimbLeft();
			break;

		case WALL_CLIMB_RIGHT:
			WallClimbRight();
			break;

		case SLIDE:
			SlideSequence();
			break;
		default:
			break;
		}

	}

	private void SlideSequence() {
		// TODO Auto-generated method stub
		if (direction == state.LEFT) {
			current = MegaSlideLeft[slideframe];
			if (deltaX - 12 < -5)
				deltaX = -5;
			else
				deltaX -= 12;
		} else if (direction == state.RIGHT) {
			current = MegaSlideRight[slideframe];
			if (deltaX + 12 > 935)
				deltaX = 935;
			else
				deltaX += 12;
		}
		slideframe++;

		if (slideframe > 11) {
			slideframe = 0;
			state = State.STANDING;
			action = State.STILL;
		}

	}

	private void WallClimbLeft() {
		// TODO Auto-generated method stub
		fallframe = 0;
		jumpframe = 0;
		current = WallClimbLeft;
		direction = state.RIGHT;

		if (air && rightwalk && WallHop == false) {
			WallHop = true;
			state = State.JUMPING;
		} else if (air && WallHop == false) {
			WallHop = true;
			if (deltaY - 38 > 0)
				deltaY -= 38;
			else
				deltaY = 0;
		}

		air = false;

		if (shoot) {
			current = WallShootLeft;
			shoot = false;
		}

		if (deltaY >= 410) {
			deltaY = 410;
			state = State.STANDING;
		} else
			deltaY += 5;

	}

	private void WallClimbRight() {
		// TODO Auto-generated method stub
		fallframe = 0;
		jumpframe = 0;
		current = WallClimbRight;
		direction = state.LEFT;
		if (air && leftwalk && WallHop == false) {
			WallHop = true;
			state = State.JUMPING;
		} else if (air && WallHop == false) {
			WallHop = true;
			if (deltaY - 38 > 0)
				deltaY -= 38;
			else
				deltaY = 0;
		}

		air = false;

		if (shoot) {
			current = WallShootRight;

			shoot = false;
		}

		if (deltaY >= 410) {
			deltaY = 410;
			state = State.STANDING;
		} else
			deltaY += 5;

	}

	String playSound() {
		return "/music/5- Charge Complete.wav";

	}

	String playSound2() {
		return "/music/4- Charge.wav";

	}

}