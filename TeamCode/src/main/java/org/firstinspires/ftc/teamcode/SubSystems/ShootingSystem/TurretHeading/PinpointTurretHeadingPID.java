package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading;

import static org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass.masterShootingMotor;
import static org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass.targetSpeed;
import static org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass.headingMotor;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.teamcode.GlobalData;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedConstants;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;

public class PinpointTurretHeadingPID
{
    private static PIDController controller;
    private static double trueAngle = 0;
    private static double power = 0;

    private static double zeroAngle = 0;

    public static double wantedAngle = 0;



    public static void init(HardwareMap hardwareMap)
    {
        controller = new PIDController(TurretHeadingConstants.p, TurretHeadingConstants.i , TurretHeadingConstants.d);
    }

    public static double getTrueAngle()
    {
        return trueAngle;
    }


    public static double moveToPos(double angle)
    {
        wantedAngle = angle;

        controller.setPID(TurretHeadingConstants.p, TurretHeadingConstants.i , TurretHeadingConstants.d);

        double motorAngle = headingMotor.getCurrentPosition()/TurretHeadingConstants.degreeInTicks;

        if (wantedAngle > 160)
        {
            trueAngle = wantedAngle - 320;
        }
        else if (wantedAngle < -160)
        {
            trueAngle = 320 + wantedAngle;
        }
        else
        {
            trueAngle = wantedAngle;
        }

        double pid = controller.calculate(motorAngle, trueAngle);

        power = pid;

        return (power + (TurretHeadingConstants.f * Math.signum(trueAngle)))*-1;
    }

    public static class PID implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            headingMotor.setPower(moveToPos(wantedAngle));

            return true;
        }
    }

    public static Action pid() {
        return new PID();
    }

}
