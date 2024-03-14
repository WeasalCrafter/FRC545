package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Photonvision;

public class range extends Command {
    private Drivetrain m_robotDrive;
    private Photonvision m_visionSystem;
    private double forwardSpeed;

    public range(Drivetrain robotDrive, Photonvision visionSystem) {
        m_robotDrive = robotDrive;
        m_visionSystem = visionSystem;

        addRequirements(robotDrive, visionSystem);
    }

    @Override
    public void execute() {
        forwardSpeed = m_visionSystem.getSpeeds()[0];
        m_robotDrive.drive(forwardSpeed, 0, 0, false, true);
    }

    @Override
    public boolean isFinished() {
        return false; 
    }
}
