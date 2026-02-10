package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class RedHumanPlayer
{
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(62, -17.8 , Math.toRadians(180)))
                //from start to collecting from the human player
                .strafeToLinearHeading(new Vector2d(56,-12),Math.toRadians(212))
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(56,-30), Math.toRadians(270))
                .lineToYConstantHeading(-60)

                //from human player to shooting from launchzone
                .strafeToLinearHeading(new Vector2d(56,-12), Math.toRadians(212))

                //leave
                .strafeTo(new Vector2d(25,-17))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
