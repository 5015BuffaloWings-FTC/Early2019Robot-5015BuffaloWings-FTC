package org.firstinspires.ftc.teamcode;

//These are need to have the app run the code properly
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

//Needed to alter inputs from joysticks. The .clip() method/function is what we use most
import com.qualcomm.robotcore.util.Range;

/**
 * @author Noah Zulick
 * @author Sam Cartered
 *
 * @version 12-5-18
 */

@TeleOp(name = "Basic TeleOp v0.4", group = "Buffalo Wings")
public class BasicTeleOp extends LinearOpMode
{
    private Definitions robot = new Definitions();
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    @Override
    public void runOpMode() throws InterruptedException
    {
    	//ROBOT DRIVING SECTION
    	//Sets up robot
        robot.robotHardwareMapInit(hardwareMap);
		robot.init();
        waitForStart();

        //This is what will run during the Remotely Operated mode
        while(opModeIsActive())
		{
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

			//Using Range.clip to limit joystick values from -1 to 1 (clipping the outputs)
			double driveFrontRightPower = Range.clip((-gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);
			double driveFrontLeftPower = Range.clip((gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);
			double driveBackRightPower = Range.clip((-gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);
			double driveBackLeftPower = Range.clip((gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);

			// Apply the values to the motors.
			robot.rightFrontMotor.setPower(driveFrontRightPower);
			robot.leftFrontMotor.setPower(driveFrontLeftPower);
			robot.rightBackMotor.setPower(driveBackRightPower);
			robot.leftBackMotor.setPower(driveBackLeftPower);





			//SCORING ARM SECTION
			//Scoring arm - Speed control, Default 0.65, With Left_bumper: 0.85,
			double scoringArmSlow;
			if(gamepad2.left_bumper)
			{
				scoringArmSlow = 0.85;
			}
			else
			{
				scoringArmSlow = 0.65;
			}

			//Scoring arm - controls input from gamepad2 left joystick
			double scoringArmMotorPower = Range.clip(gamepad2.left_stick_y, -1, 1);

			//Scoring arm - Sets speed for lift arm using the armSlow multiplier
			robot.scoringArmMotor.setPower(scoringArmMotorPower * scoringArmSlow);







			//LEAD SCREW SECTION
			if(!(robot.leadScrewLimitTop.isPressed() || robot.leadScrewLimitBot.isPressed()))
			{
				//Lead Screw Motor - limits input from gamepad2 right joystick
				double leadScrewMotorPower = Range.clip(gamepad2.right_stick_y, -1, 1);

				//Lead Screw Motor - Sets power to motor after being calculated
				robot.leadScrewMotor.setPower(leadScrewMotorPower);
			}
			else if(robot.leadScrewLimitTop.isPressed()) //Top limit switch has been pressed
			{
				robot.leadScrewMotor.setPower(-1);
			}
			else //bottom limit switch has been pressed
			{
				robot.leadScrewMotor.setPower(1);
			}




			//We added this line of telemetry because the robot arm is not strong enough at
			// 0 power to hold the arm up...
			//So we are going to find what power the arm needs to be at in order to
			//hold the arm steady.
			telemetry.addLine()
					.addData("GamePad 2", gamepad2.left_stick_y);


			//LEAD SCREW SECTION
			//Gamepad 2 button Y - releases the arm
			if(gamepad2.y)
			{
				robot.scoringArmReleaseServo.setPosition(0);
			}

			//Gamepad 2 button A - Latches the scoring arm into place
			if(gamepad2.a)
			{
				robot.armLatchServo.setPosition(1);
			}

			//Gamepad 2 button B - Opens the scoring arm container
			if(gamepad2.b)
			{
				robot.ballStopServo.setPosition(0.5);
			}
			else
			{
				robot.ballStopServo.setPosition(0);
			}

		}
    }
}
