package frc.robot.commands.intake.common;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class StartIntake extends InstantCommand{
    private final Intake intake;
    private final double speed;

    public StartIntake(Intake intake, double speed){
        this.intake = intake;
        this.speed = speed;
        addRequirements(intake);
    }
    public void initialize(){
        intake.startIntake(speed);
        System.out.println("start intake");
    }
}
