package commands;

import com.automation.commands.GeneratePassword;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.automationanywhere.botcommand.data.impl.CredentialObject;

public class Test_GeneratePassword {

    private GeneratePassword command;

    @BeforeClass
    public void setup() {
        command = new GeneratePassword();
    }

    @Test
    public void shouldGeneratePasswordWithCorrectLength() {

        CredentialObject result = command.generatePassword(
                12.0,
                true,
                true,
                true,
                true,
                "!@#$%"
        );

        String password = result.get().getInsecureString();

        Assert.assertNotNull(password);
        Assert.assertEquals(password.length(), 12);
    }

    @Test
    public void shouldGenerateLowercaseOnlyPassword() {

        CredentialObject result = command.generatePassword(
                10.0,
                true,
                false,
                false,
                false,
                ""
        );

        String password = result.get().getInsecureString();

        Assert.assertTrue(password.matches("[a-z]{10}"));
    }

    @Test
    public void shouldGenerateUppercaseOnlyPassword() {

        CredentialObject result = command.generatePassword(
                10.0,
                false,
                true,
                false,
                false,
                ""
        );

        String password = result.get().getInsecureString();

        Assert.assertTrue(password.matches("[A-Z]{10}"));
    }

    @Test
    public void shouldGenerateNumbersOnlyPassword() {

        CredentialObject result = command.generatePassword(
                8.0,
                false,
                false,
                true,
                false,
                ""
        );

        String password = result.get().getInsecureString();

        Assert.assertTrue(password.matches("[0-9]{8}"));
    }

    @Test
    public void shouldGenerateSpecialCharactersOnlyPassword() {

        CredentialObject result = command.generatePassword(
                10.0,
                false,
                false,
                false,
                true,
                "@#"
        );

        String password = result.get().getInsecureString();

        Assert.assertTrue(password.matches("[@#]{10}"));
    }

    @Test
    public void shouldGeneratePasswordWithAllowedCharactersOnly() {

        CredentialObject result = command.generatePassword(
                20.0,
                true,
                true,
                true,
                true,
                "@#"
        );

        String password = result.get().getInsecureString();

        Assert.assertNotNull(password);
        Assert.assertEquals(password.length(), 20);

        Assert.assertTrue(
                password.matches("[a-zA-Z0-9@#]+"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenLengthIsZero() {

        command.generatePassword(
                0.0,
                true,
                true,
                true,
                true,
                "!@#"
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNoCharacterTypeSelected() {

        command.generatePassword(
                10.0,
                false,
                false,
                false,
                false,
                ""
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenLengthIsLessThanRequiredCategories() {

        command.generatePassword(
                2.0,
                true,
                true,
                true,
                false,
                ""
        );
    }

    @Test
    public void shouldUseDefaultSpecialCharactersWhenEmpty() {

        CredentialObject result = command.generatePassword(
                15.0,
                false,
                false,
                false,
                true,
                ""
        );

        String password = result.get().getInsecureString();

        Assert.assertNotNull(password);
        Assert.assertEquals(password.length(), 15);
    }

    @Test
    public void shouldGenerateDifferentPasswords() {

        CredentialObject result1 = command.generatePassword(
                16.0,
                true,
                true,
                true,
                true,
                "!@#"
        );

        CredentialObject result2 = command.generatePassword(
                16.0,
                true,
                true,
                true,
                true,
                "!@#"
        );

        String password1 = result1.get().getInsecureString();
        String password2 = result2.get().getInsecureString();

        Assert.assertNotEquals(password1, password2);
    }
}