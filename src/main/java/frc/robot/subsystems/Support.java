package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;

public class Support extends SubsystemBase {
    private int SupportMotor1CanID = ShooterConstants.SupportMotor1CanID;
    private int SupportMotor2CanID = ShooterConstants.SupportMotor2CanID;

    private final static double speed = IntakeConstants.IntakeSpeedConstant * IntakeConstants.IntakeOrientation;

    private static boolean isReversed;

    private static CANSparkMax support_m1;
    private static CANSparkMax support_m2;

    public Support() {
        isReversed = true;

        support_m1 = new CANSparkMax(SupportMotor1CanID, MotorType.kBrushless);
        support_m2 = new CANSparkMax(SupportMotor2CanID, MotorType.kBrushless);

        support_m1.restoreFactoryDefaults();
        support_m2.restoreFactoryDefaults();
    }

    public void support() {
        if(getSpeed()!=speed){
            support_m1.set(speed);
            support_m2.follow(support_m1, isReversed);//true); invert
        }
    }

    public void reverseSupport() {
        if(getSpeed()!=speed*-1){
            support_m1.set(speed*-1);
            support_m2.follow(support_m1, isReversed);//true); invert
        }
    }

    public void supportIdle() {
        if(getSpeed()!=0){
            support_m1.set(0);
            support_m2.follow(support_m1, isReversed);//true); invert
        }
    }

    public double getSpeed(){
        return support_m1.get();
    }
}
