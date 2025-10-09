package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@TeleOp(name = "SimpleDriveJava")
public class SimpleDrive extends LinearOpMode {

    private DcMotor motor0;
    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    private DcMotor motor0b;
    private DcMotor motor1b;
    private Servo servo0;
    private CRServo servo1;
    private BNO055IMU imu1;
    private DigitalChannel blueLED;
    private DigitalChannel redLED;
    private DcMotor motor2b;


    float RSX;
    double YawValue;
    float LSY;
    double correctedStrafePower;
    double strafePower;
    float LSX;
    double correctedDrivePower;
    double drivePower;
    double rotatePower;
    private boolean isRedOn = false;
    private boolean isBlueOn = false;
    private boolean wasButtonAPressed = false;
    private boolean wasButtonBPressed = false;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        int motor22;
        int motor02;
        BNO055IMU.Parameters imuParameters;



        motor0 = hardwareMap.get(DcMotor.class, "motor0");
        motor1 = hardwareMap.get(DcMotor.class, "motor1");
        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        motor3 = hardwareMap.get(DcMotor.class, "motor3");
        motor0b = hardwareMap.get(DcMotor.class, "motor0b");
        motor1b = hardwareMap.get(DcMotor.class, "motor1b");
        servo0 = hardwareMap.get(Servo.class, "servo0");
        servo1 = hardwareMap.get(CRServo.class, "servo1");
        imu1 = hardwareMap.get(BNO055IMU.class, "imu 1");
        blueLED = hardwareMap.get(DigitalChannel.class, "blueLED");
        redLED = hardwareMap.get(DigitalChannel.class, "redLED");
        motor2b = hardwareMap.get(DcMotor.class, "motor2b");
        // Put initialization blocks here.

        motor0.setDirection(DcMotor.Direction.FORWARD);
        motor1.setDirection(DcMotor.Direction.REVERSE);
        motor2.setDirection(DcMotor.Direction.REVERSE);
        motor3.setDirection(DcMotor.Direction.FORWARD);
        motor0b.setDirection(DcMotor.Direction.REVERSE);
        motor0b.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1b.setDirection(DcMotor.Direction.FORWARD);
        motor1b.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2b.setDirection(DcMotorSimple.Direction.REVERSE);
        motor2b.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor0b.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor0b.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor1b.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1b.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor0b.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1b.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        servo0.setPosition(0);
        servo1.setDirection(DcMotorSimple.Direction.FORWARD);
        imuParameters = new BNO055IMU.Parameters();
        imuParameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imuParameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imuParameters.loggingEnabled = false;
        imu1.initialize(imuParameters);
        YawValue = imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;
        blueLED.setMode(DigitalChannel.Mode.OUTPUT);
        redLED.setMode(DigitalChannel.Mode.OUTPUT);


        waitForStart();
        if (opModeIsActive()) {





            while (opModeIsActive()) {
                getData();
                sticks1();
                buttons();
                lights();

                telemetry.update();
            }
        }
    }

    /**
     * Describe this function...
     */

    /**
     * Describe this function...
     */
    private void getData() {
        telemetry.addData("Motor 0 Pos", motor0.getCurrentPosition());
        telemetry.addData("Motor 1 Pos", motor1.getCurrentPosition());
        telemetry.addData("motor 2 Pos", motor2.getCurrentPosition());
        telemetry.addData("motor 3 Pos", motor3.getCurrentPosition());
        telemetry.addData("powerMotor0", motor0.getPower());
        telemetry.addData("powerMotor1", motor1.getPower());
        telemetry.addData("powerMotor2", motor2.getPower());
        telemetry.addData("powerMotor3", motor3.getPower());
        telemetry.addData("powerMotor0b", motor0b.getPower());
        telemetry.addData("powerMotor1b", motor1b.getPower());
        telemetry.addData("VelMotor0", ((DcMotorEx) motor0).getVelocity());
        telemetry.addData("VelMotor1", ((DcMotorEx) motor1).getVelocity());
        telemetry.addData("VelMotor2", ((DcMotorEx) motor2).getVelocity());
        telemetry.addData("VelMotor3", ((DcMotorEx) motor3).getVelocity());
        telemetry.update();
    }


    private void buttons() {
        // Run motor0b at full power (1) when button A is pressed, stop when released
        if (gamepad2.dpad_up) {
            motor0b.setPower(1.0); // Move up
        } else if (gamepad2.dpad_down) {
            motor0b.setPower(-1.0); // Move down
        } else {
            motor0b.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor0b.setPower(0); // Stop motor
        }

        telemetry.update();




        if (gamepad2.dpad_left) {
            motor1b.setPower(1.0);
            motor2b.setPower(1.0);
        } else if (gamepad2.dpad_right) {
            motor1b.setPower(-1.0);
            motor2b.setPower(-1.0);
        } else {
            motor1b.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor1b.setPower(0);
            motor2b.setPower(0);
        }

            if (gamepad2.right_bumper) {
                servo0.setPosition(1);
            }
            else { servo0.setPosition(0); }



        if (gamepad2.x) {
            servo1.setPower(1.0); // Move up
        } else if (gamepad2.y) {
            servo1.setPower(-1.0); // Move down
        } else {
           servo1.setPower(0); // Stop motor
        }

    }


    /**
     * Describe this function...
     */
    private void sticks2() {
        double gain;

        // turbo mode
        if (gamepad1.left_bumper) {
            gain = 1;
        } else {
            gain = 0.5;
        }
        strafePower = gain * LSX;
        drivePower = gain * LSY;
        rotatePower = gain * RSX;
        sticks4();
    }

    /**
     * Describe this function...
     */
    private void sticks1() {
        RSX = gamepad1.right_stick_x;
        LSY = gamepad1.left_stick_y;
        LSX = gamepad1.left_stick_x;
        sticks2();
    }



/**
     * Gyro correct values. Code from ChatGTP created code.
     */
    private void sticks4() {
        YawValue = Math.round(imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle) * (Math.PI / 180);
        correctedStrafePower = strafePower * Math.cos(YawValue / 180 * Math.PI) - drivePower * Math.sin(YawValue / 180 * Math.PI);
        correctedDrivePower = drivePower * Math.cos(YawValue / 180 * Math.PI) + strafePower * Math.sin(YawValue / 180 * Math.PI);
        drive2();
    }

    /**
     * Send corrected values to motors
     */
    private void drive2() {
        // Front right motor
        motor0.setPower((correctedDrivePower - correctedStrafePower) - rotatePower);
        // Front left motor
        motor2.setPower(correctedDrivePower + correctedStrafePower + rotatePower);
        // Back right motor
        motor3.setPower((correctedDrivePower - correctedStrafePower) + rotatePower);
        // Back left motor
        motor1.setPower((correctedDrivePower + correctedStrafePower) - rotatePower);
    }

    private void lights(){
        if (gamepad2.a && !wasButtonAPressed) { // Detect button press (not hold)
            isRedOn = !isRedOn;                 // Toggle the state
            redLED.setState(isRedOn);           // Update LED state
        }
        wasButtonAPressed = gamepad2.x;         // Remember the button state


        if (gamepad2.b && !wasButtonBPressed) { // Detect button press (not hold)
            isBlueOn = !isBlueOn;               // Toggle the state
            blueLED.setState(isBlueOn);         // Update LED state
        }
        wasButtonBPressed = gamepad2.y;         // Remember the button state
    }
}

