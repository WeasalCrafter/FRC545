package frc.robot.commands.climber.common;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Climber;

public class StartDescend extends InstantCommand{
    private final Climber climber;
    private final double speed;

    public StartDescend(Climber climber, double speed){
        this.climber = climber;
        this.speed = speed;
        addRequirements(climber);
    }
    public void initialize(){
        climber.descend(speed);
        System.out.println("start descend");
    }
}