package frc.robot.commands.intake.common;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Intake;

public class TempIntake extends SequentialCommandGroup {
    public TempIntake(Intake intake, double time, double speed){
        super(
            new StartIntake(intake, speed),
            new WaitCommand(time),
            new StopIntake(intake)
            //addRequirements(intake);
        );
    }
}
