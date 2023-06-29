package assignment2;

// Interface for the visitor pattern in order to receive data

public interface Visitor {
    String visit(TotalUser userTotal);

    String visit(TotalGroup groupTotal);

    String visit(TotalMessage messageTotal);

    String visit(PositiveMessage positiveMessage);

}