package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TurretHeadingClass
{
    public static DcMotor headingMotor;

    private static double tx = 0;


    public static void init(HardwareMap hardwareMap)
    {
        headingMotor = hardwareMap.dcMotor.get("headingMotor");

        headingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        headingMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        headingMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
     }

    public static void pinpointOperate(double wantedAngle)
    {
        headingMotor.setPower(PinpointTurretHeadingPID.moveToPos(wantedAngle));
    }

    public static void setTx(double Tx)
    {
        tx = Tx;
    }

    public static void setPos(double pos)
    {

    }

    public static void test(Gamepad gamepad)
    {
        if (gamepad.right_trigger > 0)
        {
            headingMotor.setPower(gamepad.right_trigger);
        }
        else if (gamepad.left_trigger > 0)
        {
            headingMotor.setPower(-gamepad.left_trigger);
        }
        else
        {
            headingMotor.setPower(0);
        }
    }


    public static void telemetry(Telemetry telemetry)
    {
        telemetry.addData("headingMotorPos" , headingMotor.getCurrentPosition()/TurretHeadingConstants.degreeInTicks);
    }

    public static class BlueFarShootAngle1 implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(67);
            return false;
        }
    }
    public static Action blueFarShootAngle1() {
        return new BlueFarShootAngle1();
    }

    public static class BlueFarShootAngle2 implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(69);
            return false;
        }
    }
    public static Action blueFarShootAngle2() {
        return new BlueFarShootAngle2();
    }


    public static class BlueCloseShootAngle1 implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(41);
            return false;
        }
    }
    public static Action blueCloseShootAngle1() {
        return new BlueCloseShootAngle1();
    }

    public static class BlueCloseShootAngle2 implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(48);
            return false;
        }
    }
    public static Action blueCloseShootAngle2() {
        return new BlueCloseShootAngle2();
    }

    public static class BlueCloseShootAngle3 implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(45);
            return false;
        }
    }
    public static Action blueCloseShootAngle3() {
        return new BlueCloseShootAngle3();
    }

    public static class EndAutoAngle implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(0);
            return false;
        }
    }
    public static Action endAutoAngle() {
        return new EndAutoAngle();
    }


    public static class RedFarShootAngle1 implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(-67);
            return false;
        }
    }
    public static Action redFarShootAngle1() {
        return new RedFarShootAngle1();
    }

    public static class RedFarShootAngle2 implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(-69);
            return false;
        }
    }
    public static Action redFarShootAngle2() {
        return new RedFarShootAngle2();
    }


    public static class RedCloseShootAngle1 implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(-41);
            return false;
        }
    }
    public static Action redCloseShootAngle1() {
        return new RedCloseShootAngle1();
    }

    public static class RedCloseShootAngle2 implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(-48);
            return false;
        }
    }
    public static Action redCloseShootAngle2() {
        return new RedCloseShootAngle2();
    }

    public static class RedCloseShootAngle3 implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(-45);
            return false;
        }
    }
    public static Action redCloseShootAngle3() {
        return new RedCloseShootAngle3();
    }
}
