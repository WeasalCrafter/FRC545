package frc.robot;

import edu.wpi.first.net.PortForwarder;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
	private Command m_autonomousCommand;
	private RobotContainer m_robotContainer;

	@Override
	public void robotInit() {
		m_robotContainer = new RobotContainer();
		Field2d m_field = new Field2d();
		
		PortForwarder.add(5800, "photonvision.local", 5800);

		SmartDashboard.putBoolean("Field Oriented", true);
		SmartDashboard.putData("Swerve Odometry", m_robotContainer.getField());
		SmartDashboard.putData("Field", m_field);
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
	}

	@Override
	public void disabledInit() {}

	@Override
	public void disabledPeriodic() {
		updateToSmartDash();
	}

	@Override
	public void autonomousInit() {
		m_robotContainer.getLights().ChangeState("auto");
		m_robotContainer.reset();

		m_autonomousCommand = m_robotContainer.getAutonomousCommand();
		if (m_autonomousCommand != null) {
			m_autonomousCommand.schedule();
		}
	}

	@Override
	public void autonomousPeriodic() {
		m_robotContainer.getLights().ChangeState("auto");
		updateToSmartDash();
	}

	@Override
	public void teleopInit() {		
		m_robotContainer.getLights().ChangeState("tele");
		m_robotContainer.reset();

		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		
	}

	@Override
	public void teleopPeriodic() {
		updateToSmartDash();
	}

	public void updateToSmartDash()
	{	
		//FL, FR, BL, BR
		double loggingState[] = {
			m_robotContainer.getDrive().getFrontLeftModule().getDesiredState().angle.getRadians(),
			m_robotContainer.getDrive().getFrontLeftModule().getDesiredState().speedMetersPerSecond,
			m_robotContainer.getDrive().getFrontRightModule().getDesiredState().angle.getRadians(),
			m_robotContainer.getDrive().getFrontRightModule().getDesiredState().speedMetersPerSecond,
			m_robotContainer.getDrive().getRearLeftModule().getDesiredState().angle.getRadians(),
			m_robotContainer.getDrive().getRearLeftModule().getDesiredState().speedMetersPerSecond,
			m_robotContainer.getDrive().getRearRightModule().getDesiredState().angle.getRadians(),
			m_robotContainer.getDrive().getRearRightModule().getDesiredState().speedMetersPerSecond
		};

		SmartDashboard.putNumberArray("SwerveModuleStates", loggingState);

		m_robotContainer.getField().setRobotPose(m_robotContainer.getDrive().getPose());
		
	}

	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {}
}
