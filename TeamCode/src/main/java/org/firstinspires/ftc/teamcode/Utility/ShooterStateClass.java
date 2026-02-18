package org.firstinspires.ftc.teamcode.Utility;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleConstants;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedConstants;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;

public class ShooterStateClass {
    public static ShooterStates shooterState = ShooterStates.DISABLED;

    private static boolean lastChange = false;
   //private static boolean patternMode = false;

    public static void setState(Gamepad gamepad) {
        if (gamepad.circle)
        {
            shooterState = ShooterStates.LAUNCHZONE;
        }
        else if (gamepad.triangle)
        {
            shooterState = ShooterStates.FARFROMGOAL;

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

    public static void manualOperate() {
        switch (shooterState)
        {
            case LAUNCHZONE:
            {
                HoodAngleClass.setPos(HoodAngleConstants.launchZonePos);
                ShootingSpeedClass.setSpeed(ShootingSpeedConstants.launchZoneSpeed);
                if ((ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio > 0) && (ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio < 300))
                {
                    IntakeClass.operate(-1);
                }
                else if (ShootingSpeedClass.inTolerence(ShootingSpeedConstants.launchZoneSpeed, ShootingSpeedConstants.launchZoneTolerance))
                {
                    TransferWheelClass.operate(1);
                    IntakeClass.operate(1);
                }
                else
                {
                    TransferWheelClass.operate(0);
                    IntakeClass.operate(0);
                }

                break;
            }
            case ATGOAL:
            {
                HoodAngleClass.setPos(HoodAngleConstants.atGoalPos);
                ShootingSpeedClass.setSpeed(ShootingSpeedConstants.atGoalSpeed);
                if ((ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio > 0) && (ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio < 300))
                {
                    IntakeClass.operate(-1);
                }
                else if (ShootingSpeedClass.inTolerence(ShootingSpeedConstants.atGoalSpeed, ShootingSpeedConstants.atGoalTolerance))
                {
                    TransferWheelClass.operate(1);
                    IntakeClass.operate(1);
                }
                else
                {
                    TransferWheelClass.operate(0);
                    IntakeClass.operate(0);
                }

                break;
            }
            case FARFROMGOAL:
            {
                HoodAngleClass.setPos(HoodAngleConstants.farFromGoalPos);
                ShootingSpeedClass.setSpeed(ShootingSpeedConstants.farFromGoalSpeed);
                if ((ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio > 0) && (ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio < 300))
                {
                    IntakeClass.operate(-1);
                }
                else if (ShootingSpeedClass.inTolerence(ShootingSpeedConstants.farFromGoalSpeed, ShootingSpeedConstants.farFromGoalTolerance))
                {
                    TransferWheelClass.operate(1);
                    IntakeClass.operate(1);
                }
                else
                {
                    TransferWheelClass.operate(0);
                    IntakeClass.operate(0);
                }

                break;
            }
            case DISABLED:
            {
                HoodAngleClass.setPos(HoodAngleConstants.disabledPos);
                ShootingSpeedClass.setSpeed(ShootingSpeedConstants.disabledSpeed);
                break;
            }

        }


    }

    public static void operate(double wantedSpeed)
    {
        ShootingSpeedClass.setSpeed(wantedSpeed);
        if (wantedSpeed == 0)
        {
            IntakeClass.operate(0);
            TransferWheelClass.operate(0);
        }
        else if ((ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio > 0) && (ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio < 450))
        {
            IntakeClass.operate(-1);
        }
        else if (ShootingSpeedClass.inTolerence(wantedSpeed, ShootingSpeedConstants.farFromGoalTolerance))
        {
            TransferWheelClass.operate(1);
            IntakeClass.operate(1);
        }
        else
        {
            TransferWheelClass.operate(0);
            IntakeClass.operate(0);
        }
    }

    public enum ShooterStates {
        LAUNCHZONE, ATGOAL, FARFROMGOAL, DISABLED
    }
}

