package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created for team: 5015 Buffalo Wings by Noah Zulick on November 10, 2018 at 9:18 AM .
 */
@Autonomous(name = "Off Lander", group = "Buffalo Wings")
public class ExampleAuto extends LinearOpMode
{
	Definitions robot = new Definitions();

	ElapsedTime runTime = new ElapsedTime();

	@Override
	public void runOpMode() throws InterruptedException
	{
		//robot.init();
		robot.robotHardwareMapInit(hardwareMap);
        //robot.encoderInit();
		waitForStart();

        runTime.reset();

		while(opModeIsActive())
		{
		    time = runTime.milliseconds();
		    robot.leadScrewMotor.setPower(1);
			if(time >= 9000)
            {
                robot.leadScrewMotor.setPower(0);
            }


		}
	}
}
