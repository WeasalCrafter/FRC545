package frc.robot.subsystems;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.VisionConstants;

public class Photonvision extends SubsystemBase{

    private final double LINEAR_P;
    private final double LINEAR_D;
    private final double ANGULAR_P; 
    private final double ANGULAR_D;
    private PIDController linearController;
    private PIDController angularController;
    private PhotonCamera camera;
    private double targetDistance;
    private double cameraHorizontalOffset = VisionConstants.cameraHorizontalOffset;
    private double errorMargin = VisionConstants.rotationErrorMargin;

    private double lateralConstant = VisionConstants.lateralSpeed;
    private double forwardConstant = VisionConstants.forwardSpeed;
    private double angularConstant = VisionConstants.rotationSpeed;

    public Photonvision(){
        LINEAR_P = VisionConstants.LINEAR_P;
        LINEAR_D = VisionConstants.LINEAR_D;
        ANGULAR_P = VisionConstants.ANGULAR_P;
        ANGULAR_D = VisionConstants.ANGULAR_D;

        linearController = new PIDController(LINEAR_P, 0, LINEAR_D);
        angularController = new PIDController(ANGULAR_P, 0, ANGULAR_D);

        camera = new PhotonCamera(OIConstants.cameraName);

        targetDistance = 0.5; // METERS
    }

    public double angularSpeed(PhotonTrackedTarget target){
        double angularSpeed = 0;
        
        double zAngle;
        double zAngleAbs;
        
        var pose = target.getBestCameraToTarget();
        if(pose!=null){
            zAngle = Units.radiansToDegrees(pose.getRotation().getZ());
            zAngleAbs = Math.abs(zAngle);
            angularSpeed = angularController.calculate(zAngleAbs, 180);

            if(zAngle < 0){
                angularSpeed *= -1;
            }

            if((180 - errorMargin) < zAngleAbs && zAngleAbs < (180 + errorMargin)){
                angularSpeed = 0;
            }
        }
        
        angularSpeed *= (-1 * angularConstant);
        return angularSpeed;
    }

    public double[] linearSpeeds(PhotonTrackedTarget target){
        double forwardSpeed = 0;
        double lateralSpeed = 0;

            var pose = target.getBestCameraToTarget();
            if(pose!=null){
                Translation3d translation = pose.getTranslation();
                double x = translation.getX();
                double y = translation.getY();

                forwardSpeed = linearController.calculate(x, targetDistance);
                lateralSpeed = linearController.calculate(y, cameraHorizontalOffset);
            }
        
        double[] speeds = {
            (-1 * forwardConstant * forwardSpeed),
            (-1 * lateralConstant * lateralSpeed),
        };

        return speeds;
    }

    public double[] getSpeeds(){
        PhotonTrackedTarget target = getTarget();
        
        double rot = 0;
        double[] linear = {0,0};
    
        if(target != null){
            rot = angularSpeed(target);
            linear = linearSpeeds(target);
        }
        
        double[] speeds = {
            linear[0], //FORWARD
            linear[1], //LATERAL
            rot, //ANGULAR
        };
            
        return speeds;
    }

    public PhotonTrackedTarget getTarget(){
        PhotonPipelineResult result = camera.getLatestResult();
        PhotonTrackedTarget target = result.getBestTarget();

        // List<PhotonTrackedTarget> targets = result.getTargets();

        // for (PhotonTrackedTarget i : targets) {
        //     switch (i.getFiducialId()) {
        //         case 7:
        //             target = i; // HIGH BLUE
        //             break;
        //         case 6:
        //             target = i; // LOW BLUE
        //             break;
        //         case 4:
        //             target = i; // HIGH RED
        //             break;
        //         case 5:
        //             target = i; // LOW RED
        //             break;                
        //         case 16:
        //             target = i; //DEBUG
        //             System.out.println("read");
        //             break;
        //         default:
        //             break;
        //     }
        // }
        return target;
    }
}
