package org.anderes.edu.di;


import static javafx.event.ActionEvent.ACTION;
import static javafx.scene.input.KeyCode.*;
import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.createBigDecimal;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javax.inject.Inject;

import org.reactfx.EventStream;
import org.reactfx.EventStreams;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.KeyEvent;

public class CalcController implements Initializable {

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
    private ListView<BigDecimal> lwStack;
    
    @Inject
    private Calc calc;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("CalcLanguagePack");
    private final static String ZERO = INTEGER_ZERO.toString();
    private ObservableList<BigDecimal> stack = FXCollections.observableArrayList();

    private final UnaryOperator<Change> textFormatterDigitFilter = change -> {
        final String text = change.getText();
        if (text.matches("[0-9.-]*")) {
            return change;
        }
        return null;
    };
    
    private final EventHandler<KeyEvent> numericHandler = event -> {
        final TextField textField = (TextField) event.getSource();
        final String character = event.getCharacter();
        if(notAllowedInputCharacter(character, textField)) {
            event.consume();
        }
    };
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        initUiControls();
               
        /* ReactFx siehe https://github.com/TomasMikula/ReactFX */
        /*                                                      */
        EventStreams.eventsOf(btnEnter, ACTION)
                .subscribe(event -> addNewValueIfNotEmpty(), ex -> showErrorMessage("error.wrong.input"));
        EventStreams.eventsOf(btnAddition, ACTION)
                .subscribe(event -> additionWithNewValue(), ex -> showErrorMessage("error.wrong.input"));
        EventStreams.eventsOf(btnSubtract, ACTION)
                .subscribe(event -> subtractWithNewValue(), ex -> showErrorMessage("error.wrong.input"));
        EventStreams.eventsOf(btnMultiply, ACTION)
                .subscribe(event -> multiplyWithNewValue(), ex -> showErrorMessage("error.wrong.input"));
        EventStreams.eventsOf(btnDivide, ACTION)
                .subscribe(event -> divideWithNewValue(), ex -> showErrorMessage("error.wrong.input"));
        
        EventStreams.eventsOf(btnCancel, ACTION).subscribe(event -> cancelStack());
        EventStreams.eventsOf(btnCE, ACTION).subscribe(event -> removeFromStack());
        EventStreams.eventsOf(btnSigned, ACTION).subscribe(event -> signedInput());
        EventStreams.eventsOf(btnStack, ACTION).subscribe(event -> formStackToInput());
        
        final EventStream<KeyEvent> inValueKeyEventStream = EventStreams.eventsOf(inValue, KeyEvent.KEY_RELEASED);
        inValueKeyEventStream.filter(event -> event.getCode() == ENTER).subscribe(e -> btnEnter.fire());
        inValueKeyEventStream.filter(event -> event.getCode() == ESCAPE).subscribe(e -> initValueForInputField());
        inValueKeyEventStream.filter(event -> event.getCode() == ADD).subscribe(event -> btnAddition.fire());
        inValueKeyEventStream.filter(event -> event.getCode() == SUBTRACT).subscribe(event -> btnSubtract.fire());
        inValueKeyEventStream.filter(event -> event.getCode() == MULTIPLY).subscribe(event -> btnMultiply.fire());
        inValueKeyEventStream.filter(event -> event.getCode() == DIVIDE).subscribe(event -> btnDivide.fire());
        inValueKeyEventStream.filter(event -> event.getCode() == DELETE).subscribe(event -> initValueForInputField());
        inValueKeyEventStream.filter(event -> event.getCode() == BACK_SPACE)
                .filter(event -> isEmpty(inValue.getText())).subscribe(event -> initValueForInputField());
        inValueKeyEventStream.filter(event -> event.getCode().isDigitKey())
                .filter(event -> startsWith(inValue.getText(), ZERO)).subscribe(event -> removeLeadingZero());
        
        Platform.runLater(() -> {
            initValueForInputField();
            EventStreams.eventsOf(inValue.getScene(), KeyEvent.KEY_RELEASED)
                .hook(event -> System.out.println(event.getCode()))
                .filter(event -> event.getCode().isDigitKey()).subscribe(event -> redirect(event));
            inValue.requestFocus();
        });
    }

    private void initUiControls() {
        final TextFormatter<String> textFormatter = new TextFormatter<>(textFormatterDigitFilter);
        inValue.setTextFormatter(textFormatter);
        inValue.setStyle("-fx-display-caret: false");
        inValue.addEventFilter(KeyEvent.KEY_TYPED, numericHandler);
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

    private boolean notAllowedInputCharacter(final String character, final TextField textField) {
        if (character.matches("[0-9.]")) {
            if (textField.getText().contains(".") && character.matches("[.]")) {
                return true;
            } else if (textField.getText().length() == 0 && character.matches("[.]")) {
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
            if (startsWith(inValue.getText(), "-")) {
                inValue.setText(removeStart(inValue.getText(), "-"));
            } else {
                inValue.setText("-" + inValue.getText());
            }
        }
    }

    private void removeFromStack() {
        calc.removeFromStack();
        stack.setAll(calc.getStack());
    }


    private void divideWithNewValue() {
        addNewValueIfNotEmpty();
        divide();
    }

    private void multiplyWithNewValue() {
        addNewValueIfNotEmpty();
        multiply();
    }

    private void additionWithNewValue() {
        addNewValueIfNotEmpty();
        addition();
    }
    
    private void subtractWithNewValue() {
        addNewValueIfNotEmpty();
        subtract();
    }
    
    private void subtract() {
        final Optional<BigDecimal> calcValue = calc.subtract();
        handleCalcValue(calcValue);
    }

    private void multiply() {
        final Optional<BigDecimal> calcValue = calc.multiply();
        handleCalcValue(calcValue);
    }

    private void divide() {
        try {
            final Optional<BigDecimal> calcValue = calc.divide();
            handleCalcValue(calcValue);
        } catch (ArithmeticException e) {
            stack.setAll(calc.getStack());
            throw new IllegalStateException();
        }
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
    
    private void addition() {
        final Optional<BigDecimal> calcValue = calc.addition();
        handleCalcValue(calcValue);
    }
    
    private void appendText(final String text) {
        if (notAllowedInputCharacter(text, inValue)) {
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

    private void showErrorMessage(String messageKey) {
        final String message = resourceBundle.getString(messageKey);
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
