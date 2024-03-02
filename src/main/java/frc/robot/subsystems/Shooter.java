package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    private int motor1ID = Constants.ShooterConstants.ShooterMotor1CanID;
    private int motor2ID = Constants.ShooterConstants.ShooterMotor2CanID;
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

    public void startShooter(double spd) {
        if(getSpeed()!=spd){
            SmartDashboard.putNumber("Shooter Speed", spd);
            m_motor1.set(spd);
            m_motor2.follow(m_motor1, isReversed);
        }
    }

    public void reverseShooter(double spd) {
        if(getSpeed()!=spd){
            SmartDashboard.putNumber("Shooter Speed", spd);
            m_motor1.set(spd);
            m_motor2.follow(m_motor1, isReversed);
        }
    }

    public void stopShooter() {
        if(getSpeed()!=0){
            SmartDashboard.putNumber("Shooter Speed", 0);
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
