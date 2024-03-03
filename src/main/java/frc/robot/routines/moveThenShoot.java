package frc.robot.routines;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.shooter.common.TempShooter;
import frc.robot.commands.support.common.TempSupport;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Support;
import frc.robot.trajectories.MoveBackward;
import frc.robot.trajectories.MoveForward;

public class moveThenShoot extends SequentialCommandGroup{

    public moveThenShoot(Drivetrain drive, Shooter shooter, Support support, RobotContainer container, Double time, Double distance, Double speed){
        
        addCommands(
            new MoveForward(drive, container, distance),
            new MoveBackward(drive, container, distance),
            new ParallelCommandGroup(
                new TempShooter(shooter, time, speed),
                new TempSupport(support, time, speed)  
            )
        );

    }
}
