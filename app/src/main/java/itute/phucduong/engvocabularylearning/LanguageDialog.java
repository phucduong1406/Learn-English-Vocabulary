package itute.phucduong.engvocabularylearning;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.RadioButton;

public class LanguageDialog extends Dialog {

    RadioButton radioEng, radioVie;

    public LanguageDialog(@NonNull Context context) {
        super(context);
    }
}
