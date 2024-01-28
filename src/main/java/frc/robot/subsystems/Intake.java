package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.wpilibj.event.EventLoop;
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
        m_intake.set(speed);
        System.out.println("Intake: Started");
    }

    public void startOuttake() {
        m_intake.set(speed*-1);
        System.out.println("Outtake: Started");
    }

    public void endIntakeOuttake() {
        m_intake.set(0);
        System.out.println("Intake/Outtake: Ended");
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
