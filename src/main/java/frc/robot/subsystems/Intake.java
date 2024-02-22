package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private int IntakeMotor1CanID = IntakeConstants.IntakeMotor1CanID;
    private int IntakeMotor2CanID = IntakeConstants.IntakeMotor2CanID;

    private static CANSparkMax m_motor1;
    private static CANSparkMax m_motor2;
    private static boolean isReversed;

    private final static double speed = IntakeConstants.IntakeSpeedConstant * IntakeConstants.IntakeOrientation;

    public Intake() {
        isReversed = false;

        m_motor1 = new CANSparkMax(IntakeMotor1CanID, MotorType.kBrushless);
        m_motor1 = new CANSparkMax(IntakeMotor2CanID, MotorType.kBrushless);

        m_motor1.restoreFactoryDefaults();
        m_motor2.restoreFactoryDefaults();

    }

    public void startIntake() {
        if(getSpeed()!=speed*1){
            m_motor1.set(speed);
            m_motor2.follow(m_motor1, isReversed);//true); invert
            //System.out.println("Intake: Started");
        }
    }

    public void startOuttake() {
        if(getSpeed()!=speed*-1){
            m_motor1.set(speed*-1);
            m_motor2.follow(m_motor1, isReversed);//true); invert
            //System.out.println("Intake: Reversed");
        }
    }

    public void endIntakeOuttake() {
        if(getSpeed()!=0){
            m_motor1.set(0);
            m_motor2.follow(m_motor1, isReversed);
            //System.out.println("Intake: Ended");
        }
    }

    public double getSpeed(){
        return m_motor1.get();
    }
}
