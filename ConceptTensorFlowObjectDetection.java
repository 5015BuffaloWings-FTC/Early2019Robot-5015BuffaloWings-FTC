/* Copyright (c) 2018 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

//This is for computer vision
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

/**
 * This 2018-2019 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the gold and silver minerals.
 */

@Autonomous(name = "Tensor Auto", group = "Auto")

public class ConceptTensorFlowObjectDetection extends LinearOpMode
{
    TensorFlowDefinitions robot = new TensorFlowDefinitions();//Gets methods and Hardware map from Definitions.java
    DigitalChannel leadScrewLimitBot;//This is where we will store information about the lead screw limit switch

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";//Stores our name for the gold mineral
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";//Stores our name for the silver mineral
    private static final String VUFORIA_KEY = "AfLWK8f/////AAABmXSmhgvQGU6jmwuqG/usGsBAQIf+a0CUGKwi0qmukrDBS/hMaBWNrLgO7KJynjTC4+rBdrKf" +
            "/A/D0UHopEKIIZOkHUrqWzGpJnz7xWDJxVb5A1tg35nNF330u1RppWp741fOIVsA582AvdeTDtCb1tA8hxOB3K6pWnI167b/hWMvxtx/8nSTrFRREyJjRT8ORO" +
            "URP7kXGdPZaBRXVoxqNzCKpr89Nsl/dCd/i2mRuiYwiEuMvF0rLaNSUoTmhpPIj+DZdSQzNCCG2P8X2T6I2eWUTHf1w9bJ40GlyXGq7u8EQATh+QxLYfPL3Z17" +
            "qzX24TaDXUFwyprlrfjf77WuD+1F8tAlAJgrAowXa52hnB2v";

    private VuforiaLocalizer vuforia;//Stores our instance of the Vuforia localization engine
    private TFObjectDetector tfod;//Stores our instance of the Tensor Flow Object Detection engine

    @Override
    public void runOpMode()
    {
        initVuforia();//Initalizes VuforiaLocalizer. This is the input for the TFObjectDetector
        initTfod();// Initalized TensorFlowObjectDetector. This is was will be doing computer vision.

        leadScrewLimitBot = hardwareMap.get(DigitalChannel.class, "leadScrewLimitBot");//Initializes Lead Screw limit Switch
        leadScrewLimitBot.setMode(DigitalChannel.Mode.INPUT);//Sets the mode for REV touch sensor to input

        robot.robotHardwareMapInit(hardwareMap);//Initializes robot hardware map

        telemetry.addData(">", "Press Play to start the match");
        telemetry.update();
        waitForStart();


        if (opModeIsActive())
        {
            tfod.activate();//This activates Tensor Flow

            while (opModeIsActive())
            {

                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions(); //Creates a List of Tensor flow "Recognitions"
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    if (updatedRecognitions.size() == 3) {
                        int goldMineralX = -1;
                        int silverMineral1X = -1;
                        int silverMineral2X = -1;
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                goldMineralX = (int) recognition.getLeft();
                            } else if (silverMineral1X == -1) {
                                silverMineral1X = (int) recognition.getLeft();
                            } else {
                                silverMineral2X = (int) recognition.getLeft();
                            }
                        }
                        if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                            if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                telemetry.addData("Gold Mineral Position", "Left");
                            } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                telemetry.addData("Gold Mineral Position", "Right");
                            } else {
                                telemetry.addData("Gold Mineral Position", "Center");
                            }
                        }
                    }
                    telemetry.update();
                }
            }
        }

        tfod.shutdown();//Shuts down Tensor Flow
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
