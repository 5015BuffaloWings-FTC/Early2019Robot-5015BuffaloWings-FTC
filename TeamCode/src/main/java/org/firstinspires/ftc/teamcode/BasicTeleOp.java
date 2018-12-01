package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;


/**
 * @author Noah Zulick
 * @author Sam Carter
 *
 * @version 9-21
 */

@TeleOp(name = "Basic TeleOp v0.4", group = "Buffalo Wings")
public class BasicTeleOp extends LinearOpMode
{
    private Definitions robot = new Definitions();
    // Creates object for using Vuforia
    //private Vuforia_Definitions vRobot = new Vuforia_Definitions();
    //private BasicTeleOp teleOp = new BasicTeleOp();
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    @Override
    public void runOpMode() throws InterruptedException
    {

        robot.robotHardwareMapInit(hardwareMap);
        //vRobot.initVuforia(teleOp, robot);
		robot.init();



        waitForStart();
        //vRobot.activateTracking();

        while(opModeIsActive())
		{
			double slow;
			if(gamepad1.right_bumper == true)
			{
				slow = 0.5;
			}
			else
			{
				slow = 1;
			}

			double DFR = Range.clip((-gamepad1.left_stick_y - (0.87 * gamepad1.left_stick_x) - gamepad1.right_stick_x) * slow, -1, 1);//to 1 making sure they don't burn out.
			double DFL = Range.clip((gamepad1.left_stick_y - (0.87 * gamepad1.left_stick_x) - gamepad1.right_stick_x) * slow, -1, 1); //This will clip the outputs to the motors
			double DBR = Range.clip((-gamepad1.left_stick_y + (0.87 * gamepad1.left_stick_x) - gamepad1.right_stick_x) * slow, -1, 1);
			double DBL = Range.clip((gamepad1.left_stick_y + (0.87 * gamepad1.left_stick_x) - gamepad1.right_stick_x) * slow, -1, 1);

			// Apply the values to the motors.

			robot.rightFront.setPower(DFR);
			robot.leftFront.setPower(DFL);
			robot.rightBack.setPower(DBR);
			robot.leftBack.setPower(DBL);


			double armSlow;
			if(gamepad2.left_bumper == true)
			{
				armSlow = 0.85;
			}
			else
			{
				armSlow = 0.5;
			}
			//Makes Lift Arm Motor Move
			double Arm = gamepad2.left_stick_y * armSlow;


			//Changes Lift Arm Speed/Power
			if(gamepad2.left_stick_y > 0 || gamepad2.left_stick_y < 0)
			{
				robot.armMotor1.setPower(Arm);
			}
			else if(gamepad2.right_bumper == true)
			{
				robot.armMotor1.setPower(-0.5);
			}
			else if(gamepad2.left_trigger > 0)
			{
				robot.armMotor1.setPower(0.8);
			}
			else
			{
				robot.armMotor1.setPower(0);
			}

			//Controls  Servos IMPROVE THIS COMMENT
			if(gamepad2.y == true)
			{
				robot.release.setPower(1);
			}
			else
			{
				robot.release.setPower(0);
			}

			if(gamepad2.b == true)
			{
				robot.ballStopper.setPosition(0.5);
			}
			else if(gamepad2.b != true)
			{
				robot.ballStopper.setPosition(0);
			}

			if(gamepad2.a == true)
			{
				robot.latch.setPower(1);
			}




			//We added this line of telemetry because the robot arm is not strong enough at
			// 0 power to hold the arm up...
			//So we are going to find what power the arm needs to be at in order to
			//hold the arm steady.
			telemetry.addLine()
					.addData("GamePad 2", gamepad2.left_stick_y);

			//vRobot.addNavTelemetry();


		}





    }
}
