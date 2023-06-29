package assignment2;

// Visitor that visits users and user groups

public class UserVisitor implements Visitor {

    @Override
    public String visit(TotalUser totalUser) {
        return String.valueOf(totalUser.getUserTotal());
    }

    @Override
    public String visit(TotalGroup totalGroup) {
        return String.valueOf(totalGroup.getGroupTotal());
    }

    @Override
    public String visit(TotalMessage totalMessage) {
        return String.valueOf(totalMessage.getMessageTotal());
    }

    @Override
    public String visit(PositiveMessage positiveMessage) {
        return positiveMessage.getPositiveMessagePercent();
    }


}