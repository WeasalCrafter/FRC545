package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.commands.vision.aimAtTarget;

public class Vision3d extends SubsystemBase{

    private final double LINEAR_P;
    private final double LINEAR_D;
    private final double ANGULAR_P; 
    private final double ANGULAR_D;
    private PIDController linearController;
    private PIDController angularController;
    private PhotonCamera camera;
    private double targetDistance;
    private TrapezoidProfile angularProfile;

    public Vision3d(){
        LINEAR_P = VisionConstants.LINEAR_P;
        LINEAR_D = VisionConstants.LINEAR_D;
        ANGULAR_P = VisionConstants.ANGULAR_P;
        ANGULAR_D = VisionConstants.ANGULAR_D;

        linearController = new PIDController(LINEAR_P, 0, LINEAR_D);
        angularController = new PIDController(ANGULAR_P, 0, ANGULAR_D);

        camera = new PhotonCamera(OIConstants.cameraName);
        targetDistance = 1; // METERS
    }

    public double angle(){
        PhotonTrackedTarget target = null;
        PhotonPipelineResult result = camera.getLatestResult();
        
        double angularSpeed = 0;
        
        double zAngle;
        double zAngleAbs;
        
        double range = 5; // DEGREES

        if(result.hasTargets()){
            target = result.getBestTarget();
            var pose = target.getBestCameraToTarget();
            if(pose!=null){
                zAngle = Units.radiansToDegrees(pose.getRotation().getZ());
                zAngleAbs = Math.abs(zAngle);
                angularSpeed = angularController.calculate(zAngleAbs, 180);

                if(zAngle < 0){
                    angularSpeed = -0.5 * angularSpeed;
                }

                if((180 - range) < zAngleAbs && zAngleAbs < (180 + range)){
                    angularSpeed = 0;
                }

                SmartDashboard.putNumber("z angle: ", zAngle);
                SmartDashboard.putNumber("z speed: ", angularSpeed);
            }
        }

        return angularSpeed;
    }

    public double[] getSpeeds(){
        PhotonTrackedTarget target = null;
        double forwardSpeed = 0;
        double lateralSpeed = 0;
        double angularSpeed = 0;

        PhotonPipelineResult result = camera.getLatestResult();
        if(result.hasTargets()){
            target = result.getBestTarget();
            var pose = target.getBestCameraToTarget();
            if(pose!=null){
                Translation3d translation = pose.getTranslation();
                double x = translation.getX();
                double y = translation.getY();
                double z = pose.getZ();

                angularSpeed = angularController.calculate(z, 180);
                forwardSpeed = linearController.calculate(x, targetDistance);
                lateralSpeed = linearController.calculate(y, 0);
                // System.out.println("distance: "+ x + " meters");
                // System.out.println("forward speed: "+ forwardSpeed);

                // System.out.println("lat distance: "+ y + " meters");
                // System.out.println("lat speed: "+ lateralSpeed);

                System.out.println("distance: "+ z + " meters");
                System.out.println("speed: "+ angularSpeed);
            }else{
                System.out.println("no position");
            }
        }else{
            System.out.println("no target");
        }
        double[] speeds = {
            forwardSpeed,
            lateralSpeed,
            angularSpeed
        };

        return speeds;
    }
}
