package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class StartOuttake extends InstantCommand{
    private final Intake intake;

    public StartOuttake(Intake intake){
        this.intake = intake;
        addRequirements(intake);
    }
    public void initialize(){
        intake.startOuttake();
        System.out.println("start outtake");
    }
}

