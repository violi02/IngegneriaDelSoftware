package it.unimi.di.sweng.esame;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import it.unimi.di.sweng.esame.views.DisplayView;
import it.unimi.di.sweng.esame.views.USRView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.assertj.core.util.introspection.FieldSupport;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class GUITest {


  private static final boolean HEADLESS = false;
  private final TextField[] insertTextMessage = new TextField[Main.NUMPOSTAZIONI];
  private final TextField[] acceptTextMessage = new TextField[Main.NUMPOSTAZIONI];
  private final Button[] insertButton = new Button[Main.NUMPOSTAZIONI];
  private final Button[] acceptButton = new Button[Main.NUMPOSTAZIONI];
  private final Label[] insertErrorMessage = new Label[Main.NUMPOSTAZIONI];
  private final Label[] acceptErrorMessage = new Label[Main.NUMPOSTAZIONI];
  private Label[] elencoSinistra;
  private Label[] elencoDestra;

  @BeforeAll
  public static void setupSpec() {
    if (HEADLESS) System.setProperty("testfx.headless", "true");
  }

  @Start
  public void start(Stage primaryStage) {
    Main m = new Main();
    m.start(primaryStage);

    GridPane gp = (GridPane) primaryStage.getScene().getRoot();
    ObservableList<Node> view = gp.getChildren();

    USRView[] postazioniView = new USRView[Main.NUMPOSTAZIONI];
    for (int i = 0; i < Main.NUMPOSTAZIONI; i++) {
      postazioniView[i] = (USRView) view.get(i);
      insertButton[i] = FieldSupport.EXTRACTION.fieldValue("button", Button.class, postazioniView[i]);
      insertTextMessage[i] = FieldSupport.EXTRACTION.fieldValue("textField", TextField.class, postazioniView[i]);
      insertErrorMessage[i] = FieldSupport.EXTRACTION.fieldValue("error", Label.class, postazioniView[i]);
    }

    for (int i = 0; i < Main.NUMPOSTAZIONI; i++) {
      postazioniView[i] = (USRView) view.get(i + 4);
      acceptButton[i] = FieldSupport.EXTRACTION.fieldValue("button", Button.class, postazioniView[i]);
      acceptTextMessage[i] = FieldSupport.EXTRACTION.fieldValue("textField", TextField.class, postazioniView[i]);
      acceptErrorMessage[i] = FieldSupport.EXTRACTION.fieldValue("error", Label.class, postazioniView[i]);
    }

    DisplayView leftView = (DisplayView) view.get(Main.NUMPOSTAZIONI);
    DisplayView rightView = (DisplayView) view.get(Main.NUMPOSTAZIONI + 1);

    elencoSinistra = FieldSupport.EXTRACTION.fieldValue("rows", Label[].class, leftView);
    elencoDestra = FieldSupport.EXTRACTION.fieldValue("rows", Label[].class, rightView);
  }

  void selezioneContenutoCasellaTesto(FxRobot robot, TextField field) {
    robot.doubleClickOn(field).clickOn(field); //triplo click per selezionare tutto
  }

  @Test
  public void situazioneInizialeVistaSinistra(FxRobot robot) {
    verifyThat(elencoSinistra[0], hasText("Bergamo : dal 03/09/2023 - BG202"));
    verifyThat(elencoSinistra[1], hasText("Bergamo : dal 04/09/2023 - BG201"));
    verifyThat(elencoSinistra[2], hasText("Milano : dal 03/09/2023 - MI205"));
    verifyThat(elencoSinistra[7], hasText(""));
  }


  @Test
  public void situazioneInizialeVistaDestra(FxRobot robot) {
    verifyThat(elencoDestra[0], hasText("1 : dal 03/09/2023 - BG202"));
    verifyThat(elencoDestra[1], hasText("2 : dal 03/09/2023 - MI205"));
    verifyThat(elencoDestra[2], hasText("2 : dal 05/09/2023 - MI205"));
    verifyThat(elencoDestra[7], hasText(""));
  }


  @ParameterizedTest
  @ValueSource(strings =
      {
          "PP02:1:1:1",
          "P002P:1:1:1",
          "00002:1:1:1"
      }
  )
  public void testSegnalaCodiceIstitutoNonValido(String input, FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, insertTextMessage[0]);
    robot.write(input);
    robot.clickOn(insertButton[0]);
    verifyThat(insertErrorMessage[0], hasText("Codice istituto non valido"));
  }

  @ParameterizedTest
  @ValueSource(strings =
      {
          "PP02:1:1",
          "0002:1:1",
          "P002Q:1:1"
      }
  )
  public void testAccettaConCodiceInsegnanteNonValido(String input, FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, acceptTextMessage[0]);
    robot.write(input);
    robot.clickOn(acceptButton[0]);
    verifyThat(acceptErrorMessage[0], hasText("Codice insegnante non valido"));
  }


  @Disabled
  @Test
  public void testAccettaSupplenzaNonPresente(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, acceptTextMessage[0]);
    robot.write("P22E:BG202:01/09/2023");
    robot.clickOn(acceptButton[0]);
    verifyThat(acceptErrorMessage[0], hasText("Richiesta non presente"));
  }


  @Test
  public void testDataNonValida(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, insertTextMessage[0]);
    robot.write("LC169:8:Lecco:11-09-2023");
    robot.clickOn(insertButton[0]);
    verifyThat(insertErrorMessage[0], hasText("Data inizio non corretta"));
  }


  @Test
  public void testInsertValido(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, insertTextMessage[0]);
    robot.write("LC169:8:Lecco:11/09/2023");
    robot.clickOn(insertButton[0]);
    verifyThat(insertErrorMessage[0], hasText(""));
    verifyThat(elencoSinistra[2], hasText("Lecco : dal 11/09/2023 - LC169"));
    verifyThat(elencoDestra[7], hasText("8 : dal 11/09/2023 - LC169"));
  }

  @Test
  public void testInsertValidoSX(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, insertTextMessage[0]);
    robot.write("LC169:8:Lecco:11/09/2023");
    robot.clickOn(insertButton[0]);
    verifyThat(insertErrorMessage[0], hasText(""));
    verifyThat(elencoSinistra[2], hasText("Lecco : dal 11/09/2023 - LC169"));
  }


  @Test
  public void testAccettaValido(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, acceptTextMessage[1]);
    robot.write("G16E:BG201:04/09/2023");
    robot.clickOn(acceptButton[1]);
    verifyThat(acceptErrorMessage[1], hasText(""));
    verifyThat(elencoSinistra[1], hasText("Milano : dal 03/09/2023 - MI205"));
    verifyThat(elencoSinistra[6], hasText(""));
    verifyThat(elencoDestra[5], hasText("5 : dal 03/09/2023 - PV004"));
  }
}
