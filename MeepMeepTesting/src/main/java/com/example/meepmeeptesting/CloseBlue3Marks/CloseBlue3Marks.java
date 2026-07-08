package com.example.meepmeeptesting.CloseBlue3Marks;

import com.example.meepmeeptesting.CloseBlue.CloseBlueConstants;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class CloseBlue3Marks
{
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(CloseBlueConstants3Marks.startingPos)

                .strafeTo(CloseBlueConstants3Marks.startShootingPos)
                .strafeTo(CloseBlueConstants3Marks.ThirdRow)
                .strafeTo(CloseBlueConstants3Marks.collectThirdRow)
                .strafeTo(CloseBlueConstants3Marks.shootingPos)
                .strafeTo(CloseBlueConstants3Marks.secondRow)
                .strafeTo(CloseBlueConstants3Marks.collectSecondRow)
                .strafeTo(CloseBlueConstants3Marks.shootingPos)
                .strafeTo(CloseBlueConstants3Marks.firstRow)
                .strafeTo(CloseBlueConstants3Marks.collectFirstRow)
                .strafeTo(CloseBlueConstants3Marks.shootingPos)
                .strafeTo(CloseBlueConstants3Marks.leave)



                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
