package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp")
public class TeleOpBuffaloWings extends OpMode
{
    Definitions robot = new Definitions();
    double slowMovement = 0.5; //Slow movement is useed as a multiplier to change movement speed

    public void init()
    {
        robot.robotHardwareMapInit(hardwareMap);
        robot.resetEncoders();
        robot.runWithOutEncoders();
    }

    public void loop()
    {
        /**
         * DRIVING SECTION
         */
        //Using Range.clip to limit joystick values from -1 fto 1 (clipping the outputs)
        //Apply the values to the motors.
        if(gamepad1.right_bumper)
        {
            robot.rightFrontMotor.setPower(Range.clip((-gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1));
            robot.leftFrontMotor.setPower(Range.clip((gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1));
            robot.rightBackMotor.setPower(Range.clip((-gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1));
            robot.leftBackMotor.setPower(Range.clip((gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1));
        }
        else
        {
            robot.rightFrontMotor.setPower(Range.clip((-gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x), -1, 1));
            robot.leftFrontMotor.setPower(Range.clip((gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x), -1, 1));
            robot.rightBackMotor.setPower(Range.clip((-gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x), -1, 1));
            robot.leftBackMotor.setPower(Range.clip((gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x), -1, 1));
        }




        /**
         * LEADSCREW SECTION
         */
        if(robot.leadScrewLimitBot.getState())
        {
            robot.leadScrewMotor.setPower(-0.75);
        }
        else
        {
            if (gamepad2.dpad_up)
                robot.leadScrewMotor.setPower(-1);
            else if (gamepad2.dpad_down)
                robot.leadScrewMotor.setPower(1);
            else
                robot.leadScrewMotor.setPower(0);
        }

//        if(gamepad2.dpad_up)
//        {
//            robot.leadScrewMotor.setPower(-1);
//        }
//        else if(gamepad2.dpad_down)
//        {
//            if(!leadScrewLimitBot.getState())
//                robot.leadScrewMotor.setPower(-0.75);
//            else
//                robot.leadScrewMotor.setPower(1);
//        }
//        else
//        {
//            if(!leadScrewLimitBot.getState())
//                robot.leadScrewMotor.setPower(-0.75);
//            else
//                robot.leadScrewMotor.setPower(0);
//        }


        /**
         * SCORING ARM SECTION
         */

//        //Scoring arm - controls input from gamepad2 left joystick
//        double scoringArmMotorPower = Range.clip(gamepad2.left_stick_y, -1, 1);
//
//        //Scoring arm - Sets speed for lift arm
//        robot.scoringArmMotor.setPower(scoringArmMotorPower * 0.8);

        robot.armReelMotor.setPower(-gamepad2.right_stick_y);





        /**
         * SERVO SECTION
         */
        if(gamepad2.a)
        {
            robot.teamMarkerServo.setPower(1);
        }
        else if(gamepad2.b)
        {
            robot.teamMarkerServo.setPower(-1);
        }
        else
        {
            robot.teamMarkerServo.setPower(0);
        }


        /**
         * Telemetry Section
         */
        //No debugging needed right now!! YAY

        telemetry.addData("Lead Screw Position:", robot.leadScrewMotor.getCurrentPosition());
        telemetry.addData("Lead Screw Limit switch", robot.leadScrewLimitBot.getState());
        telemetry.addData("Right Back Motor Position", robot.rightBackMotor.getCurrentPosition());
        telemetry.update();
    }

    public void stop()
    {
        robot.setPower(0);
        robot.leadScrewMotor.setPower(0);
        robot.armReelMotor.setPower(0);
        //robot.scoringArmMotor.setPower(0);
    }
}
