package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Intake;

public class TempIntake extends SequentialCommandGroup {
    public TempIntake(Intake intake, double i){
        super(
            new StartIntake(intake),
            new WaitCommand(i),
            new StopIntake(intake)
            //addRequirements(intake);
        );
    }

}
