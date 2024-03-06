package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision3d;

public class rotationAim extends Command {
    private Drivetrain m_robotDrive;
    private Vision3d m_visionSystem;

    private double forwardSpeed;
    private double lateralSpeed;
    private double rotationSpeed;

    private double speedConstant = 0.5;

    public rotationAim(Drivetrain robotDrive, Vision3d visionSystem) {
        m_robotDrive = robotDrive;
        m_visionSystem = visionSystem;

        addRequirements(robotDrive, visionSystem);
    }

    @Override
    public void execute() {
        double speeds[] = m_visionSystem.getSpeeds();

        forwardSpeed = 0 * speeds[0];
        lateralSpeed = 0 * speeds[1];

        rotationSpeed = -0.1 * m_visionSystem.angle();;

        m_robotDrive.drive(forwardSpeed, lateralSpeed, rotationSpeed, true, true);
    }

    @Override
    public boolean isFinished() {
        return false; 
    }
}
