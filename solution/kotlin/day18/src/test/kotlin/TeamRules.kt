import com.tngtech.archunit.core.domain.JavaField
import com.tngtech.archunit.core.domain.JavaMethod
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.ArchCondition
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.ConditionEvents
import com.tngtech.archunit.lang.SimpleConditionEvent
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import io.kotest.core.spec.style.StringSpec

class TeamRules : StringSpec({
    "no getter can return Unit" {
        ArchRuleDefinition.methods().that()
            .haveNameMatching("get.*")
            .should(notBeVoid())
            .because("any method which gets something should actually return something")
            .check()
    }

    "iser haser must return boolean" {
        ArchRuleDefinition.methods().that()
            .haveNameMatching("is[A-Z].*").or()
            .haveNameMatching("has[A-Z].*").should()
            .haveRawReturnType(Boolean::class.java).orShould()
            .haveRawReturnType(Boolean::class.javaPrimitiveType)
            .because("any method which fetch a state should actually return something (a boolean)")
            .check()
    }

    "detect consecutive underscores" {
        nameAnomaly(".*__.*", "consecutive underscores").check()
    }

    "detect external underscores" {
        nameAnomaly("_.*|w+._", "external underscores").check()
    }
})

private val classes = ClassFileImporter().withImportOption(ImportOption.DoNotIncludeTests()).importPackages("lap")
private fun ArchRule.check() = this.check(classes)

private fun notBeVoid(): ArchCondition<JavaMethod> = object : ArchCondition<JavaMethod>("not return void") {
    override fun check(method: JavaMethod, events: ConditionEvents) {
        val matches = "void" != method.rawReturnType.name
        val message = method.fullName + " returns " + method.rawReturnType.name
        events.add(SimpleConditionEvent(method, matches, message))
    }
}

private fun nameAnomaly(regex: String, reason: String): ArchRule {
    return ArchRuleDefinition.fields().that()
        .haveNameMatching(regex)
        .should(notExist("not contain $reason..."))
        .because("it ruins readability")
}

private fun notExist(reason: String): ArchCondition<in JavaField> {
    return object : ArchCondition<JavaField>(reason) {
        override fun check(field: JavaField, events: ConditionEvents) {
            val message = field.name + " is not a valid name"
            events.add(SimpleConditionEvent(field, false, message))
        }
    }
}