package org.anderes.edu.di;


import static javafx.event.ActionEvent.ACTION;
import static javafx.scene.input.KeyCode.ADD;
import static javafx.scene.input.KeyCode.BACK_SPACE;
import static javafx.scene.input.KeyCode.DELETE;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT3;
import static javafx.scene.input.KeyCode.DIGIT7;
import static javafx.scene.input.KeyCode.DIVIDE;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.MINUS;
import static javafx.scene.input.KeyCode.MULTIPLY;
import static javafx.scene.input.KeyCode.SHIFT;
import static javafx.scene.input.KeyCode.SUBTRACT;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;
import static javafx.scene.input.KeyEvent.KEY_RELEASED;
import static javafx.scene.input.KeyEvent.KEY_TYPED;
import static javafx.scene.input.MouseEvent.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.createBigDecimal;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reactfx.BiEventStream;
import org.reactfx.EventStream;
import org.reactfx.EventStreams;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CalcController implements Initializable {

    public final static String FXMLCALC = "/fxml/JavaFX-Calc.fxml";
    @FXML
    private Button btnZero;
    @FXML
    private Button btnOne;
    @FXML
    private Button btnTwo;
    @FXML
    private Button btnThree;
    @FXML
    private Button btnFour;
    @FXML
    private Button btnFive;
    @FXML
    private Button btnSix;
    @FXML
    private Button btnSeven;
    @FXML
    private Button btnEight;
    @FXML
    private Button btnNine;
    @FXML
    private Button btnPoint;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnCE;
    @FXML
    private Button btnStack;
    @FXML
    private Button btnAddition;
    @FXML
    private Button btnSubtract;
    @FXML
    private Button btnMultiply;
    @FXML
    private Button btnDivide;
    @FXML
    private Button btnSigned;
    @FXML
    private TextField inValue;
    @FXML
    private Button btnEnter;
    @FXML
    private Button btnUndo;
    @FXML
    private Button btnInverse;
    @FXML
    private ListView<BigDecimal> lwStack;
    
    @Inject
    private Calc calc;
    private final static String ZERO = INTEGER_ZERO.toString();
    private ObservableList<BigDecimal> stack = FXCollections.observableArrayList();
    private Logger logger = LogManager.getLogger(this.getClass().getName());

    private final UnaryOperator<Change> textFormatterDigitFilter = change -> {
        final String text = change.getText();
        if (text.matches("[0-9.-]*")) {
            return change;
        }
        return null;
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("CalcController-Instanz: " + this.toString());
        initUiControls();
               
        /* ReactFx siehe https://github.com/TomasMikula/ReactFX */
        /*                                                      */
        EventStreams.eventsOf(btnEnter, ACTION)
                .subscribe(event -> addNewValueIfNotEmpty(), exception -> handleError(exception, resources));
        EventStreams.eventsOf(btnAddition, ACTION)
                .subscribe(event -> addition(), exception -> handleError(exception, resources));
        EventStreams.eventsOf(btnSubtract, ACTION)
                .subscribe(event -> subtract(), exception -> handleError(exception, resources));
        EventStreams.eventsOf(btnMultiply, ACTION)
                .subscribe(event -> multiply(), exception -> handleError(exception, resources));
        EventStreams.eventsOf(btnDivide, ACTION)
                .subscribe(event -> divide(), exception -> handleError(exception, resources));
        
        EventStreams.eventsOf(btnCancel, ACTION).subscribe(event -> cancelStack());
        EventStreams.eventsOf(btnCE, ACTION).subscribe(event -> removeFromStack());
        EventStreams.eventsOf(btnSigned, ACTION).subscribe(event -> signedInput());
        EventStreams.eventsOf(btnStack, ACTION).subscribe(event -> formStackToInput());
        EventStreams.eventsOf(btnUndo, ACTION).subscribe(event -> undoLastFunction());
        EventStreams.eventsOf(btnInverse, ACTION).subscribe(event -> inverse());
        
        EventStream<KeyEvent> keyPressed = EventStreams.eventsOf(inValue, KEY_PRESSED);
        EventStream<KeyEvent> keyReleased = EventStreams.eventsOf(inValue, KEY_RELEASED);
        EventStream<KeyEvent> keyTyped = EventStreams.eventsOf(inValue, KEY_TYPED);
        EventStream<KeyEvent> keyPressedOrReleased = EventStreams.merge(keyPressed, keyReleased);
        EventStream<Boolean> shiftPresses = keyPressedOrReleased.filter(key -> key.getCode().equals(SHIFT)).map(key -> key.isShiftDown());
        
        keyTyped.filter(key -> notAllowedInputCharacter(key.getCharacter())).subscribe(key -> key.consume());
        
        final EventStream<KeyCode> keyCode = keyReleased.map(key -> key.getCode());
        keyCode.filter(key -> key.equals(ENTER)).subscribe(key -> btnEnter.fire());
        keyCode.filter(key -> key.equals(ESCAPE)).subscribe(key -> initValueForInputField());
        keyCode.filter(key -> key.equals(ADD)).subscribe(key -> btnAddition.fire());
        keyCode.filter(key -> key.equals(SUBTRACT)).subscribe(key -> btnSubtract.fire());
        keyCode.filter(key -> key.equals(MINUS)).subscribe(key -> btnSubtract.fire());
        keyCode.filter(key -> key.equals( MULTIPLY)).subscribe(key -> btnMultiply.fire());
        keyCode.filter(key -> key.equals(DIVIDE)).subscribe(key -> btnDivide.fire());
        keyCode.filter(key -> key.equals(DELETE)).subscribe(key -> initValueForInputField());
        keyCode.filter(key -> key.equals(BACK_SPACE)).filter(key -> isEmpty(inValue.getText())).subscribe(key -> initValueForInputField());
        keyCode.filter(key -> key.isDigitKey()).filter(key -> startsWith(inValue.getText(), ZERO)).subscribe(key -> removeLeadingZero());
        
        final BiEventStream<KeyCode, Boolean> combo = EventStreams.combine(keyCode, shiftPresses);
        combo.subscribe((key, shift) -> {
            if (shift && key.equals(DIGIT1)) {
                btnAddition.fire();
            }
            if (shift && key.equals(DIGIT3)) {
                btnMultiply.fire();
            }
            if (shift && key.equals(DIGIT7)) {
                btnDivide.fire();
            }
        });
        
        Platform.runLater(() -> {
            final EventStream<KeyEvent> sceneKeyReleased = EventStreams.eventsOf(inValue.getScene(), KEY_RELEASED)
                            .hook(event -> logger.trace("Taste: " + event.getCode()))
                            .filter(event -> event.getCode().isDigitKey());
            sceneKeyReleased.subscribe(event -> redirect(event));
        });
        EventStreams.eventsOf(lwStack, MOUSE_CLICKED)
            .filter(event -> event.getClickCount() == 2)
            .map(event -> lwStack.getSelectionModel().getSelectedItem())
            .filter(n -> n != null)
            .subscribe(n -> inValue.setText(n.toString()));
    }

    private void initUiControls() {
        final TextFormatter<String> textFormatter = new TextFormatter<>(textFormatterDigitFilter);
        inValue.setTextFormatter(textFormatter);
        lwStack.setItems(stack);
        lwStack.setFocusTraversable(false);
        
        btnOne.setOnAction(e -> appendText("1"));
        btnOne.setFocusTraversable(false);
        btnTwo.setOnAction(e -> appendText("2"));
        btnTwo.setFocusTraversable(false);
        btnThree.setOnAction(e -> appendText("3"));
        btnThree.setFocusTraversable(false);
        btnFour.setOnAction(e -> appendText("4"));
        btnFour.setFocusTraversable(false);
        btnFive.setOnAction(e -> appendText("5"));
        btnFive.setFocusTraversable(false);
        btnSix.setOnAction(e -> appendText("6"));
        btnSix.setFocusTraversable(false);
        btnSeven.setOnAction(e -> appendText("7"));
        btnSeven.setFocusTraversable(false);
        btnEight.setOnAction(e -> appendText("8"));
        btnEight.setFocusTraversable(false);
        btnNine.setOnAction(e -> appendText("9"));
        btnNine.setFocusTraversable(false);
        btnZero.setOnAction(e -> appendText("0"));
        btnZero.setFocusTraversable(false);
        btnPoint.setOnAction(e -> appendText("."));
        btnPoint.setFocusTraversable(false);
        btnEnter.setFocusTraversable(false);
        btnAddition.setFocusTraversable(false);
        btnSubtract.setFocusTraversable(false);
        btnMultiply.setFocusTraversable(false);
        btnDivide.setFocusTraversable(false);
        btnCancel.setFocusTraversable(false);
        btnCE.setFocusTraversable(false);
        btnSigned.setFocusTraversable(false);
        btnStack.setFocusTraversable(false);
    }

    private boolean notAllowedInputCharacter(final String character) {
        final String text = inValue.getText();
        if (character.matches("[0-9.]")) {
            if (text.contains(".") && character.matches("[.]")) {
                return true;
            } else if (text.length() == 0 && character.matches("[.]")) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    private void formStackToInput() {
        calc.removeFromStack().ifPresent(d -> inValue.setText(d.toString()));
        stack.setAll(calc.getStack());
        inValue.requestFocus();
        inValue.end();
    }

    private void signedInput() {
        if (isNotEmpty(inValue.getText())) {
            final BigDecimal temp = new BigDecimal(inValue.getText());
            inValue.setText(temp.negate().toString());
        }
    }

    private void undoLastFunction() {
        calc.undo();
        stack.setAll(calc.getStack());
    }

    private void inverse() {
        addNewValueIfNotEmpty();
        final Optional<BigDecimal> calcValue = calc.inverse();
        handleCalcValue(calcValue);
    }
    
    private void removeFromStack() {
        calc.removeFromStack();
        stack.setAll(calc.getStack());
    }

    private void divide() {
        addNewValueIfNotEmpty();
        try {
            final Optional<BigDecimal> calcValue = calc.divide();
            handleCalcValue(calcValue);
        } catch (ArithmeticException e) {
            stack.setAll(calc.getStack());
            throw new IllegalStateException(e);
        }
    }

    private void multiply() {
        addNewValueIfNotEmpty();
        final Optional<BigDecimal> calcValue = calc.multiply();
        handleCalcValue(calcValue);
    }

    private void addition() {
        addNewValueIfNotEmpty();
        final Optional<BigDecimal> calcValue = calc.addition();
        handleCalcValue(calcValue);
    }
    
    private void subtract() {
        addNewValueIfNotEmpty();
        final Optional<BigDecimal> calcValue = calc.subtract();
        handleCalcValue(calcValue);
    }

    private void handleCalcValue(final Optional<BigDecimal> calcValue) {
        stack.setAll(calc.getStack());
        if (calcValue.isPresent()) {
            lwStack.scrollTo(calcValue.get());
        } else {
            throw new IllegalStateException();
        }
    }

    private void addNewValueIfNotEmpty() {
        if (isEmpty(inValue.getText())) {
            return;
        }
        final BigDecimal newValue = createBigDecimal(inValue.getText());
        newValueToStack(newValue);
    }
    
    private void appendText(final String text) {
        if (notAllowedInputCharacter(text)) {
            return;
        }
        inValue.appendText(text);
        if (inValue.getText().startsWith(ZERO)) {
            removeLeadingZero();
        }
    }

    private void cancelStack() {
        while(calc.removeFromStack().isPresent()){};
        stack.clear();
    }

    private void handleError(final Throwable e, final ResourceBundle resources) {
        logger.warn(e.getMessage());
        final String message = resources.getString("error.wrong.input");
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    private void redirect(KeyEvent event) {
        if (!inValue.isFocused()) {
            inValue.requestFocus();
            appendText(event.getText());
        }
    }
    
    private void initValueForInputField() {
        inValue.clear();
        inValue.setText(EMPTY);
        inValue.end();
    }
    
    private void removeLeadingZero() {
        if (inValue.getText().matches("[0][.]?[0-9]*")) {
            return;
        }
        inValue.setText(removeStart(inValue.getText(), ZERO));
        inValue.end();
    }
    
    private void newValueToStack(final BigDecimal newValue) {
        initValueForInputField();
        calc.addToStack(newValue);
        stack.setAll(calc.getStack());
        lwStack.scrollTo(newValue);
    }

 }
