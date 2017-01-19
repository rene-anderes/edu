package org.anderes.edu.di;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.util.ResourceBundle;

import org.anderes.edu.di.guice.CalcGuiceModule;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class CalcGuiIT extends GuiTest {

    private final static Injector INJECTOR = Guice.createInjector(new CalcGuiceModule());
   
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
        type(expectedValue.toString()).type(KeyCode.ENTER);
        
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
    public void shouldBeStackMove() {
        
        // when
        type("20.45").type(KeyCode.ENTER).type("30.89").type(KeyCode.ENTER).click("#stButton");
        
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
        type("111").type(KeyCode.ENTER).type("222").type(KeyCode.ENTER).click("#ceButton").sleep(20);
        
        assertThat(stackView.getItems().size(), is(1));
        assertThat(inputField.getText(), is(""));
    }
    
    @Test
    public void shouldBeCheckEsc() {
        
        // when
        type("20.45").type(KeyCode.ESCAPE);
        
        // then
        assertThat(inputField.getText(), is(""));
        
    }
    
    @Test
    public void shouldBeCheckAddition() {
        // given
        final BigDecimal expectedValue = BigDecimal.valueOf(51.34);
        
        // when
        type("20.45").type(KeyCode.ENTER).type("30.89").type(KeyCode.ENTER)
                        .press(KeyCode.SHIFT).type(KeyCode.DIGIT1).release(KeyCode.SHIFT);
        
        // then
        assertThat(inputField.getText(), is(""));
        assertThat(stackView.getItems().size(), is(1));
        assertThat(stackView.getItems().get(0), is(expectedValue));
    }
    
    @Override
    protected Parent getRootNode() {
        final CalcController calcController = INJECTOR.getInstance(CalcController.class);
        final ResourceBundle resourceBundle = ResourceBundle.getBundle("CalcLanguagePack");

        final FXMLLoader myLoader = new FXMLLoader(getClass().getResource(CalcController.FXMLCALC), resourceBundle);
        myLoader.setController(calcController);
        
        try {
            return myLoader.load();
        } catch (IOException e) {
            throw new UncheckedIOException(e); 
        }
    }
}
