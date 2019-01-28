//Sat Dec 22, Software: Clean up code, added limit switc h.
//Sat Dec 22, Hardware: Added limit

package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="CraterAuto")

public class CraterAuto extends LinearOpMode
{
    // Detector object
    GoldAlignDetector detector;
    Definitions robot = new Definitions();
    DigitalChannel leadScrewLimitBot;

    @Override
    public void runOpMode() throws InterruptedException
    {
        /**
         * Initialization
         */
        leadScrewLimitBot = hardwareMap.get(DigitalChannel.class, "leadScrewLimitBot");//Initalizes REV touch sensor - for lead screw limit switch
        leadScrewLimitBot.setMode(DigitalChannel.Mode.INPUT);//Sets the mode for REV touch sensor to input
        robot.robotHardwareMapInit(hardwareMap);//Initializes robot hardware map

        //Setup detector
        detector = new GoldAlignDetector();// Create the detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());// Initialize detector with app context and camera
        detector.useDefaults();// Set detector to use default settings
        detector.downscale = 0.4;//How much to downscale the input frames
        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA;//Camera input tuning
        detector.maxAreaScorer.weight = 0.001;
        detector.ratioScorer.weight = 15;
        detector.ratioScorer.perfectRatio = 1.0;
        detector.enable();//Start detector

        //Resets the lead screw to lowest position
        robot.leadScrewMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        while (leadScrewLimitBot.getState()) {
            robot.leadScrewMotor.setPower(0.75);
        }
        robot.leadScrewMotor.setPower(0);

        robot.teamMarkerServo.setPower(0);

        //Resets robot values before match1
        robot.resetEncoders();//Resets encoder tick values, sets motor PID mode to STOP_AN1D_RESET_ENCODERS
        //robot.servoInit();//Initialized servos to starting positions1

        waitForStart();//Waits until driver clicks the start button1



        /**
         * Autonomous starts - Match time of 0 seconds
         */

        if (opModeIsActive()) {
            //Robot drops from the lander, but is still attached
            robot.leadScrewMotor.setTargetPosition(-7000);
            robot.leadScrewMotor.setPower(-1);
            sleep(13400);
            robot.leadScrewMotor.setPower(0);

            robot.moveInches(robot.FORWARD,12,1);
            sleep(750);
            robot.setPower(0);

            robot.setRotateRight();
            robot.moveInches(36,1);
            sleep(2000);
            robot.setPower(0);

            robot.moveInches(robot.STRAFERIGHT,17,0.75);
            sleep(900);
            robot.setPower(0);

            robot.runWithOutEncoders();
            robot.setStrafeLeft();
            while(!detector.getAligned() && opModeIsActive())
            {
                robot.setPower(0.35);
            }
            robot.setPower(0);

            robot.moveInches(robot.STRAFERIGHT,4,0.35);
            sleep(1000);
            robot.setPower(0);

            robot.setDriveBackward();
            robot.moveInches(30, 1);
            sleep(1000);

            robot.teamMarkerServo.setPower(1);
            sleep(300);
            robot.teamMarkerServo.setPower(0);



            detector.disable();

        }

    }
}
