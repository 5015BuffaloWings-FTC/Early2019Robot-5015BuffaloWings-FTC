package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


public class Definitions
{

    ElapsedTime runtime = new ElapsedTime(); // Defines the Up Time of the program


    public final int FORWARD = 0;
    public final int BACKWARD = 1;
    public final int STRAFELEFT = 2;
    public final int STRAFERIGHT = 3;
    public final int ROTATE = 4;

    DcMotor leftFrontMotor = null;
    DcMotor leftBackMotor = null;
    DcMotor rightFrontMotor = null;
    DcMotor rightBackMotor = null;
    DcMotor scoringArmMotor = null;
    DcMotor leadScrewMotor = null;
    Servo scoringArmReleaseServo = null;
    Servo ballStopServo = null;
    Servo armLatchServo = null;
    TouchSensor leadScrewLimitTop;
    TouchSensor leadScrewLimitBot;

    //Constructor to initialize variables

    public Definitions()
    {

    }

    public void robotHardwareMapInit(HardwareMap Map)
    {
        //Naming Scheme for configuring robot controller app
        leftBackMotor = Map.dcMotor.get("leftBackMotor");
        leftFrontMotor = Map.dcMotor.get("leftFrontMotor");
        rightBackMotor = Map.dcMotor.get("rightBackMotor");
        rightFrontMotor = Map.dcMotor.get("rightFrontMotor");
        scoringArmMotor = Map.dcMotor.get("scoringArmMotor");
        leadScrewMotor = Map.dcMotor.get("leadScrewMotor");
        scoringArmReleaseServo = Map.servo.get("armReleaseServo");
        ballStopServo = Map.servo.get("ballStopper");
        armLatchServo = Map.servo.get("latch");
        //leadScrewLimitTop = Map.touchSensor.get("leadScrewLimitTop");
        //leadScrewLimitBot = Map.touchSensor.get("leadScrewLimitBot");
    }

    void init()
    {
         leftFrontMotor.setPower(0);
         leftBackMotor.setPower(0);
         rightFrontMotor.setPower(0);
         rightBackMotor.setPower(0);
         scoringArmMotor.setPower(0);
         leadScrewMotor.setPower(0);
    }

    void servoInit()
    {
        scoringArmReleaseServo.setPosition(0);
        ballStopServo.setPosition(0);
        armLatchServo.setPosition(0);
    }

    void encoderInit()
    {
        resetEncoders();
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    void setDriveForward()
    {
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }


    void setDriveBackward()
    {
        leftBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    void setRot()
    {
        leftBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    void setStrafeLeft()
    {
        leftBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    void setStrafeRight()
    {
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    void setPower(double power)
    {
        leftBackMotor.setPower(power);
        leftFrontMotor.setPower(power);
        rightBackMotor.setPower(power);
        rightFrontMotor.setPower(power);
    }

    void setPos(int pos)
    {
        leftBackMotor.setTargetPosition(pos);
        leftFrontMotor.setTargetPosition(pos);
        rightBackMotor.setTargetPosition(pos);
        rightFrontMotor.setTargetPosition(pos);
    }

    void setRotPos(int pos)
    {
        leftBackMotor.setTargetPosition((pos*1120)/360);
        leftFrontMotor.setTargetPosition((pos*1120)/360);
        rightBackMotor.setTargetPosition((pos*1120)/360);
        rightFrontMotor.setTargetPosition((pos*1120)/360);
    }

    void runPos()
    {
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    void resetEncoders()
    {
        leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    void moveInches(int direction, double power, int inchesToMove)
    {
        resetEncoders();
        switch(direction)
        {
            case FORWARD:
                setDriveForward();
            case BACKWARD:
                setDriveBackward();
            case STRAFELEFT:
                setStrafeLeft();
            case STRAFERIGHT:
                setStrafeRight();
        }

        setPos(inchesToMove);
        setPower(power);
        runPos();


    }

    void rotate(double power, int degreesToRoatate)
    {
        setRot();
        setPower(power);
        setRotPos(degreesToRoatate);
    }

}
