package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class StartShooter extends InstantCommand{
    private final Shooter shooter;

    public StartShooter(Shooter shooter){
        this.shooter = shooter;
        addRequirements(shooter);
    }
    public void initialize(){
        shooter.shoot();
        System.out.println("start shooter");
    }
}
