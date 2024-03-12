package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Lights;

/**
 * Switches heading to opposite.
 */
public class DrivetrainReverseHeading extends InstantCommand {

	private Drivetrain drivetrain;
	private Lights lights;

	public DrivetrainReverseHeading(Drivetrain drivetrain, Lights m_Lights) {
		this.drivetrain = drivetrain;
		this.lights = m_Lights;
		addRequirements(drivetrain);
	}

	// This instant command can run disabled
	@Override
	public boolean runsWhenDisabled() {
		return true;
	}

	// Called once when this command runs
	@Override
	public void initialize() {
		System.out.println("DrivetrainOppositeHeading: initialize");
		drivetrain.toggleHeading();
		lights.ChangeState("tele");
	}

}