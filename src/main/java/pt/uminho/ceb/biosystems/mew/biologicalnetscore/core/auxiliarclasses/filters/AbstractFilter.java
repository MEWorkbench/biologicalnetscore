package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;


public abstract class AbstractFilter {

   protected AbstractComparison[] comparisons;
   protected int maskSize;


   public boolean[] getMask() {
      boolean[] mask = new boolean[this.maskSize];

      for(int a = 0; a < this.maskSize; ++a) {
         mask[a] = this.evaluateRow(a);
      }

      return mask;
   }

   protected boolean evaluateRow(int row) {
      boolean res = true;
      this.giveData(row);

      for(int a = 0; a < this.comparisons.length; ++a) {
         if(this.comparisons[a].checkReady()) {
            res = res && this.comparisons[a].evaluate();
         } else {
            res = false;
         }
      }

      return res;
   }

   protected abstract void giveData(int var1);
}
