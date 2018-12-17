package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Noah on 12/16/18.
 */

@TeleOp(name = "Test Robot TeleOp")
public class TestRobotTeleOp extends LinearOpMode
{

    Definitions robot = new Definitions();

    @Override
    public void runOpMode() throws InterruptedException
    {
        robot.teleOpInit();
        robot.testHardwareMapInit(hardwareMap);

        waitForStart();

        while(opModeIsActive())
        {

        }
    }
}
