package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class StartIntake extends InstantCommand{
    private final Intake intake;

    public StartIntake(Intake intake){
        this.intake = intake;
        addRequirements(intake);
    }
    public void initialize(){
        intake.startIntake();
        System.out.println("start intake");
    }
}

