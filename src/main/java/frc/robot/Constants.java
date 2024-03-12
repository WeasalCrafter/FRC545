package frc.robot;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

public final class Constants {
	 /**
	PORTS AND ID'S

	CONTROLLERS
	  driver - 0
	  operator - 1

	FRONT LEFT (drivetrain 3-10)
	  drive: CAN- 5
	  turn: CAN- 6
	  encoder: ANALOG- 3

	FRONT RIGHT
	  drive: CAN- 10
	  turn: CAN- 9
	  encoder: ANALOG- 2

	BACK LEFT
	  drive: CAN- 7
	  turn: CAN- 8
	  encoder: ANALOG- 1

	BACK RIGHT
	  drive: CAN- 4
	  turn: CAN- 3
	  encoder: ANALOG- 0

	INTAKE
	  orange - 11
	  green - 12

	SHOOTER
	  motor1 - 13
	  motor2 - 14

	CLIMBER
	  left - 15
	  right - 16

	LED
	  module_pwm - 0
  */


	public static final class DrivetrainConstants {
		// Driving Parameters - Note that these are not the maximum capable speeds of
		// the robot, rather the allowed maximum speeds
		public static final double kMaxSpeedMetersPerSecond = 4.0; //4.42; //4.8;
		public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second

		public static final double kDirectionSlewRate = 1.2; // radians per second
		public static final double kMagnitudeSlewRate = 1.8; // 2.0; //1.8; // percent per second (1 = 100%)
		public static final double kRotationalSlewRate = 2.0; // 20.0; //2.0; // percent per second (1 = 100%)

		public static final double kTrackWidth = Units.inchesToMeters(30);
		
		// Distance between centers of right and left wheels on robot
		public static final double kWheelBase = Units.inchesToMeters(30);
		
		public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
				new Translation2d(kWheelBase / 2, kTrackWidth / 2),
				new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
				new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
				new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

		// Angular offsets of the modules relative to the chassis in radians
		public static final double kFrontLeftChassisAngularOffset = 0.0; // -Math.PI / 2;
		public static final double kFrontRightChassisAngularOffset = 0.0; // 0;
		public static final double kBackLeftChassisAngularOffset = 0.0; // Math.PI;
		public static final double kBackRightChassisAngularOffset = 0.0; // Math.PI / 2;

		// SPARK MAX CAN IDs
		public static final int kFrontLeftDrivingCanId = 6;
		public static final int kFrontRightDrivingCanId = 10;
		public static final int kRearLeftDrivingCanId = 7;
		public static final int kRearRightDrivingCanId = 3;

		public static final int kFrontLeftTurningCanId = 5;
		public static final int kFrontRightTurningCanId = 9;
		public static final int kRearLeftTurningCanId = 8;
		public static final int kRearRightTurningCanId = 4;

		 // SPARK MAX Absolute encoders
		public static final int kFrontLeftTurningAnalogPort = 3;
		public static final int kFrontRightTurningAnalogPort = 2;
		public static final int kRearLeftTurningAnalogPort = 1;
		public static final int kRearRightTurningAnalogPort = 0;

		public static final boolean kGyroReversed = false;
	}

	//-------------------------------------------------------------------------------------------------------------------------------------//
	//-------------------------------------------------------------------------------------------------------------------------------------//

	public static final class SpeedConstants {
		public static final double HumanInputSpeed = -0.1;
		public static final double HumanInputTime = 1;

		public static final double IntakeSpeed = 0.30;

		public static final double ShooterSpeedLow = 0.215;
		public static final double ShooterSpeedHigh = 1;

		public static final double climberSpeed = 0.2;	
	}

	//-------------------------------------------------------------------------------------------------------------------------------------//
	//-------------------------------------------------------------------------------------------------------------------------------------//

	public static final class VisionConstants {
		public static final double cameraHorizontalOffset = 0; //meters from robot center
		public static final double rotationErrorMargin = 5; //degrees
		
		public static final double TargetDistance = 1;
		public static final double goalDeadband = 0.25;

		public static final double ANGULAR_P = 0.1;
		public static final double ANGULAR_D = 0.0;

        public static final double LINEAR_P = 0.1;
        public static final double LINEAR_D = 0.0;

		public static final double rotationSpeed = 0.1;
		public static final double forwardSpeed = 5;
		public static final double lateralSpeed = 7.5;	
	}

	//-------------------------------------------------------------------------------------------------------------------------------------//
	//-------------------------------------------------------------------------------------------------------------------------------------//

	public static final class IntakeConstants {
		public static final int IntakeMotor1CanID = 15; 
		public static final int IntakeMotor2CanID = 16; 
	}

	//-------------------------------------------------------------------------------------------------------------------------------------//
	//-------------------------------------------------------------------------------------------------------------------------------------//

