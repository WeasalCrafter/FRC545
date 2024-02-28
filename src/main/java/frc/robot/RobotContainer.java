package frc.robot;
import org.photonvision.PhotonCamera;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.SpeedConstants;
import frc.robot.commands.common.HumanInput;
import frc.robot.commands.drivetrain.DrivetrainReverseHeading;
import frc.robot.commands.intake.common.StartIntake;
import frc.robot.commands.intake.common.StopIntake;
import frc.robot.commands.shooter.common.StartShooter;
import frc.robot.commands.shooter.common.StopShooter;
import frc.robot.commands.support.common.StartSupport;
import frc.robot.commands.support.common.StopSupport;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Support;
import frc.robot.trajectories.MoveForward;
import frc.robot.trajectories.MoveSShape;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotContainer {
	public static final String AUTON_PATH_FLIP = "TEST";
	public static final String AUTON_FORWARD = "Forward";
	public static final String AUTON_S_SHAPE = "S Shape";
	private SendableChooser<String> autonChooser = new SendableChooser<>();

	private final Field2d field = new Field2d(); //  a representation of the field

	private final Lights m_lights = new Lights(); // MUST BE BEFORE ITS USED
	private final Support m_support = new Support();

	private final Drivetrain m_robotDrive = new Drivetrain();
	private final Intake m_intake = new Intake();
	private final Shooter m_shooter = new Shooter();
	private final Climber m_climber = new Climber();

    private final double ANGULAR_P = 0.1;
    private final double ANGULAR_D = 0.0;
    private PIDController turnController = new PIDController(ANGULAR_P, 0, ANGULAR_D);

	PhotonCamera camera = new PhotonCamera(OIConstants.cameraName);

	CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
	CommandXboxController m_operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);

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

		//m_intake.setDefaultCommand(new StopIntake(m_intake));
		// m_lights.setDefaultCommand(new RunCommand(() -> m_lights.DefaultState(), m_lights));
		// m_support.setDefaultCommand(new RunCommand(() -> m_support.stopSupport(), m_support));
		// m_shooter.setDefaultCommand(new RunCommand(() -> m_shooter.stopShooter(), m_shooter));

	}

	private void configureButtonBindings() {	
		m_driverController.a() // AIMING
				.whileTrue(			
					new RunCommand(
					() -> m_robotDrive.drive(
						0,
						0,
						aimSpeed(camera, turnController),
						true, true),
					m_robotDrive));

		// m_driverController.b() //CLIMBING
		// 		.whileTrue(new RunCommand(() -> m_lights.ClimbReverseState(), m_lights));

		m_driverController.x() // BRAKE
				.whileTrue(new RunCommand(() -> m_robotDrive.setX(), m_robotDrive))
				.whileTrue(new RunCommand(() -> m_lights.BreakState(), m_lights));

		m_driverController.y() // REVERSE HEADING
				.onTrue(new DrivetrainReverseHeading(m_robotDrive));

		m_driverController.leftTrigger() // START INTAKE
				.whileTrue(new StartIntake(m_intake, SpeedConstants.IntakeSpeed))
				.whileTrue(new StartSupport(m_support, -1*SpeedConstants.IntakeSpeed))
				.whileFalse(new StopIntake(m_intake))
				.whileFalse(new StopSupport(m_support));

		m_driverController.leftBumper() // START OUTTAKE
				.whileTrue(new StartIntake(m_intake, -1*SpeedConstants.IntakeSpeed))
				.whileTrue(new StartSupport(m_support, SpeedConstants.IntakeSpeed))
				.whileFalse(new StopIntake(m_intake))
				.whileFalse(new StopSupport(m_support));

		m_driverController.rightTrigger() // SHOOT
				.whileTrue(new StartShooter(m_shooter, SpeedConstants.ShooterSpeedDefault))
				.whileTrue(new StartSupport(m_support, SpeedConstants.ShooterSpeedDefault))
				.whileFalse(new StopShooter(m_shooter))
				.whileFalse(new StopSupport(m_support));

		m_driverController.rightBumper() // HUMAN INPUT3
				.onTrue(new HumanInput(m_shooter,m_support));
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

	public TrajectoryConfig createReverseTrajectoryConfig() {
		TrajectoryConfig config = createTrajectoryConfig();
		config.setReversed(true);
		return config;
	}

	public double aimSpeed(PhotonCamera camera, PIDController turnController){
		double rotationSpeed = 0;
		var result = camera.getLatestResult();
		if (result.hasTargets()) {
			rotationSpeed = -turnController.calculate(result.getBestTarget().getYaw(), 0);
		}
		return rotationSpeed;
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

	public Shooter getShooter()
	{
		return m_shooter;
	}

	public Climber getcClimber()
	{
		return m_climber;
	}

	public Lights getLights()
	{
		return m_lights;
	}
	public Support getSupport()
	{
		return m_support;
	}
}
