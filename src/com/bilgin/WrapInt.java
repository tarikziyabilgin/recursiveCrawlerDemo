package com.bilgin;

public class WrapInt {

    public WrapInt(int totalValue, int filteredCount){
       // this.totalValue = totalValue;
        this.filteredCount = filteredCount;
    }
   // private int totalValue;
    private int filteredCount;

   /* public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }*/

    public int getFilteredCount() {
        return filteredCount;
    }

    public void setFilteredCount(int filteredCount) {
        this.filteredCount = filteredCount;
    }
}
