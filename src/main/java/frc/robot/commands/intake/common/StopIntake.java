package frc.robot.commands.intake.common;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class StopIntake extends InstantCommand{
    private final Intake intake;

    public StopIntake(Intake intake){
        this.intake = intake;
        addRequirements(intake);
    }
    public void initialize(){
        intake.stopIntake();
        System.out.println("stop intake");
    }
}
