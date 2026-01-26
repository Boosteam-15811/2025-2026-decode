package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-49, 49 , Math.toRadians(125)))
                .strafeTo(new Vector2d(-49,40))
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(-7, 29, Math.toRadians(90)), Math.toRadians(90))
                .lineToYConstantHeading(47)
                .lineToYConstantHeading(42)
                .turn(Math.toRadians(90)).strafeTo(new Vector2d(7,55))
                .waitSeconds(1.1)
                .setTangent(180)
                .splineToLinearHeading(new Pose2d(-6, -6 , Math.toRadians(135)), Math.toRadians(135))

                .splineToLinearHeading(new Pose2d(23, 29, Math.toRadians(90)), Math.toRadians(90))
                .lineToYConstantHeading(42)

                .setTangent(180)
                .splineToLinearHeading(new Pose2d(-6, -6 , Math.toRadians(135)), Math.toRadians(135))



                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}