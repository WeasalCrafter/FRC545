package frc.robot.commands.common;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants.SpeedConstants;
import frc.robot.commands.shooter.common.TempShooter;
import frc.robot.commands.support.common.TempSupport;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Support;

public class HumanInput extends ParallelCommandGroup{
    private double time = SpeedConstants.HumanInputTime;
    private double speed = SpeedConstants.HumanInputSpeed;

    public HumanInput(Shooter shooter, Support support, Double seconds){
        if(seconds == null){
            seconds=time;
        }
        
        addCommands(
            new TempShooter(shooter, time, speed),
            new TempSupport(support, time, speed)
        );
    }
}
