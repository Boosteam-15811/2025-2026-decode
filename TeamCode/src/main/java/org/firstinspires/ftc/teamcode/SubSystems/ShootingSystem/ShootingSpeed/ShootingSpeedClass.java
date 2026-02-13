package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ShootingSpeedClass
{
    public static DcMotorEx masterShootingMotor;

    public static double targetSpeed;

    private static double error;



    public static void init(HardwareMap hardwareMap)
    {

        masterShootingMotor = hardwareMap.get(DcMotorEx.class , "masterShootingMotorSpeed");


        masterShootingMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        masterShootingMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        masterShootingMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        masterShootingMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

    }

    public static void setSpeed(double speed)
    {
        targetSpeed = speed;
        if (targetSpeed == 0)
        {
            masterShootingMotor.setMotorDisable();
        }
        else {
            masterShootingMotor.setPower(ShootingSpeedPID.updateMotorOutput(targetSpeed, masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio));
        }
    }
    public static boolean inTolerence(double speed, double tolerance)
    {
        error = speed - masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio;

        return Math.abs(error) < tolerance;

    }


    public static void telemetry(Telemetry telemetry)
    {
        //telemetry.addData("error", error);
        telemetry.addData("flywheel rpm" , masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio);
        //telemetry.addData("motorPower" , masterShootingMotor.getPower());
        //telemetry.addData("error" , 2200-masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio);
        //telemetry .addData("in tolerance" , inTolerence(ShootingSpeedStates.ATGOAL));
    }

    public static class AtGoal implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            targetSpeed = ShootingSpeedConstants.atGoalSpeed;
            return false;
        }
    }
    public static Action atGoal() {
        return new AtGoal();
    }

    public static class FarFromGoal implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            targetSpeed = ShootingSpeedConstants.farFromGoalSpeed;
            return false;
        }
    }
    public static Action farFromGoal() {
        return new FarFromGoal();
    }

    public static class Disabled implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            targetSpeed = 0;
            return false;
        }
    }
    public static Action disabled() {
        return new Disabled();
    }
}
