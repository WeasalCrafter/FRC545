// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class Robot extends TimedRobot {
	private Command m_autonomousCommand;
	private RobotContainer m_robotContainer;

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

		SmartDashboard.putNumber("Intake Speed", m_robotContainer.getIntake().getSpeed());
		SmartDashboard.putString("Light Status", m_robotContainer.getLights().GetState());
		
		SmartDashboard.putNumberArray("offsets", offsets);
		SmartDashboard.putNumberArray("absolute", absolutePositions);
		SmartDashboard.putNumberArray("virtual", virtualPositions);
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
