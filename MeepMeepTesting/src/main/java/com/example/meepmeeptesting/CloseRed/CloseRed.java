package com.example.meepmeeptesting.CloseRed;

import com.example.meepmeeptesting.CloseBlue.CloseBlueConstants;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class CloseRed
{
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(CloseRedConstants.startingPos)

                .strafeTo(CloseRedConstants.startShootingPos)
                .strafeTo(CloseRedConstants.secondRow)
                .strafeTo(CloseRedConstants.collectSecondRow)
                .strafeTo(CloseRedConstants.shootingPos)
                .strafeToLinearHeading(CloseRedConstants.collectGate, Math.toRadians(120))
                .strafeTo(CloseRedConstants.Back)
                .strafeTo(CloseRedConstants.collectGate)
                .strafeToLinearHeading(CloseRedConstants.shootingPos,Math.toRadians(90))
                .strafeTo(CloseRedConstants.leave)


                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
