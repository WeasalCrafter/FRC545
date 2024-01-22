// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	private Command m_autonomousCommand;

	private RobotContainer m_robotContainer;
	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		// Instantiate our RobotContainer.  This will perform all our button bindings, and put our
		// autonomous chooser on the dashboard.
		m_robotContainer = new RobotContainer();

		SmartDashboard.putData("Swerve Odometry", m_robotContainer.getField());		
	}

	/**
	 * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
	 * that you want ran during disabled, autonomous, teleoperated and test.
	 *
	 * <p>This runs after the mode specific periodic functions, but before LiveWindow and
	 * SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic() {
		// Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
		// commands, running already-scheduled commands, removing finished or interrupted commands,
		// and running subsystem periodic() methods.  This must be called from the robot's periodic
		// block in order for anything in the Command-based framework to work.
		CommandScheduler.getInstance().run();
	}

	/** This function is called once each time the robot enters Disabled mode. */
	@Override
	public void disabledInit() {}

	@Override
	public void disabledPeriodic() {

		updateToSmartDash();
	}

	/** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_robotContainer.getAutonomousCommand();
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		if (m_autonomousCommand != null) {
			m_autonomousCommand.schedule();
		}
	}

	/** This function is called periodically during autonomous. */
	@Override
	public void autonomousPeriodic() {
		updateToSmartDash();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/** This function is called periodically during operator control. */
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

		final Field2d m_field = new Field2d();
		
		// Do this in either robot or subsystem init
		SmartDashboard.putData("Field", m_field);

		SmartDashboard.putNumber("Front Left Offset", m_robotContainer.getDrive().FRONT_LEFT_VIRTUAL_OFFSET_RADIANS);
		SmartDashboard.putNumber("Front Right Offset", m_robotContainer.getDrive().FRONT_RIGHT_VIRTUAL_OFFSET_RADIANS);
		SmartDashboard.putNumber("Back Left Offset", m_robotContainer.getDrive().REAR_LEFT_VIRTUAL_OFFSET_RADIANS);
		SmartDashboard.putNumber("Back Right Offset", m_robotContainer.getDrive().REAR_RIGHT_VIRTUAL_OFFSET_RADIANS);

		SmartDashboard.putNumber("Front Left Absolute", m_robotContainer.getDrive().getFrontLeftModule().getTurningAbsoluteEncoder().getPosition());
		SmartDashboard.putNumber("Back Left Absolute", m_robotContainer.getDrive().getRearLeftModule().getTurningAbsoluteEncoder().getPosition());
		SmartDashboard.putNumber("Front Right Absolute", m_robotContainer.getDrive().getFrontRightModule().getTurningAbsoluteEncoder().getPosition());
		SmartDashboard.putNumber("Back Right Absolute", m_robotContainer.getDrive().getRearRightModule().getTurningAbsoluteEncoder().getPosition());

		SmartDashboard.putNumber("Front Left Virtual", m_robotContainer.getDrive().getFrontLeftModule().getTurningAbsoluteEncoder().getVirtualPosition());
		SmartDashboard.putNumber("Back Left Virtual", m_robotContainer.getDrive().getRearLeftModule().getTurningAbsoluteEncoder().getVirtualPosition());
		SmartDashboard.putNumber("Front Right Virtual", m_robotContainer.getDrive().getFrontRightModule().getTurningAbsoluteEncoder().getVirtualPosition());
		SmartDashboard.putNumber("Back Right Virtual", m_robotContainer.getDrive().getRearRightModule().getTurningAbsoluteEncoder().getVirtualPosition());
	
		SmartDashboard.putNumber("Intake Speed", m_robotContainer.getIntake().getSpeed());
		SmartDashboard.putNumberArray("SwerveModuleStates", loggingState);
		m_robotContainer.getField().setRobotPose(m_robotContainer.getDrive().getPose());
	}

	@Override
	public void testInit() {
		// Cancels all running commands at the start of test mode.
		CommandScheduler.getInstance().cancelAll();
	}

	/** This function is called periodically during test mode. */
	@Override
	public void testPeriodic() {}
}
