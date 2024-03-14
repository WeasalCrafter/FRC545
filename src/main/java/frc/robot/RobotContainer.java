package frc.robot;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.SpeedConstants;
import frc.robot.commands.drivetrain.DrivetrainReverseHeading;
import frc.robot.commands.drivetrain.DrivetrainZeroHeading;
import frc.robot.commands.intake.common.StartIntake;
import frc.robot.commands.intake.common.StopIntake;
import frc.robot.commands.shooter.common.StartShooter;
import frc.robot.commands.shooter.common.StopShooter;
import frc.robot.commands.support.common.StartSupport;
import frc.robot.commands.support.common.StopSupport;
import frc.robot.commands.vision.aim;
import frc.robot.commands.vision.fullVision;
import frc.robot.commands.vision.lateral;
import frc.robot.commands.vision.loopVision;
import frc.robot.commands.vision.range;
import frc.robot.routines.middleHighShot;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Support;
import frc.robot.subsystems.Photonvision;
import frc.robot.trajectories.MoveSShape;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotContainer {
	public static final String AUTON_MIDDLE_HIGHSHOOT = "Middle of High Shooter";
	public static final String AUTON_S_SHAPE = "S Shape";
	private SendableChooser<String> autonChooser = new SendableChooser<>();

	private final Field2d field = new Field2d();

	private final Lights m_lights = new Lights();
	private final Support m_support = new Support();

	private final Drivetrain m_robotDrive = new Drivetrain();
	private final Intake m_intake = new Intake();
	private final Shooter m_shooter = new Shooter();
	private final Climber m_climber = new Climber();
	private final Photonvision m_vision = new Photonvision();

	CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
	CommandXboxController m_operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);

	public RobotContainer() {
		autonChooser.setDefaultOption("Middle of High Shooter", AUTON_MIDDLE_HIGHSHOOT);
		autonChooser.addOption("S Shape", AUTON_S_SHAPE);
		SmartDashboard.putData("Auto choices", autonChooser);
		
		configureDriverBindings();
		configureOperatorBindings();
		configureDebugBindings(); //TODO remove before comp

		m_robotDrive.setDefaultCommand(		
			new RunCommand(
				() -> m_robotDrive.drive(
					-MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
					-MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
					-MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
					true, true),
				m_robotDrive));
	}

	private void configureDriverBindings() {
		m_driverController.rightTrigger() //CLIMB
				.whileTrue(new RunCommand(() -> m_climber.ascend(SpeedConstants.climberSpeed),m_climber))
				.whileFalse(new RunCommand(() -> m_climber.stop(),m_climber))
				.onTrue(new InstantCommand(() -> m_lights.ChangeState("climb"), m_lights))
				.onFalse(new InstantCommand(() -> m_lights.ChangeState("tele"), m_lights));
				
		m_driverController.leftTrigger() //DESCEND
				.whileTrue(new RunCommand(() -> m_climber.descend(SpeedConstants.climberSpeed),m_climber))
				.whileFalse(new RunCommand(() -> m_climber.stop(),m_climber))
				.onTrue(new InstantCommand(() -> m_lights.ChangeState("climb"), m_lights))
				.onFalse(new InstantCommand(() -> m_lights.ChangeState("tele"), m_lights));

		m_driverController.x() // BRAKE
				.whileTrue(new RunCommand(() -> m_robotDrive.setX(), m_robotDrive))
				.onTrue(new InstantCommand(() -> m_lights.ChangeState("break"), m_lights))
				.onFalse(new InstantCommand(() -> m_lights.ChangeState("tele"), m_lights));

		m_driverController.y() // REVERSE HEADING
			.onTrue(new DrivetrainReverseHeading(m_robotDrive,m_lights));					

		m_driverController.b() //AIM AND MOVE AT TARGET
			.whileTrue(new fullVision(m_robotDrive, m_vision));
	}

	private void configureOperatorBindings() {
		m_operatorController.leftTrigger() // START INTAKE
				.whileTrue(new StartIntake(m_intake, -1*SpeedConstants.IntakeSpeed))
				.whileTrue(new StartSupport(m_support, SpeedConstants.IntakeSpeed))
				.whileFalse(new StopIntake(m_intake))
				.whileFalse(new StopSupport(m_support));

		m_operatorController.leftBumper() // START OUTTAKE
				.whileTrue(new StartIntake(m_intake, SpeedConstants.IntakeSpeed))
				.whileTrue(new StartSupport(m_support, -1*SpeedConstants.IntakeSpeed))
				.whileFalse(new StopIntake(m_intake))
				.whileFalse(new StopSupport(m_support));

		m_operatorController.rightTrigger() // LOW SHOOT
				.whileTrue(new StartShooter(m_shooter, SpeedConstants.ShooterSpeedLow))
				.whileTrue(new StartSupport(m_support, SpeedConstants.ShooterSpeedLow))
				.whileFalse(new StopShooter(m_shooter))
				.whileFalse(new StopSupport(m_support));

		m_operatorController.rightStick().and(
			m_operatorController.rightTrigger()) // HIGH SHOOT
				.whileTrue(new StartShooter(m_shooter, SpeedConstants.ShooterSpeedHigh))
				.whileTrue(new StartIntake(m_intake, -.1*SpeedConstants.ShooterSpeedHigh))
				.whileTrue(new StartSupport(m_support, SpeedConstants.ShooterSpeedHigh))
				.whileFalse(new StopShooter(m_shooter))
				.whileFalse(new StopSupport(m_support))			
				.whileFalse(new StopIntake(m_intake));

		m_operatorController.rightBumper() // HUMAN INPUT
				.whileTrue(new StartShooter(m_shooter, SpeedConstants.HumanInputSpeed))
				.whileTrue(new StartSupport(m_support, SpeedConstants.HumanInputSpeed))
				.whileFalse(new StopShooter(m_shooter))
				.whileFalse(new StopSupport(m_support));
	}

	public void configureDebugBindings(){
		m_driverController.leftTrigger().and(
			m_driverController.pov(180)) //DOWN
				.whileTrue(new aim(m_robotDrive, m_vision));

		m_driverController.leftTrigger().and(
			m_driverController.pov(270)) //LEFT
				.whileTrue(new lateral(m_robotDrive, m_vision));

		m_driverController.leftTrigger().and(
			m_driverController.pov(90)) //RIGHT
				.whileTrue(new range(m_robotDrive, m_vision));

		m_driverController.leftTrigger().and(
			m_driverController.pov(0)) //UP
				.whileTrue(new fullVision(m_robotDrive, m_vision));

		m_driverController.rightTrigger().and(
			m_driverController.pov(0)) //UP
				.whileTrue(new loopVision(m_robotDrive, m_vision));

		m_driverController.a()
				.onTrue(new InstantCommand(() -> m_lights.ChangeState("i"), m_lights));

		m_driverController.b()
				.onTrue(new InstantCommand(() -> m_lights.ChangeState("-i"), m_lights));

		//m_driverController.rightStick().and(
			m_driverController.leftStick()//)
				.onTrue(new DrivetrainZeroHeading(m_robotDrive))
				.onTrue(new DrivetrainReverseHeading(m_robotDrive,m_lights));					
	}


	public Command getAutonomousCommand() {
		switch (getSelected()) {
			case AUTON_MIDDLE_HIGHSHOOT:
				return new middleHighShot(m_intake,m_robotDrive,m_shooter,m_support,this);
			case AUTON_S_SHAPE:
				return new MoveSShape(m_robotDrive, this, 3);
			default:
				return new middleHighShot(m_intake,m_robotDrive,m_shooter,m_support,this);
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

	public void reset(){
		m_climber.stop();
		m_intake.stopIntake();
		m_shooter.stopShooter();
		m_support.stopSupport();
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

	public Climber getClimber()
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
	public Photonvision getVision(){
		return m_vision;
	}
}
