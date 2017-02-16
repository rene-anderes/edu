package org.anderes.edu.upncalc;

import java.util.ResourceBundle;

import javax.inject.Inject;

import org.anderes.edu.upncalc.util.SystemInfoService;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class HelpDialog extends Dialog<ButtonType> {

    @Inject
    public HelpDialog(final ResourceBundle resources, final SystemInfoService systemInfoService) {
        super();
        this.getDialogPane().getButtonTypes().add(ButtonType.OK);
        final String contentText = String.format("%s%n%s %s%n%s %s%n", 
                        resources.getString("info.copyright"), 
                        resources.getString("info.implenetation.version"), systemInfoService.getImplementationVersion(),
                        resources.getString("info.jre"), systemInfoService.getJREVersion());
        setContentText(contentText);
    }

}
