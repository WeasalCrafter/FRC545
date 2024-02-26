package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class StopIntake extends InstantCommand{
    private Intake intake;

    public StopIntake(Intake intake){
        this.intake = intake;
        addRequirements(intake);
    }
    public void initialize(){
        intake.endIntakeOuttake();
        System.out.println("stop intake");
    }
}
