package frc.robot.routines.highShot;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.trajectories.MoveForward;

public class noShotForward extends SequentialCommandGroup{
    public noShotForward(RobotContainer container, Drivetrain drive, double distance, double delay){
        addCommands(
            new WaitCommand(delay),
            new MoveForward(drive, container, distance)
        );
    }
}
