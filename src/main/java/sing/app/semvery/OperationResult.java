package sing.app.semvery;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class OperationResult {

  @Getter
  @Setter
  ReturnValue returnValue;

  ArrayList<OperationResultEntry> resultEntries = new ArrayList<>();

  public void addEntry(String value, String result) {
    resultEntries.add(new OperationResultEntry(value, result));
  }

  public ArrayList<OperationResultEntry> getResultEntries() {
    return this.resultEntries;
  }
}
