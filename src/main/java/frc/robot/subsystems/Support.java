package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Support extends SubsystemBase {
    private int motor1ID = ShooterConstants.SupportMotor1CanID;
    private int motor2ID = ShooterConstants.SupportMotor2CanID;

    private static boolean isReversed;

    private static CANSparkMax m_motor1;
    private static CANSparkMax m_motor2;

    public Support() {
        isReversed = true;
        m_motor1 = new CANSparkMax(motor1ID, MotorType.kBrushless);
        m_motor2 = new CANSparkMax(motor2ID, MotorType.kBrushless);

        m_motor1.restoreFactoryDefaults();
        m_motor2.restoreFactoryDefaults();
    }

    public void startSupport(double spd) {
        if(getSpeed()!=spd){
            SmartDashboard.putNumber("Support Speed", spd);
            m_motor1.set(spd);
            m_motor2.follow(m_motor1, isReversed);
        }
    }

    public void reverseSupport(double spd) {
        if(getSpeed()!=spd){
            SmartDashboard.putNumber("Support Speed", spd);
            m_motor1.set(spd);
            m_motor2.follow(m_motor1, isReversed);
        }
    }

    public void stopSupport() {
        if(getSpeed()!=0){
            SmartDashboard.putNumber("Support Speed", 0);
            m_motor1.set(0);
            m_motor2.follow(m_motor1, isReversed);
        }
    }

    public double getSpeed(){
        return m_motor1.get();
    }
}
