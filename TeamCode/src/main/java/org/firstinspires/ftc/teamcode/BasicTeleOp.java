package org.firstinspires.ftc.teamcode;

//These are need to have the app run the code properly
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DigitalChannel;

//Needed to alter inputs from joysticks. The .clip() method/function is what we use most
import com.qualcomm.robotcore.util.Range;

/**
 * @author Noah Zulick
 * @author Sam Carter
 *
 * @version 12-5-18
 */

/**
 *Controls:
 * Start A - Controls wheels
 * Start B - Controls scoring arm and lead screw
 *
 *     Left bumper                           Right bumper
 *       _=====_                               _=====_
 *      / _____ \                             / _____ \
 *    +.-'_____'-.---------------------------.-'_____'-.+
 *   /   |     |  '.        L O G I        .'  |  _  |   \
 *  / ___| /|\ |___ \                     / ___| /Y\ |___ \
 * / |      |      | ;  __           _   ; | _         _ | ;
 * | | <---   ---> | | |__|         |_:> | ||X|       (B)| |
 * | |___   |   ___| ;SELECT       START ; |___       ___| ;
 * |\    | \|/ |    /  _     ___      _   \    | (A) |    /|
 * | \   |_____|  .','" "', |___|  ,'" "', '.  |_____|  .' |
 * |  '-.______.-' /       \ANALOG/       \  '-._____.-'   |
 * |               | Joy L |------| Joy R  |               |
 * |              /\       /      \       /\               |
 * |             /  '.___.'        '.___.'  \              |
 * |            /                            \             |
 *  \          /                              \           /
 *   \________/                                \_________/
 * From : https://www.asciiart.eu/computers/game-consoles
 *
 * Start A - Movement;
 * Joy R: lateral movement (All directions)
 * Joy L: Controls Rotation (Limited to left and right)
 * Right bumper: Slows movement to 0.5 or normal
 *
 *
 *
 * Start B - Scoring:
 * Joy R: Lead screw (Limited to up and down)
 * Joy L: Scoring arm (Limited to up and down)
 *
 *
 */

@TeleOp(name = "Basic TeleOp v0.4", group = "Buffalo Wings")
public class BasicTeleOp extends LinearOpMode //change to OP mode
{
    private Definitions robot = new Definitions();
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
	DigitalChannel leadScrewLimitBot;

    @Override
    public void runOpMode() throws InterruptedException
    {
    	/**
		 * ROBOT DRIVING SECTION
		 */

    	//Sets up robot
        robot.robotHardwareMapInit(hardwareMap);
		robot.resetEncoders();
		robot.servoInit();
		leadScrewLimitBot = hardwareMap.get(DigitalChannel.class, "leadScrewLimitBot");
		leadScrewLimitBot.setMode(DigitalChannel.Mode.INPUT);

        waitForStart();

        //This is what will run during the Remotely Operated mode
        while(opModeIsActive())
		{

			/**
			 * DRIVING SECTION
			 */
			//slow is used as a multiplier to change speed
			double slowMovement;
			if(gamepad1.right_bumper)
			{
				slowMovement = 0.5;
			}
			else
			{
				slowMovement= 1;
			}

			//Using Range.clip to limit joystick values from -1 fto 1 (clipping the outputs)
			double driveFrontRightPower = Range.clip((-gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);
			double driveFrontLeftPower = Range.clip((gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);
			double driveBackRightPower = Range.clip((-gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);
			double driveBackLeftPower = Range.clip((gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);

			 //Apply the values to the motors.
			robot.rightFrontMotor.setPower(driveFrontRightPower);
			robot.leftFrontMotor.setPower(driveFrontLeftPower);
			robot.rightBackMotor.setPower(driveBackRightPower);
			robot.leftBackMotor.setPower(driveBackLeftPower);

			telemetry.addData("stick input", gamepad1.left_stick_y);
			telemetry.addData("power", robot.leftBackMotor.getPower());


			/**
			 * LEADSCREW SECTION
			 */
			double leadScrewMotorPower = Range.clip(gamepad2.right_stick_y, -1, 1);
			if(!leadScrewLimitBot.getState())
				robot.leadScrewMotor.setPower(0.5);
			else
				robot.leadScrewMotor.setPower(-leadScrewMotorPower);

			//telemetry.addData("Pressed?", robot.leadScrewLimitBot.isPressed());
			telemetry.addData("Power to LeadScrew", leadScrewMotorPower);
			telemetry.update();




			/**
			 * SCORING ARM SECTION
			 */
			//Scoring arm - Speed control, Default 0.65, With Left_bumper: 0.85,
			double scoringArmSlow;
			if(gamepad2.left_bumper)
			{
				scoringArmSlow = 1;
			}
			else
			{
				scoringArmSlow = 0.85;
			}

			//Scoring arm - controls input from gamepad2 left joystick
			double scoringArmMotorPower = Range.clip(gamepad2.left_stick_y, -1, 1);

			//Scoring arm - Sets speed for lift arm using the armSlow multiplier
			if(gamepad2.right_bumper)
			{
				robot.scoringArmMotor.setPower(0.5);
			}
			else
			{
				robot.scoringArmMotor.setPower(scoringArmMotorPower * scoringArmSlow);
			}



			/**
			 * SERVO SECTION
			 */
			//Gamepad 2 button Y - releases the arm
			if(gamepad2.y)
			{
				robot.scoringArmReleaseServo.setPosition(0.5);
			}

			//Gamepad 2 button A - Latches the scoring arm into place
			if(gamepad2.a)
			{
				robot.scoringArmLatchServo.setPosition(0.65);
			}

			//Gamepad 2 button B - Opens the scoring arm container
			if(gamepad2.b)
			{
				robot.ballStopperServo.setPosition(0.25);
			}
			else
			{
				robot.ballStopperServo.setPosition(0);
			}

			if(gamepad2.x)
			{
				//code
			}
		}
    }
}
