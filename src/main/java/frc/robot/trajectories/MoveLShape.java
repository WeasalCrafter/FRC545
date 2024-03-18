package frc.robot.trajectories;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.drivetrain.DrivetrainSwerveRelative;
import frc.robot.subsystems.Drivetrain;

public class MoveLShape extends SequentialCommandGroup {

    private double forwardDistance;
    private double sideDistance;
	
	public MoveLShape(Drivetrain drivetrain, RobotContainer container, double forwardDistance,double sideDistance) {

        this.forwardDistance = forwardDistance;
        this.sideDistance = sideDistance;
		
		addCommands(
			new DrivetrainSwerveRelative(drivetrain, container, createSShapeTrajectory(container))            
        ); 
  
    }
    
    public Trajectory createSShapeTrajectory(RobotContainer container) {
		Trajectory trajectory = TrajectoryGenerator.generateTrajectory(

            new Pose2d(0, 0, Rotation2d.fromDegrees(0)),

            List.of(
                new Translation2d(((1/3) * forwardDistance), ((1/2)*sideDistance)), 
                new Translation2d(((2/3) * forwardDistance), ((1/2)*sideDistance))
            ),

            new Pose2d(forwardDistance, sideDistance, Rotation2d.fromDegrees(0)),

			container.createTrajectoryConfig());

		return trajectory;
	}

}