package greeting;

public class Greeter {
    String formality;

    public String greet() {
        if (this.formality == null) {
            return "Hello.";
        }

        if (this.formality.equals("formal")) {
            return "Good evening, sir.";
        } else if (this.formality.equals("casual")) {
            return "Sup bro?";
        } else if (this.formality.equals("intimate")) {
            return "Hello Darling!";
        } else {
            return "Hello.";
        }
    }

    public void setFormality(String formality) {
        this.formality = formality;
    }
}
