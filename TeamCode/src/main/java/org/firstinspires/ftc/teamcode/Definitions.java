package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
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

    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor rightFront;
    DcMotor rightBack;
    DcMotor armMotor1;
    CRServo release;
    Servo ballStopper;
    CRServo latch;

    //Constructor to initalize variables

    public void robotHardwareMapInit(HardwareMap Map)
    {
        //Finds Motors in each of the Expansion Hubs
        leftBack = Map.dcMotor.get("leftBack");
        leftFront = Map.dcMotor.get("leftFront");
        rightBack = Map.dcMotor.get("rightBack");
        rightFront = Map.dcMotor.get("rightFront");
        armMotor1 = Map.dcMotor.get("arm");
        release = Map.crservo.get("release");
        ballStopper = Map.servo.get("ballStopper");
        latch = Map.crservo.get("latch");
    }

    public void init()
    {
        DcMotor leftFront = null;
        DcMotor leftBack = null;
        DcMotor rightFront = null;
        DcMotor rightBack = null;
        DcMotor armMotor1 = null;
        CRServo release = null;
        Servo ballStopper = null;
        CRServo latch = null;
    }


    void encoderInit()
    {
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    void setDriveForward()
    {
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
    }


    void setDriveBackward()
    {
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    void setRot()
    {
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    void setStrafeLeft()
    {
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    void setStrafeRight()
    {
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    void setPower(double power)
    {
        leftBack.setPower(power);
        leftFront.setPower(power);
        rightBack.setPower(power);
        rightFront.setPower(power);
    }

    void setPos(int pos)
    {
        leftBack.setTargetPosition(pos);
        leftFront.setTargetPosition(pos);
        rightBack.setTargetPosition(pos);
        rightFront.setTargetPosition(pos);
    }

    void setRotPos(int pos)
    {
        leftBack.setTargetPosition((pos*1120)/360);
        leftFront.setTargetPosition((pos*1120)/360);
        rightBack.setTargetPosition((pos*1120)/360);
        rightFront.setTargetPosition((pos*1120)/360);
    }

    void runPos()
    {
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    void resetEncoders()
    {
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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

        setPower(power);
        setPos(inchesToMove);
        runPos();


    }

    void rotate(double power, int degreesToRoatate)
    {
        setRot();
        setPower(power);
        setRotPos(degreesToRoatate);
    }

}
