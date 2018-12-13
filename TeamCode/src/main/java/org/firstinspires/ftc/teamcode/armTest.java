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
        robot.autoInit();
        robot.resetEncoders();

        waitForStart();

        while(opModeIsActive())
        {
//            double armSlow;
//            if(gamepad2.left_bumper)
//            {
//                armSlow = 0.85;
//            }
//            else
//            {
//                armSlow = 0.5;
//            }
//            //Makes Lift Arm Motor Move
//            double Arm = gamepad2.left_stick_y * armSlow;
//
//
//            //Changes Lift Arm Speed/Power
//            if(gamepad2.left_stick_y > 0 || gamepad2.left_stick_y < 0)
//            {
//                robot.scoringArmMotor.setPower(Arm);
//            }
//            else if(gamepad2.right_bumper)
//            {
//                robot.scoringArmMotor.setPower(-0.5);
//            }
//            else if(gamepad2.left_trigger > 0)
//            {
//                robot.scoringArmMotor.setPower(0.8);
//            }
//            else
//            {
//                robot.scoringArmMotor.setPower(Arm);
//            }
//
//
//            if(gamepad2.right_bumper)
//            {
//                robot.scoringArmMotor.setPower(-0.5);
//            }
//            else if(gamepad2.left_trigger > 0)
//            {
//                robot.scoringArmMotor.setPower(0.8);
//            }
//            else
//            {
//                robot.scoringArmMotor.setPower(0);
//            }

            //allows us to reset the Arm encoders ourselves just for better calibration
            if(gamepad2.dpad_up)
            {
                robot.scoringArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
            else
            {
                robot.scoringArmMotor.setPower(gamepad2.left_stick_y);
            }




            /**
             * The next chunk of code will lower the arm slowly when it is at any angle lower than 90 degrees
             *
             */
            final double armPositionAt90 = -37;
            final double powerToHoldArmAt90 = -0.5;
            double armPosition = robot.scoringArmMotor.getCurrentPosition();
            double angleOfArm = (90 * armPosition)/armPositionAt90;
            double loweringPower = (angleOfArm * powerToHoldArmAt90)/ 90 - 0.1; //I subtracted the 0.1 because we want the arm to lower slowly not just hold

            while(armPosition > 0 && gamepad2.left_stick_y < loweringPower)
            {
                if(armPosition <= armPositionAt90)
                {

                }

            }
            robot.scoringArmMotor.setPower(gamepad2.left_stick_y);

            telemetry.addLine("Values for Arm\n")
                    .addData("Arm Position: ", armPosition)
                    .addData("Arm Power: ", gamepad2.left_stick_y)
                    .addData("Lowering Power: ", loweringPower)
                    .addData("Angle Of Arm: ", angleOfArm);

            telemetry.update();
        }
    }
}
