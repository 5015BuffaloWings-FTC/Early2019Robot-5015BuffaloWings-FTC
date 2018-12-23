//Sat Dec 22, Software: Clean up code, added limit switc h.
//Sat Dec 22, Hardware: Added limit

package org.firstinspires.ftc.teamcode;

        import com.disnodeteam.dogecv.CameraViewDisplay;
        import com.disnodeteam.dogecv.DogeCV;
        import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DigitalChannel;
        import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="CraterAuto")

public class CraterAuto extends LinearOpMode
{
    // Detector object
    private SamplingOrderDetector detector;
    Definitions robot = new Definitions();
    DigitalChannel leadScrewLimitBot;
    int i = 0;

    boolean landing = true;
    boolean sampling = false;
    boolean wallKiss = false;

    @Override
    public void runOpMode()
    {
        leadScrewLimitBot = hardwareMap.get(DigitalChannel.class, "leadScrewLimitBot");
        leadScrewLimitBot.setMode(DigitalChannel.Mode.INPUT);

        robot.robotHardwareMapInit(hardwareMap);
        robot.autoInit();

        telemetry.addData("Status", "DogeCV 2018.0 - Sampling Order Example");

        // Setup detector
        detector = new SamplingOrderDetector(); // Create the detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize detector with app context and camera
        detector.useDefaults(); // Set detector to use default settings

        detector.downscale = 0.4; // How much to downscale the input frames

        // Optional tuning
        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.001;

        detector.ratioScorer.weight = 15;
        detector.ratioScorer.perfectRatio = 1.0;

        detector.enable(); // Start detector



        robot.leadScrewMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        while(leadScrewLimitBot.getState())
        {
            robot.leadScrewMotor.setPower(-0.75);
        }
        robot.leadScrewMotor.setPower(0);
        robot.resetEncoders();
        robot.autoInit();

        waitForStart();


        while(opModeIsActive())
        {
            if(landing)
            {
                robot.leadScrewMotor.setTargetPosition(6000);
                robot.leadScrewMotor.setPower(-1);
                if(robot.leadScrewMotor.getCurrentPosition() == 6000)
                {
                    landing = false;
                    sampling = true;
                }
            }
            if(sampling)
            {
                switch(detector.getCurrentOrder())
                {
                    case RIGHT:
                        robot.cubeRight();
                        break;
                    case LEFT:
                        robot.cubeLeft();
                        break;
                    case CENTER:
                        robot.cubeCenter();
                        break;
                    case UNKNOWN:
                        break;
                    default:
                        break;
                }
                if(robot.leadScrewMotor.getCurrentPosition() == 3000)
                {
                    sampling = false;
                    wallKiss = true;
                }
            }

            if(wallKiss)
            {
                robot.leadScrewMotor.setTargetPosition(6000);
                robot.leadScrewMotor.setPower(-1);
                if(robot.leadScrewMotor.getCurrentPosition() == 6000)
                    wallKiss = true;

            }


            telemetry.addData("Current Order", detector.getCurrentOrder().toString()); // The current result for the frame
            telemetry.addData("Last Order", detector.getLastOrder().toString()); // The last known result
            telemetry.update();
        }

        detector.disable();
    }

}
