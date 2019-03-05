package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
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

    public void loop() {
        /**
         * DRIVING SECTION
         */
        //Using Range.clip to limit joystick values from -1 fto 1 (clipping the outputs)
        //Apply the values to the motors.
        if (gamepad1.right_bumper) {
            robot.rightFrontMotor.setPower(Range.clip((-gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1));
            robot.leftFrontMotor.setPower(Range.clip((gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1));
            robot.rightBackMotor.setPower(Range.clip((-gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1));
            robot.leftBackMotor.setPower(Range.clip((gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1));
        } else {
            robot.rightFrontMotor.setPower(Range.clip((-gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x), -1, 1));
            robot.leftFrontMotor.setPower(Range.clip((gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x), -1, 1));
            robot.rightBackMotor.setPower(Range.clip((-gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x), -1, 1));
            robot.leftBackMotor.setPower(Range.clip((gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x), -1, 1));
        }


        /**
         * LEADSCREW SECTION
         */
        if (robot.leadScrewLimitBot.getState()) {
            if (gamepad2.dpad_down || gamepad1.left_trigger == 1) {
                robot.leadScrewMotor.setPower(1);
            } else if (gamepad2.dpad_up || gamepad1.left_bumper) {
                robot.leadScrewMotor.setPower(-1);
            } else
                robot.leadScrewMotor.setPower(0);
        } else if (!robot.leadScrewLimitBot.getState())
            robot.leadScrewMotor.setPower(-0.5);
        else
            robot.leadScrewMotor.setPower(0);

        /**
         * SCORING ARM SECTION
         */

        robot.scoringArmMotor.setPower(gamepad2.right_stick_y);
        robot.armReelMotor.setPower(-gamepad2.left_stick_y);





        /**
         * SERVO SECTION
         */
        if(gamepad2.a)
        {
            robot.teamMarkerServo.setPower(1);
        }
        else if(gamepad2.x)
        {
            robot.teamMarkerServo.setPower(-1);
        }
        else
        {
            robot.teamMarkerServo.setPower(0);
        }


        if(gamepad2.right_trigger == 1)
        {
            robot.rightIntakeServo.setPower(-1);
        }
        else if(gamepad2.right_bumper)
        {
            robot.rightIntakeServo.setPower(1);
        }
        else
        {
            robot.rightIntakeServo.setPower(0);
        }

        if(gamepad2.left_trigger == 1)
        {
            robot.leftIntakeServo.setPower(1);
        }
        else if(gamepad2.left_bumper)
        {
            robot.leftIntakeServo.setPower(-1);
        }
        else
        {
            robot.leftIntakeServo.setPower(0);
        }


        /**
         * Telemetry Section
         */
        //No debugging needed right now!! YAY

        telemetry.addData("Lead Screw Position:", robot.leadScrewMotor.getCurrentPosition());
        telemetry.addData("Lead Screw Limit switch", robot.leadScrewLimitBot.getState());
        telemetry.addData("scoring arm motor position", robot.scoringArmMotor.getCurrentPosition());
        telemetry.addData("arm reel position", robot.armReelMotor.getCurrentPosition());
        telemetry.addData("left trigger", gamepad2.left_trigger);
        telemetry.addData("right wheel", robot.leftBackMotor.getCurrentPosition());
        telemetry.update();
    }

    public void stop()
    {
        robot.setPower(0);
        robot.leadScrewMotor.setPower(0);
        robot.armReelMotor.setPower(0);
        robot.scoringArmMotor.setPower(0);
    }
}
