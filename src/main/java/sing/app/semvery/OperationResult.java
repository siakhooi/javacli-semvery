package sing.app.semvery;

import java.util.ArrayList;
import java.util.List;

public class OperationResult {

    private int successCount = 0;
    private int failCount = 0;

    private List<OperationResultEntry> resultEntries = new ArrayList<>();

    public void addEntry(String value, String result, boolean success) {
        resultEntries.add(new OperationResultEntry(value, result));
        if (success)
            successCount++;
        else
            failCount++;
    }

    public List<OperationResultEntry> getResultEntries() {
        return this.resultEntries;
    }

    public ReturnValue getReturnValue(boolean any) {
        if (any)
            return successCount > 0 ? ReturnValue.OK : ReturnValue.NOT_OK;
        else
            return failCount == 0 ? ReturnValue.OK : ReturnValue.NOT_OK;
    }
}
