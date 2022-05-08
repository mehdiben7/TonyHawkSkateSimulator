package ca.thetonyhawks.tonyhawksimulator.controller;

import ca.thetonyhawks.tonyhawksimulator.model.AnimationModel;
import ca.thetonyhawks.tonyhawksimulator.model.Planet;
import ca.thetonyhawks.tonyhawksimulator.model.Skater;
import ca.thetonyhawks.tonyhawksimulator.model.SkaterAnimationTimer;
import ca.thetonyhawks.tonyhawksimulator.model.planes.AngledSkaterPlane;
import ca.thetonyhawks.tonyhawksimulator.model.planes.ParabolaSkaterPlane;
import ca.thetonyhawks.tonyhawksimulator.model.planes.SkaterPlane;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

/**
 * The Controller class linked to the main user interface window of the simulator <br>
 * whose FXML file is <em>UserInterface.fxml</em>
 */
public class TonyHawkSimulatorController {

    public static final ObjectProperty<Interpolator> BEZIER_INTERPOLATOR_PROPERTY = new SimpleObjectProperty<>(Interpolator.SPLINE(0.76, 0.05, 0.95, 0.69));
    public static final ObjectProperty<Interpolator> BEZIER_INTERPOLATOR_PROPERTY2 = new SimpleObjectProperty<>(Interpolator.SPLINE(0, 0, 1, 1));

    public static final String MATCH_DECIMAL_CHARACTERS_REGEX = "\\d*";
    public static final String REPLACE_NON_DECIMAL_CHARACTERS_REGEX = "[^\\d.]";

    public static final AngledSkaterPlane DEFAULT_ANGLED_SKATER_PLANE = new AngledSkaterPlane(SkaterPlane.DEFAULT_FRICTION_COEFFICIENT,
            AngledSkaterPlane.DEFAULT_PLANE_ANGLE);


    public final AnimationModel animationModel = new AnimationModel(new Planet(Planet.PLANETS_GRAVITATIONAL_CONSTANTS[0]),
            DEFAULT_ANGLED_SKATER_PLANE,
            new Skater(Skater.DEFAULT_SKATER_MASS),
            false, this);
    private final SkaterAnimationTimer animationTimer = new SkaterAnimationTimer(animationModel);

    private final BooleanProperty showForceVectorsProperty;

    @FXML
    private ImageView skater;

    @FXML
    private ImageView skater2;

    @FXML
    private BorderPane backgroundPane;

    @FXML
    private VBox rightPane, leftPane;

    @FXML
    private HBox bottomPane;

//

    /**
     * Menu item buttons that trigger the opening of complementary windows
     */
    @FXML
    private MenuItem about, database;

    /**
     * Start, pause, and reset buttons
     */
    @FXML
    private Button start, pause, reset;

    @FXML
    private RadioButton normalSpeedRadioButton, slowMotionRadioButton;

    @FXML
    private ToggleGroup speed;

    @FXML
    private Slider planeAngleSlider, planeDynamicCoefficientSlider;

    @FXML
    private Label planeAngleLabel, planeDynamicCoefficientLabel;

    @FXML
    private CheckBox showForceVectorsCheckBox;

    @FXML
    private Line angledPlaneLine;


    private CubicCurveTo planeParabola;
    private CubicCurve cc;
    private CubicCurve cc2;
    private MoveTo moveToStart;

    @FXML
    private HBox centerPanel;

    @FXML
    private TextField skaterMassField;

    @FXML
    public Label skaterPositionLabel, skaterSpeedLabel, skaterAccelerationLabel;

    @FXML
    public ComboBox<String> planetComboBox, planeTypesComboBox;

    @FXML
    public Pane midpane;


    @FXML
    private BarChart<String, Double> energyBarChart;



    private PathTransition pt;
    private PathTransition pt2;

    private final Path path = new Path();
    private Path path2;

    private final DoubleProperty upperEnergyBoundProperty;


