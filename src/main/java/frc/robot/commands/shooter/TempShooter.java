package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;

public class TempShooter extends SequentialCommandGroup {
    public TempShooter(Shooter shooter, double i){
        super(
            new StartShooter(shooter),
            new WaitCommand(i),
            new StopShooter(shooter)
            //addRequirements(intake);
        );
    }
}
