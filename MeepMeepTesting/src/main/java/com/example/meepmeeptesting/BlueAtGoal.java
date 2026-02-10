package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BlueAtGoal
{
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-49, -49 , Math.toRadians(235)))
                //from start to collecting the third row
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-11,-30, Math.toRadians(270)), Math.toRadians(270))
                .lineToYConstantHeading(-54)

                //from third row to shooting from medium range
                .strafeToLinearHeading(new Vector2d(-16,-17),Math.toRadians(225))

                //from medium range to second row
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(12,-30, Math.toRadians(270)), Math.toRadians(270))
                .lineToYConstantHeading(-54)

                //from second row to shooting from medium range
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(-16, -17 , Math.toRadians(225)), Math.toRadians(270))

                //from medium range to first row
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(36,-30, Math.toRadians(270)), Math.toRadians(270))
                .lineToYConstantHeading(-54)

                //from first row to shooting from launchzone range
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(56,-12),Math.toRadians(212))

                //leave
                .strafeTo(new Vector2d(25,-14))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
