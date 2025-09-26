package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LanguageCodeConverter lc = new LanguageCodeConverter();
            CountryCodeConverter cc = new CountryCodeConverter();
            Translator translator = new JSONTranslator();

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String languageCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(lc.fromLanguageCode(languageCode));
            }
            languagePanel.add(languageComboBox);

            JLabel translationLabel = new JLabel("Translation:");
            translationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            JPanel countryPanel = new JPanel();
            countryPanel.setLayout(new GridLayout(0, 1));

            String[] items = new String[translator.getCountryCodes().size()];
            int i = 0;
            for(String countryCode : translator.getCountryCodes()) {
                items[i++] = cc.fromCountryCode(countryCode);
            }
            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(list);
            countryPanel.add(scrollPane, 0);

            languageComboBox.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {

                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String country = list.getSelectedValue();
                        if (country == null) return;
                        String lang = languageComboBox.getSelectedItem().toString();
                        String result = translator.translate(cc.fromCountry(country), lc.fromLanguage(lang));
                        translationLabel.setText("Translation: " + result);
                    }
                }
            });
            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) return;
                    String country = list.getSelectedValue();
                    if (country == null) return;
                    String lang = languageComboBox.getSelectedItem().toString();
                    String result = translator.translate(cc.fromCountry(country), lc.fromLanguage(lang));
                    translationLabel.setText("Translation: " + result);
                }
            });


            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(translationLabel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);


        });
    }
}
