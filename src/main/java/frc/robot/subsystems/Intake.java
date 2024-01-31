package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private int IntakeMotorCanID = IntakeConstants.IntakeMotorCanID;
    private static CANSparkMax m_intake;
    private final static double speed = IntakeConstants.IntakeSpeedConstant * IntakeConstants.IntakeOrientation;

    public Intake() {
        m_intake = new CANSparkMax(IntakeMotorCanID, MotorType.kBrushless);
        m_intake.restoreFactoryDefaults();
    }

    public void startIntake() {
        if(getSpeed()!=speed*1){
            m_intake.set(speed);
            //System.out.println("Intake: Started");
        }
    }

    public void startOuttake() {
        if(getSpeed()!=speed*-1){
            m_intake.set(speed*-1);
            //System.out.println("Intake: Reversed");
        }
    }

    public void endIntakeOuttake() {
        if(getSpeed()!=0){
            m_intake.set(0);
            //System.out.println("Intake: Ended");
        }
    }

    public double getSpeed(){
        return m_intake.get();
    }

    public void toggleIntake() {
        if(getSpeed()>0){
            endIntakeOuttake();
        }else{
            startIntake();
        }
    }
}
