package com.automation.commands;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static com.automationanywhere.commandsdk.model.DataType.BOOLEAN;

import com.automationanywhere.botcommand.data.impl.CredentialObject;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.Execute;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Inject;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.model.DataType;
import com.automationanywhere.commandsdk.model.AttributeType;


/**
 * Generates a secure random password and returns it as a Credential variable.
 *
 * The generated password guarantees that at least one character from each
 * enabled category is included in the final password.
 */
@BotCommand
@CommandPkg(
        name = "GeneratePassword",
        label = "Generate Random Password",
        node_label = "generate a secure random password {{length}} characters long",
        description = "Generates a secure random password",
        return_required = true,
        return_label = "Random password",
        return_name = "randomPassword",
        return_type = DataType.CREDENTIAL,
        documentation_url = "",
        icon = "VaultIcon.png"
        
)
public class GeneratePassword {

    /**
     * Character sets used during password generation.
     */
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String DEFAULT_SPECIAL =
            "!@#$%^&*()_-+=<>?/{}[]|~";

    /**
     * Cryptographically secure random generator.
     */
    private static final SecureRandom RANDOM = new SecureRandom();

    @Execute
    public CredentialObject generatePassword(

            @Idx(index = "1", type = AttributeType.NUMBER)
            @Pkg(label = "Password Length") @NotEmpty
            @Inject
            Double length,

            @Idx(index = "2", type = AttributeType.BOOLEAN)
            @Pkg(label = "Use Lowercase Letters (a-z)", description = "The password must contain lowercase letters",
                default_value = "true", default_value_type = BOOLEAN
            )
            @Inject
            Boolean useLowercase,

            @Idx(index = "3", type = AttributeType.BOOLEAN)
            @Pkg(label = "Use Uppercase Letters (A-Z)", description = "The password must contain uppercase letters",
                default_value = "true", default_value_type = BOOLEAN
            )
            Boolean useUppercase,

            @Idx(index = "4", type = AttributeType.BOOLEAN)
            @Pkg(label = "Use Numbers (0-9)", description = "The password must contain numbers",
                default_value = "true", default_value_type = BOOLEAN
            )
            Boolean useNumbers,

            @Idx(index = "5", type = AttributeType.BOOLEAN)
            @Pkg(label = "Use Special Characters", description = "The password must contain special characters",
                default_value = "true", default_value_type = BOOLEAN
            )
            Boolean useSpecialCharacters,

            @Idx(index = "6", type = AttributeType.TEXT)
            @Pkg(label = "Special Characters", description = "(OPTIONAL) Enter allowed special characters. If blank, default special characters will be used. Default = "+DEFAULT_SPECIAL)
            String specialCharacters
    ) {

        int passwordLength = length.intValue();

        // Validate password length.
        if (passwordLength <= 0) {
            throw new IllegalArgumentException("Password length must be greater than 0.");
        }

        StringBuilder characterPool = new StringBuilder(); //Combined character pool used for random generation.
        List<Character> passwordChars = new ArrayList<>(); // Stores password characters before final assembly.

        // Add mandatory lowercase character.
        if (useLowercase) {
            characterPool.append(LOWERCASE);
            passwordChars.add(
                    LOWERCASE.charAt( RANDOM.nextInt(LOWERCASE.length()) ));
        }

        // Add mandatory uppercase character.
        if (useUppercase) { 
            characterPool.append(UPPERCASE);
            passwordChars.add(
                    UPPERCASE.charAt( RANDOM.nextInt(UPPERCASE.length()) ));
        }

        // Add mandatory numeric character.
        if (useNumbers) {
            characterPool.append(NUMBERS);
            passwordChars.add(
                    NUMBERS.charAt( RANDOM.nextInt(NUMBERS.length()) ));
        }

        // Add mandatory special character.
        if (useSpecialCharacters){
            // If user leaves blank, use default special chars
            if (specialCharacters == null || specialCharacters.isEmpty()) {
                specialCharacters = DEFAULT_SPECIAL;
            }

            characterPool.append(specialCharacters);
            passwordChars.add(
                    specialCharacters.charAt( RANDOM.nextInt(specialCharacters.length())));
        }

        // Ensure at least one character type is enabled.
        if (characterPool.length() == 0) {
            throw new IllegalArgumentException("At least one character type must be selected.");
        }

        // Ensure password length can satisfy all mandatory categories.
        int minimumRequiredLength = passwordChars.size();
        if (passwordLength < minimumRequiredLength) {

            throw new IllegalArgumentException("Password length must be at least " + minimumRequiredLength + " based on selected character types.");
        }

        // Fill remaining password length with random characters.
        while (passwordChars.size() < passwordLength) {

            int randomIndex = RANDOM.nextInt(characterPool.length());
            passwordChars.add( characterPool.charAt(randomIndex));
        }

        /*
         * Shuffle password using Fisher-Yates shuffle algorithm
         * to avoid predictable character positions.
         */
        for (int i = passwordChars.size() - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            Character temp = passwordChars.get(i);
            passwordChars.set(i, passwordChars.get(j));
            passwordChars.set(j, temp);
        }

        // Build final password string.
        StringBuilder generatedPassword = new StringBuilder();

        for (Character c : passwordChars) {
            generatedPassword.append(c);
        }
        
        // Return as SecureString credential output.
        return new CredentialObject(generatedPassword.toString());
    }
}