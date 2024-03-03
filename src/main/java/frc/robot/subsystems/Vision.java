package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.VisionConstants;

public class Vision extends SubsystemBase{

    private final double ANGULAR_P;
    private final double ANGULAR_D;
    private PIDController turnController;

    private final double LINEAR_P;
    private final double LINEAR_D;
    private PIDController forwardController;

	private double cameraHeight;
	private double targetHeight;
	private	double cameraAngle;

    PhotonCamera camera;

    public Vision(){
        camera = new PhotonCamera(OIConstants.cameraName);

        ANGULAR_P = VisionConstants.ANGULAR_P;
        ANGULAR_D = VisionConstants.ANGULAR_D;
        turnController = new PIDController(ANGULAR_P, 0, ANGULAR_D);

        LINEAR_P = VisionConstants.LINEAR_P;
        LINEAR_D = VisionConstants.LINEAR_D;
        forwardController = new PIDController(LINEAR_P, 0, LINEAR_D);

		cameraHeight = VisionConstants.CameraHeightMeters;
		targetHeight = VisionConstants.TargetHeightMeters;
		cameraAngle = VisionConstants.CameraPitchRadians;
    }

    public PhotonTrackedTarget getTarget(PhotonCamera cam){
        var result = cam.getLatestResult();
        if (checkTarget(cam) == true){
            return result.getBestTarget();
        }else{
            return null;    
        }
    }

    public Boolean checkTarget(PhotonCamera cam){
        var result = cam.getLatestResult();
        if (result.hasTargets()) {
            return true;
        }else{
            return false;
        }
    } 

	public double getRange(PIDController controller, double TargetHeightMeters){
        PhotonCamera cam = camera;
        PhotonTrackedTarget target = getTarget(cam);
        if (target != null){
            double range =
                PhotonUtils.calculateDistanceToTargetMeters(
                    cameraHeight,
                    TargetHeightMeters,
                    cameraAngle,
                    Units.degreesToRadians(target.getPitch())
                );
            return range;
        }else{
            return 0;
        }
    }

    public double forwardSpeed(){
        PhotonCamera cam = camera;
        PIDController controller = forwardController;
        double targetRangeMeters = 1;

        double range = getRange(controller,targetHeight);
        
        if (range != 0){
            return -0.1 * -controller.calculate(range, targetRangeMeters);
        }else{
            return 0;
        }
    }

    public double rotationSpeed(){
        PhotonCamera cam = camera;
        PIDController controller = turnController;
        PhotonTrackedTarget target = getTarget(cam); 

        return 0.1 * controller.calculate(target.getYaw(), 0);
    }

}
