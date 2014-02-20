package org.idea.plugin.GoogleIt;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import com.intellij.openapi.util.TextRange;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by #ROOT
 * Date: 20.02.14
 * Time: 20:35
 * Contact me: alistar.neron@gmail.com
 */
public class GoogleItSelected extends EditorAction {

    public GoogleItSelected(EditorActionHandler defaultHandler) {
        super(defaultHandler);
    }

    public GoogleItSelected() {
        this(new GHandler());
    }

    private static class GHandler extends EditorWriteActionHandler {
        private GHandler() {
        }

        @Override
        public void executeWriteAction(Editor editor, DataContext dataContext) {
            Document document = editor.getDocument();

            if (editor == null || document == null || !document.isWritable()) {
                return;
            }

            SelectionModel selectionModel = editor.getSelectionModel();

            // get the range of the selected characters
            TextRange charsRange = new TextRange(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd());

            // get the string to search
            String searchText = document.getText().substring(charsRange.getStartOffset(), charsRange.getEndOffset());

            if( searchText.length() < 1 ) return;
            try {
                String headerUrl = "http://google.com/search?q=";
                String encodedUrl = URLEncoder.encode(searchText.replaceAll("\n", ""),"UTF-8");

                GHandler.openURI(new URL(headerUrl+encodedUrl).toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public static void openURI(URI uri) {
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}