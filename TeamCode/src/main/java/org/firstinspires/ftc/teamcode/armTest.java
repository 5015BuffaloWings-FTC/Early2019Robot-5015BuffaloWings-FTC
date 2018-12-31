package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Noah on 12/5/18.
 */
@TeleOp(name = "armTest Auto")

public class armTest extends LinearOpMode
{
    Definitions robot = new Definitions();

    @Override
    public void runOpMode() throws InterruptedException
    {
        robot.robotHardwareMapInit(hardwareMap);

        waitForStart();

        while(opModeIsActive())
        {
            //allows us to reset the Arm encoders ourselves just for better calibration
            if(gamepad2.dpad_up)
            {
                robot.scoringArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.scoringArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            else
            {
                robot.scoringArmMotor.setPower(gamepad2.left_stick_y);
            }
            if(gamepad2.a)
            {
                robot.scoringArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.scoringArmMotor.setTargetPosition(420);
                robot.scoringArmMotor.setPower(0.5);
                sleep(1000);
                robot.scoringArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

            int armPosition = robot.scoringArmMotor.getCurrentPosition();


            telemetry.addData("Encoder Value", armPosition);

            telemetry.update();
        }
    }
}
