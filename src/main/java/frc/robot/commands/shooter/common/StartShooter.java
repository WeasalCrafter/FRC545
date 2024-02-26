package frc.robot.commands.shooter.common;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class StartShooter extends InstantCommand{
    private final Shooter shooter;
    private final double speed;

    public StartShooter(Shooter shooter, double speed){
        this.shooter = shooter;
        this.speed = speed;
        addRequirements(shooter);
    }
    public void initialize(){
        shooter.startShooter(speed);
        System.out.println("start shooter");
    }
}
