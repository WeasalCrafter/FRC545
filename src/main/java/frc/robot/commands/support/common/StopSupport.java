package frc.robot.commands.support.common;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Support;

public class StopSupport extends InstantCommand{
    private final Support support;

    public StopSupport(Support support){
        this.support = support;
        //addRequirements(support);
    }
    public void initialize(){
        support.stopSupport();
        System.out.println("stop support");
    }
}
