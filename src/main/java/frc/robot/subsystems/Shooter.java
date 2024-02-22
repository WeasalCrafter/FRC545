package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;

public class Shooter extends SubsystemBase {
    private int motor1ID = Constants.ShooterConstants.ShooterMotor1CanID;
    private int motor2ID = Constants.ShooterConstants.ShooterMotor2CanID;

    private final static double speed = 5 * IntakeConstants.IntakeSpeedConstant * IntakeConstants.IntakeOrientation;

    private static boolean isReversed;

    private static CANSparkMax m_motor1;
    private static CANSparkMax m_motor2;

    public Shooter() {
        isReversed = true;
        m_motor1 = new CANSparkMax(motor1ID, MotorType.kBrushless);
        m_motor2 = new CANSparkMax(motor2ID, MotorType.kBrushless);

        m_motor1.restoreFactoryDefaults();
        m_motor2.restoreFactoryDefaults();
    }

    public void shoot() {
        if(getSpeed()!=speed*1){
            m_motor1.set(speed);
            m_motor2.follow(m_motor1, isReversed);
            //m_support.support();
        }
    }

    public void reverseShoot() {
        if(getSpeed()!=speed*-1){
            m_motor1.set(speed*-1);
            m_motor2.follow(m_motor1, isReversed);
            //m_support.reverseSupport();
        }
    }

    public void shooterIdle() {
        if(getSpeed()!=0){
            m_motor1.set(0);
            m_motor2.follow(m_motor1, isReversed);
            //m_support.supportIdle();
        }
    }

    public double getSpeed(){
        return m_motor1.get();
    }
}
