package health.ere.ps.validation.fhir.bundle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ca.uhn.fhir.validation.ValidationResult;

public class PrescriptionBundleValidatorTest {

    @Test
    public void testKBV() throws IOException {
        PrescriptionBundleValidator prescriptionBundleValidator = new PrescriptionBundleValidator();

        List<ValidationResult> validationResults = Files.list(Paths.get("src/test/resources/"))
            .filter(Files::isRegularFile)
            .filter(f -> f.toString().endsWith("0428d416-149e-48a4-977c-394887b3d85c.xml"))
            .map(f -> {
                try {
                    return prescriptionBundleValidator.validateResource(Files.readString(f), true);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .collect(Collectors.toList());
        Assertions.assertTrue(validationResults.stream().filter(o -> !o.isSuccessful()).count() == 0, "Sample simplifier.net bundle " +
                "has been successfully validated.");
    }

}