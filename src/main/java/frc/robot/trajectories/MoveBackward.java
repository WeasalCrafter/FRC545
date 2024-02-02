package frc.robot.trajectories;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.RobotContainer;
import frc.robot.commands.drivetrain.DrivetrainSwerveRelative;
import frc.robot.subsystems.*;

public class MoveBackward extends SequentialCommandGroup {

    private double distance;
	
	public MoveBackward(Drivetrain drivetrain, RobotContainer container, double distance) {
        this.distance = distance;
		
		addCommands(
			new DrivetrainSwerveRelative(drivetrain, container, createMoveForwardTrajectory(container))
        ); 
    }
    
    public Trajectory createMoveForwardTrajectory(RobotContainer container) {
		Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
			new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
			List.of(),
				new Pose2d(-distance, 0, Rotation2d.fromDegrees(0)),
				container.createReverseTrajectoryConfig()
			);
		return trajectory;
	}

}