	public static final class ShooterConstants{
		public static final int ShooterMotor1CanID = 11;
		public static final int ShooterMotor2CanID = 12;

		public static final int SupportMotor1CanID = 13;
		public static final int SupportMotor2CanID = 14;
	}

	//-------------------------------------------------------------------------------------------------------------------------------------//
	//-------------------------------------------------------------------------------------------------------------------------------------//

	public static final class ClimberConstants{
		public static final int LeftActuatorCANID = 18;
		public static final int RightActuatorCANID = 17;
	}

	//-------------------------------------------------------------------------------------------------------------------------------------//
	//-------------------------------------------------------------------------------------------------------------------------------------//

	public static final class OIConstants {
		public static final int kDriverControllerPort = 0;
		public static final int kOperatorControllerPort = 1;
		
		public static final double kDriveDeadband = 0.1;

		public static final int BlinkinPWMPort = 0;

		public static final String cameraName = "Logitech,_Inc._Webcam_C270"; //"Microsoft_LifeCam_HD-3000"; 
	}

	//-------------------------------------------------------------------------------------------------------------------------------------//
	//-------------------------------------------------------------------------------------------------------------------------------------//

	public static final class SwerveModuleConstants {
		// The MAXSwerve module can be configured with one of three pinion gears: 12T, 13T, or 14T.
		// This changes the drive speed of the module (a pinion gear with more teeth will result in a
		// robot that drives faster).
		public static final int kDrivingMotorPinionTeeth = 14; // ALL GOOD

		// Invert the turning encoder, since the output shaft rotates in the opposite direction of
		// the steering motor in the MAXSwerve Module.
		public static final boolean kTurningEncoderInverted = true;

		// Calculations required for driving motor conversion factors and feed forward
		public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
		public static final double kWheelDiameterMeters = 0.1016; // ALL GOOD
		public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;

		// 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15 teeth on the bevel pinion
		public static final double kDrivingMotorReduction = (45.0 * 17 * 50) / (kDrivingMotorPinionTeeth * 15 * 27);
		public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters) / kDrivingMotorReduction;

		public static final double kDrivingEncoderPositionFactor = (kWheelDiameterMeters * Math.PI) / kDrivingMotorReduction; // meters
		public static final double kDrivingEncoderVelocityFactor = ((kWheelDiameterMeters * Math.PI) / kDrivingMotorReduction) / 60.0; // meters per second

		public static final double TURNING_MOTOR_REDUCTION = 150.0 / 7.0; // ratio between internal relative encoder and Through Bore (or Thrifty in our case) absolute encoder - 150.0 / 7.0

		public static final double kTurningEncoderPositionFactor = (2 * Math.PI) / TURNING_MOTOR_REDUCTION ; // radians - 
		public static final double kTurningEncoderVelocityFactor = (2 * Math.PI) / TURNING_MOTOR_REDUCTION / 60.0; // radians per second

		public static final double kTurningEncoderPositionPIDMinInput = 0; // radians
		public static final double kTurningEncoderPositionPIDMaxInput = (2 * Math.PI); // radians

		public static final double kDrivingP = 0.04;
		public static final double kDrivingI = 0;
		public static final double kDrivingD = 0;
		public static final double kDrivingFF = 1 / kDriveWheelFreeSpeedRps;
		public static final double kDrivingMinOutput = -1;
		public static final double kDrivingMaxOutput = 1;

		public static final double kTurningP = 1.0; //1.0; // 1.0 might be a bit too much - reduce a bit if needed
		public static final double kTurningI = 0;
		public static final double kTurningD = 0;
		public static final double kTurningFF = 0;
		public static final double kTurningMinOutput = -1;
		public static final double kTurningMaxOutput = 1;

		//public static final IdleMode kDrivingMotorIdleMode = IdleMode.kBrake;
		//public static final IdleMode kTurningMotorIdleMode = IdleMode.kBrake;

		public static final int kDrivingMotorCurrentLimit = 40; //50; // amps
		public static final int kTurningMotorCurrentLimit = 20; // amps
	}

	//-------------------------------------------------------------------------------------------------------------------------------------//
	//-------------------------------------------------------------------------------------------------------------------------------------//

	public static final class AutoConstants {


		public static final double kMaxSpeedMetersPerSecond = 3.0; //4.42; //3.0;
		public static final double kMaxAccelerationMetersPerSecondSquared = 3;
		public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
		public static final double kMaxAngularAccelerationRadiansPerSecondSquared = Math.PI;

		public static final double kPXController = 1;
		public static final double kPYController = 1;
		public static final double kPThetaController = 1;

		public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
				kMaxAngularSpeedRadiansPerSecond, kMaxAngularAccelerationRadiansPerSecondSquared);
	}

	public static final class NeoMotorConstants {
		public static final double kFreeSpeedRpm = 5676;
	}
}
