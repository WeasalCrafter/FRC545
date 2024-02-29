package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
    CANSparkMax m_leftActuator;
    CANSparkMax m_rightActuator;

    public Climber() {        
        m_leftActuator = new CANSparkMax(Constants.ClimberConstants.LeftActuatorCANID, MotorType.kBrushless);
        m_rightActuator = new CANSparkMax(Constants.ClimberConstants.RightActuatorCANID, MotorType.kBrushless);
    }

    public void ascend(double spd){
        m_leftActuator.set(spd);
        m_rightActuator.set(spd);
    }
    public void descend(double spd){
        spd = -1 * spd;
        m_leftActuator.set(spd);
        m_rightActuator.set(spd);
    }
    public void stop(){
        m_leftActuator.set(0);
        m_rightActuator.set(0);
    }
}
