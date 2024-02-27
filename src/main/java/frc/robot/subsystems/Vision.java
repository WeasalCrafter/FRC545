package frc.robot.subsystems;

import org.photonvision.PhotonCamera;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase{
    // Change this to match the name of your camera
    private PhotonCamera camera = new PhotonCamera("Logitech,_Inc._Webcam_C270");

    // // PID constants should be tuned per robot
    // private final double LINEAR_P = 0.1;
    // private final double LINEAR_D = 0.0;
    // private PIDController forwardController = new PIDController(LINEAR_P, 0, LINEAR_D);

    // private final double ANGULAR_P = 0.1;
    // private final double ANGULAR_D = 0.0;
    // private PIDController turnController = new PIDController(ANGULAR_P, 0, ANGULAR_D);

    // private double CameraHeightMeters;
    // private double TargetHeightMeters;
    // private double CameraPitchRadians;

    public Vision(){//double camHeight, double targetHeight, double camPitch){
        // this.CameraHeightMeters = camHeight;
        // this.TargetHeightMeters = targetHeight;
        // this.CameraPitchRadians = camPitch;
    }
    @Override
    public void periodic() {
        // var result = camera.getLatestResult();    
        // boolean hasTargets = result.hasTargets();
    }
}
