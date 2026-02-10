package com.example.meepmeeptesting;


import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class RedLaunchZone {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(62, 17.8 , Math.toRadians(180)))
                //from start to collecting the first row
                .strafeToLinearHeading(new Vector2d(56,12),Math.toRadians(152))
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(36,30, Math.toRadians(90)), Math.toRadians(90))
                .lineToYConstantHeading(54)

                //from first row to shooting from launchzone range
                .strafeToLinearHeading(new Vector2d(56,12),Math.toRadians(152))

                //from launchzone to second row
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(12.5,30, Math.toRadians(90)), Math.toRadians(90))
                .lineToYConstantHeading(54)

                //from second row to shooting from medium range
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(-16, 17 , Math.toRadians(135)), Math.toRadians(90))

                //from medium range to third row
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-11,30, Math.toRadians(90)), Math.toRadians(90))
                .lineToYConstantHeading(54)

                //from third row to shooting from medium range
                .setTangent(180)
                .splineToLinearHeading(new Pose2d(-16, 17 , Math.toRadians(135)), Math.toRadians(90))

                //leave
                .strafeTo(new Vector2d(5,17))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
