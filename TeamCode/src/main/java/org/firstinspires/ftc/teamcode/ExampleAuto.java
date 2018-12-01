package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created for team: 5015 Buffalo Wings by Noah Zulick on November 10, 2018 at 9:18 AM .
 */
@Autonomous(name = "Test Auto", group = "Buffalo Wings")
public class ExampleAuto extends LinearOpMode
{
	Definitions robot = new Definitions();

	@Override
	public void runOpMode() throws InterruptedException
	{
		robot.encoderInit();
		robot.robotHardwareMapInit(hardwareMap);
		robot.init();

		waitForStart();

		while(opModeIsActive())
		{
			robot.resetEncoders();
			robot.setDriveForward();
			robot.setPower(1);
			robot.setPos(10);
			robot.runPos();
		}
	}
}
