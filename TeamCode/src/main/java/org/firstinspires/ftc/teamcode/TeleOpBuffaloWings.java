package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOpBuffaloWings")
public class TeleOpBuffaloWings extends OpMode
{
    Definitions robot = new Definitions();
    ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
    DigitalChannel leadScrewLimitBot;

    public void init()
    {
        robot.runWithOutEncoders();
        robot.servoInit();
        robot.robotHardwareMapInit(hardwareMap);
        leadScrewLimitBot = hardwareMap.get(DigitalChannel.class, "leadScrewLimitBot");
        leadScrewLimitBot.setMode(DigitalChannel.Mode.INPUT);
    }

    public void loop()
    {
        /**
         * DRIVING SECTION
         */
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

        //Using Range.clip to limit joystick values from -1 fto 1 (clipping the outputs)
        double driveFrontRightPower = Range.clip((-gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);
        double driveFrontLeftPower = Range.clip((gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);
        double driveBackRightPower = Range.clip((-gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);
        double driveBackLeftPower = Range.clip((gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);

        //Apply the values to the motors.
        robot.rightFrontMotor.setPower(driveFrontRightPower);
        robot.leftFrontMotor.setPower(driveFrontLeftPower);
        robot.rightBackMotor.setPower(driveBackRightPower);
        robot.leftBackMotor.setPower(driveBackLeftPower);

        telemetry.addData("stick input", gamepad1.left_stick_y);
        telemetry.addData("power", robot.leftBackMotor.getPower());


        /**
         * LEADSCREW SECTION
         */
        if(gamepad2.dpad_up)
        {
            if(!leadScrewLimitBot.getState())
                robot.leadScrewMotor.setPower(-0.5);
            else
                robot.leadScrewMotor.setPower(-1);
        }
        else if(gamepad2.dpad_down)
        {
            if(!leadScrewLimitBot.getState())
                robot.leadScrewMotor.setPower(-0.5);
            else
                robot.leadScrewMotor.setPower(1);
        }
        else
        {
            if(!leadScrewLimitBot.getState())
                robot.leadScrewMotor.setPower(0.5);
            else
                robot.leadScrewMotor.setPower(0);
        }



        /**
         * SCORING ARM SECTION
         */
        //Scoring arm - Speed control, Default 0.65, With Left_bumper: 0.85,
        double scoringArmSlow;
        if(gamepad2.left_bumper)
        {
            scoringArmSlow = 1;
        }
        else
        {
            scoringArmSlow = 0.85;
        }

        //Scoring arm - controls input from gamepad2 left joystick
        double scoringArmMotorPower = Range.clip(gamepad2.left_stick_y, -1, 1);

        //Scoring arm - Sets speed for lift arm using the armSlow multiplier
        robot.scoringArmMotor.setPower(scoringArmMotorPower * scoringArmSlow);





        /**
         * SERVO SECTION
         */
        //We are working on mounting our servos still..
        //While we do damage control, there will be minimal servo code

        /**
         * Telemetry Section
         */
        //No debugging needed right now!! YAY

        telemetry.addData("Status:", "Running TeleOpMode");
        telemetry.update();
    }

    public void stop()
    {
        robot.setPower(0);
        robot.leadScrewMotor.setPower(0);
    }
}
