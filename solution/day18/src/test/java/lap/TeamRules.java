package lap;

import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(packages = "lap", importOptions = ImportOption.DoNotIncludeTests.class)
public class TeamRules {
    @ArchTest
    private static final ArchRule no_getter_can_return_void =
            methods().that()
                    .haveNameMatching("get.*")
                    .should(notBeVoid())
                    .because("any method which gets something should actually return something");

    @ArchTest
    private static final ArchRule iser_haser_must_return_boolean =
            methods().that()
                    .haveNameMatching("is[A-Z].*").or()
                    .haveNameMatching("has[A-Z].*").should()
                    .haveRawReturnType(Boolean.class).orShould()
                    .haveRawReturnType(boolean.class)
                    .because("any method which fetch a state should actually return something (a boolean)");

    public static ArchCondition<JavaMethod> notBeVoid() {
        return new ArchCondition<>("not return void") {
            @Override
            public void check(JavaMethod method, ConditionEvents events) {
                final var matches = !"void".equals(method.getRawReturnType().getName());
                final var message = method.getFullName() + " returns " + method.getRawReturnType().getName();
                events.add(new SimpleConditionEvent(method, matches, message));
            }
        };
    }

    @ArchTest
    private static final ArchRule detect_consecutives_underscores =
            nameAnomaly(".*__.*", "consecutive underscores");

    @ArchTest
    private static final ArchRule detect_external_underscores =
            nameAnomaly("_.*|\\w+._", "external underscores");

    private static ArchRule nameAnomaly(String regex, String reason) {
        return fields().that()
                .haveNameMatching(regex)
                .should(notExist("not contain " + reason + "..."))
                .because("it ruins readability");
    }

    private static ArchCondition<? super JavaField> notExist(String reason) {
        return new ArchCondition<>(reason) {
            @Override
            public void check(JavaField field, ConditionEvents events) {
                final var message = field.getName() + " is not a valid name";
                events.add(new SimpleConditionEvent(field, false, message));
            }
        };
    }
}