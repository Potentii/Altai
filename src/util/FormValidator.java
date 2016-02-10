package util;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Guilherme Reginaldo
 * @since 05/02/2016
 */
public class FormValidator {
    private Map<FieldInputErrorOutputPair, EnumSet<EValidation>> fieldMap;
    private Map<Validatable, FieldInputErrorOutputPair> complexFieldSet;

    public enum EValidation{
        REQUIRED,
        INTEGER,
        FLOAT
    }


    public FormValidator(){
        fieldMap = new HashMap<>();
        complexFieldSet = new HashMap<>();
    }



    /**
     * Maps an {@link TextField} with its proper validation set.
     * @param field The {@code EditText} to be added
     * @param validation Its validation set
     * @return The same {@code FormValidator} object
     */
    public FormValidator addField(@NotNull TextField field, @Nullable Label errorOut, @NotNull EnumSet<EValidation> validation){
        if(errorOut!=null) {
            errorOut.setDisable(true);
        }
        fieldMap.put(new FieldInputErrorOutputPair(field, errorOut), validation);
        return this;
    }

    /**
     * Removes an {@link TextField} from the map.
     * @param field The {@code EditText} to be removed
     * @return The same {@code FormValidator} object
     */
    public FormValidator removeField(@NotNull TextField field){
        for (FieldInputErrorOutputPair key : fieldMap.keySet()) {
            if(key.getFieldIn().equals(field)){
                fieldMap.remove(key);
                break;
            }
        }
        return this;
    }



    /**
     * Adds a {@link Validatable} on the set.
     * @param field The {@code Validatable} to be added
     * @return The same {@code FormValidator} object
     */
    public FormValidator addComplexField(@NotNull Validatable validatable, @Nullable TextField field, @Nullable Label errorOut){
        if(errorOut!=null) {
            errorOut.setDisable(true);
        }
        complexFieldSet.put(validatable, new FieldInputErrorOutputPair(field, errorOut));
        return this;
    }

    /**
     * Removes a {@link Validatable} from the set.
     * @param validatable The {@code Validatable} to be removed
     * @return The same {@code FormValidator} object
     */
    public FormValidator removeComplexField(Validatable validatable){
        complexFieldSet.remove(validatable);
        return this;
    }


    /**
     * Verifies if all the registered fields are valid.
     * @return {@code True} if all fields are valid, {@code True} otherwise
     */
    public boolean isValid(){
        for (FieldInputErrorOutputPair key : fieldMap.keySet()) {
            EnumSet<EValidation> validation = fieldMap.get(key);
            TextField field = key.getFieldIn();
            if(!validation_eachField(field, validation)){
                return false;
            }
        }

        for (Validatable key : complexFieldSet.keySet()) {
            if(!key.isValid()){
                return false;
            }
        }

        return true;
    }




    public FormValidator doVisualValidation(){
        for (FieldInputErrorOutputPair key : fieldMap.keySet()) {
            visualValidation_eachField(key.getFieldIn(), fieldMap.get(key), key.getErrorOut());
        }

        for (Validatable key : complexFieldSet.keySet()) {
            FieldInputErrorOutputPair value = complexFieldSet.get(key);
            visualValidation_eachComplexField(key, value.getFieldIn(), value.getErrorOut());
        }

        return this;
    }




    private boolean validation_eachField(@NotNull TextField field, @NotNull EnumSet<EValidation> validation){
        if(validation.contains(EValidation.REQUIRED)){
            if(!containsAnything(field)) {
                return false;
            }
        }

        if(validation.contains(EValidation.INTEGER)){
            if(!containsInteger(field)){
                return false;
            }
        }

        if(validation.contains(EValidation.FLOAT)){
            if(!containsFloat(field)){
                return false;
            }
        }

        return true;
    }

    @NotNull
    private String validation_eachField_toString(@NotNull TextField field, @NotNull EnumSet<EValidation> validation){
        if(validation.contains(EValidation.REQUIRED)){
            if(!containsAnything(field)) {
                return "It should not be left empty";
            }
        }

        if(validation.contains(EValidation.INTEGER)){
            if(!containsInteger(field)){
                return "It should contain numbers";
            }
        }

        if(validation.contains(EValidation.FLOAT)){
            if(!containsFloat(field)){
                return "It should contain decimal numbers";
            }
        }

        return "";
    }



    private boolean containsAnything(@NotNull TextField field){
        return field.getText().trim().length() != 0;
    }

    private boolean containsInteger(@NotNull TextField field){
        try{
            Integer.parseInt(field.getText().trim());
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    private boolean containsFloat(@NotNull TextField field){
        try{
            Float.parseFloat(field.getText().trim());
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }







    private void visualValidation_eachField(@NotNull TextField field, @NotNull EnumSet<EValidation> validation, @Nullable Label errorOut){
        String errorText = validation_eachField_toString(field, validation);
        boolean valid = errorText.length() == 0;

        if(errorOut != null){
            if(valid){
                errorOut.setDisable(true);
                errorOut.setText("");
            } else {
                errorOut.setDisable(false);
                errorOut.setText(errorText);
            }
        }
    }

    private void visualValidation_eachComplexField(@NotNull Validatable validatable, @Nullable TextField field, @Nullable  Label errorOut){
        boolean valid = validatable.isValid();

        if(errorOut != null){
            if(valid){
                errorOut.setDisable(true);
                errorOut.setText("");
            } else {
                errorOut.setDisable(false);
                errorOut.setText(validatable.getInvalidText());
            }
        }
    }







    public class FieldInputErrorOutputPair{
        private TextField fieldIn;
        private Label errorOut;

        public FieldInputErrorOutputPair(TextField fieldIn, Label errorOut) {
            this.fieldIn = fieldIn;
            this.errorOut = errorOut;
        }

        public TextField getFieldIn() {
            return fieldIn;
        }
        public void setFieldIn(TextField fieldIn) {
            this.fieldIn = fieldIn;
        }
        public Label getErrorOut() {
            return errorOut;
        }
        public void setErrorOut(Label errorOut) {
            this.errorOut = errorOut;
        }
    }
}

