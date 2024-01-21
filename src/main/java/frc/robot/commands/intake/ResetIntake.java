package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class ResetIntake extends InstantCommand{
    private Intake intake;
    public ResetIntake(Intake intake){
        this.intake = intake;
        addRequirements(intake);
    }
    
    @Override
	public void initialize() {
        intake.endIntakeOuttake();
    }
}