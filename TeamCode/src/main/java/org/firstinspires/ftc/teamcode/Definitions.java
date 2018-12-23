package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.configuration.annotations.DigitalIoDeviceType;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorDigitalTouch;


public class Definitions
{
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
    Servo ballStopperServo = null;
    Servo scoringArmLatchServo = null;

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
        scoringArmReleaseServo = Map.servo.get("scoringArmReleaseServo");
        ballStopperServo = Map.servo.get("ballStopperServo");
        scoringArmLatchServo = Map.servo.get("scoringArmLatchServo");
    }

    public void testHardwareMapInit(HardwareMap Map)
    {
        //Naming Scheme for configuring robot controller app
        leftBackMotor = Map.dcMotor.get("leftBackMotor");
        leftFrontMotor = Map.dcMotor.get("leftFrontMotor");
        rightBackMotor = Map.dcMotor.get("rightBackMotor");
        rightFrontMotor = Map.dcMotor.get("rightFrontMotor");
        scoringArmMotor = Map.dcMotor.get("scoringArmMotor");
        leadScrewMotor = Map.dcMotor.get("leadScrewMotor");
        ballStopperServo = Map.servo.get("ballStopperServo");
        scoringArmLatchServo = Map.servo.get("scoringArmLatchServo");
    }

    void servoInit()
    {
        scoringArmReleaseServo.setPosition(0);
        ballStopperServo.setPosition(0);
        scoringArmLatchServo.setPosition(1);
    }

    public void teleOpInit()
    {
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        servoInit();
    }

    public void autoInit()
    {
        resetEncoders();
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leadScrewMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        servoInit();
    }

    void runWithOutEncoders()
    {
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public int inchesToTicks(double inches)
    {
        return (int) ((1120 / (Math.PI * 4)) * inches);
    }


    //Used For
    public void moveInches(int direction, double inches, double power)
    {
        switch (direction) {
            case FORWARD: //Forward
                setDriveForward();
                moveInches(inches, power);
            case BACKWARD: //Backwards
                setDriveBackward();
                moveInches(inches, power);
            case STRAFERIGHT: //Strafe Right
                setStrafeLeft();
                moveInches(inches, power);
            case STRAFELEFT: //Strafe Left
                setStrafeRight();
                moveInches(inches, power);
            default:
                break;
        }
    }

    public void moveInches(double inches, double power)
    {
        leftBackMotor.setTargetPosition(inchesToTicks(inches));
        leftBackMotor.setPower(power);
        rightBackMotor.setTargetPosition(inchesToTicks(inches));
        rightBackMotor.setPower(power);
        leftFrontMotor.setTargetPosition(inchesToTicks(inches));
        leftFrontMotor.setPower(power);
        rightFrontMotor.setTargetPosition(inchesToTicks(inches));
        rightFrontMotor.setPower(power);
    }

    void resetEncoders()
    {
        leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leadScrewMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    boolean isRobotBusy()
    {
        if(leftBackMotor.isBusy() || leftFrontMotor.isBusy() || rightFrontMotor.isBusy() ||
                rightBackMotor.isBusy() || leadScrewMotor.isBusy())
            return true;
        else
            return false;
    }

    void setPower(double power)
    {
        leftBackMotor.setPower(power);
        leftFrontMotor.setPower(power);
        rightBackMotor.setPower(power);
        rightFrontMotor.setPower(power);
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

    void setRotateRight()
    {
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    void setRotateLeft()
    {
        leftBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }
}
