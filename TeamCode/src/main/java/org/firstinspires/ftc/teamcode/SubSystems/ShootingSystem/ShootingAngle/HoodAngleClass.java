package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.GlobalData;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.Utility.DynamicShootingClass;

import static com.seattlesolvers.solverslib.util.MathUtils.*;

public class HoodAngleClass {
    private static Servo hoodServo;
    private static double servoPos;

    private static boolean lastChange = false;


    public static void init(HardwareMap hardwareMap) {
        hoodServo = hardwareMap.servo.get("hoodServo");

        hoodServo.setDirection(Servo.Direction.REVERSE);
    }


    public static void setPos(double pos) {
        hoodServo.setPosition(clamp(pos, HoodAngleConstants.hoodAngleMin, HoodAngleConstants.hoodAngleMax));
    }


    public static void test(Gamepad gamepad) {
        if (gamepad.dpad_up) {
            if (!lastChange) {
                servoPos += 0.02;
                lastChange = true;
            }
        } else if (gamepad.dpad_down) {
            if (!lastChange) {
                servoPos -= 0.02;
                lastChange = true;
            }
        } else {
            lastChange = false;
        }

        hoodServo.setPosition(servoPos);
    }

    public static void telemetry(Telemetry telemetry) {
        telemetry.addData("hoodServoAngle:", hoodServo.getPosition());
    }

    public static class ShootHumanPlayerDis implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            setPos(0.285);
            return true;
        }
    }

    public static Action shootHumanPlayerDis() {
        return new ShootHumanPlayerDis();
    }


    public static class ShootClose1Dis implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            setPos(0.285);
            return true;
        }
    }

    public static Action shootClose1Dis() {
        return new ShootClose1Dis();
    }


    public static class ShootClose2Dis implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            setPos(0.285);
            return true;
        }
    }

    public static Action shootClose2Dis() {
        return new ShootClose2Dis();
    }

}
