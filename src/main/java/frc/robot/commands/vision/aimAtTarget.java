package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class aimAtTarget extends Command {
    private Drivetrain m_robotDrive;
    private Vision m_visionSystem;
    private double rotationSpeed;

    public aimAtTarget(Drivetrain robotDrive, Vision visionSystem) {
        m_robotDrive = robotDrive;
        m_visionSystem = visionSystem;
        addRequirements(robotDrive, visionSystem);
    }

    @Override
    public void execute() {
        rotationSpeed = m_visionSystem.rotationSpeed();
        System.out.println("Aiming: " + rotationSpeed);
        m_robotDrive.drive(0, 0, rotationSpeed, true, true);
    }

    @Override
    public boolean isFinished() {
        return false; 
    }
}
