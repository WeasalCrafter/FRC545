package frc.robot.routines.highShot;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.AutonConstants;
import frc.robot.Constants.SpeedConstants;
import frc.robot.RobotContainer;
import frc.robot.commands.shooter.common.TempShooter;
import frc.robot.commands.support.common.TempSupport;
import frc.robot.routines.highShot.common.pickupThenShootSide;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Support;

public class doubleRightHighShoot extends SequentialCommandGroup{
    public doubleRightHighShoot(Intake intake, Drivetrain drive, Shooter shooter, Support support, RobotContainer container){
        double SHOOT_TIME = AutonConstants.HighShot.SHOOT_TIME; 
        double ANGLE = AutonConstants.HighShot.SIDE_ANGLE_DEGREES; 
        double HIGH_SHOOT_SPEED = SpeedConstants.ShooterSpeedHigh;
        addCommands(
            new ParallelCommandGroup( //Shoot loaded note
                new TempShooter(shooter, SHOOT_TIME, HIGH_SHOOT_SPEED),
                new TempSupport(support, SHOOT_TIME, HIGH_SHOOT_SPEED)  
            ),
            
            new pickupThenShootSide(intake, drive, shooter, support, container, "right",ANGLE)
        );
    }
}

