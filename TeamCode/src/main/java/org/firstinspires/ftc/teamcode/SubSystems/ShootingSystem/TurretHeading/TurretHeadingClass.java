package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.GlobalData;

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


    public static class Reset implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(0);
            return false;
        }
    }
    public static Action reset() {
        return new Reset();
    }

    public static class BlueShootHumanPlayerAngle implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(0);
            return false;
        }
    }
    public static Action blueShootHumanPlayerAngle() {
        return new BlueShootHumanPlayerAngle();
        }


    public static class BlueShootClose1Angle implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(41);
            return false;
        }
    }
    public static Action blueShootClose1Angle() {
        return new BlueShootClose1Angle();
    }

    public static class BlueShootClose2Angle implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(45);
            return false;
        }
    }
    public static Action blueShootClose2Angle() {
        return new BlueShootClose2Angle();
    }


    public static class RedShootHumanPlayerAngle  implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(GlobalData.redShootHumanPlayerAngle );
            return false;
        }
    }
    public static Action redShootHumanPlayerAngle () {
        return new RedShootHumanPlayerAngle();
    }


    public static class RedShootClose1Angle  implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(GlobalData.redShootClose1Angle);
            return false;
        }
    }
    public static Action redShootClose1Angle () {
        return new RedShootClose1Angle();
    }

    public static class RedShootClose2Angle  implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pinpointOperate(GlobalData.redShootClose2Angle);
            return false;
        }
    }
    public static Action redShootClose2Angle () {
        return new RedShootClose2Angle();
    }

//    public static class AtGoal implements Action
//    {
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//            headingAngle = TurretHeadingConstants.atGoalPos;
//            return false;
//        }
//    }
//    public static Action atGoal()
//    {
//        return new AtGoal();
//    }
//
//    public static class FarFromGoal implements Action
//    {
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//            headingAngle = TurretHeadingConstants.farFromGoalPos;
//            return false;
//        }
//    }
//    public static Action farFromGoal()
//    {
//        return new FarFromGoal();
//    }
//
//    public static class LaunchZone implements Action
//    {
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//            headingAngle = TurretHeadingConstants.launchZonePos;
//            return false;
//        }
//    }
//    public static Action launchZone()
//    {
//        return new LaunchZone();
//    }
//
//    public static class Start implements Action
//    {
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//            headingAngle = TurretHeadingConstants.startPos;
//            return false;
//        }
//    }
//    public static Action start()
//    {
//        return new Start();
//    }
}
