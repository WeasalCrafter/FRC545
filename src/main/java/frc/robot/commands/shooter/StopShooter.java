package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class StopShooter extends InstantCommand{
    private final Shooter shooter;

    public StopShooter(Shooter shooter){
        this.shooter = shooter;
        addRequirements(shooter);
    }
    public void initialize(){
        shooter.shooterIdle();
        System.out.println("stop shooter");
    }
}
