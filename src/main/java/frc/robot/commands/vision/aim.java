package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Photonvision;

public class aim extends Command {
    private Drivetrain m_robotDrive;
    private Photonvision m_visionSystem;
    private double rotationSpeed;

    public aim(Drivetrain robotDrive, Photonvision visionSystem) {
        m_robotDrive = robotDrive;
        m_visionSystem = visionSystem;

        addRequirements(robotDrive, visionSystem);
    }

    @Override
    public void execute() {
        rotationSpeed = m_visionSystem.getSpeeds()[2];
        m_robotDrive.drive(0, 0, rotationSpeed, true, true);
    }

    @Override
    public boolean isFinished() {
        return false; 
    }
}
