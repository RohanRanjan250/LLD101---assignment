import java.util.*;

public class OnboardingService {
    private final StudentStore store;
    private final InputParser parser;
    private final InputValidator validator;
    private final OnboardingPrinter printer;

    public OnboardingService(StudentStore store) {
        this.store = store;
        this.parser = new InputParser();
        this.validator = new InputValidator();
        this.printer = new OnboardingPrinter();
    }

    public void registerFromRawInput(String raw) {
        System.out.println("INPUT: " + raw);

        Map<String, String> kv = parser.parse(raw);
        List<String> errors = validator.validate(kv);

        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(store.count());
        StudentRecord rec = new StudentRecord(id,
                kv.get("name"), kv.get("email"), kv.get("phone"), kv.get("program"));

        store.save(rec);
        printer.printSuccess(rec, store.count());
    }
}
