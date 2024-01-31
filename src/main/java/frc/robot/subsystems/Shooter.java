package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    private int motor1ID = Constants.ShooterConstants.ShooterMotor1CanID;
    private int motor2ID = Constants.ShooterConstants.ShooterMotor2CanID;

    CANSparkMax m_motor1 = new CANSparkMax(motor1ID, MotorType.kBrushless);
    CANSparkMax m_motor2 = new CANSparkMax(motor2ID, MotorType.kBrushless);

    //private double speed = 0.5;

    public Shooter() {
        
    }

    public void shoot() {
        System.out.println("shooting!!!");
    }

}
