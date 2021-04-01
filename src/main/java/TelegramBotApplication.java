import domain.ExchangeRate;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import parser.ExchangeRateParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TelegramBotApplication extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new TelegramBotApplication());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(false);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        ExchangeRate exchangeRate = new ExchangeRate();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    try {
                        sendMsg(message, "Welcome to Exchange Rate bot!");
                    } catch (Exception e) {
                        sendMsg(message, "Could not start bot...");
                    }
                    break;
                case "USD":
                    try {
                        sendMsg(message, ExchangeRateParser.getExchangeRate(message.getText(), exchangeRate));
                    } catch (IOException e) {
                        sendMsg(message, "Error while fetching USD!");
                    }
                    break;
                case "EUR":
                    try {
                        sendMsg(message, ExchangeRateParser.getExchangeRate("EUR", exchangeRate));
                    } catch (IOException e) {
                        sendMsg(message, "Error while fetching EUR!");
                    }
                    break;
                case "RUB":
                    try {
                        sendMsg(message, ExchangeRateParser.getExchangeRate("RUB", exchangeRate));
                    } catch (IOException e) {
                        sendMsg(message, "Error while fetching RUB!");
                    }
                    break;
                default:
                    sendMsg(message, "There is no such command found...");
            }
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirdRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("USD"));
        keyboardSecondRow.add(new KeyboardButton("EUR"));
        keyboardThirdRow.add(new KeyboardButton("RUB"));

        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);
        keyboardRowList.add(keyboardThirdRow);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public String getBotUsername() {
        return "ExchangeRateFreedomFinance_bot";
    }

    public String getBotToken() {
        return "1683489329:AAF4jqWXlj_atOnLTH7DdUdSL2ZJ9tDwYFA";
    }
}
