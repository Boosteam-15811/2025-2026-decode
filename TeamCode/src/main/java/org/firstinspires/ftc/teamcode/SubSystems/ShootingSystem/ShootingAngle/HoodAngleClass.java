package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle;


import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;

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

    public static void operate(HoodAngleStates hoodAngleState)
    {
        switch (hoodAngleState)
        {
            case ATGOAL:
                servoPos = HoodAngleConstants.atGoalPos;
                break;
            case LAUNCHZONE:
                servoPos = HoodAngleConstants.launchZonePos;
                break;
            case FARFROMGOAL:
                servoPos = HoodAngleConstants.farFromGoalPos;
                break;
            case DISABLED:
                servoPos = HoodAngleConstants.disabledPos;
                break;
        }
        hoodServo.setPosition(servoPos);
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

    public static void tuning(double wantedPos)
    {
        hoodServo.setPosition(wantedPos);
    }

    public static void telemetry(Telemetry telemetry)
    {
        telemetry.addData("hoodServoAngle:" , hoodServo.getPosition());
    }

    public static class AtGoal implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            operate(HoodAngleStates.ATGOAL);
            return false;
        }
    }
    public static Action atGoal() {
        return new AtGoal();
    }
    public static class FarFromGoal implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            operate(HoodAngleStates.FARFROMGOAL);
            return false;
        }
    }
    public static Action farFromGoal() {
        return new FarFromGoal();
    }


}
