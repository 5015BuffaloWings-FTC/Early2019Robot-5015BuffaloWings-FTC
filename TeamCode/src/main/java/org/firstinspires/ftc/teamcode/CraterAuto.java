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
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="CraterAuto")

public class CraterAuto extends LinearOpMode {
    // Detector object
    private GoldAlignDetector detector;
    Definitions robot = new Definitions();
    DigitalChannel leadScrewLimitBot;

    @Override
    public void runOpMode() throws InterruptedException {
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
        detector.downscale = 0.4;// How much to downscale the input frames
        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA;//Camera input tuning
        detector.maxAreaScorer.weight = 0.001;
        detector.ratioScorer.weight = 15;
        detector.ratioScorer.perfectRatio = 1.0;
        detector.enable();//Start detector

        //Moves lead screw to lowest position
        robot.leadScrewMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        while (leadScrewLimitBot.getState()) {
            robot.leadScrewMotor.setPower(0.75);
        }
        robot.leadScrewMotor.setPower(0);

        //Resets robot values before match
        robot.resetEncoders();//Resets encoder tick values, sets motor PID mode to STOP_AND_RESET_ENCODERS
        robot.servoInit();//Initialized servos to starting positions

        waitForStart();//Waits until driver clicks the start button


        /**
         * Autonomous starts - Match time of 0 seconds
         */
        if (opModeIsActive()) {
            //Robot drops from the lander, but is still attached
            robot.leadScrewMotor.setTargetPosition(-6100);
            robot.leadScrewMotor.setPower(-1);
            sleep(12000);
            robot.leadScrewMotor.setPower(0);

            robot.runWithOutEncoders();
            robot.setRotateRight();
            robot.setPower(0.5);
            sleep(300);
            robot.setPower(0);

            robot.moveInches(robot.STRAFERIGHT, 5, 1);
            sleep(1000);
            robot.setPower(0);

            robot.moveInches(robot.FORWARD, 5, 1);
            sleep(1000);
            robot.setPower(0);

            robot.moveInches(robot.STRAFELEFT, 5, 1);
            sleep(1000);
            robot.setPower(0);

            robot.scoringArmMotor.setTargetPosition(-100);
            robot.scoringArmMotor.setPower(-1);
            sleep(3000);
            robot.scoringArmMotor.setPower(0);

            robot.runWithOutEncoders();
            robot.armReelMotor.setPower(0.5);
            sleep(4000);
            robot.armReelMotor.setPower(0);


            //look towards left silver and moves hook out from lander
            robot.runWithOutEncoders();
            robot.setRotateLeft();
            robot.setPower(0.5);
            sleep(400);
            robot.setPower(0);


            //Scans clockwise looking for the Gold Mineral
            robot.setRotateRight();
            while (!detector.getAligned()) {
                robot.moveInches(robot.FORWARD, 31, 1);
                sleep(4000);//this needs testing
                robot.setPower(0);

                robot.moveInches(robot.BACKWARD, 31, 1);
                sleep(4000);//this needs testing
                robot.setPower(0);
            }

            detector.disable();

        }
    }
}