package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle;


import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.Utility.MathUtil.MathUtilClass;

public class HoodAngleClass
{
    private static Servo hoodServo;
    private static double servoPos;

    private static boolean lastChange = false;


    public static void init(HardwareMap hardwareMap)
    {
        hoodServo = hardwareMap.servo.get("hoodServo");

        hoodServo.setDirection(Servo.Direction.REVERSE);
    }


    public static void setPos(double pos)
    {
        hoodServo.setPosition(MathUtilClass.clamp(pos, HoodAngleConstants.hoodAngleMin, HoodAngleConstants.hoodAngleMax));
    }


    public static void test(Gamepad gamepad)
    {
        if (gamepad.dpad_up)
        {
            if (!lastChange)
            {
                servoPos +=0.02;
                lastChange = true;
            }
        }
        else if(gamepad.dpad_down)
        {
            if (!lastChange)
            {
                servoPos -=0.02;
                lastChange = true;
            }
        }
        else
        {
            lastChange = false;
        }

        hoodServo.setPosition(servoPos);
    }

    public static void telemetry(Telemetry telemetry)
    {
        telemetry.addData("hoodServoAngle:" , hoodServo.getPosition());
    }

    public static class AtGoal implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            setPos(HoodAngleConstants.atGoalPos);
            return false;
        }
    }
    public static Action atGoal() {
        return new AtGoal();
    }
    public static class FarFromGoal implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            setPos(HoodAngleConstants.farFromGoalPos);
            return false;
        }
    }
    public static Action farFromGoal() {
        return new FarFromGoal();
    }


}
