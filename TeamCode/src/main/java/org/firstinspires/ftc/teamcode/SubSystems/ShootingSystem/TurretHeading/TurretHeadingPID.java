package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading;

import static org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass.headingMotor;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedConstants;

public class TurretHeadingPID
{
    private static PIDController controller;



    public static void init(HardwareMap hardwareMap)
    {
        controller = new PIDController(TurretHeadingConstants.p, TurretHeadingConstants.i , TurretHeadingConstants.d);
    }

    public static double moveToPos(double tx)
    {
        controller.setPID(TurretHeadingConstants.p, TurretHeadingConstants.i , TurretHeadingConstants.d);

        double pid =controller.calculate(tx,0);

        double power = pid;

        return power;
    }
    public static double angleToTicks(double angle)
    {
        return angle*TurretHeadingConstants.degreeInTicks;
    }

//    public static class PID implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//
//            controller.setPID(ShootingSpeedConstants.p, ShootingSpeedConstants.i , ShootingSpeedConstants.d);
//
//            double pid = controller.calculate(headingMotor.getCurrentPosition(), angleToTicks(headingAngle));
//
//            headingMotor.setPower(pid);
//
//            return true;
//        }
//    }

//    public static Action pid() {
//        return new PID();
//    }


}
