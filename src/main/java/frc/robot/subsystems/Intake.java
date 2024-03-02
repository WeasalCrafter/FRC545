package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private int IntakeMotor1CanID = IntakeConstants.IntakeMotor1CanID;
    private int IntakeMotor2CanID = IntakeConstants.IntakeMotor2CanID;

    private static CANSparkMax m_motor1;
    private static CANSparkMax m_motor2;

    private static boolean isReversed;

    public Intake() {
        isReversed = true;

        m_motor1 = new CANSparkMax(IntakeMotor1CanID, MotorType.kBrushless);
        m_motor2 = new CANSparkMax(IntakeMotor2CanID, MotorType.kBrushless);

        m_motor1.restoreFactoryDefaults();
        m_motor2.restoreFactoryDefaults();

    }

    public void startIntake(double spd) {
        if(getSpeed()!=spd){
            SmartDashboard.putNumber("Intake Speed", spd);
            m_motor1.set(spd);
            m_motor2.follow(m_motor1, isReversed);//true); invert
        }
    }

    // public void reverseIntake(double spd) {
    //     if(getSpeed()!=spd*-1){
    //         m_motor1.set(spd*-1);
    //         m_motor2.follow(m_motor1, isReversed);//true); invert
    //         //System.out.println("Intake: Reversed");
    //     }
    // }

    public void stopIntake() {
        if(getSpeed()!=0){
            SmartDashboard.putNumber("Intake Speed", 0);
            m_motor1.set(0);
            m_motor2.follow(m_motor1, isReversed);
        }
    }

    public double getSpeed(){
        return m_motor1.get();
    }

    public void clearFaults(){
        m_motor1.clearFaults();
        m_motor2.clearFaults();
    }
}
