package com.example.meepmeeptesting.RedHumanPlayer;

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

        myBot.runAction(myBot.getDrive().actionBuilder(RedHumanPlayerConstants.startingPos)
                .setTangent(180)
                .splineToLinearHeading(RedHumanPlayerConstants.firstRow, Math.toRadians(90))
                .strafeTo(RedHumanPlayerConstants.shootingPos)
                .strafeTo(RedHumanPlayerConstants.collectHumanPlayer)
                .strafeTo(RedHumanPlayerConstants.shootingPos)



                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
