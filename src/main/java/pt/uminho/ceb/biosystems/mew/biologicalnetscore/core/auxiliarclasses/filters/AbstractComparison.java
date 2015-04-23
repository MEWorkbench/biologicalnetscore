package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;


public abstract class AbstractComparison<T> {

   public abstract void inputData(T var1);

   public abstract boolean checkReady();

   public abstract boolean evaluate();
}
