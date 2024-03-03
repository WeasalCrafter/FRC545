package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class getInRange extends InstantCommand {
    private Drivetrain m_robotDrive;
    private Vision m_visionSystem;
    private double forwardSpeed;

    public getInRange(Drivetrain robotDrive, Vision visionSystem) {
        m_robotDrive = robotDrive;
        m_visionSystem = visionSystem;

        addRequirements(robotDrive, visionSystem);
    }

    @Override
    public void execute() {
        forwardSpeed = m_visionSystem.forwardSpeed();
        m_robotDrive.drive(forwardSpeed, 0, 0, true, true);
    }
}
