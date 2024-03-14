package frc.robot.commands.vision;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Photonvision;

public class fullVision extends Command {
    private Drivetrain m_robotDrive;
    private Photonvision m_visionSystem;

    private double forwardSpeed;
    private double lateralSpeed;
    private double rotationSpeed;

    public fullVision(Drivetrain robotDrive, Photonvision visionSystem) {
        m_robotDrive = robotDrive;
        m_visionSystem = visionSystem;

        addRequirements(robotDrive, visionSystem);
    }

    @Override
    public void execute() {
        double speeds[] = m_visionSystem.getSpeeds();

        forwardSpeed = speeds[0];
        lateralSpeed = speeds[1];
        rotationSpeed = speeds[2];

        m_robotDrive.drive(forwardSpeed, lateralSpeed, rotationSpeed, false, true);   

    }

    @Override
    public boolean isFinished() {
        return false; 
    }
}
