package org.anderes.edu.upncalc;

import static javafx.scene.input.KeyCode.ADD;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.SHIFT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.util.ResourceBundle;

import org.anderes.edu.upncalc.util.Utf8Control;
import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class CalcGuiIT extends GuiTest {

    private final static Injector INJECTOR = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(Service.class).to(PrimeNumberService.class);
            bind(ResourceBundle.class).toInstance(ResourceBundle.getBundle("CalcLanguagePack", new Utf8Control()));
        }
    });
   
    private TextField inputField;
    private ListView<BigDecimal> stackView;

    @Before
    public void setup() {
        inputField = find("#inputField");
        stackView = find("#stackView");
        sleep(20);
    }
    
    @Test
    public void shouldBeCheckInput() {
        // given
        final BigDecimal expectedValue = BigDecimal.valueOf(29121967);
        
        // when
        type(expectedValue.toString()).type(ENTER);
        
        // then
        assertThat(inputField.getText(), is(""));
        assertThat(inputField.isFocused(), is(true));
        assertThat(stackView.getItems().size(), is(1));
        assertThat(stackView.getItems().get(0), is(not(nullValue())));
        assertThat(stackView.getItems().get(0), is(expectedValue));
    }

    @Test
    public void shouldBeCheckInputByButton() {
        // given
        final BigDecimal expectedValue = BigDecimal.valueOf(29121967);
        
        // when
        type(expectedValue.toString()).click("#enterButton");
        
        // then
        assertThat(inputField.getText(), is(""));
        assertThat(inputField.isFocused(), is(true));
        assertThat(stackView.getItems().size(), is(1));
        assertThat(stackView.getItems().get(0), is(not(nullValue())));
        assertThat(stackView.getItems().get(0), is(expectedValue));
 
    }
    
    @Test
    public void shouldBeCorrectInputByButton() {
        // when
        click("#nineButton").click("#eightButton").click("#sevenButton").click("#sixButton").click("#fiveButton")
            .click("#fourButton").click("#threeButton").click("#twoButton").click("#oneButton").click("#zeroButton").click("#pointButton").click("#sevenButton");
        
        // then
        assertThat(inputField.getText(), is("9876543210.7"));
    }
    
    @Test
    public void shouldBeStackMove() {
        
        // when
        type("20.45").type(ENTER).type("30.89").type(ENTER).click("#stButton");
        
        // then
        assertThat(stackView.getItems().size(), is(1));
        assertThat(inputField.getText(), is("30.89"));
        
        // when
        click("#ceButton");
        
        // then
        assertThat(stackView.getItems().size(), is(0));
        assertThat(inputField.getText(), is("30.89"));
    }
    
    @Test
    public void shouldBeCheckCE() {
        
        // when
        type("111").type(ENTER).type("222").type(ENTER).click("#ceButton").sleep(20);
        
        assertThat(stackView.getItems().size(), is(1));
        assertThat(inputField.getText(), is(""));
    }
    
    @Test
    public void shouldBeCheckEsc() {
        
        // when
        type("20.45").type(ESCAPE);
        
        // then
        assertThat(inputField.getText(), is(""));
        
    }
    
    @Test
    public void shouldBeCheckAddition_1() {
        // given
        final BigDecimal expectedValue = BigDecimal.valueOf(51.34);
        
        // when
        type("20.45").type(ENTER).type("30.89").type(ENTER).press(SHIFT).type(DIGIT1).release(SHIFT);
        
        // then
        assertThat(inputField.getText(), is(""));
        assertThat(stackView.getItems().size(), is(1));
        assertThat(stackView.getItems().get(0), is(expectedValue));
    }
    
    @Test
    public void shouldBeCheckAddition_2() {
        // given
        final BigDecimal expectedValue = BigDecimal.valueOf(51.34);
        
        // when
        type("20.45").type(ENTER).type("30.89").click("#additionButton");
        
        // then
        assertThat(inputField.getText(), is(""));
        assertThat(stackView.getItems().size(), is(1));
        assertThat(stackView.getItems().get(0), is(expectedValue));
    }
    
    @Test
    public void shouldBeCheckAddition_3() {
        // given
        final BigDecimal expectedValue = BigDecimal.valueOf(51.34);
        
        // when
        type("20.45").type(ENTER).type("30.89").type(ADD);
        
        // then
        assertThat(inputField.getText(), is(""));
        assertThat(stackView.getItems().size(), is(1));
        assertThat(stackView.getItems().get(0), is(expectedValue));
    }
    
    @Test
    public void shouldBeCheckAdditionWithSigned() {
        // given
        final BigDecimal expectedValue = BigDecimal.valueOf(-10.44);
        
        // when
        type("20.45").type(ENTER).type("30.89").click("#signedButton").type(ADD);
        
        // then
        assertThat(inputField.getText(), is(""));
        assertThat(stackView.getItems().size(), is(1));
        assertThat(stackView.getItems().get(0), is(expectedValue));
    }
    
    @Test
    public void shouldBeCheckSubtract() {
        // given
        final BigDecimal expectedValue = BigDecimal.valueOf(-10.44);
        
        // when
        type("20.45").type(ENTER).type("30.89").click("#subtractButton");
        
        // then
        assertThat(inputField.getText(), is(""));
        assertThat(stackView.getItems().size(), is(1));
        assertThat(stackView.getItems().get(0), is(expectedValue));
    }
    
    @Test
    public void shouldBeCheckMultiply() {
        // given
        final BigDecimal expectedValue = BigDecimal.valueOf(631.7005);
        
        // when
        type("20.45").type(ENTER).type("30.89").click("#multiplyButton");
        
        // then
        assertThat(inputField.getText(), is(""));
        assertThat(stackView.getItems().size(), is(1));
        assertThat(stackView.getItems().get(0), is(expectedValue));
    }
    
    @Test
    public void shouldBeCheckDivide() {
        // given
        final BigDecimal expectedValue = new BigDecimal("0.662026545808");
        
        // when
        type("20.45").type(ENTER).type("30.89").click("#divideButton");
        
        // then
        assertThat(inputField.getText(), is(""));
        assertThat(stackView.getItems().size(), is(1));
        assertThat(stackView.getItems().get(0), is(expectedValue));
    }
    
    @Test
    public void shouldBeCheckSigned() {
        // when
        click("#signedButton");
        
        // then
        assertThat(inputField.getText(), is(""));
        
        // when
        type("22.2").click("#signedButton");
        
        // then
        assertThat(inputField.getText(), is("-22.2"));
        
        // when
        click("#signedButton");
        
        // then
        assertThat(inputField.getText(), is("22.2"));
    }
    
    @Test
    public void shouldBeCheckCancelStack() {
        // when
        type("20.45").type(ENTER).type("30.89").type(ENTER).type("88").type(ENTER).click("#cButton");
        
        // then
        assertThat(inputField.getText(), is(""));
        assertThat(stackView.getItems().size(), is(0));
    }
    
    @Test
    public void shouldBeCheckNotAllowedCharcter_1() {
        // when
        type("abcdEfG");
        
        // then
        assertThat(inputField.getText(), is(""));
    }
    
    @Test
    public void shouldBeCheckNotAllowedCharcter_2() {
        // when
        type(".");
        
        // then
        assertThat(inputField.getText(), is(""));
    }
    
    @Test
    public void shouldBeCheckNotAllowedCharcter_3() {
        // when
        type("2.5634.");
        
        // then
        assertThat(inputField.getText(), is("2.5634"));
    }
    
    @Test
    public void shouldBeInverse() {
        
        // when
        type("5").click("#inverseButton");
        
        // then
        assertThat(inputField.getText(), is(""));
        assertThat(stackView.getItems().size(), is(1));
        assertThat(stackView.getItems().get(0), is(BigDecimal.valueOf(0.2)));
    }
    
    @Test
    public void shouldBeSquared() {
        
        // when
        type("5").click("#squaredButton");
        
        // then
        assertThat(inputField.getText(), is(""));
        assertThat(stackView.getItems().size(), is(1));
        assertThat(stackView.getItems().get(0), is(BigDecimal.valueOf(25)));
    }
    
    @Test
    public void shouldBeSquaredRoot() {
        
        // when
        type("25").click("#squaredRootButton");
        
        // then
        assertThat(inputField.getText(), is(""));
        assertThat(stackView.getItems().size(), is(1));
        assertThat(stackView.getItems().get(0), is(BigDecimal.valueOf(5)));
    }
    
    @Test
    public void shouldBeUndo() {
        
        // when
        type("20.45").type(ENTER).type("30.89").click("#divideButton").click("#undoButton");
        
        // then
        assertThat(inputField.getText(), is(""));
        assertThat(stackView.getItems().size(), is(2));
        assertThat(stackView.getItems().get(0), is(BigDecimal.valueOf(30.89)));
        assertThat(stackView.getItems().get(1), is(BigDecimal.valueOf(20.45)));
    }
    
    @Test
    public void shouldBePi() {
        
        // when
        click("#piButton");
        
        // then
        assertThat(inputField.getText(), is("3.141592653590"));
        assertThat(stackView.getItems().size(), is(0));
    }
    
    @Test
    public void shouldBeDoubleClickOnStack() {
        
        // when
        type("20").type(ENTER).type("30").type(ENTER).type("40").type(ENTER).doubleClick(offset(stackView, 20D, 40D));
        
        // then
        assertThat(inputField.getText(), is("30"));
        assertThat(stackView.getItems().size(), is(3));
    }
    
    @Override
    protected Parent getRootNode() {
        final CalcController calcController = INJECTOR.getInstance(CalcController.class);
        final ResourceBundle resourceBundle = INJECTOR.getInstance(ResourceBundle.class);

        final FXMLLoader myLoader = new FXMLLoader(getClass().getResource(CalcStarter.FXMLCALC), resourceBundle);
        myLoader.setController(calcController);
        
        try {
            return myLoader.load();
        } catch (IOException e) {
            throw new UncheckedIOException(e); 
        }
    }
}