    /**
     * Sets up the bar chart for displaying kinetic and potential gravitational energy
     */
    public void setUpEnergyChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Energy type");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Energy (in J)");
        energyBarChart.setAnimated(false);
        yAxis.setAnimated(false);
        yAxis.setForceZeroInRange(false);
    }


    /**
     * Updates the values of energy of the skater to the chart
     *
     * @param kineticEnergy                The skater's kinetic energy (in J)
     * @param potentialGravitationalEnergy The skater's potential gravitational energy (in J)
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void updateEnergyValues(double kineticEnergy, double potentialGravitationalEnergy) {
        energyBarChart.getData().removeAll(energyBarChart.getData());

        XYChart.Series kineticEnergySeries = new XYChart.Series();
        kineticEnergySeries.setName("Kinetic Energy");
        kineticEnergySeries.getData().add(new XYChart.Data("E_k", kineticEnergy));


        XYChart.Series potentialGravitationalEnergySeries = new XYChart.Series();
        potentialGravitationalEnergySeries.setName("Potential Energy");
        potentialGravitationalEnergySeries.getData().add(new XYChart.Data("U_g", potentialGravitationalEnergy));

        energyBarChart.getData().addAll(kineticEnergySeries, potentialGravitationalEnergySeries);
        this.upperEnergyBoundProperty.set(Math.max(kineticEnergy, potentialGravitationalEnergy));
    }

    /**
     * Updates the value of the acceleration of the angled plane
     */
    private void updateAngledPlaneValues() {
        animationModel.getSkater().accelerationProperty().set(animationModel.getModelAcceleration());

    }

    /**
     * Updates the skater's mass in the animation's model
     */
    private void updateSkaterMassValue() {
        animationModel.getSkater().skaterMassProperty().set(Double.parseDouble(skaterMassField.getText()));
    }

    /**
     * Updates the fall duration of the skater (in s)
     */
    private void updateFallDuration() {
        animationModel.animationDurationProperty().set(animationModel.getModelFallDuration());
        double animationNormalSpeed = animationModel.animationDurationProperty().get();
        double animationSlowMotionSpeed = animationModel.animationDurationProperty().get() * 2;
        pt = new PathTransition(Duration.seconds(normalSpeedRadioButton.isSelected() ? animationNormalSpeed : animationSlowMotionSpeed), path, skater);


    }

    /**
     * Toggles the change between slow motion and regular speed of animation
     */
    @FXML
    private void motionSpeedChangeEventHandler() {
        double duration = normalSpeedRadioButton.isSelected() ? animationModel.animationDurationProperty().get() * 0.5 :
                animationModel.animationDurationProperty().get();

        //pt.setDuration(Duration.seconds(duration));
        //pt2.setDuration(Duration.seconds(duration));
    }

    /**
     * Sets up the animation of the skater along the plane
     */
    void setUpAngledPlaneAnimation() {

        MoveTo moveTo = new MoveTo();
        moveTo.xProperty().bind(angledPlaneLine.startXProperty());
        moveTo.yProperty().bind((angledPlaneLine.startYProperty().subtract(28)));

        LineTo lineTo = new LineTo();
        lineTo.xProperty().bind(angledPlaneLine.endXProperty());
        lineTo.yProperty().bind((angledPlaneLine.endYProperty().subtract(28)));

        path.getElements().addAll(moveTo, lineTo);

        pt.setCycleCount(1);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.interpolatorProperty().bind(BEZIER_INTERPOLATOR_PROPERTY);
        pt.playFromStart();


    }


    /**
     * Sets up the animation for the parabola
     *
     * @author Changfan & David
     * @apiNote Method is not functional yet
     */

    void setUpParabolaPlaneAnimation() { // TODO Use this method

        initializeParabolaSkaterPlane();
        pt2 = new PathTransition();
        pt2.setDuration(Duration.seconds(1));
        pt2.setNode(skater2);
        pt2.setPath(path2);
        pt2.setCycleCount(2);
        pt2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt2.interpolatorProperty().bind(BEZIER_INTERPOLATOR_PROPERTY2);
        pt2.setAutoReverse(true);
        pt2.play();

    }

    void initializeParabolaSkaterPlane(){

        //width of the arc path
        double startX = 100;
        double startY = 500;
        double control1X = startX + 200;
        double control1Y = startY + 200;
        double control2X = control1X + 200;
        double control2Y = control1Y;
        double endX = control2X + 200;
        double endY = startY;

        //CubicCurve (Shape)
        cc = new CubicCurve (startX, startY, control1X, control1Y, control2X, control2Y, endX, endY);
        cc.setFill(Color.TRANSPARENT);
        cc.setStroke(Color.BLUE);
        cc.setStrokeWidth(10);

        int c = 125;
        cc2 = new CubicCurve (startX-100, startY+25, control1X-c+10, control1Y+c, control2X+c-10, control2Y+c, endX+100, endY+25);
        cc2.setFill(Color.TRANSPARENT);
        cc2.setStroke(Color.BLUE);
        cc2.setStrokeWidth(10);

        //Cubic Curve (Path Element)
        planeParabola = new CubicCurveTo();

        int y = 280;
        int x = 100;
        moveToStart = new MoveTo();
        moveToStart.xProperty().bind(cc.startXProperty().add(-x));
        moveToStart.yProperty().bind(cc.startYProperty().subtract(y));

        planeParabola.controlX1Property().bind(cc.controlX1Property().add(-x));
        planeParabola.controlY1Property().bind(cc.controlY1Property().subtract(y));
        planeParabola.controlX2Property().bind(cc.controlX2Property().add(-x));
        planeParabola.controlY2Property().bind(cc.controlY2Property().subtract(y));
        planeParabola.xProperty().bind(cc.endXProperty().add(-x));
        planeParabola.yProperty().bind(cc.endYProperty().subtract(y));

        path2 = new Path();
        path2.setStroke(Color.BLACK);
        path2.setStrokeWidth(5);
        path2.setStrokeType(StrokeType.INSIDE);

        path2.getElements().addAll(moveToStart, planeParabola);

    }
    /**
     * Either plays pt or pt2 according to the combobox chosen
     */
    private void  startPlay(){
        if (planeTypesComboBox.getValue().equalsIgnoreCase("Angled plane")){pt.play();}
        else if (planeTypesComboBox.getValue().equalsIgnoreCase("Parabola")){pt2.play();}
        else System.out.println("error1");
    }
    private void  stopPlay(){

            if (planeTypesComboBox.getValue().equalsIgnoreCase("Angled plane")) {pt.stop();}
            else if (planeTypesComboBox.getValue().equalsIgnoreCase("Parabola")) {pt2.stop();}
            else System.out.println("error2");
    }

    private void setUpAnimation(){
        if (planeTypesComboBox.getValue().equalsIgnoreCase("Angled plane")){setUpAngledPlaneAnimation();}
        else if (planeTypesComboBox.getValue().equalsIgnoreCase("Parabola")){setUpParabolaPlaneAnimation();}
        else System.out.println("error3");
    }

    private void playFromStart(){

        if (planeTypesComboBox.getValue().equalsIgnoreCase("Angled plane")) {pt.playFromStart();}
        else if (planeTypesComboBox.getValue().equalsIgnoreCase("Parabola")) {pt2.playFromStart();}
        System.out.println("error4");

    }

    /**
     * Triggers the animation start, putting the skater on top of the plane
     */
    @FXML
    private void startEventHandler() {

        if (animationModel.hasBeenStartedBeforeProperty().get()) {
            if (animationModel.isPausedProperty().get()) {
                System.out.println("Paused -> Restarted");
                startPlay();
                animationTimer.start();
            } else {
                System.out.println("Animation resumes!");
                updateSkaterMassValue();
                updateAngledPlaneValues();
                updateFallDuration();
                pt.setInterpolator(BEZIER_INTERPOLATOR_PROPERTY.get());
                animationTimer.start();
                startPlay();
            }
        } else {
            System.out.println("Animation started !");
            updateSkaterMassValue();
            updateAngledPlaneValues();
            updateFallDuration();
            setUpAnimation();
            animationTimer.start();
            animationModel.hasBeenStartedBeforeProperty().set(true);
        }
    }

    /**
     * Triggers the reset of the animation, putting the skater back on the top of the plane
     */
    @FXML
    private void resetEventHandler() {
        animationTimer.stop();
        if (pt != null) { // Prevents the animation from being reset if it has been started before
            animationModel.isPausedProperty().set(false);
            stopPlay();
            updateSkaterMassValue();
            updateAngledPlaneValues();
            updateFallDuration();
        }
    }

    /**
     * Triggers the pause of the animation, stopping the "fall" of the skater on the plane
     */
    @FXML
    private void pauseEventHandler() {
        System.out.println("Animation paused!");
            animationModel.isPausedProperty().set(true);
            animationTimer.pause();
            stopPlay();
            animationModel.hasBeenStartedBeforeProperty().set(true);
    }

    /**
     * Can be called by other classes to pause the animation of the skater and keep physical coherence
     */
    public void pauseAnimation() {
        stopPlay();
    }

    /**
     * Triggers the opening of the <em>import</em> database window
     */
    @FXML
    private void showImportDatabaseWindow() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ca.thetonyhawks.tonyhawksimulator/ImportDatabase.fxml"));
        Parent databaseContent = null;

        try {
            databaseContent = loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Stage importDatabaseStage = new Stage();
        Scene databaseScene = new Scene(databaseContent, 400, 600);
        importDatabaseStage.setTitle("Import planets' acceleration values");
        importDatabaseStage.setScene(databaseScene);
        importDatabaseStage.setResizable(false);
        importDatabaseStage.show();
    }

    /**
     * Triggers the opening of the <em>about</em> window
     */
    @FXML
    private void showAboutWindow() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ca.thetonyhawks.tonyhawksimulator/About.fxml"));
        Parent aboutContent = null;
        try {
            aboutContent = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Stage aboutStage = new Stage();
        Scene scene = new Scene(aboutContent);

        aboutStage.setTitle("About this project");
        aboutStage.setScene(scene);
        aboutStage.show();

    }

    @FXML
    public void onSkaterMassEntered() {
        animationModel.getSkater().skaterMassProperty().set(Double.parseDouble(skaterMassField.getText()));
        updateAngledPlaneValues();

    }


    public TonyHawkSimulatorController() {
        this.showForceVectorsProperty = new SimpleBooleanProperty(false);
        this.upperEnergyBoundProperty = new SimpleDoubleProperty(2_500.0);
    }

    public void initialize() {
        setUpEnergyChart();
        midpane.getChildren().remove(skater2);

        // Temporary - Disabling non-functional parts of the UI for the beta
        showForceVectorsCheckBox.setDisable(true);

        midpane.rotateProperty().bind(planeAngleSlider.valueProperty().subtract(45));
        planetComboBox.setItems(animationModel.getPlanet().getPlanetNameObservableList());
        planetComboBox.getSelectionModel().selectFirst();
        planetComboBox.valueProperty().addListener((observableValue, s, t1) -> {
            int selectedItemIndex = planetComboBox.getSelectionModel().getSelectedIndex();
            double gravitationalConstant = Planet.PLANETS_GRAVITATIONAL_CONSTANTS[selectedItemIndex];
            animationModel.getPlanet().getGravitationalAccelerationProperty().set(gravitationalConstant);

        });

        planeTypesComboBox.setItems(animationModel.planeTypesProperty());
        planeTypesComboBox.getSelectionModel().selectFirst();
        planeTypesComboBox.valueProperty().addListener((observableValue, s, t1) -> {
            if (t1.equalsIgnoreCase("Angled plane")) {
                System.out.println("An angled plane");
                midpane.getChildren().addAll(angledPlaneLine, skater);
                midpane.getChildren().removeAll(skater2, cc2);
                animationModel.setPlane(DEFAULT_ANGLED_SKATER_PLANE);
                energyBarChart.setVisible(true);

            } else if (t1.equalsIgnoreCase("Parabola")) {

                initializeParabolaSkaterPlane();
                System.out.println("A parabola");
                setUpParabolaPlaneAnimation();
                pt2.pause();
                midpane.getChildren().removeAll(angledPlaneLine, skater);
                midpane.getChildren().addAll(skater2, cc2);
                energyBarChart.setVisible(false);
            }

        });

        skaterMassField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches(MATCH_DECIMAL_CHARACTERS_REGEX)) {
                skaterMassField.setText(t1.replaceAll(REPLACE_NON_DECIMAL_CHARACTERS_REGEX, ""));

            }
        });


        skaterMassField.setText(String.valueOf(animationModel.getSkater().skaterMassProperty().get()));
        skaterMassField.disableProperty().bind(animationModel.isPausedProperty().not());

        planeAngleSlider.valueProperty().bindBidirectional(animationModel.getPlane().planeCoefficientProperty());
        planeAngleSlider.disableProperty().bind(animationModel.isPausedProperty().not());

        planeDynamicCoefficientSlider.valueProperty().bindBidirectional(animationModel.getPlane().kineticFrictionCoefficientProperty());

        slowMotionRadioButton.selectedProperty().bindBidirectional(animationModel.isInSlowMotionProperty());

        showForceVectorsCheckBox.selectedProperty().bindBidirectional(this.showForceVectorsProperty);

        skater.layoutXProperty().bind(angledPlaneLine.startXProperty().add(55));
        skater.layoutYProperty().bind(angledPlaneLine.startYProperty().subtract(88));

        animationModel.getPlane().planeCoefficientProperty().addListener((observableValue, number, t1) -> {
            double newAngle = (double) t1;
            String formattedNewAngleString = AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(newAngle);
            planeAngleLabel.setText(formattedNewAngleString + " deg");
        });
        animationModel.getPlane().kineticFrictionCoefficientProperty().addListener((observableValue, number, t1) -> {
            double newDynamicCoefficient = (double) t1;
            String formattedNewDynamicCoefficient = AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(newDynamicCoefficient);
            planeDynamicCoefficientLabel.setText("μ_k = " + formattedNewDynamicCoefficient);

        });

        animationModel.getSkater().positionProperty().addListener((observableValue, number, t1) -> {
            double newPosition = (double) t1;
            String formattedNewPosition = AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(newPosition);
            skaterPositionLabel.setText("x = " + formattedNewPosition + " m");
        });
        animationModel.getSkater().velocityProperty().addListener((observableValue, number, t1) -> {
            double newSpeed = (double) t1;
            String formattedNewSpeed = AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(newSpeed);
            skaterSpeedLabel.setText("v = " + formattedNewSpeed + " m/s");
        });
        animationModel.getSkater().accelerationProperty().addListener((observableValue, number, t1) -> {
            double newAcceleration = (double) t1;
            String formattedNewAcceleration = AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(newAcceleration);
            System.out.println(formattedNewAcceleration);
            skaterAccelerationLabel.setText("a = " + formattedNewAcceleration + " m/s^2");
        });
    }

    /**
     *  Triggered when the user changes the planet
     */
    public void changePlanet() {
        System.out.println("Changed planet");
        if (Objects.equals(planetComboBox.getValue(), "Moon")) {

            backgroundPane.getStylesheets().clear();
            backgroundPane.getStylesheets().add(getClass().getResource("/CSS_stylesheets/Moon.css").toExternalForm());

        } else if (Objects.equals(planetComboBox.getValue(), "Earth")) {

            backgroundPane.getStylesheets().clear();
            backgroundPane.getStylesheets().add(getClass().getResource("/CSS_stylesheets/Main.css").toExternalForm());

        } else if (Objects.equals(planetComboBox.getValue(), "Mars")) {

            backgroundPane.getStylesheets().clear();
            backgroundPane.getStylesheets().add(getClass().getResource("/CSS_stylesheets/Mars.css").toExternalForm());
        }
    }

}