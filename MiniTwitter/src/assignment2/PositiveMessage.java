package assignment2;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PositiveMessage implements AdminAnalytics {

    ArrayList<String> positiveMessages;
    List<String> tokens; //  to look for certain words
    TotalMessage totalMessage;
    String positiveMessagePercent;

    public PositiveMessage(TotalMessage totalMessage) {
        positiveMessages = new ArrayList<>();
        tokens = new ArrayList<String>();
        addPositiveWords();
        this.totalMessage = totalMessage;
    }

    public void findPositiveWords(ArrayList<String> messageList) {

        String patternString = "\\b(" + String.join("|", tokens) + ")\\b";
        Pattern pattern = Pattern.compile(patternString);

        for (int i = 0; i < messageList.size(); i++) {
            String message = messageList.get(i).toLowerCase();
            Matcher matcher = pattern.matcher(message);

            while (matcher.find()) {
                positiveMessages.add(matcher.group(1));
            }
        }

        NumberFormat formatter = new DecimalFormat("#0.00");
        setPositiveMessagePercent(formatter.format((double) positiveMessages.size() / (double) messageList.size()));

    }

    public void addPositiveWords() {
        tokens.add("good");
        tokens.add("great");
        tokens.add("excellent");
        tokens.add("amazing");
        tokens.add("awesome");
        tokens.add("nice");
        tokens.add("sweet");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getPositiveMessagePercent() {
        return positiveMessagePercent;
    }

    public void setPositiveMessagePercent(String percent) {
        this.positiveMessagePercent = percent;
    }

}