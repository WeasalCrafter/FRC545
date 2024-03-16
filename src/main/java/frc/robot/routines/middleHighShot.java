package frc.robot.routines;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.SpeedConstants;
import frc.robot.RobotContainer;
import frc.robot.commands.common.HumanInput;
import frc.robot.commands.intake.common.StartIntake;
import frc.robot.commands.intake.common.StopIntake;
import frc.robot.commands.shooter.common.TempShooter;
import frc.robot.commands.support.common.StartSupport;
import frc.robot.commands.support.common.StopSupport;
import frc.robot.commands.support.common.TempSupport;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Support;
import frc.robot.trajectories.MoveBackward;
import frc.robot.trajectories.MoveForward;

public class middleHighShot extends SequentialCommandGroup{

    public middleHighShot(Intake intake, Drivetrain drive, Shooter shooter, Support support, RobotContainer container){
        //double DISTANCE_TO_NOTE = 2; 
        double DISTANCE_TO_NOTE = 3; //testing
        
        double SHOOT_TIME = 1; 
        double OUTTAKE_TIME = 0.075;
        double HUMANN_INPUT_TIME = 0.5; 
        double SHOOT_DELAY_TIME = 0.5;

        double HIGH_SHOOT_SPEED = SpeedConstants.ShooterSpeedHigh;
        double INTAKE_SPEED = SpeedConstants.IntakeSpeed;

        addCommands(
            new ParallelCommandGroup( //Shoot loaded note
                new TempShooter(shooter, SHOOT_TIME, HIGH_SHOOT_SPEED),
                new TempSupport(support, SHOOT_TIME, HIGH_SHOOT_SPEED)  
            ),

			new StartIntake(intake, -1*INTAKE_SPEED), //Start Intaking
			new StartSupport(support, INTAKE_SPEED),

            new MoveForward(drive, container, DISTANCE_TO_NOTE), //Drive backward to note on floor

            new StopIntake(intake), //Stop Intaking
            new StopSupport(support),

            new HumanInput(shooter, support, HUMANN_INPUT_TIME),

            new MoveBackward(drive, container, DISTANCE_TO_NOTE), //Drive to goal

            new StartIntake(intake, INTAKE_SPEED), //Start Outtaking
            new StartSupport(support, -1*INTAKE_SPEED),

            new WaitCommand(OUTTAKE_TIME),

            new StopIntake(intake), //Stop Outtaking
            new StopSupport(support),

            new WaitCommand(SHOOT_DELAY_TIME), //Slight delay before shooting

            new ParallelCommandGroup( //Shoot loaded note
                new TempShooter(shooter, SHOOT_TIME, HIGH_SHOOT_SPEED),
                new TempSupport(support, SHOOT_TIME, HIGH_SHOOT_SPEED)  
            ),

            new MoveForward(drive, container, DISTANCE_TO_NOTE) //Drive backward to where note was
        );

    }
}
