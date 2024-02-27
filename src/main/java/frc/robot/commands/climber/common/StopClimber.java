package frc.robot.commands.climber.common;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Climber;

public class StopClimber extends InstantCommand{
    private final Climber climber;

    public StopClimber(Climber climber){
        this.climber = climber;
        addRequirements(climber);
    }
    public void initialize(){
        climber.stop();
        System.out.println("stop climb");
    }
}