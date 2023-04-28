package util;

import Network.messages.Message;

public interface Observer{
    void update(Message message);
}