package it.unimi.di.sweng.esame.view;


import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

import it.unimi.di.sweng.esame.Main;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.assertj.core.util.introspection.FieldSupport;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javax.imageio.ImageIO;
import java.io.File;


@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestIntegrazione {
  private static final boolean HEADLESS = false;

  private Stage stage;
  private OutputView bookDisplay;
  private Label errorMessage;
  private TextField input1;
  private TextField input2;
  private Button buyButton;

  private Label errorMessage1;
  private TextField bookCodeToBuy;
  private TextField email;
  private Button sellButton;

  @BeforeAll
  public static void setupSpec() {
    if (HEADLESS) System.setProperty("testfx.headless", "true");
  }

  @Start
  public void start(@NotNull Stage primaryStage) {

    Main m = new Main();
    m.start(primaryStage);

    stage = primaryStage;
    GridPane gp = (GridPane) primaryStage.getScene().getRoot();
    ObservableList<Node> view = gp.getChildren();



    InputConcreteView sellView = (InputConcreteView) view.get(0);
    bookDisplay = (DisplayView) view.get(1);
    InputConcreteView buyView = (InputConcreteView) view.get(2);

    errorMessage = FieldSupport.EXTRACTION.fieldValue("error", Label.class, sellView);
    input1 = FieldSupport.EXTRACTION.fieldValue("f1", TextField.class, sellView);
    input2 = FieldSupport.EXTRACTION.fieldValue("f2", TextField.class, sellView);
    sellButton = FieldSupport.EXTRACTION.fieldValue("button", Button.class, sellView);

    errorMessage1 = FieldSupport.EXTRACTION.fieldValue("error", Label.class, buyView);
    bookCodeToBuy = FieldSupport.EXTRACTION.fieldValue("f1", TextField.class, buyView);
    email = FieldSupport.EXTRACTION.fieldValue("f2", TextField.class, buyView);
    buyButton = FieldSupport.EXTRACTION.fieldValue("button", Button.class, buyView);
  }


  // TESTS


  @ParameterizedTest()
  @CsvSource(textBlock = """     
      'NV;A', 'A;B', Invalid book code
      """)
  void testInputSellError(String nameString, String excursionString, String errorString, @NotNull FxRobot robot) {
    sell(nameString, excursionString, robot);

    verifyThat(errorMessage, hasText(errorString));
  }


  @Test
  public void testDisplayStartOK() {
    assertThat(bookDisplay.get(0)).startsWith("H002").endsWith("DAMAGED");
  }


  @Test
  void testCorrectSellInputSequence(@NotNull FxRobot robot) {
    sell("F001;Prima fondazione", "10.00;ASNEW", robot);
    sell("F002;Fondazione e impero", "5.00;GOOD", robot);
    sell("F003;L'orlo della fondazione", "20;DAMAGED", robot);

    verifyThat(errorMessage, hasText(""));
    assertThat(bookDisplay.get(1)).startsWith("F002").endsWith("GOOD");
  }




  @Test
  void testCorrectSellAndBuyInputSequence(@NotNull FxRobot robot) {
    sell("F001;Prima fondazione", "10.00;ASNEW", robot);
    sell("F002;Fondazione e impero", "5.00;GOOD", robot);
    sell("F003;L'orlo della fondazione", "20;DAMAGED", robot);

    verifyThat(errorMessage, hasText(""));
    assertThat(bookDisplay.get(1)).startsWith("F002").endsWith("GOOD");

    buy("F001","violicata@gmail.com",robot);
    assertThat(bookDisplay.get(0)).startsWith("H002").endsWith("DAMAGED");

  }

  @Test
  void verifySuccessMessagge(@NotNull FxRobot robot) {
    sell("F001;Prima fondazione", "10.00;ASNEW", robot);
    sell("F002;Fondazione e impero", "5.00;GOOD", robot);
    sell("F003;L'orlo della fondazione", "20;DAMAGED", robot);

    verifyThat(errorMessage, hasText(""));
    assertThat(bookDisplay.get(1)).startsWith("F002").endsWith("GOOD");

    buy("F001","violicata@gmail.com",robot);
    verifyThat(errorMessage1, hasText("Receipt for the book F001 has been sent to violicata@gmail.com"));
  }

  @Test
  void verifyErrorEmail(@NotNull FxRobot robot) {
    buy("H001","violicatà@gmail.com",robot);
    verifyThat(errorMessage1, hasText("Formato email errato"));
  }

  @Test
  void verifyErrorBuyLibroNonInvendita(@NotNull FxRobot robot) {
    buy("F001","violicata@gmail.com",robot);
    verifyThat(errorMessage1, hasText("Libro non in vendita"));
  }

  @Test
  void testBuyTuttiILibri(@NotNull FxRobot robot) {
    buy("H001","violicata@gmail.com",robot);
    buy("H002","violicata@gmail.com",robot);
    assertThat(bookDisplay.get(0)).startsWith(" ").endsWith(" ");
  }

  @Test
  void testSellPiudi5Sell(@NotNull FxRobot robot) {
    sell("F001;Prima fondazione", "10.00;ASNEW", robot);
    sell("F002;Fondazione e impero", "5.00;GOOD", robot);
    sell("F003;L'orlo della fondazione", "20;DAMAGED", robot);
    sell("A002;Norwegian Wood", "3.00;ASNEW", robot);
    sell("A022;La vegetariana", "15.12;GOOD", robot);
    buy("F002","violalicata@gmail.com",robot);
    verifyThat(errorMessage, hasText(""));
    assertThat(bookDisplay.get(0)).startsWith("A002").endsWith("ASNEW");
  }


  @Test
  void testSellStessoCodice(@NotNull FxRobot robot) {
    sell("H001;Ciao", "12;ASNEW", robot);

    verifyThat(errorMessage, hasText("Stesso codice per libri diversi"));
  }
  // UTILITY FUNCTIONS

  private void sell(String f1, String f2, @NotNull FxRobot robot) {
    writeOnGui(robot, input1, f1);
    writeOnGui(robot, input2, f2);
    robot.clickOn(sellButton);
  }

  private void buy(String bookCode, String email, @NotNull FxRobot robot) {
    writeOnGui(robot, bookCodeToBuy, bookCode);
    writeOnGui(robot, this.email, email);
    robot.clickOn(buyButton);
  }

  private void writeOnGui(@NotNull FxRobot robot, TextField input, String text) {
    robot.doubleClickOn(input);
    robot.write(text, 0);
  }

}
