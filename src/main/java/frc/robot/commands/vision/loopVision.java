package frc.robot.commands.vision;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.VisionConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Photonvision;

public class loopVision extends Command {
    private Drivetrain m_robotDrive;
    private Photonvision m_visionSystem;

    private double forwardSpeed;
    private double lateralSpeed;
    private double rotationSpeed;

    private double goalDeadband = VisionConstants.goalDeadband;

    public loopVision(Drivetrain robotDrive, Photonvision visionSystem) {
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
        PhotonTrackedTarget target = m_visionSystem.getTarget();
        double[] speeds = m_visionSystem.getSpeeds();

        if (target==null){
            return true;
        }else{
            return Math.abs(speeds[0]) < goalDeadband && Math.abs(speeds[1]) < goalDeadband && Math.abs(speeds[2]) < goalDeadband;
        }
    }
}
