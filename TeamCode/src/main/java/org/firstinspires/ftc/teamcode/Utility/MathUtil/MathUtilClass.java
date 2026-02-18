package org.firstinspires.ftc.teamcode.Utility.MathUtil;

public class MathUtilClass
{
    public static double clamp(double val, double min, double max)
    {
        if (val > max)
        {
            return max;
        }
        else if (val < min)
        {
            return min;
        }
        return val;
    }
}
