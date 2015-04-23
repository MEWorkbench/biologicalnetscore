package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;


public class DoubleValueBiggerThanComparison extends AbstractComparison<Double> {

   protected double value;
   protected double input;
   protected boolean ready = false;
   protected boolean forceTrue = false;


   public DoubleValueBiggerThanComparison(double value) {
      this.value = value;
   }

   public boolean checkReady() {
      return this.ready;
   }

   public boolean evaluate() {
      boolean res = this.value < this.input;
      this.ready = false;
      if(this.forceTrue) {
         res = true;
         this.forceTrue = false;
      }

      return res;
   }

   public void inputData(Double data) {
      this.input = data.doubleValue();
      this.ready = true;
   }

   public void forceTrue(boolean forceTrue) {
      this.forceTrue = forceTrue;
      this.ready = true;
   }
}
