package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class ReverseShooter extends InstantCommand{
    private final Shooter shooter;

    public ReverseShooter(Shooter shooter){
        this.shooter = shooter;
        addRequirements(shooter);
    }
    public void initialize(){
        shooter.reverseShoot();
        System.out.println("reverse shooter");
    }
}
