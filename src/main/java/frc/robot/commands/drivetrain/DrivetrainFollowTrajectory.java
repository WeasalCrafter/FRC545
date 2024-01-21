package frc.robot.commands.drivetrain;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.Trajectory;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;

public class DrivetrainFollowTrajectory extends SwerveControllerCommand {
    public DrivetrainFollowTrajectory(Drivetrain drivetrain, Trajectory trajectory, PIDController xController, PIDController yController, ProfiledPIDController thetaController) {
		super(
			trajectory, // trajectory to follow
			drivetrain::getPose, // Functional interface to feed supplier
			DrivetrainConstants.kDriveKinematics, // kinematics of the drivetrain
			xController, // trajectory tracker PID controller for x position
			yController, // trajectory tracker PID controller for y position
			thetaController, // trajectory tracker PID controller for rotation
			drivetrain::setModuleStates, // raw output module states from the position controllers
			drivetrain); // subsystems to require
	}

    @Override
	public void initialize() {
		System.out.println("DrivetrainFollowTrajectory: initialize");
		super.initialize();
	}

	// Called once after isFinished returns true
	@Override
	public void end(boolean interrupted) {
		System.out.println("DrivetrainFollowTrajectory: end");
		super.end(interrupted);
	}
}
