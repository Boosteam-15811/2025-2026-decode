package com.example.meepmeeptesting.BlueHumanPlayer3Marks;

import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BlueHumanPlayer3Marks
{
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(BlueHumanPlayerConstants3Marks.startingPos)
                        .setTangent(90)
                        .splineToLinearHeading(BlueHumanPlayerConstants3Marks.firstRow, Math.toRadians(270))
                        .strafeTo(BlueHumanPlayerConstants3Marks.shootingPos)
                        .strafeTo(BlueHumanPlayerConstants3Marks.secondRow)
                        .strafeTo(BlueHumanPlayerConstants3Marks.collectSecondRow)
                        .strafeTo(BlueHumanPlayerConstants3Marks.shootingPos)
                        .strafeTo(BlueHumanPlayerConstants3Marks.thirdRow)
                        .strafeTo(BlueHumanPlayerConstants3Marks.collectThirdRow)
                        .strafeTo(BlueHumanPlayerConstants3Marks.backThirdRow)
                        .strafeTo(BlueHumanPlayerConstants3Marks.shootingPos)



                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
