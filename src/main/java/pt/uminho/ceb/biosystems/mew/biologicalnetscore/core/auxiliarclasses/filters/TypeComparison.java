package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;


public class TypeComparison extends AbstractComparison<String> {

   protected String value;
   protected String input;


   public TypeComparison(String value) {
      this.value = value;
   }

   public boolean checkReady() {
      return this.input != null;
   }

   public boolean evaluate() {
      boolean res = this.value.equals(this.input);
      this.input = null;
      return res;
   }

   public void inputData(String data) {
      this.input = data;
   }
}
