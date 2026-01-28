package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleStates;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedStates;

public class ShooterStateClass {
    public static ShooterStates shooterState = ShooterStates.DISABLED;

    private static boolean lastChange = false;
   //private static boolean patternMode = false;

    public static void setState(Gamepad gamepad) {
        if (gamepad.triangle)
        {
            shooterState= ShooterStates.FARFROMGOAL;

        }
        else if (gamepad.square)
        {
            shooterState = ShooterStates.ATGOAL;
        }
        else if (gamepad.cross)
        {
            shooterState = ShooterStates.DISABLED;
        }
    }

//    public static void setPatternMode(Gamepad gamepad)
//    {
//        if (gamepad.circle)
//        {
//            if (!lastChange)
//            {
//                patternMode = true;
//                lastChange = true;
//            }
//            else
//            {
//                patternMode = false;
//                lastChange = false;
//            }
//
//        }
//
//    }
    public static void operate() throws InterruptedException {
        switch (shooterState)
        {
            case LAUNCHZONE:
            {
                HoodAngleClass.operate(HoodAngleStates.LAUNCHZONE);
                ShootingSpeedClass.operate(ShootingSpeedStates.LAUNCHZONE);
                if(ShootingSpeedClass.inTolerence(ShootingSpeedStates.LAUNCHZONE))
                {
                    IntakeClass.operate(1);
                    TransferWheelClass.operate(1);
                }
                break;
            }
            case ATGOAL:
            {
                HoodAngleClass.operate(HoodAngleStates.ATGOAL);
                ShootingSpeedClass.operate(ShootingSpeedStates.ATGOAL);
                if(ShootingSpeedClass.inTolerence(ShootingSpeedStates.ATGOAL))
                {
                    IntakeClass.operate(1);
                    TransferWheelClass.operate(1);
                }
                break;
            }
            case FARFROMGOAL:
            {
                HoodAngleClass.operate(HoodAngleStates.FARFROMGOAL);
                ShootingSpeedClass.operate(ShootingSpeedStates.FARFROMGOAL);
                if(ShootingSpeedClass.inTolerence(ShootingSpeedStates.FARFROMGOAL))
                {
                    IntakeClass.operate(1);
                    TransferWheelClass.operate(1);
                }
                break;
            }
            case DISABLED:
            {
                HoodAngleClass.operate(HoodAngleStates.DISABLED);
                ShootingSpeedClass.operate(ShootingSpeedStates.DISABLED);
                break;
            }

        }


    }

    public enum ShooterStates {
        LAUNCHZONE, ATGOAL, FARFROMGOAL, DISABLED
    }
}

