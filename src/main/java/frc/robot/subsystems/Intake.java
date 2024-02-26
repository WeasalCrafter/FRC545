package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private int IntakeMotor1CanID = IntakeConstants.IntakeMotor1CanID;
    private int IntakeMotor2CanID = IntakeConstants.IntakeMotor2CanID;

    private static CANSparkMax intake_motor1;
    private static CANSparkMax intake_motor2;

    private static boolean isReversed;

    public Intake() {
        isReversed = true;

        intake_motor1 = new CANSparkMax(IntakeMotor1CanID, MotorType.kBrushless);
        intake_motor2 = new CANSparkMax(IntakeMotor2CanID, MotorType.kBrushless);

        intake_motor1.restoreFactoryDefaults();
        intake_motor2.restoreFactoryDefaults();

    }

    public void startIntake(double spd) {
        if(getSpeed()!=spd){
            intake_motor1.set(spd);
            intake_motor2.follow(intake_motor1, isReversed);//true); invert
        }
    }

    // public void reverseIntake(double spd) {
    //     if(getSpeed()!=spd*-1){
    //         intake_motor1.set(spd*-1);
    //         intake_motor2.follow(intake_motor1, isReversed);//true); invert
    //         //System.out.println("Intake: Reversed");
    //     }
    // }

    public void stopIntake() {
        if(getSpeed()!=0){
            intake_motor1.set(0);
            intake_motor2.follow(intake_motor1, isReversed);
        }
    }

    public double getSpeed(){
        return intake_motor1.get();
    }
}
