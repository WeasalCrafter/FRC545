package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision3d;

public class fullVision extends Command {
    private Drivetrain m_robotDrive;
    private Vision3d m_visionSystem;

    private double forwardSpeed;
    private double lateralSpeed;
    private double rotationSpeed;

    private double speedConstant = 0.1;

    public fullVision(Drivetrain robotDrive, Vision3d visionSystem) {
        m_robotDrive = robotDrive;
        m_visionSystem = visionSystem;

        addRequirements(robotDrive, visionSystem);
    }

    @Override
    public void execute() {
        double speeds[] = m_visionSystem.getSpeeds();

        forwardSpeed = speedConstant * speeds[0];
        lateralSpeed = speedConstant * speeds[1];
        rotationSpeed = speedConstant * speeds[2];

        m_robotDrive.drive(forwardSpeed, lateralSpeed, rotationSpeed, true, true);
    }

    @Override
    public boolean isFinished() {
        return false; 
    }
}
