package frc.robot.routines.highShot.common;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.SpeedConstants;
import frc.robot.AutonConstants.HighShot;
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
import frc.robot.trajectories.MoveDiagonalBackward;
import frc.robot.trajectories.MoveDiagonalForward;
import frc.robot.trajectories.MoveForward;
import frc.robot.trajectories.MoveLShape;
import frc.robot.trajectories.MoveLeft;
import frc.robot.trajectories.MoveRight;

public class pickupThenShootMiddleSide extends SequentialCommandGroup{


    public pickupThenShootMiddleSide(Intake intake, Drivetrain drive, Shooter shooter, Support support, RobotContainer container, String direction){
        double DISTANCE_TO_NOTE_METERS = HighShot.DISTANCE_TO_NOTE_METERS;
        double DISTANCE_BETWEEN_NOTES_METERS = HighShot.DISTANCE_BETWEEN_NOTES_METERS;
        double SHOOT_TIME = HighShot.SHOOT_TIME; 
        double OUTTAKE_TIME = HighShot.OUTTAKE_TIME;
        double HUMANN_INPUT_TIME = HighShot.HUMANN_INPUT_TIME; 
        double SHOOT_DELAY_TIME = HighShot.SHOOT_DELAY_TIME;

        double HIGH_SHOOT_SPEED = SpeedConstants.ShooterSpeedHigh;
        double INTAKE_SPEED = SpeedConstants.IntakeSpeed;

        double constant = 0;

        if(direction == "left"){
            constant = 1;
        }else if(direction == "right"){
            constant = -1;
        }

        addCommands(
			new StartIntake(intake, -1*INTAKE_SPEED), //Start Intaking
			new StartSupport(support, INTAKE_SPEED),

            new MoveForward(drive, container, ((1/3)*DISTANCE_TO_NOTE_METERS)),
            new MoveLeft(drive, container, (constant * DISTANCE_BETWEEN_NOTES_METERS)),
            new MoveForward(drive, container, ((2/3)*DISTANCE_TO_NOTE_METERS)),
            // new MoveLShape(drive, container, DISTANCE_TO_NOTE_METERS,(constant * DISTANCE_BETWEEN_NOTES_METERS)),

            new StopIntake(intake), //Stop Intaking
            new StopSupport(support),

            new HumanInput(shooter, support, HUMANN_INPUT_TIME),

            new MoveBackward(drive, container, ((1/3)*DISTANCE_TO_NOTE_METERS)),
            new MoveRight(drive, container, (constant * -DISTANCE_BETWEEN_NOTES_METERS)),
            new MoveBackward(drive, container, ((2/3)*DISTANCE_TO_NOTE_METERS)),
            // new MoveLShape(drive, container, DISTANCE_TO_NOTE_METERS,(constant * -DISTANCE_BETWEEN_NOTES_METERS)),

            new StartIntake(intake, INTAKE_SPEED), //Start Outtaking
            new StartSupport(support, -1*INTAKE_SPEED),

            new WaitCommand(OUTTAKE_TIME),

            new StopIntake(intake), //Stop Outtaking
            new StopSupport(support),

            new WaitCommand(SHOOT_DELAY_TIME), //Slight delay before shooting

            new ParallelCommandGroup( //Shoot loaded note
                new TempShooter(shooter, SHOOT_TIME, HIGH_SHOOT_SPEED),
                new TempSupport(support, SHOOT_TIME, HIGH_SHOOT_SPEED)  
            )
        );

    }


}
