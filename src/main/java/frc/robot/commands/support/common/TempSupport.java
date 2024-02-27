package frc.robot.commands.support.common;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Support;

public class TempSupport extends SequentialCommandGroup {
    public TempSupport(Support support, double time, double speed){
        super(
            new StartSupport(support, speed),
            new WaitCommand(time),
            new StopSupport(support)
            //addRequirements(intake);
        );
    }
}
