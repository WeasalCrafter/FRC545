package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class fullVision extends InstantCommand {
    private Drivetrain m_robotDrive;
    private Vision m_visionSystem;
    private double rotationSpeed;
    private double forwardSpeed;

    public fullVision(Drivetrain robotDrive, Vision visionSystem) {
        m_robotDrive = robotDrive;
        m_visionSystem = visionSystem;

        addRequirements(robotDrive, visionSystem);
    }

    @Override
    public void execute() {
        rotationSpeed = m_visionSystem.rotationSpeed();
        forwardSpeed = m_visionSystem.forwardSpeed();
        m_robotDrive.drive(forwardSpeed, 0, rotationSpeed, true, true);
    }
}
