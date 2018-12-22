package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class CraterAuto extends LinearOpMode
{
    // Detector object
    private SamplingOrderDetector detector;
    Definitions robot = new Definitions();
    ElapsedTime runTime = new ElapsedTime();


    @Override
    public void runOpMode()
    {
        //do it with switch
        robot.land();

        telemetry.addData("Lead Screw", robot.leadScrewMotor.getCurrentPosition());
        telemetry.addData("Current Order", detector.getCurrentOrder().toString()); // The current result for the frame
        telemetry.addData("Last Order", detector.getLastOrder().toString()); // The last known result
        detector.disable();
    }

}
