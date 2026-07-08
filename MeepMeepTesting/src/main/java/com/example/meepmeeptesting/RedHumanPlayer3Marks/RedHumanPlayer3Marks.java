package com.example.meepmeeptesting.RedHumanPlayer3Marks;

import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class RedHumanPlayer3Marks
{
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(RedHumanPlayerConstants3Marks.startingPos)
                .setTangent(180)
                .splineToLinearHeading(RedHumanPlayerConstants3Marks.firstRow, Math.toRadians(90))
                .strafeTo(RedHumanPlayerConstants3Marks.shootingPos)
                .strafeTo(RedHumanPlayerConstants3Marks.secondRow)
                .strafeTo(RedHumanPlayerConstants3Marks.collectSecondRow)
                .strafeTo(RedHumanPlayerConstants3Marks.shootingPos)
                .strafeTo(RedHumanPlayerConstants3Marks.thirdRow)
                .strafeTo(RedHumanPlayerConstants3Marks.collectThirdRow)
                .strafeTo(RedHumanPlayerConstants3Marks.backThirdRow)
                .strafeTo(RedHumanPlayerConstants3Marks.shootingPos)
                .strafeTo(RedHumanPlayerConstants3Marks.leave)




                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
