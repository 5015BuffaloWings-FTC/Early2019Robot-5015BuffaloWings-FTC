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

@Autonomous(name="TestAuto")

public class TestAuto extends LinearOpMode
{
    // Detector object
    private GoldAlignDetector detector;
    Definitions robot = new Definitions();

    @Override
    public void runOpMode() throws InterruptedException
    {
        /**
         * Initialization
         */
        robot.testHardwareMapInit(hardwareMap);//Initializes robot hardware map


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

        //Resets robot values before match

        waitForStart();//Waits until driver clicks the start button


        /**
         * Autonomous starts - Match time of 0 seconds
         */

        //look towards left silver and moves hook out from lander
        robot.runWithOutEncoders();
        robot.setRotateLeft();
        robot.setPower(0.5);
        sleep(400);
        robot.setPower(0);



        //Scans clockwise looking for the Gold Mineral
        robot.setRotateRight();
        while(!detector.getAligned())
        {
            robot.setPower(0.25);
        }
        robot.setPower(0);

        robot.moveInches(robot.BACKWARD,31,0.5);//here
        sleep(10000);//this needs testing
        robot.setPower(0);

        robot.moveInches(robot.FORWARD,31,0.5);
        sleep(10000);//this needs testing
        robot.setPower(0);

        detector.disable();

    }
}
