package frc.robot.commands.shooter.common;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;

public class TempShooter extends SequentialCommandGroup {
    public TempShooter(Shooter shooter, double time, double speed){
        super(
            new StartShooter(shooter, speed),
            new WaitCommand(time),
            new StopShooter(shooter)
            //addRequirements(intake);
        );
    }
}
