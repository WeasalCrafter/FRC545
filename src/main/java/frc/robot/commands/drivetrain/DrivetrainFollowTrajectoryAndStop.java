package frc.robot.commands.drivetrain;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Constants.AutoConstants;

import frc.robot.subsystems.Drivetrain;


/**
 * Follows specified trajectory and then stops the drivetrain using trajectory trackers defined locally.
 */
public class DrivetrainFollowTrajectoryAndStop extends SequentialCommandGroup {

	public DrivetrainFollowTrajectoryAndStop(Drivetrain drivetrain, Trajectory trajectory) {

		PIDController xController = new PIDController(AutoConstants.kPXController, 0, 0); // trajectory tracker PID controller for x position

		PIDController yController = new PIDController(AutoConstants.kPYController, 0, 0); // trajectory tracker PID controller for y position
		
		ProfiledPIDController thetaController = new ProfiledPIDController(
			AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints); // trajectory tracker PID controller for rotation
			
		thetaController.enableContinuousInput(-Math.PI, Math.PI);

		addCommands(
			new DrivetrainFollowTrajectory(drivetrain, trajectory, xController, yController, thetaController),
			new DrivetrainStop(drivetrain));
	}

}