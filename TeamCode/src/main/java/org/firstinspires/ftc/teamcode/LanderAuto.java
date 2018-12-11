package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Lander Auto")
public class LanderAuto extends LinearOpMode
{

    Definitions robot = new Definitions();

    ElapsedTime runTime = new ElapsedTime();



    @Override
    public void runOpMode() throws InterruptedException
    {
        robot.robotHardwareMapInit(hardwareMap);

        waitForStart();
        runTime.reset();

        while(opModeIsActive())
        {
            while(time <= 10)
            {
                if(!robot.leadScrewLimitTop.isPressed())
                {
                    robot.leadScrewMotor.setPower(1);
                }
            }

            robot.setDriveForward();
            if(runTime.time() <= 1.2)
            {
                robot.setPower(1);
            }

            else
            {
                robot.setPower(0);
            }

            telemetry.addLine("Telemetry\n")
                    .addData("Time", runTime.time());
            telemetry.update();

        }
    }
}