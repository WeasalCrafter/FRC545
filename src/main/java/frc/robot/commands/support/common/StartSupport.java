package frc.robot.commands.support.common;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Support;

public class StartSupport extends InstantCommand{
    private final Support support;
    private final double speed;

    public StartSupport(Support support, double speed){
        this.support = support;
        this.speed = speed;
        addRequirements(support);
    }
    public void initialize(){
        support.startSupport(speed);
        System.out.println("start support");
    }
}
