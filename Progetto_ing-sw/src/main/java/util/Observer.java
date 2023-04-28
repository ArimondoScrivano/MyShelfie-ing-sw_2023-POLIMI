package util;

import Network.RMI.messages.Message;

public interface Observer{
    void update(Message message);
}