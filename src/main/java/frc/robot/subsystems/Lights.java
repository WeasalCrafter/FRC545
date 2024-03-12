package frc.robot.subsystems;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LedConstants.FixedPatterns;
import frc.robot.LedConstants.SolidColors;
import frc.robot.Constants;


public class Lights extends SubsystemBase {

    int m_port = Constants.OIConstants.BlinkinPWMPort;
    boolean isReversed = false;
    
    Spark m_ledController;

    double currentState;
    double desiredState;

    double TELEOP_STATE = FixedPatterns.STROBE_BLUE;
    double REVERSE_TELEOP_STATE = FixedPatterns.STROBE_GOLD;

    double BREAK_STATE = FixedPatterns.STROBE_RED;
    double AUTON_STATE = SolidColors.BLUE;
    double CLIMB_STATE = SolidColors.VIOLET;   
     
    public Lights(){
        currentState = AUTON_STATE;
        m_ledController = new Spark(m_port);
    }

    @Override
    public void periodic() {
        currentState = m_ledController.get();

        if(currentState != desiredState){
            m_ledController.set(currentState);
        }

        super.periodic();
    }

    public void ChangeState(String name){
        SmartDashboard.putString("Lights: ", name);
        switch (name) {
            case "tele":
                isReversed = SmartDashboard.getBoolean("IS REVERSED", true);
                if(isReversed==true){
                    desiredState = REVERSE_TELEOP_STATE;
                    SmartDashboard.putString("Lights: ", "reversed " + name);
                }else{
                    desiredState = TELEOP_STATE;
                }
                break;
            case "tele-reverse":
                desiredState = REVERSE_TELEOP_STATE;
                break;
            case "auto":
                desiredState = AUTON_STATE;
                break;
            case "break":
                desiredState = BREAK_STATE;
                break;
            case "climb":
                desiredState = CLIMB_STATE;
                break;
        }
    }
}
