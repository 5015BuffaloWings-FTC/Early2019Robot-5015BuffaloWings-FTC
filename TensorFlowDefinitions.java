package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class TensorFlowDefinitions
{
    DcMotor leftFrontMotor = null;
    DcMotor leftBackMotor = null;
    DcMotor rightFrontMotor = null;
    DcMotor rightBackMotor = null;
    DcMotor leadScrewMotor = null;

    //DcMotor scoringArmMotor = null;
    //DcMotor armReelMotor = null;
    //CRServo armServo = null;
    //CRServo armExtendorServo = null;

    public void robotHardwareMapInit(HardwareMap Map)
    {
        //Naming Scheme for configuring robot controller app
        leftBackMotor = Map.dcMotor.get("leftBackMotor");
        leftFrontMotor = Map.dcMotor.get("leftFrontMotor");
        rightBackMotor = Map.dcMotor.get("rightBackMotor");
        rightFrontMotor = Map.dcMotor.get("rightFrontMotor");
        leadScrewMotor = Map.dcMotor.get("leadScrewMotor");
        //scoringArmMotor = Map.dcMotor.get("scoringArmMotor");
        //armReelMotor = Map.dcMotor.get("armReelMotor");
        //armServo = Map.crservo.get("armServo");
        //armExtendorServo = Map.crservo.get("armExtendorServo");
    }
}
