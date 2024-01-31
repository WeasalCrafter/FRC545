package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Lights extends SubsystemBase {
    int m_port = Constants.LEDConstants.BlinkinPWMPort;
    Spark m_ledController = new Spark(m_port);

    double TELEOP_STATE = Constants.LEDConstants.TELEOP_STATE;
    double AUTON_STATE = Constants.LEDConstants.AUTON_STATE;
    double SHOOT_STATE = Constants.LEDConstants.SHOOT_STATE;
    double INTAKE_STATE = Constants.LEDConstants.INTAKE_STATE;
    double OUTTAKE_STATE = Constants.LEDConstants.OUTTAKE_STATE;
    double CLIMB_STATE = Constants.LEDConstants.CLIMB_STATE;  
    double BREAK_STATE = Constants.LEDConstants.BREAK_STATE;  

    private String STATE;   

    public Lights() {
        AutonState();
    }

    public void changeState(double state, String name){
        if(this.STATE != name){
            m_ledController.set(state);
            System.out.println("Lights: " + name);
            this.STATE = name;
        }
    }

    public void TeleopState(){
        changeState(TELEOP_STATE, "tele-op");
    }

    public void AutonState(){
        changeState(AUTON_STATE, "autonomous");
    }

    public void ShootState(){
        changeState(SHOOT_STATE, "shoot");
    }

    public void IntakeState(){
        changeState(INTAKE_STATE, "intake");
    }

    public void OuttakeState(){
        changeState(OUTTAKE_STATE, "outtake");
    }

    public void ClimbState(){
        changeState(CLIMB_STATE, "climb");
    }

    public void BreakState(){
        changeState(BREAK_STATE, "break");
    }

    public String GetState(){
        return this.STATE;
    }
}
