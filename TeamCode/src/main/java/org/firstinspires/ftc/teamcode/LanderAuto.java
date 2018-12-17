package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Lander Auto")
public class LanderAuto extends LinearOpMode
{
    //1440New, Redesigned Circuit Display
    Definitions robot = new Definitions();
    ElapsedTime runTime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException
    {
        robot.robotHardwareMapInit(hardwareMap);
        robot.autoInit();
        waitForStart();
        int i = 0;

        while(opModeIsActive())
        {
            if(!robot.leftBackMotor.isBusy())
            {
                i++;
            }
            switch(i)
            {
                case 1:
                    robot.moveInches(robot.BACKWARD, 12, 0.5);
                    //robot.setDriveBackward();
                    //robot.moveInches(12,0.5);
                    //break;

                case 2:
                    robot.moveInches(robot.STRAFERIGHT, 12, 0.5);
                    //robot.setStrafeRight();
                    //robot.moveInches(12, 0.5);
                default:
                    break;
            }




            //landing
            //robot.leadScrewMotor.setTargetPosition(6312);//change up value
            //robot.leadScrewMotor.setPower(1);

            //getting off the

            //lining up to cubes
            //if(!robot.leftBackMotor.isBusy()) {
              //  robot.setDriveBackward();
                //robot.moveInches(12, 0.5);


            //}

            //robot.setStrafeRight();
            //robot.moveInches(2, 0.5);

            //sampling .argb() measures the input as a hue
            //if(df.format(robot.leftColorSensor.argb()) == df.format(robot.colorSensorRight.argb()))
            //{
            //    cubeLeft();
            //}
            //else if(df.format(robot.leftColorSensor))

        }


        telemetry.addLine("Telemetry\n");
        telemetry.addData("Time", runTime);
        telemetry.addData("Right Color Sensor", robot.rightColorSensor.argb());
        telemetry.addData("Left Color Sensor", robot.leftColorSensor.argb());
        telemetry.update();
    }
}