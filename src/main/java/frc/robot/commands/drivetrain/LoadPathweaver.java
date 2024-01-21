package frc.robot.commands.drivetrain;
import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class LoadPathweaver extends SequentialCommandGroup{
    private String PATHSTRING;	

	public LoadPathweaver(Drivetrain drivetrain, RobotContainer container, String path) {	
        this.PATHSTRING = path;
		addCommands(
			new DrivetrainSwerveRelative(drivetrain, container, Pathweaver(PATHSTRING))
        ); 
    }
    Trajectory Pathweaver(String str){
        Trajectory trajectory = new Trajectory();
        String Path = "output/output/" + str + ".wpilib.json";
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(Path);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + Path, ex.getStackTrace());
        }
        return trajectory;
    }
}


    