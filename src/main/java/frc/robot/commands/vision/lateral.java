package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Photonvision;

public class lateral extends Command {
    private Drivetrain m_robotDrive;
    private Photonvision m_visionSystem;
    private double lateralSpeed;

    public lateral(Drivetrain robotDrive, Photonvision visionSystem) {
        m_robotDrive = robotDrive;
        m_visionSystem = visionSystem;

        addRequirements(robotDrive, visionSystem);
    }

    @Override
    public void execute() {
        lateralSpeed = m_visionSystem.getSpeeds()[1];
        m_robotDrive.drive(0, lateralSpeed, 0, false, true);
    }

    @Override
    public boolean isFinished() {
        return false; 
    }
}
