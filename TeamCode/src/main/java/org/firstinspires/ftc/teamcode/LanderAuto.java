package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.text.DecimalFormat;

@Autonomous(name = "Lander Auto")
public class LanderAuto extends LinearOpMode
{
    //1440New, Redesigned Circuit Display
    Definitions robot = new Definitions();
    DecimalFormat df = new DecimalFormat("#");
    ElapsedTime runTime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException
    {
        robot.robotHardwareMapInit(hardwareMap);
        robot.autoInit();
        waitForStart();

        while(opModeIsActive())
        {
            //landing
            robot.leadScrewMotor.setTargetPosition(1234);//change up value
            robot.leadScrewMotor.setPower(1);

            //lining up to cubes
            robot.setDriveBackward();
            robot.moveInches(12, 0.5);

            robot.setStrafeRight();
            robot.moveInches(12, 0.5);

            //sampling .argb() measures the input as a hue
            //if(df.format(robot.colorSensorLeft.argb()) == df.format(robot.colorSensorRight.argb()))
            //{
            //    cubeLeft();
            //}
            //else if(df.format(robot.colorSensorLeft))





        }


        telemetry.addLine("Telemetry\n");
        telemetry.addData("Time", runTime);
        telemetry.addData("Right Color Sensor", robot.colorSensorRight.argb());
        telemetry.addData("Left Color Sensor", robot.colorSensorLeft.argb());
        telemetry.update();
    }
}