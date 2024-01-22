package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class StartIntake extends InstantCommand{ //CURRENTLY UNUSED
    private Intake intake;
    public StartIntake(Intake intake){
        this.intake = intake;
        addRequirements(intake);
    }
    
    @Override
	public void initialize() {
        intake.startIntake();
    }
}

