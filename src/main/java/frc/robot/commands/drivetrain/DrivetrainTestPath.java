package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.trajectories.MoveBackward;
import frc.robot.trajectories.MoveForward;
import frc.robot.trajectories.MoveLeft;
import frc.robot.trajectories.MoveRight;

public class DrivetrainTestPath extends SequentialCommandGroup {
    public DrivetrainTestPath(RobotContainer container, Drivetrain drivetrain, int distance){addCommands(
        //SHOULD MOVE FORWARD, LEFT, BACKWARD, RIGHT, END UP AT BEGINNING
        new MoveForward(drivetrain, container, distance),
        new MoveBackward(drivetrain, container, distance),
        new MoveLeft(drivetrain, container, distance),
        new MoveRight(drivetrain, container, distance)
    );}
}
