package org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedConstants;

public class IntakeClass
{
    private static DcMotor intakeMotor;

    private static CRServo intakeServo;

    public static void init(HardwareMap hardwareMap)
    {
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        //intakeServo = hardwareMap.get(CRServo.class, "intakeServo");

        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeMotor.setDirection(DcMotor.Direction.REVERSE);
    }
    public static void operate(double power)
    {
        intakeMotor.setPower(power);
        //intakeServo.setPower(power);
    }

    public static void telemetry(Telemetry telemetry)
    {
        telemetry.addData("intakeMotor:", intakeMotor.getPower());
    }
    public static class Activate implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            operate(1);
            return false;
        }
    }
    public static Action activate() {
        return new Activate();
    }

    public static class ShootFarFromGoal implements Action
    {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
//            if ((ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio > 0) && (ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio < 150))
//            {
//                IntakeClass.operate(-1);
//            }
            if (ShootingSpeedClass.inTolerence(ShootingSpeedConstants.farFromGoalSpeed, 135))
            {
                operate(1);
            }
            else if(ShootingSpeedClass.targetSpeed == 0)
            {
                operate(1);
            }
            else
            {
                operate(0);
            }

            return true;
        }
    }

    public static Action shootfarfromgoal() {
        return new ShootFarFromGoal();
    }

    public static class ShootFar implements Action
    {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if ((ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio > 0) && (ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio < 150))
            {
                IntakeClass.operate(-1);
            }
            else if (ShootingSpeedClass.inTolerence(ShootingSpeedConstants.launchZoneSpeed, 200))
            {
                operate(1);
            }
            else if(ShootingSpeedClass.targetSpeed == 0)
            {
                operate(1);
            }
            else
            {
                operate(0);
            }

            return true;
        }
    }

    public static Action shootFar() {
        return new ShootFar();
    }
    public static class Deactivate implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            operate(0);
            return false;
        }
    }
    public static Action deactivate() {
        return new Deactivate();
    }
}