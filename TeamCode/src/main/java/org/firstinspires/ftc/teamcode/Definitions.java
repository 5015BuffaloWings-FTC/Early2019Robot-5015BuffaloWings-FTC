package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Definitions
{
    public final int FORWARD = 0;
    public final int BACKWARD = 1;
    public final int STRAFELEFT = 2;
    public final int STRAFERIGHT = 3;

    DcMotor leftFrontMotor = null;
    DcMotor leftBackMotor = null;
    DcMotor rightFrontMotor = null;
    DcMotor rightBackMotor = null;
    //DcMotor scoringArmMotor = null;
    DcMotor armReelMotor = null;
    DcMotor leadScrewMotor = null;
    CRServo teamMarkerServo = null;
    DigitalChannel leadScrewLimitBot = null;
    //CRServo armServo = null;

    public void robotHardwareMapInit(HardwareMap Map)
    {
        //Naming Scheme for configuring robot controller app
        leftBackMotor = Map.dcMotor.get("leftBackMotor");
        leftFrontMotor = Map.dcMotor.get("leftFrontMotor");
        rightBackMotor = Map.dcMotor.get("rightBackMotor");
        rightFrontMotor = Map.dcMotor.get("rightFrontMotor");
        leadScrewMotor = Map.dcMotor.get("leadScrewMotor");
        teamMarkerServo = Map.crservo.get("teamMarkerServo");
        leadScrewLimitBot = Map.digitalChannel.get("leadScrewLimitBot");
        //scoringArmMotor = Map.dcMotor.get("scoringArmMotor");
        armReelMotor = Map.dcMotor.get("armReelMotor");
        //armServo = Map.crservo.get("armServo");
        //armExtendorServo = Map.crservo.get("armExtendorServo");
    }

    public void dogeCVInit(GoldAlignDetector detector)
    {

    }

    void runWithOutEncoders()
    {
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leadScrewMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //scoringArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //armReelMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public int inchesToTicks(double inches)
    {
        return (int) ((1120 / (Math.PI * 4)) * inches);
    }

    //This is used to move a specified number of inches in a gived direction
    //Sample input robot.moveInches(robot.FORWARD, 12, 0.5);
    public void moveInches(int direction, double inches, double power)
    {
        resetEncoders();//This will make sure all encoders are reset. This is crucial.
        switch (direction)//This takes the input of the robot direction
        {
            case FORWARD: //Forward
                setDriveForward();
                moveInches(inches, power);
                break;
            case BACKWARD: //Backwards
                setDriveBackward();
                moveInches(inches, power);
                break;
            case STRAFERIGHT: //Strafe Right
                setStrafeRight();
                moveInches(inches, power);
                break;
            case STRAFELEFT: //Strafe Left
                setStrafeLeft();
                moveInches(inches, power);
                break;
            default:
                break;
        }
    }

    public void moveInches(double inches, double power)
    {
        resetEncoders();
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
        //armReelMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //scoringArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leadScrewMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //armReelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //scoringArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    void setStrafeRight()
    {
        leftBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
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
