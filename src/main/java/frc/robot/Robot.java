// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class Robot extends TimedRobot {
	private Command m_autonomousCommand;
	private RobotContainer m_robotContainer;
	//private PhotonCamera camera = new PhotonCamera("photonvision");

	@Override
	public void robotInit() {
		m_robotContainer = new RobotContainer();
		Field2d m_field = new Field2d();
		
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
		m_autonomousCommand = m_robotContainer.getAutonomousCommand();
		if (m_autonomousCommand != null) {
			m_autonomousCommand.schedule();
		}
	}

	@Override
	public void autonomousPeriodic() {
		new RunCommand(() -> m_robotContainer.getLights().DefaultState(), m_robotContainer.getLights());
		updateToSmartDash();
	}

	@Override
	public void teleopInit() {		
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		
	}

	@Override
	public void teleopPeriodic() {
		updateToSmartDash();
		// var result = camera.getLatestResult();
        // boolean hasTargets = result.hasTargets();
        // SmartDashboard.putBoolean("hasTargets", hasTargets);
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
		
		//FL, FR, BL, BR
		double offsets[] = {
			m_robotContainer.getDrive().FRONT_LEFT_VIRTUAL_OFFSET_RADIANS,
			m_robotContainer.getDrive().FRONT_RIGHT_VIRTUAL_OFFSET_RADIANS,
			m_robotContainer.getDrive().REAR_LEFT_VIRTUAL_OFFSET_RADIANS,
			m_robotContainer.getDrive().REAR_RIGHT_VIRTUAL_OFFSET_RADIANS
		};

		//FL, FR, BL, BR
		double virtualPositions[] = {
			m_robotContainer.getDrive().getFrontLeftModule().getTurningAbsoluteEncoder().getVirtualPosition(),
			m_robotContainer.getDrive().getFrontRightModule().getTurningAbsoluteEncoder().getVirtualPosition(),
			m_robotContainer.getDrive().getRearLeftModule().getTurningAbsoluteEncoder().getVirtualPosition(),
			m_robotContainer.getDrive().getRearRightModule().getTurningAbsoluteEncoder().getVirtualPosition()
		};

		//FL, FR, BL, BR
		double absolutePositions[] = {
			m_robotContainer.getDrive().getFrontLeftModule().getTurningAbsoluteEncoder().getPosition(),
			m_robotContainer.getDrive().getFrontRightModule().getTurningAbsoluteEncoder().getPosition(),
			m_robotContainer.getDrive().getRearLeftModule().getTurningAbsoluteEncoder().getPosition(),
			m_robotContainer.getDrive().getRearRightModule().getTurningAbsoluteEncoder().getPosition()
		};


		//STICKY FAULTS
		m_robotContainer.getIntake().clearFaults();
		m_robotContainer.getClimber().clearFaults();
		m_robotContainer.getShooter().clearFaults();
		m_robotContainer.getSupport().clearFaults();
		m_robotContainer.getDrive().getFrontLeftModule().clearFaults();
		m_robotContainer.getDrive().getFrontRightModule().clearFaults();
		m_robotContainer.getDrive().getRearLeftModule().clearFaults();
		m_robotContainer.getDrive().getRearRightModule().clearFaults();

		double flTest = virtualPositions[0] - absolutePositions[0];
		double frTest = virtualPositions[1] - absolutePositions[1];
		double blTest = virtualPositions[2] - absolutePositions[2];
		double brTest = virtualPositions[3] - absolutePositions[3];

		double[] frontLeft = {flTest, Math.round(flTest * 100.0) / 100.0};
		double[] frontRight = {frTest, Math.round(frTest * 100.0) / 100.0};
		double[] backLeft = {blTest, Math.round(blTest * 100.0) / 100.0};
		double[] backRight = {brTest, Math.round(brTest * 100.0) / 100.0};

		SmartDashboard.putNumberArray("Front Left", frontLeft);
		SmartDashboard.putNumberArray("Front Right", frontRight);
		SmartDashboard.putNumberArray("Back Left", backLeft);
		SmartDashboard.putNumberArray("Back Right", backRight);

		// SmartDashboard.putNumber("Front Left Calculated Offset", flTest);
		// SmartDashboard.putNumber("Front Right Calculated Offset", frTest);
		// SmartDashboard.putNumber("Back Left Calculated Offset", blTest);
		// SmartDashboard.putNumber("Back Right Calculated Offset", brTest);

		// SmartDashboard.putString("Light Status", m_robotContainer.getLights().GetState());
		
		// SmartDashboard.putNumberArray("offsets", offsets);
		// SmartDashboard.putNumberArray("absolute", absolutePositions);
		// SmartDashboard.putNumberArray("virtual", virtualPositions);
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
