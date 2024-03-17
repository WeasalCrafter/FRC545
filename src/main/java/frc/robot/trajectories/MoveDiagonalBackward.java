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

public class MoveDiagonalBackward extends SequentialCommandGroup {

    private double distanceX;
    private double distanceY;
    private double degrees;
	
	public MoveDiagonalBackward(Drivetrain drivetrain, RobotContainer container, double distanceX, double distanceY,double degrees) {
        this.distanceX = distanceX;
        this.distanceY = distanceY;
		this.degrees = degrees;
        
		addCommands(
			new DrivetrainSwerveRelative(drivetrain, container, createMoveBackwardTrajectory(container))
        ); 
    }
    
    public Trajectory createMoveBackwardTrajectory(RobotContainer container) {
		Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
			new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
			List.of(),
				new Pose2d(distanceX, distanceY, Rotation2d.fromDegrees(degrees)),
				container.createReverseTrajectoryConfig()
			);
		return trajectory;
	}

}
