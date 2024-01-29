package frc.robot;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.drivetrain.DrivetrainReverseHeading;
import frc.robot.commands.drivetrain.DrivetrainTestPath;
import frc.robot.commands.drivetrain.LoadPathweaver;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.trajectories.MoveForward;
import frc.robot.trajectories.MoveSShape;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotContainer {
	public static final String AUTON_PATH_FLIP = "TEST";
	public static final String AUTON_FORWARD = "Forward";
	public static final String AUTON_S_SHAPE = "S Shape";
	private SendableChooser<String> autonChooser = new SendableChooser<>();

	private final Field2d field = new Field2d(); //  a representation of the field

	private final Drivetrain m_robotDrive = new Drivetrain();
	private final Intake m_intake = new Intake();

	XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);

	public RobotContainer() {
		autonChooser.addOption("Forward", AUTON_FORWARD);
		autonChooser.setDefaultOption("S Shape", AUTON_S_SHAPE);
		SmartDashboard.putData("Auto choices", autonChooser);
		
		configureButtonBindings();

		m_robotDrive.setDefaultCommand(		
			new RunCommand(
				() -> m_robotDrive.drive(
					-MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
					-MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
					-MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
					true, true),
				m_robotDrive));

		m_intake.setDefaultCommand(new RunCommand( //INTAKE WHILE-PRESSED MODE
			() -> m_intake.endIntakeOuttake(), m_intake));
	}

	private void configureButtonBindings() {		
		new JoystickButton(m_driverController, Button.kA.value) //INTAKE WHILE-PRESSED MODE
			.whileTrue(new RunCommand(
				() -> m_intake.startIntake(),
				m_intake));
		
		new JoystickButton(m_driverController, Button.kB.value) //INTAKE WHILE-PRESSED MODE
			.whileTrue(new RunCommand(
				() -> m_intake.startOuttake(),
				m_intake));

		new JoystickButton(m_driverController, Button.kX.value)
			.whileTrue(new RunCommand(() -> m_robotDrive.setX(), m_robotDrive));

		new JoystickButton(m_driverController, Button.kY.value)
			.onTrue(new DrivetrainTestPath(this, m_robotDrive, 1));
			//.onTrue(new DrivetrainReverseHeading(m_robotDrive));   

		//lefTrigger.whileTrue(new RunCommand(() -> m_robotDrive.setX(), m_robotDrive));
		//.whileTrue(new RunCommand(() -> m_intake.startIntake(), m_intake));
					
	}

	public Command getAutonomousCommand() {
		switch (getSelected()) {
			case AUTON_FORWARD:
				return new MoveForward(m_robotDrive, this, 3); //3 meters
			case AUTON_S_SHAPE:
				return new MoveSShape(m_robotDrive, this, 3);
			default:
				return new MoveForward(m_robotDrive, this, 3);
		}
	}

	public TrajectoryConfig createTrajectoryConfig(){
		TrajectoryConfig config = new TrajectoryConfig(
			AutoConstants.kMaxSpeedMetersPerSecond,
			AutoConstants.kMaxAccelerationMetersPerSecondSquared).setKinematics(DrivetrainConstants.kDriveKinematics);
		return config;
	}

	public Field2d getField()
	{
		return field;
	}

	public String getSelected()
	{
		return autonChooser.getSelected();
	}

	public Drivetrain getDrive()
	{
		return m_robotDrive;
	}

	public Intake getIntake()
	{
		return m_intake;
	}
}
