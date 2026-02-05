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
    public static DcMotorEx slaveShootingMotor;


    public static double masterShootingMotorSpeed;
    private static double slaveShootingMotorSpeed;

    private static double error;



    public static void init(HardwareMap hardwareMap)
    {

        masterShootingMotor = hardwareMap.get(DcMotorEx.class , "masterShootingMotorSpeed");
        slaveShootingMotor = hardwareMap.get(DcMotorEx.class , "slaveShootingMotorSpeed");


        masterShootingMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        slaveShootingMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        masterShootingMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slaveShootingMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        masterShootingMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slaveShootingMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        masterShootingMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        slaveShootingMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

    }

    public static void operate(ShootingSpeedStates shootingSpeedStates)
    {
        switch (shootingSpeedStates)
        {
            case LAUNCHZONE:
            {
                masterShootingMotorSpeed = ShootingSpeedConstants.launchZoneSpeed;
                slaveShootingMotorSpeed = ShootingSpeedConstants.launchZoneSpeed;
                break;
            }
            case ATGOAL:
            {
                masterShootingMotorSpeed = ShootingSpeedConstants.atGoalSpeed;
                slaveShootingMotorSpeed = ShootingSpeedConstants.atGoalSpeed;
                break;
            }
            case FARFROMGOAL:
            {
                masterShootingMotorSpeed = ShootingSpeedConstants.farFromGoalSpeed;
                slaveShootingMotorSpeed = ShootingSpeedConstants.farFromGoalSpeed;
                break;
            }
            case DISABLED:
            {
                masterShootingMotorSpeed = ShootingSpeedConstants.disabledSpeed;
                slaveShootingMotorSpeed = ShootingSpeedConstants.disabledSpeed;
                break;
            }
        }
        if ((ShootingSpeedPID.updateMotorOutput(masterShootingMotorSpeed, masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio) == 0))
        {
            masterShootingMotor.setMotorDisable();
            slaveShootingMotor.setMotorDisable();
        }
        else {
            masterShootingMotor.setPower(ShootingSpeedPID.updateMotorOutput(masterShootingMotorSpeed, masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio));
            slaveShootingMotor.setPower(ShootingSpeedPID.updateMotorOutput(masterShootingMotorSpeed, masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio));
        }
    }
    public static boolean inTolerence(ShootingSpeedStates shootingSpeedStates)
    {
        double speed;
        switch (shootingSpeedStates)
        {
            default:
            case LAUNCHZONE:
            {
                speed = ShootingSpeedConstants.launchZoneSpeed;
                break;
            }
            case ATGOAL:
            {
                speed = ShootingSpeedConstants.atGoalSpeed;
                break;
            }
            case FARFROMGOAL:
            {
                speed = ShootingSpeedConstants.farFromGoalSpeed;
                break;
            }
            case DISABLED:
            {
                speed = ShootingSpeedConstants.disabledSpeed;
                break;
            }
        }
        error = speed - masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio;


        return Math.abs(error) < ShootingSpeedConstants.tolerance;

    }

    public static void telemetry(Telemetry telemetry)
    {
        telemetry.addData("error", error);
        telemetry.addData("flywheel rpm" , masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio);
        //telemetry.addData("motorPower" , masterShootingMotor.getPower());
        //telemetry.addData("error" , 2200-masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio);
        telemetry .addData("in tolerance" , inTolerence(ShootingSpeedStates.ATGOAL));
    }

    public static class AtGoal implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            masterShootingMotorSpeed = ShootingSpeedConstants.atGoalSpeed;
            return false;
        }
    }
    public static Action atGoal() {
        return new AtGoal();
    }

    public static class FarFromGoal implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            masterShootingMotorSpeed = ShootingSpeedConstants.farFromGoalSpeed;
            return false;
        }
    }
    public static Action farFromGoal() {
        return new FarFromGoal();
    }

    public static class Disabled implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            masterShootingMotorSpeed = 0;
            return false;
        }
    }
    public static Action disabled() {
        return new Disabled();
    }
}
